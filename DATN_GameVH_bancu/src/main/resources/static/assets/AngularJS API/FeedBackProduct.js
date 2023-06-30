
app.controller("feedback", function($scope, $http) {

	$scope.feedbacks = {};

	$scope.get_all = function() {
		// Lấy về Id của product hiện tại
		var element = document.getElementById('feedBackProductId');
		var feedbackProductId = element.getAttribute('data-feedbackProduct-id');
		
		var url = `${host}/api/feedback/getProduct/${feedbackProductId}`;
		$http.get(url).then(resp => {
			$scope.feedbacks = resp.data;
		}).catch(error => {
			console.log("Error", error)
		})
	}
	$scope.get_all();
}).filter('dateFormat', function() {
    return function(dateString) {
      var date = new Date(dateString);
      var day = date.getDate();
      var month = date.getMonth() + 1;
      var year = date.getFullYear();

      // Đảm bảo ngày và tháng có độ dài là 2
      day = (day < 10) ? '0' + day : day;
      month = (month < 10) ? '0' + month : month;

      var formattedDate = day + '-' + month + '-' + year;
      return formattedDate;
    };
  });