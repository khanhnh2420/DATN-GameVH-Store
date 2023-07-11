// Angular js
app.controller("CheckoutController", function (MomoService, $scope, $http, $window) {
	$scope.cart = [];
	$scope.TotalPrice = 0;
	$scope.paymentMethod = "cod";

	function getCartData() {
		$scope.cart = ($window.localStorage.getItem("carts") != null) ? JSON.parse($window.localStorage.getItem("carts")) : [];
		console.log($scope.cart)
		$scope.TotalPrice = 0;
		if ($scope.cart.length > 0) {
			$scope.cart.forEach(function (data) {
				$scope.TotalPrice += (data.salePrice - (data.salePrice * data.offer)) * data.Qty;
			});
		}
	}
	$scope.load_all = function () {
		console.log("check")
		getCartData();
	};

	$scope.load_all();

	//Show thông báo khi mặc định chạy trang web (có thể có hoặc không)
	// $scope.message = ($window.sessionStorage.getItem("messageCheckout") != null) ? JSON.parse($window.sessionStorage.getItem("messageCheckout")) : null;// Trạng thái
    // $window.sessionStorage.removeItem("messageCheckout");
	
	// if($scope.message) {
	// 	if ($scope.message.success) {
	// 		
	// 	} else {
	// 		
	// 	}
	// 	$scope.message = null;
	// }

	$scope.checkout = function() {
		if($scope.paymentMethod === "cod") {
			// thánh toán khi nhận hàng

		} else if($scope.paymentMethod === "momo") {
			// thánh toán qua momo
			var date = new Date().getTime();
			var orderId = "HD" + date;
			var returnUrl = "http://localhost:3000/checkout";
			var totalPrice = $scope.TotalPrice;
			var OrerInfo = "Thanh toán đơn hàng " + orderId;
			$scope.createMomoData(orderId, returnUrl, totalPrice, OrerInfo);
		} else if($scope.paymentMethod === "vnpay") {
			// thánh toán qua vnpay

		} else if($scope.paymentMethod === "paypal") {
			// thánh toán qua paypal

		}
	}


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
			// Hiển thị thông báo khi thanh toán
			if($scope.momo) {
				var statusCheckout = {
					"success": "Thanh toán đơn hàng " + orderId + "thành công!",
					"error": null
				}
				$window.sessionStorage.setItem("messageCheckout", JSON.stringify(statusCheckout));
				$window.location.href = $scope.momo.payUrl;
			} else {
				var statusCheckout = {
					"success": null,
					"error": "Lỗi khi thanh toán qua momo!"
				}
				$window.sessionStorage.setItem("messageCheckout", JSON.stringify(statusCheckout));
			}
        }).catch(function (error) {
            console.error('Lỗi khi tạo order Momo:', error);
        });
    }
});
