app.controller("LoginController", function (AccountService, BcryptService, $scope, $window) {
    $scope.account = {}; // Thông tin người dùng nhập từ form
    $scope.user = {}; // Thông tin người dùng lấy từ Server
    $scope.message; // Trạng thái
    $scope.rememberMe; // Lưu đăng nhập

    // Trở về trang đăng nhập
    $scope.redirectToLogin = function () {
        $window.location.href = 'login.html';
    };

    $scope.login = function () {
        // Reset biến mỗi lần bấm đăng nhập
        $scope.LoginForm.$submitted = true;
        $scope.isValidPassword = false;
        $scope.message = "";

        if ($scope.LoginForm.$invalid) {
            // Form không hợp lệ, xử lý hiển thị lỗi
            return;
        } else {
            // Kiểm tra thông tin người dùng nhập
            if ($scope.account != null && $scope.account != undefined && $scope.account.username != null &&
                $scope.account.username != undefined && $scope.account.password != null && $scope.account.password != undefined) {

                // Call API lấy thông tin người dùng theo username
                AccountService.getByUsername($scope.account.username).then(function (user) {
                    $scope.user = user.data; // Thông tin user từ Server
                    if ($scope.user != null && $scope.user != undefined && $scope.user != "") {
                        // So sánh password người dùng nhập với password đã mã hóa 
                        BcryptService.compare($scope.account.password, $scope.user.password).then(function (isComparePassword) {
                            if (!$scope.user.status) {
                                // Kiểm tra tài khoản đã bị khóa hay chưa
                                $scope.message = "Tài khoản này đã bị khóa!";
                            } else if (!isComparePassword.data) {
                                // So sánh password người dùng nhập với password đã mã hóa
                                // Nếu không đúng thì xuất lỗi
                                $scope.isValidPassword = true;
                            } else if ($scope.user.role != "CUST") {
                                // Kiểm tra quyền của người dùng có phải là CUST hay không ?
                                $scope.message = "Bạn không có quyền truy cập trang này!";
                            } else {
                                // Đăng nhập thành công trở về trang chủ
                                $scope.userAccount = {
                                    "username": $scope.user.username,
                                    "rememberMe": false
                                }
                                if ($scope.rememberMe) {
                                    $scope.userAccount.rememberMe = true;
                                }
                                $scope.saveAccountToLocalStorage($scope.userAccount);
                                $window.location.href = '/';
                            }
                        }).catch(function (error) {
                            console.error('Lỗi khi so sánh password:', error);
                        });
                    } else {
                        $scope.message = "Tài khoản không tồn tại!";
                    }
                }).catch(function (error) {
                    console.error('Lỗi khi lấy thông tin tài khoản:', error);
                    $scope.LoginForm.$invalid = true;
                    $scope.login();
                });
            } else {
                $scope.LoginForm.$invalid = true;
                $scope.login();
            }
        }
    };

    // Lưu account lên local storage 
    $scope.saveAccountToLocalStorage = function (user) {
        $window.localStorage.setItem("user", JSON.stringify(user));
    }
});
