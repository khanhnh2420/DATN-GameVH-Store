app.controller("ProductController", function(ProductAdminService, $scope, $routeParams, $timeout, $rootScope) {
    $scope.product = {}; // Thông tin sản phẩm sẽ được hiển thị trên trang chi tiết
    $scope.thumbnails = []; // Mảng hình ảnh thumbnail của sản phẩm

    $scope.sameProduct = []; // Mảng sản phẩm cùng loại
    $scope.productIdPrev; // Sản phẩm trước đó
    $scope.productIdNext; // Sản phẩm sau đó


    // Lấy tất cả sản phẩm
    ProductAdminService.getAllProducts()
        .then(function(response) {
            $scope.products = response.data;
            // Do additional processing or actions with the retrieved products here
        })
        .catch(function(error) {
            console.error('Lỗi khi lấy danh sách sản phẩm:', error);
        });



    ProductAdminService.getSameProducts(productId)
        .then(function(response) {
            $scope.sameProduct = response.data;
            $scope.checkPrevAndNextProduct($scope.sameProduct);
            $scope.randomizeArray($scope.sameProduct);
            $scope.checkCarouselInitialization();
        })
        .catch(function(error) {
            console.error('Lỗi khi lấy danh sách sản phẩm cùng loại:', error);
        });

    // Lấy sản phẩm trước và sau của sản phẩm (previous and next)
    $scope.checkPrevAndNextProduct = function(listProduct) {
        if (listProduct != null && listProduct != undefined) {
            for (let i = 0; i < listProduct.length; i++) {
                if (listProduct[i].id == productId) {
                    if (i == 0) {
                        $scope.productIdPrev = listProduct[listProduct.length - 1].id; // Sản phẩm trước đó
                        $scope.productIdNext = listProduct[i + 1].id; // Sản phẩm sau đó
                    } else if (i == (listProduct.length - 1)) {
                        $scope.productIdPrev = listProduct[i - 1].id; // Sản phẩm trước đó
                        $scope.productIdNext = listProduct[0].id; // Sản phẩm sau đó
                    } else {
                        $scope.productIdPrev = listProduct[i - 1].id; // Sản phẩm trước đó
                        $scope.productIdNext = listProduct[i + 1].id; // Sản phẩm sau đó
                    }
                }
            }
        }
    };


}).filter('vndFormat', function() {
    // Filter định dạng tiền tệ
    return function(input) {
        if (!input) return '';
        var number = parseFloat(input);
        if (isNaN(number)) return input;
        var formattedNumber = number.toLocaleString('vi-VN');
        formattedNumber += ' VND';
        return formattedNumber;
    };
}).filter('dateFormat', function() {
    // Filter định dạng ngày tháng năm
    return function(dateString) {
        var date = new Date(dateString);
        var day = date.getDate();
        var month = date.getMonth() + 1;
        var year = date.getFullYear();
        day = (day < 10) ? '0' + day : day;
        month = (month < 10) ? '0' + month : month;
        var formattedDate = day + '/' + month + '/' + year;
        return formattedDate;
    };
});