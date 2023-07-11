// Angular js
app.controller("Checkout", function ($scope, $http, $window) {
	$scope.cart = [];
	$scope.TotalPrice = 0;

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
});
