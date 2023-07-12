
app.controller("bloglist", function ($scope, $http) {

	$scope.bloglst = [];





	$scope.getBlogList = function () {
		var url = `${host}/api/blog/getbloglist`;
		url += `?page=0&size=4`;


		console.log(url)
		$http.get(url).then(resp => {
			$scope.bloglst= resp.data;
			console.log($scope.bloglst)
		}).catch(error => {
			console.log("Error", error)
		})
	}
	$scope.getBlogList();
	
	$scope.currentPage = 0;

	$scope.getNextPage = function () {
		if ($scope.currentPage < $scope.products.totalPages) {
			$scope.currentPage++;
			$scope.get_ByPage($scope.currentPage);
		}
	};

	$scope.getPrevPage = function () {
		if ($scope.currentPage > 0) {
			$scope.currentPage--;
			$scope.get_ByPage($scope.currentPage);
		}
	};
});