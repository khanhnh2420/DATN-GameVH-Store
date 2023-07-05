app.controller("AccountController", function (CategoryService, $scope, $window) {
    $scope.user = ($window.localStorage.getItem("user") != null) ? JSON.parse($window.localStorage.getItem("user")) : [];
    console.log($scope.user)
});
