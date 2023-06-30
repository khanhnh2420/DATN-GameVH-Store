app.controller("LoginController", function ($scope, $rootScope, $window) {
    $scope.account = {};

    $scope.redirectToLogin = function () {
        $window.location.href = 'login.html';
    };

    $scope.login = function () {
        console.log($scope.account);
        // $window.location.href = '/';
    };
});
