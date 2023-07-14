app.controller("HeaderController", function (AccountService, $scope) {
    $scope.account = null; // Biến lưu thông tin account 

    AccountService.checkLogin().then(function (account) {
        // Người dùng đã đăng nhập
        $scope.account = account;

    }).catch(function (error) {
        // Người dùng chưa đăng nhập hoặc có lỗi
        console.error('Lỗi đăng nhập hoặc chưa đăng nhập:', error);

    });
});
