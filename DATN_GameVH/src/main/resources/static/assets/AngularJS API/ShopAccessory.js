
app.controller("Shop-ctrl", function ($scope, $http, $anchorScroll, $location) {

	$scope.accessories = {};

	$scope.get_all = function () {
		var url = `${host}/api/accessory/getAll`;
		var get_allBysize = localStorage.getItem('getAllBySize');
		if (get_allBysize) {
			url += `?size=${get_allBysize}`;
		}
		$http.get(url).then(resp => {
			$scope.accessories = resp.data;
		}).catch(error => {
			console.log("Error", error)
		})
	}
	$scope.get_all();

	$scope.get_ByPage = function (page) {
		$scope.currentPage = page;
		var url = `${host}/api/accessory/getAll?page=${page}`;
		var get_allBysize = localStorage.getItem('getAllBySize');
		if (get_allBysize) {
			url += `&size=${get_allBysize}`;
		}
		$http.get(url).then(resp => {
			$scope.accessories = resp.data;
		}).catch(error => {
			console.log("Error", error)
		})
		$scope.scrollToTop();
	}
	$scope.scrollToTop = function () {
		$location.hash('sortby');
		$anchorScroll();
	};

	$scope.currentPage = 0;

	$scope.getNextPage = function () {
		if ($scope.currentPage < $scope.accessories.totalPages) {
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

	$scope.goToAccessoryPage = function (event, size) {
		var href = event.currentTarget.getAttribute('link'); // Lấy đường dẫn trang
		localStorage.setItem('getAllBySize', size); // Lưu số lượng sản phẩm cần hiển thị
		event.preventDefault(); // Ngăn chặn hành động mặc định của liên kết
		window.location.href = href; // Chuyển đổi trang
		$scope.$apply(); // Đồng bộ hoá các thay đổi với mô hình dữ liệu của bạn
	}
})