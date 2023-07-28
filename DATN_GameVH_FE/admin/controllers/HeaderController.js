app.controller("HeaderController", HeaderController);
function HeaderController($scope,$window, SweetAlert) {
    $scope.fullName
    $scope.photo
    $scope.init = function(){
        $scope.fullName  = $window.localStorage.getItem("userName")
        $scope.photo  = $window.localStorage.getItem("photo")
    }
    $scope.init()
}
