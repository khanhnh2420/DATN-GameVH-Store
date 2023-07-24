app.controller("ProfileController", function (AccountService, LocationService, $scope, $window, $http) {
    $scope.account = {}; // Biến lưu thông tin account 
    $scope.username = null;

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
        console.log($scope.location)
        provinceId = $scope.location.province;
        // Gọi hàm để lấy dữ liệu quận/huyện của thành phố đã chọn
        $scope.callApiDistrict(hostLocation + "p/" + provinceId + "?depth=2");
        printResult();
        $scope.selectedDistrict = true;
        $scope.selectedWard = false;
    };

    $scope.updateDistrict = function () {
        districtId = $scope.location.district
        // Gọi hàm để lấy dữ liệu Xã/Phường của quận/huyện đã chọn
        $scope.callApiWard(hostLocation + "d/" + districtId + "?depth=2");
        printResult();
        $scope.selectedWard = true;
    };

    $scope.updateWard = function () {
        wardId = $scope.location.ward
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
});
