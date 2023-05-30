// Angular js
app.controller("AccountMana", function($scope, $http) {

	$scope.accounts = {};

	$scope.get_allAccount = function () {
		var url = `${host}/admin/api/accounts`;
		$http.get(url).then(resp => {
			$scope.accounts = resp.data;
		}).catch(error => {
			console.log("Error", error)
		})
	}
	$scope.get_allAccount();
});
