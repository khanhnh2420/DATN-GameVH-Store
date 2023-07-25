app.controller("WishlistController", function($scope, AccountService, ProductService, WishlistService, $http) {
    $scope.favorite = []
    $scope.favoriteLength;
    $scope.account = {}; // Biến lưu thông tin account 
    $scope.username = null;




    $scope.logout = function() {
        $window.localStorage.removeItem("username");
        $window.sessionStorage.removeItem("username");
        $window.sessionStorage.setItem("messageLogin", "Bạn đã đăng xuất!");
        $window.location.href = 'login.html';
    };

    AccountService.checkLogin().then(function(account) {
        // Người dùng đã đăng nhập
        $scope.account = account;
        fetchWishlistItems()
    }).catch(function(error) {
        // Người dùng chưa đăng nhập hoặc có lỗi
        console.error('Lỗi đăng nhập hoặc chưa đăng nhập:', error);
        if (error === "Người dùng chưa đăng nhập") {
            // Xử lý logic khi người dùng chưa đăng nhập
            $window.localStorage.removeItem("username");
            $window.sessionStorage.removeItem("username");
            $window.sessionStorage.setItem("messageLogin", "Vui lòng đăng nhập!");
            $window.location.href = 'login.html';
        }
    });

    function fetchWishlistItems() {
        if ($scope.account && $scope.account.id) {
            var accountId = $scope.account.id;
            // Gọi WishlistService để lấy danh sách yêu thích
            WishlistService.getWishlist(accountId)
                .then(function(response) {
                    // Gán dữ liệu danh sách yêu thích vào biến $scope.favorite
                    $scope.favorite = response.data;

                    // Cập nhật số lượng sản phẩm yêu thích vào phần tử có class "wishlist-count"
                    $scope.favoriteLength = $scope.favorite.length;
                })
                .catch(function(error) {
                    console.error('Lỗi khi lấy danh sách yêu thích:', error);
                });
        }
    }


    $scope.toggleFavorite = function(event) {
        var productId = event.currentTarget.getAttribute('data-product-id');
        var existingFavorite = $scope.favorite.find(function(item) {
            return item.product.id === productId;
        });

        if (existingFavorite) {
            // Sản phẩm đã tồn tại trong danh sách yêu thích
            existingFavorite.status = !existingFavorite.status; // Đảo ngược trạng thái yêu thích

            WishlistService.updateFavorite(existingFavorite)
                .then(function(response) {
                    // Xử lý phản hồi từ máy chủ nếu cần
                    console.log('Cập nhật trạng thái sản phẩm thành công.');

                    // Cập nhật số lượng yêu thích sau khi thay đổi trạng thái
                    $scope.favoriteLength = $scope.favorite.filter(function(item) {
                        return item.status === true;
                    }).length;
                })
                .catch(function(error) {
                    console.error('Lỗi khi cập nhật sản phẩm yêu thích:', error);
                });
        } else {
            // Sản phẩm chưa tồn tại trong danh sách yêu thích, thêm mới vào
            ProductService.getProduct(productId)
                .then(function(response) {
                    var product = response.data;
                    if (product.available && product.qty > 0) {
                        var favoriteData = {
                            account: {
                                id: $scope.account.id
                            },
                            product: {
                                id: productId
                            },
                            status: true // Trạng thái mặc định là true khi thêm mới vào danh sách yêu thích
                        };

                        WishlistService.addWishlist(favoriteData)
                            .then(function(response) {
                                // Xử lý phản hồi từ máy chủ nếu cần
                                console.log('Thêm sản phẩm vào danh sách yêu thích thành công.');
                                // Thêm sản phẩm mới vào danh sách yêu thích hiển thị
                                $scope.favorite.push(response.data);

                                // Cập nhật số lượng yêu thích sau khi thêm sản phẩm
                                $scope.favoriteLength = $scope.favorite.filter(function(item) {
                                    return item.status === true;
                                }).length;
                            })
                            .catch(function(error) {
                                console.error('Lỗi khi thêm sản phẩm vào danh sách yêu thích:', error);
                            });
                    }
                })
                .catch(function(error) {
                    console.error('Lỗi khi lấy sản phẩm theo Id:', error);
                });
        }
    };

    $scope.removeFromFavorite = function(event) {
        var productId = event.currentTarget.getAttribute('data-product-id');
        var existingFavoriteIndex = $scope.favorite.findIndex(function(item) {
            return item.product.id === productId;
        });

        if (existingFavoriteIndex !== -1) {
            // Xoá sản phẩm khỏi danh sách yêu thích
            $scope.favorite.splice(existingFavoriteIndex, 1);

            WishlistService.removeFromWishlist(productId)
                .then(function() { // Use underscore to indicate that 'response' is intentionally not used
                    // Xử lý phản hồi từ máy chủ nếu cần
                    console.log('Xoá sản phẩm khỏi danh sách yêu thích thành công.');

                    // In thông báo thành công
                    alert('Xoá sản phẩm khỏi danh sách yêu thích thành công.');

                    // Cập nhật số lượng yêu thích sau khi xoá sản phẩm
                    $scope.favoriteLength = $scope.favorite.filter(function(item) {
                        return item.status === true;
                    }).length;
                })
                .catch(function(error) {
                    console.error('Lỗi khi xoá sản phẩm khỏi danh sách yêu thích:', error);
                });
        }
    };












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