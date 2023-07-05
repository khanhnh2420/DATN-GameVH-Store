
app.controller("BlogDetail", function ($scope, $http) {

	$scope.comment = {
		BlogId: '4',
		Username:'phamthuc',
		content: "Game tốtssssssssssssssssss ",
		createDate: "2023-04-01T17:00:00.000+00:00",
		status: 0
		
	};

	$scope.createCMT = function () {
		var url = `${host}/blog-detail/createCMT`;
		$http.post(url, $scope.comment).then(resp => {
			console.log(resp)
		}).catch(error => {
			console.log("Error", error)
		})
	}

	
})