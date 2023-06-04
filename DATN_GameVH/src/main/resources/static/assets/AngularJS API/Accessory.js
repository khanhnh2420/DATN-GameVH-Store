
app.controller("accessory", function($scope, $http, $filter) {

	$scope.accessories = [];

	$scope.get_all = function() {
		var url = `${host}/api/accessory/getall`;
		$http.get(url).then(resp => {
			$scope.accessories = resp.data;
			$scope.randomElements = $filter('shuffle')($scope.accessories).slice(0, 5);
		}).catch(error => {
			console.log("Error", error)
		})
	}
	$scope.get_all();

}).filter('shuffle', function() {
    return function(array) {
      var currentIndex = array.length, temporaryValue, randomIndex;

      // While there remain elements to shuffle...
      while (0 !== currentIndex) {

        // Pick a remaining element...
        randomIndex = Math.floor(Math.random() * currentIndex);
        currentIndex -= 1;

        // And swap it with the current element.
        temporaryValue = array[currentIndex];
        array[currentIndex] = array[randomIndex];
        array[randomIndex] = temporaryValue;
      }

      return array;
    };
  });