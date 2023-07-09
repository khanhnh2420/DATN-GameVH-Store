app.controller("ProfileController", function (AccountService, $scope, $window) {
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
});
