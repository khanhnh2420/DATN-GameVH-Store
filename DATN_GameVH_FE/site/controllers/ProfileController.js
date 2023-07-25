app.controller("ProfileController", function (AccountService, LocationService, OrderService, OrderDetailService, CouponOwnerService, PageService, $scope, $window, $http) {
    $scope.account = {}; // Biến lưu thông tin account 
    $scope.username = null;
    $scope.orders = [];
    $scope.order = {};
    $scope.coupons = [];

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
    $scope.location = {};


    /*----CHECK LOGIN----*/
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
        if ($scope.account) {
            LocationService.getByUsername($scope.account.username).then(function (location) {
                $scope.locations = location.data;
            }).catch(function (error) {
                console.error('Lỗi khi lấy location:', error);
            });

            OrderService.getByUsername($scope.account.username).then(function (order) {
                $scope.orders = order.data;
                if ($scope.orders) {
                    $scope.orders.account = null;
                    $scope.orders.sort(function (a, b) {
                        return new Date(b.createDate).getTime() - new Date(a.createDate).getTime();
                    });
                    $scope.getListOrderOnPage();
                }
            }).catch(function (error) {
                console.error('Lỗi khi lấy danh sách order:', error);
            });

            CouponOwnerService.getCouponByUsername($scope.account.username).then(function (coupon) {
                $scope.coupons = coupon.data;
                $scope.getListCouponOnPage();
            }).catch(function (error) {
                console.error('Lỗi khi lấy list coupon:', error);
            });
        }
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

    /*----END CHECK LOGIN----*/

    /*----RATING----*/
    $scope.stars = [
        { filled: false },
        { filled: false },
        { filled: false },
        { filled: false },
        { filled: false }
    ];

    $scope.currentRating = 3;

    // Cập nhật trạng thái các ngôi sao dựa vào giá trị currentRating
    for (var i = 0; i < $scope.currentRating; i++) {
        $scope.stars[i].filled = true;
    }

    $scope.setRating = function (star) {
        // Đánh giá sao khi người dùng nhấp vào
        var index = $scope.stars.indexOf(star);
        for (var i = 0; i < $scope.stars.length; i++) {
            $scope.stars[i].filled = i <= index;
        }

        // Cập nhật thông tin đánh giá hiện tại
        $scope.currentRating = index + 1;
    };

    $scope.hoverStar = function (star) {
        // Đánh giá sao khi người dùng di chuột qua
        var index = $scope.stars.indexOf(star);
        for (var i = 0; i <= index; i++) {
            $scope.stars[i].hovered = true;
        }
        for (var i = index + 1; i < $scope.stars.length; i++) {
            $scope.stars[i].hovered = false;
        }
    };

    $scope.resetHover = function () {
        // Reset trạng thái khi di chuột ra khỏi đánh giá
        for (var i = 0; i < $scope.stars.length; i++) {
            $scope.stars[i].hovered = false;
        }
    };
    /*----END RATING----*/

    /*----CHANGE TAB----*/
    // Change tab điều hướng
    $scope.selectedTab = 'tab-dashboard';

    $scope.changeTabRedirect = function (tab) {
        // Cập nhật tab đang được chọn khi người dùng nhấn vào tab
        $scope.selectedTab = tab;
    };

    // Change tab Modal
    $scope.activeTab = 'order'; // Đặt tab mặc định là "Thông tin đơn hàng"

    $scope.changeTab = function (event, tabName) {
        event.preventDefault(); // Ngăn chặn sự kiện mặc định khi click vào tab
        $scope.activeTab = tabName; // Cập nhật tab đang được chọn
    };
    /*----END CHANGE TAB----*/


    /*----MODAL----*/
    $scope.fillDataModal = function (locationId, type) {
        $('#address-modal').modal('show');
        if (type == "add") {
            $scope.title = "THÊM ĐỊA CHỈ MỚI";
            $scope.button = "THÊM";
            $scope.clearModal();
        } else {
            $scope.title = "CHỈNH SỬA ĐỊA CHỈ";
            $scope.button = "LƯU THAY ĐỔI";

            LocationService.getById(locationId).then(function (location) {
                $scope.locationForm = location.data;

                $scope.locationForm.province = parseInt($scope.locationForm.province);
                $scope.locationForm.district = parseInt($scope.locationForm.district);
                $scope.locationForm.ward = parseInt($scope.locationForm.ward);

                $scope.updateLocation();
            }).catch(function (error) {
                console.error('Lỗi lấy location:', error);
            });
        }
    }

    $scope.clearModal = function () {
        $scope.selectedProvince = true;
        $scope.selectedDistrict = false;
        $scope.selectedWard = false;

        $scope.locationForm = {};
    }

    $scope.openModalOrder = function (orderId) {
        $('#order-modal').modal('show');
        if (orderId) {
            OrderService.getById(orderId).then(function (order) {
                $scope.order = order.data;
                if ($scope.order) {
                    OrderDetailService.getByOrderDataId($scope.order.id).then(function (orderDetail) {
                        $scope.orderDetails = orderDetail.data;
                    }).catch(function (error) {
                        console.error('Lỗi khi lấy orderDetail:', error);
                    });
                }
            }).catch(function (error) {
                console.error('Lỗi khi lấy order:', error);
            });
        }
    };

    /*----END MODAL----*/

    /*----CHIA TRANG----*/
    // Page Order
    $scope.itemsPerPage = 6; // Số lượng phần tử hiển thị trên mỗi trang
    $scope.currentPage = 1; // Trang hiện tại
    $scope.totalPages = 0;
    $scope.displayedItems = [];
    $scope.getListOrderOnPage = function () {
        // Tổng số trang
        $scope.totalPages = PageService.calctotalPage($scope.itemsPerPage, $scope.orders);

        // Lấy danh sách sản phẩm cho trang hiện tại
        $scope.displayedItems = PageService.getDisplayedItems($scope.itemsPerPage, $scope.currentPage, $scope.orders);

        // Danh sách số trang hiển thị
        $scope.getPageRange = function (totalPages) {
            const maxPages = 10; // Số trang tối đa hiển thị liên tiếp
            const currentPage = $scope.currentPage;

            if (totalPages <= maxPages) {
                return Array(totalPages).fill().map((_, index) => index + 1);
            } else {
                let pageRange = [];
                if (currentPage <= 3) {
                    pageRange = Array(5).fill().map((_, index) => index + 1);
                    pageRange.push('...');
                    pageRange.push(totalPages);
                } else if (currentPage >= totalPages - 2) {
                    pageRange.push(1);
                    pageRange.push('...');
                    pageRange = pageRange.concat(Array(5).fill().map((_, index) => totalPages - 4 + index));
                } else {
                    pageRange.push(1);
                    pageRange.push('...');
                    pageRange = pageRange.concat(Array(3).fill().map((_, index) => currentPage - 1 + index));
                    pageRange.push('...');
                    pageRange.push(totalPages);
                }
                return pageRange;
            }
        };

    };

    $scope.previousPage = function () {
        if ($scope.currentPage > 1) {
            $scope.currentPage--;
        } else {
            $scope.currentPage = $scope.totalPages;
        }
        $scope.getListOrderOnPage();
    };

    $scope.nextPage = function () {
        if ($scope.currentPage < $scope.totalPages) {
            $scope.currentPage++;
        } else {
            $scope.currentPage = 1;
        }
        $scope.getListOrderOnPage();
    };

    $scope.get_ByPage = function (page) {
        $scope.currentPage = page;
        $scope.getListOrderOnPage();
    };

    // Page coupon
    $scope.itemsPerPageCoupon = 6; // Số lượng phần tử hiển thị trên mỗi trang
    $scope.currentPageCoupon = 1; // Trang hiện tại
    $scope.totalPageCoupons = 0;
    $scope.displayedItemCoupons = [];
    $scope.getListCouponOnPage = function () {
        // Tổng số trang
        $scope.totalPageCoupons = PageService.calctotalPage($scope.itemsPerPageCoupon, $scope.coupons);

        // Lấy danh sách sản phẩm cho trang hiện tại
        $scope.displayedItemCoupons = PageService.getDisplayedItems($scope.itemsPerPageCoupon, $scope.currentPageCoupon, $scope.coupons);

        // Danh sách số trang hiển thị
        $scope.getPageRangeCoupon = function (totalPages) {
            const maxPages = 10; // Số trang tối đa hiển thị liên tiếp
            const currentPage = $scope.currentPageCoupon;

            if (totalPages <= maxPages) {
                return Array(totalPages).fill().map((_, index) => index + 1);
            } else {
                let pageRange = [];
                if (currentPage <= 3) {
                    pageRange = Array(5).fill().map((_, index) => index + 1);
                    pageRange.push('...');
                    pageRange.push(totalPages);
                } else if (currentPage >= totalPages - 2) {
                    pageRange.push(1);
                    pageRange.push('...');
                    pageRange = pageRange.concat(Array(5).fill().map((_, index) => totalPages - 4 + index));
                } else {
                    pageRange.push(1);
                    pageRange.push('...');
                    pageRange = pageRange.concat(Array(3).fill().map((_, index) => currentPage - 1 + index));
                    pageRange.push('...');
                    pageRange.push(totalPages);
                }
                return pageRange;
            }
        };

    };

    $scope.previousPageCoupon = function () {
        if ($scope.currentPageCoupon > 1) {
            $scope.currentPageCoupon--;
        } else {
            $scope.currentPageCoupon = $scope.totalPageCoupons;
        }
        $scope.getListCouponOnPage();
    };

    $scope.nextPageCoupon = function () {
        if ($scope.currentPageCoupon < $scope.totalPageCoupons) {
            $scope.currentPageCoupon++;
        } else {
            $scope.currentPageCoupon = 1;
        }
        $scope.getListCouponOnPage();
    };

    $scope.get_ByPageCoupon = function (page) {
        $scope.currentPageCoupon = page;
        $scope.getListCouponOnPage();
    };

    /*----END CHIA TRANG----*/

    /*----API LOCATION---*/
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
        provinceId = $scope.locationForm.province;
        // Gọi hàm để lấy dữ liệu quận/huyện của thành phố đã chọn
        $scope.callApiDistrict(hostLocation + "p/" + provinceId + "?depth=2");
        printResult();
        $scope.selectedDistrict = true;
        $scope.selectedWard = false;
        $scope.locationForm.district = null;
        $scope.locationForm.ward = null;
    };

    $scope.updateDistrict = function () {
        districtId = $scope.locationForm.district
        // Gọi hàm để lấy dữ liệu Xã/Phường của quận/huyện đã chọn
        $scope.callApiWard(hostLocation + "d/" + districtId + "?depth=2");
        printResult();
        $scope.selectedWard = true;
    };

    $scope.updateWard = function () {
        wardId = $scope.locationForm.ward
        printResult();
    };

    $scope.updateLocation = function () {
        $scope.selectedDistrict = true;
        $scope.selectedWard = true;

        // Gọi API để lấy danh sách tỉnh/thành phố
        $scope.callApiGetAll(hostLocation);
        // Gọi API để lấy danh sách quận/huyện
        $scope.getLocation(hostLocation + "d/", $scope.districts);
        // Gọi API để lấy danh sách xã/phường
        $scope.getLocation(hostLocation + "w/", $scope.wards);
    }

    var printResult = () => {
        if (provinceId != "" && districtId != "" && wardId != "") {
            let result = $("#ward option:selected").text() + ", " + $("#district option:selected").text() + ", " + $("#province option:selected").text();
            $scope.selectedLocation = result;
        }
    }
    /*----END API LOCATION---*/

}).filter('vndFormat', function () {
    // Filter định dạng tiền tệ
    return function (input) {
        if (!input) return '';
        var number = parseFloat(input);
        if (isNaN(number)) return input;
        var formattedNumber = number.toLocaleString('vi-VN');
        formattedNumber += ' VND';
        return formattedNumber;
    };
}).filter('dateFormat', function () {
    // Filter định dạng ngày tháng năm
    return function (dateString) {
        var date = new Date(dateString);
        var day = date.getDate();
        var month = date.getMonth() + 1;
        var year = date.getFullYear();
        day = (day < 10) ? '0' + day : day;
        month = (month < 10) ? '0' + month : month;
        var formattedDate = day + '/' + month + '/' + year;
        return formattedDate;
    };
});