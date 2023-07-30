app.controller("HeaderController", function (AccountService, $scope, $window) {
    $scope.account = null; // Biến lưu thông tin account 

    AccountService.checkLogin().then(function (account) {
        // Người dùng đã đăng nhập
        $scope.account = account;
    }).catch(function (error) {
    });

    $scope.searchProduct = function () {
        var link = $window.location.href || '';
        if (link.indexOf("shop-4cols") !== -1) {
            $window.location.href = "/shop-4cols?search=" + document.getElementById("search").value;
        } else if (link.indexOf("shop-2cols") !== -1) {
            $window.location.href = "/shop-2cols?search=" + document.getElementById("search").value;
        } else if (link.indexOf("shop-list") !== -1) {
            $window.location.href = "/shop-list?search=" + document.getElementById("search").value;
        } else {
            $window.location.href = "/shop?search=" + document.getElementById("search").value;
        }

    }
});
