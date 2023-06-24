
app.controller("Bloglist", function ($scope, $http) {

	$scope.bloglist = [];


		
	;

	$scope.getBlogList = function () {
		var url = `${host}/blog`;
		$http.get(url).then(resp => {
			$scope.bloglist= resp.data;
			console.log($scope.bloglist)
		}).catch(error => {
			console.log("Error", error)
		})
	}
	$scope.getBlogList();
	
})