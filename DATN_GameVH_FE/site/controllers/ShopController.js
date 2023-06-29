app.controller("ShopController", function (PageService, ProductService, CategoryService, $scope, $routeParams, $location, $window) {
	$scope.type = $routeParams.type;
	$scope.category = $routeParams.category;
	$scope.itemsPerPage = 9; // Số lượng phần tử hiển thị trên mỗi trang
	$scope.currentPage = 1; // Trang hiện tại
	$scope.totalPages = 0;
	$scope.displayedItems = [];
	$scope.products = [];
	$scope.categories = [];
	$scope.typePageShop = 'shop';

	if ($scope.type != null && $scope.type != undefined) {
		$scope.selectedType = $scope.type;
	} else {
		$scope.selectedType = '0';
	}

	$scope.checkLink = function () {
		var link = $window.location.href || '';

		if (link.indexOf("shop-4cols") !== -1) {
			$scope.itemsPerPage = 12;
			$scope.typePageShop = "shop-4cols";
		} else if (link.indexOf("shop-2cols") !== -1) {
			$scope.itemsPerPage = 6;
			$scope.typePageShop = "shop-2cols";
		} else if (link.indexOf("shop-list") !== -1) {
			$scope.itemsPerPage = 4;
			$scope.typePageShop = "shop-list";
			console.log("abc")
		} else {
			$scope.itemsPerPage = 9;
			$scope.typePageShop = "shop";
		}	
	};

	$scope.checkLink();

	$scope.loadData = function () {
		if ($scope.type == null || $scope.type == undefined) {
			// Lấy sản phẩm theo ID để hiển thị trên trang chi tiết
			ProductService.getAllProductDTO().then(function (response) {
				$scope.products = response.data;
				$scope.getListProductOnPage();
			}).catch(function (error) {
				console.error('Lỗi khi lấy tất cả sản phẩm DTO:', error);
			});
		} else {
			if ($scope.category == null || $scope.category == undefined) {
				if ($scope.type == "Game") {
					ProductService.getAllProductDTOByType($scope.type).then(function (response) {
						$scope.products = response.data;
						$scope.getListProductOnPage();
					}).catch(function (error) {
						console.error('Lỗi khi lấy danh sách game:', error);
					});
				} else {
					ProductService.getAllProductDTOByType($scope.type).then(function (response) {
						$scope.products = response.data;
						$scope.getListProductOnPage();
					}).catch(function (error) {
						console.error('Lỗi khi lấy danh sách phụ kiện:', error);
					});
				}
			} else {
				ProductService.getAllProductDTOByTypeAndCategory($scope.type, $scope.category).then(function (response) {
					$scope.products = response.data;
					$scope.getListProductOnPage();
				}).catch(function (error) {
					console.error('Lỗi khi lấy tất cả sản phẩm DTO theo loại và danh mục:', error);
				});
			}
		}

		// Lấy danh sách danh mục
		CategoryService.getAll().then(function (response) {
			$scope.categories = response.data;
		}).catch(function (error) {
			console.error('Lỗi khi lấy tất cả danh mục:', error);
		});
	};

	$scope.loadData();

	$scope.fillByType = function (select) {
		// Kiểm tra giá trị select và thiết lập đường dẫn mới
		if (select.selectedType === 'Game') {
			$location.path('/' + $scope.typePageShop + '/Game');
		} else if (select.selectedType === 'PK') {
			$location.path('/' + $scope.typePageShop + '/PK');
		} else {
			$location.path('/' + $scope.typePageShop);
		}
	};

	$scope.getListProductOnPage = function () {
		// Tổng số trang
		$scope.totalPages = PageService.calctotalPage($scope.itemsPerPage, $scope.products);

		// Lấy danh sách sản phẩm cho trang hiện tại
		$scope.displayedItems = PageService.getDisplayedItems($scope.itemsPerPage, $scope.currentPage, $scope.products);

		// Danh sách số trang hiển thị
		$scope.getPageRange = function (totalPages) {
			const maxPages = 10; // Số trang tối đa hiển thị liên tiếp
			const currentPage = $scope.currentPage;

			if (totalPages <= maxPages) {
				return Array(totalPages).fill().map((_, index) => index + 1);
			} else {
				let pageRange = [];
				if (currentPage <= 3) {
					pageRange = Array(5).fill().map((_, index) => index + 1);
					pageRange.push('...');
					pageRange.push(totalPages);
				} else if (currentPage >= totalPages - 2) {
					pageRange.push(1);
					pageRange.push('...');
					pageRange = pageRange.concat(Array(5).fill().map((_, index) => totalPages - 4 + index));
				} else {
					pageRange.push(1);
					pageRange.push('...');
					pageRange = pageRange.concat(Array(3).fill().map((_, index) => currentPage - 1 + index));
					pageRange.push('...');
					pageRange.push(totalPages);
				}
				return pageRange;
			}
		};

	};

	$scope.previousPage = function () {
		if ($scope.currentPage > 1) {
			$scope.currentPage--;
		} else {
			$scope.currentPage = $scope.totalPages;
		}
		$scope.getListProductOnPage();
	};

	$scope.nextPage = function () {
		if ($scope.currentPage < $scope.totalPages) {
			$scope.currentPage++;
		} else {
			$scope.currentPage = 1;
		}
		$scope.getListProductOnPage();
	};

	$scope.get_ByPage = function (page) {
		$scope.currentPage = page;
		$scope.getListProductOnPage();
	};
});
