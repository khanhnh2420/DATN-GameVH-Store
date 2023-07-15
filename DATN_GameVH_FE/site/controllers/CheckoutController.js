// Angular js
app.controller("CheckoutController", function (AccountService, CouponOwnerService, MomoService, IPService, ZaloPayService, VNPayService, $scope, $window, $http) {
	$scope.cart = [];
	$scope.TotalPrice = 0;
	$scope.paymentMethod = "cod";
	$scope.form = {};
	// Validate coupon
	$scope.isValidCoupon = false;
	$scope.isValidMinSpendCoupon = false;
	$scope.isValidDateCoupon = false;
	$scope.DateCouponError = "";
	// Location
	const hostLocation = "https://provinces.open-api.vn/api/";
	$scope.selectedDistrict = false;
	$scope.selectedWard = false;
	$scope.provinces = [];
	$scope.districts = [];
	$scope.wards = [];
	var provinceId;
	var wardId;
	var districtId;

	$scope.account = {}; // Biến lưu thông tin account 
	$scope.username = null;

	// Trở về trang đăng nhập
	$scope.logout = function () {
		$window.localStorage.removeItem("username");
		$window.sessionStorage.removeItem("username");
		$window.sessionStorage.setItem("messageLogin", "Bạn đã đăng xuất!");
		$window.location.href = 'login.html';
	};

	AccountService.checkLogin().then(function (account) {
		// Người dùng đã đăng nhập
		$scope.account = account;
	}).catch(function (error) {
		// Người dùng chưa đăng nhập hoặc có lỗi
		console.error('Lỗi đăng nhập hoặc chưa đăng nhập:', error);
		if (error === "Người dùng chưa đăng nhập") {
			// Xử lý logic khi người dùng chưa đăng nhập
			$window.localStorage.removeItem("username");
			$window.sessionStorage.removeItem("username");
			$window.sessionStorage.setItem("messageLogin", "Vui lòng đăng nhập!");
			$window.location.href = 'login.html';
		}
	});

	function getCartData() {
		$scope.cart = ($window.localStorage.getItem("carts") != null) ? JSON.parse($window.localStorage.getItem("carts")) : [];
		$scope.TotalPrice = 0;
		if ($scope.cart.length > 0) {
			$scope.cart.forEach(function (data) {
				$scope.TotalPrice += (data.salePrice - (data.salePrice * data.offer)) * data.Qty;
			});
		}
	}
	$scope.load_all = function () {
		getCartData();
	};

	$scope.load_all();

	// Check nhập mã giảm giá
	$scope.checkCoupon = function () {
		$scope.updateStatusCoupon();
		if ($scope.form.coupon && $scope.account) {
			CouponOwnerService.getCoupon($scope.account.username, $scope.form.coupon).then(function (coupon) {
				$scope.coupon = coupon.data;
				if ($scope.coupon) {
					var today = new Date();
					// Kiểm tra xem coupon còn hạn hay không ?
					if (today < new Date($scope.coupon.mfgDate)) {
						$scope.DateCouponError = "Mã giảm giá chưa đến ngày áp dụng!";
						$scope.isValidDateCoupon = true;
						$scope.coupon = null;
						getCartData();
					} else if (today > new Date($scope.coupon.expDate)) {
						$scope.DateCouponError = "Mã giảm giá đã hết hạn!";
						$scope.isValidDateCoupon = true;
						$scope.coupon = null;
						getCartData();
					} else {
						// Lấy tổng tiền
						$scope.cart = ($window.localStorage.getItem("carts") != null) ? JSON.parse($window.localStorage.getItem("carts")) : [];
						$scope.TotalPrice = 0;
						if ($scope.cart.length > 0) {
							$scope.cart.forEach(function (data) {
								$scope.TotalPrice += (data.salePrice - (data.salePrice * data.offer)) * data.Qty;
							});
						}
						// Kiểm tra tổng tiền với đơn giá tối thiểu
						if ($scope.TotalPrice >= $scope.coupon.minSpend) {
							// Tính lại tổng tiền
							if ($scope.TotalPrice > $scope.coupon.value) {
								$scope.TotalPrice -= $scope.coupon.value;
							} else {
								$scope.TotalPrice = 0;
							}
						} else {
							$scope.isValidMinSpendCoupon = true;
							$scope.coupon = null;
							getCartData();
						}
					}
				} else {
					$scope.isValidCoupon = true;
					$scope.coupon = null;
					getCartData();
				}
			}).catch(function (error) {
				console.error('Lỗi khi lấy thông tin mã giảm giá:', error);
			});
		} else {
			$scope.isValidCoupon = true;
			$scope.coupon = null;
			getCartData();
		}
	}

	$scope.updateStatusCoupon = function () {
		$scope.isValidCoupon = false;
		$scope.isValidMinSpendCoupon = false;
		$scope.isValidDateCoupon = false;
		$scope.DateCouponError = "";
	};

	$scope.clearCoupon = function () {
		$scope.updateStatusCoupon();

		$scope.coupon = null;
		getCartData();
	};

	$scope.checkout = function () {
		if ($scope.orderForm.$valid) {
			// Xử lý logic khi form đã được điền đúng

			// Ghép chuỗi địa chỉ nhận hàng
			$scope.form.address = $scope.form.address + ", " + $scope.selectedLocation;

			// Tạo data call API
			// Tạo một đối tượng ngày hiện tại
			var today = new Date();

			// Lấy thông tin ngày, tháng, năm
			var year = today.getFullYear();
			var month = (today.getMonth() + 1).toString().padStart(2, '0'); // Tháng trong JavaScript bắt đầu từ 0
			var day = today.getDate().toString().padStart(2, '0');

			// Kết hợp thông tin ngày, tháng, năm thành chuỗi định dạng yêu cầu
			var formattedDate = year + '-' + month + '-' + day;

			var OrderId = "HD" + today.getTime() + Math.floor(Math.random() * 1000);
			var OrderIdLimited = OrderId.substring(0, 14);
			$scope.data = {
				"orderId": OrderIdLimited,
				"fullname": $scope.form.fullname,
				"createDate": formattedDate,
				"address": $scope.form.address,
				"paymentType": $scope.paymentMethod,
				"shippingFee": 0,
				"couponCode": "",
				"email": $scope.form.email,
				"phone": $scope.form.phone,
				"orderStatus": "Đang chờ xử lý",
				"paymentStatus": false,
				"note": $scope.form.note || "",
				"totalPrice": $scope.TotalPrice,
				"qty": $scope.cart.length,

			}

			console.log($scope.data)

			// if ($scope.paymentMethod === "cod") {
			// 	// thánh toán khi nhận hàng

			// } else if ($scope.paymentMethod === "momo") {
			// 	// thánh toán qua momo
			// 	var date = new Date().getTime();
			// 	var orderId = "HD" + date;
			// 	var returnUrl = "http://localhost:3000/checkoutResult";
			// 	var totalPrice = $scope.TotalPrice;
			// 	var OrerInfo = "Thanh toán đơn hàng " + orderId;
			// 	$scope.createMomoData(orderId, returnUrl, totalPrice, OrerInfo);
			// } else if ($scope.paymentMethod === "vnpay") {
			// 	// thánh toán qua vnpay
			// 	var totalPrice = $scope.TotalPrice;
			// 	IPService.getIPAddress()
			// 		.then(function (ipAddress) {
			// 			var userIP = ipAddress.data;
			// 			VNPayService.createOrder(totalPrice, userIP.ip).then(function (resp) {
			// 				console.log(resp)
			// 				$scope.zalopay = resp.data;
			// 				// Hiển thị thông báo khi thanh toán
			// 				if ($scope.zalopay) {
			// 					// $window.sessionStorage.setItem("messageCheckout", JSON.stringify(statusCheckout));
			// 					$window.location.href = $scope.zalopay.url;
			// 				} 
			// 			})
			// 		})
			// 		.catch(function (error) {
			// 			console.log('Error:', error);
			// 		});

			// } else if ($scope.paymentMethod === "paypal") {
			// 	// thánh toán qua paypal

			// }
		}
	}

	// $scope.zaloPayData = {
	// 	appUser: "ZaloPayDemo",
	// 	appTime: 1660717311101,
	// 	amount: 10000,
	// 	appTransID: "220817_1660717311101",
	// 	bankCode: "zalopayapp",
	// 	embedData: "{}",
	// 	items: [],
	// 	callbackUrl: "<http://localhost:3000/checkout>",
	// 	description: "ZaloPayDemo - Thanh toán cho đơn hàng #220817_1660717311101",
	// };

	// $scope.createZaloPayData = function () {
	// 	ZaloPayService.createPayment($scope.zaloPayData)
	// 		.then(function (response) {
	// 			// Xử lý kết quả từ API backend
	// 			if (response.data && response.data.return_code === 1) {
	// 				// Giao dịch thành công
	// 				console.log("Payment successful");
	// 			} else {
	// 				// Giao dịch thất bại
	// 				console.log("Payment failed");
	// 			}
	// 		})
	// 		.catch(function (error) {
	// 			// Xử lý lỗi
	// 			console.error("Error:", error);
	// 		});
	// };

	/*Tạo data cho momo
	inpOrderId: String  - Mã hóa đơn momo
	inpReturnUrl : String  - link trả về khi thanh toán
	inpAmount : Double  - tổng tiền của đơn hàng
	inpOrderInfo : String  - Mô tả của đơn hàng
	*/
	$scope.createMomoData = function (inpOrderId, inpReturnUrl, inpAmount, inpOrderInfo) {
		// Thông tin của momo business
		var accessKey = "hglxIRUGLnBd5h50";
		var partnerCode = "MOMOX7V920220806";
		var secretKey = "UMJQIJnOpNPnzQrDtWcRdLYu4oVt4UrV";
		var partnerName = "GamesVH";

		var date = new Date().getTime();

		// Thông tin của đơn hàng
		var requestId = date + "id";
		var orderId = inpOrderId; // Mã hóa đơn
		var autoCapture = true;
		var requestType = "captureWallet";
		var notifyUrl = inpReturnUrl;
		var returnUrl = inpReturnUrl; // thanh toán thành công về đâu
		var amount = inpAmount; // Giá đơn hàng
		var orderInfo = inpOrderInfo; // Thông tin thanh toán
		var extraData = "ew0KImVtYWlsIjogImh1b25neGRAZ21haWwuY29tIg0KfQ==";
		var lang = "vi";

		// Tạo signature
		var signature = "accessKey=" + accessKey + "&amount=" + amount + "&extraData=" + extraData + "&ipnUrl=" + notifyUrl + "&orderId=" + orderId + "&orderInfo=" + orderInfo + "&partnerCode=" + partnerCode + "&redirectUrl=" + returnUrl + "&requestId=" + requestId + "&requestType=" + requestType;
		var hash = CryptoJS.HmacSHA256(signature, secretKey);
		signature = CryptoJS.enc.Hex.stringify(hash);

		// Tạo data gửi về cho server
		var data = {
			"partnerCode": partnerCode,
			"partnerName": partnerName,
			"storeId": partnerCode,
			"requestType": requestType,
			"ipnUrl": notifyUrl,
			"redirectUrl": returnUrl,
			"orderId": orderId,
			"amount": amount,
			"lang": lang,
			"autoCapture": autoCapture,
			"orderInfo": orderInfo,
			"requestId": requestId,
			"extraData": extraData,
			"signature": signature
		}

		// Call API Momo
		MomoService.createOrder(data).then(function (response) {
			$scope.momo = response.data;
			$window.location.href = $scope.momo.payUrl;
		}).catch(function (error) {
			console.error('Lỗi khi tạo order Momo:', error);
		});
	}

	// API Location
	$scope.getLocation = function (api, scopeVariable) {
		$http.get(api)
			.then(function (response) {
				angular.copy(response.data, scopeVariable);
			})
	};

	$scope.callApiGetAll = function (api) {
		$http.get(api)
			.then(function (response) {
				$scope.provinces = response.data;
			}).catch(function (error) { });
	};

	$scope.callApiGetAll(hostLocation + "?depth=1");

	$scope.callApiDistrict = function (api) {
		$http.get(api)
			.then(function (response) {
				$scope.districts = response.data.districts;
			}).catch(function (error) { });;
	};

	$scope.callApiWard = function (api) {
		$http.get(api)
			.then(function (response) {
				$scope.wards = response.data.wards;
			}).catch(function (error) { });;
	};

	$scope.updateProvince = function () {
		provinceId = $scope.form.province;
		// Gọi hàm để lấy dữ liệu quận/huyện của thành phố đã chọn
		$scope.callApiDistrict(hostLocation + "p/" + provinceId + "?depth=2");
		printResult();
		$scope.selectedDistrict = true;
		$scope.selectedWard = false;
	};

	$scope.updateDistrict = function () {
		districtId = $scope.form.district
		// Gọi hàm để lấy dữ liệu Xã/Phường của quận/huyện đã chọn
		$scope.callApiWard(hostLocation + "d/" + districtId + "?depth=2");
		printResult();
		$scope.selectedWard = true;
	};

	$scope.updateWard = function () {
		wardId = $scope.form.ward
		printResult();
	};

	var printResult = () => {
		if (provinceId != "" && districtId != "" && wardId != "") {
			let result = $("#ward option:selected").text() + ", " + $("#district option:selected").text() + ", " + $("#province option:selected").text();
			$scope.selectedLocation = result;
		}
	}
});
