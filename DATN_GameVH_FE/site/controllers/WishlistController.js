app.controller("WishlistController", function($scope, WishlistService, $http) {
    $scope.favorite = []
    $scope.favoriteLength;


    WishlistService.getWishlist(1).then(function(response) {
        $scope.favorite = response.data;
    }).catch(function(error) {
        console.error('Lỗi khi lấy danh sách yêu thích:', error);
    });




    $scope.loadFavoritesByAccount = function(accountId) {
        var url = `${host}/api/restWishlist/getAll/${accountId}`;
        $http.get(url).then(resp => {
            $scope.favorite = resp.data;
        }).catch(error => {
            console.log("Error", error);
        });
    };

    $scope.addWishlist = function() {
        var favorite = {
            account: {
                id: 1
            }
        };

        $http.post('/addWishlist', favorite)
        console.log(favorite)
            .then(function(response) {
                // Xử lý khi yêu thích được thêm thành công
                var updatedFavorite = response.data;
                // Cập nhật trạng thái yêu thích trong danh sách hiện tại hoặc thêm mới vào danh sách
                var existingFavoriteIndex = $scope.findFavoriteIndex(updatedFavorite);
                if (existingFavoriteIndex !== -1) {
                    $scope.favorites[existingFavoriteIndex] = updatedFavorite;
                } else {
                    $scope.favorites.push(updatedFavorite);
                }
            })
            .catch(function(error) {
                // Xử lý khi có lỗi xảy ra
                console.log('Error adding wishlist:', error);
            });
    };

    $scope.addWishlist();



    // Tìm vị trí của favorite trong danh sách favorites
    $scope.findFavoriteIndex = function(favorite) {
        for (var i = 0; i < $scope.favorites.length; i++) {
            if ($scope.favorites[i].account.id === favorite.account.id && $scope.favorites[i].product.id === favorite.product.id) {
                return i;
            }
        }
        return -1;
    };



    $scope.clear_all = function() {
        var url = `${host}/clearCart`;
        $http.post(url).then(resp => {
            $scope.favorite = resp.data;
            $scope.load_all();
        }).catch(error => {
            console.log("Error", error)
        })
    }

    $scope.resetCategory = function() {
        localStorage.removeItem("getAllBySize");
    }

}).filter('vndFormat', function() {
    return function(input) {
        if (!input) return '';

        // Chuyển đổi số về dạng chuỗi và đảm bảo nó là một số
        var number = parseFloat(input);

        // Kiểm tra xem có phải là một số hợp lệ hay không
        if (isNaN(number)) return input;

        // Định dạng số về VD: "100,000 VND"
        var formattedNumber = number.toLocaleString('vi-VN');
        formattedNumber += ' VND';

        return formattedNumber;
    };
});