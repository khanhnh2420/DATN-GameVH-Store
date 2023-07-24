app.controller("WishlistController", function($scope, AccountService, WishlistService, $http) {
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

    // Hàm để lấy danh sách yêu thích sử dụng accountId
    function fetchWishlistItems() {
        if ($scope.account && $scope.account.id) {
            var accountId = $scope.account.id;
            // Gọi WishlistService để lấy danh sách yêu thích
            WishlistService.getWishlist(accountId)
                .then(function(response) {
                    // Gán dữ liệu danh sách yêu thích vào biến $scope.favorite
                    $scope.favorite = response.data;

                })
                .catch(function(error) {
                    console.error('Lỗi khi lấy danh sách yêu thích:', error);
                });
        }
    }



    $scope.toggleFavorite = function(productId) {
        console.log('Toggle favorite function called with productId:', productId);

        // Kiểm tra xem người dùng đã đăng nhập chưa
        if (!$scope.account || !$scope.account.id) {
            // Hiển thị thông báo hoặc chuyển hướng đến trang đăng nhập nếu cần
            console.log('Bạn cần đăng nhập để thực hiện chức năng này.');
            return;
        }

        // Lấy accountId từ $scope.account
        var accountId = $scope.account.id;

        // Tạo đối tượng favorite để thêm vào danh sách yêu thích
        var favoriteData = {
            account: {
                id: accountId
            },
            product: {
                id: productId
            }
        };

        // Gọi hàm addWishlist từ WishlistService để thêm hoặc cập nhật sản phẩm vào danh sách yêu thích
        WishlistService.addWishlist(favoriteData)
            .then(function(response) {
                // Xử lý phản hồi từ máy chủ nếu cần
                var updatedFavorite = response.data;
                console.log()
                    // Tìm vị trí của sản phẩm trong danh sách yêu thích frontend dựa trên dữ liệu updatedFavorite
                var existingFavoriteIndex = $scope.findFavoriteIndex(updatedFavorite);
                if (existingFavoriteIndex !== -1) {
                    // Nếu sản phẩm đã tồn tại trong danh sách yêu thích, cập nhật trạng thái
                    $scope.favorite[existingFavoriteIndex] = updatedFavorite;
                } else {
                    // Nếu sản phẩm chưa tồn tại trong danh sách yêu thích, thêm sản phẩm mới vào danh sách
                    $scope.favorite.push(updatedFavorite);
                }
            })
            .catch(function(error) {
                // Xử lý bất kỳ lỗi nào xảy ra trong quá trình yêu cầu POST
                console.error('Lỗi thêm/cập nhật sản phẩm yêu thích:', error);
            });
    };



    $scope.findFavoriteIndex = function(favorite) {
        for (var i = 0; i < $scope.favorite.length; i++) { // Sửa thành $scope.favorite
            if ($scope.favorite[i].account.id === favorite.account.id && $scope.favorite[i].product.id === favorite.product.id) {
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