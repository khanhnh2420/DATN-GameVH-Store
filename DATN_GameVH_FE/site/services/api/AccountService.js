app.factory('AccountService', function ($http, $window, $q) {
    var baseUrl = host + '/api/account';

    return {
        checkLogin: function () {
            var deferred = $q.defer(); // Tạo deferred object
            var username = null;

            // Kiểm tra xem người dùng đã đăng nhập hay chưa
            username = $window.localStorage.getItem("username") || $window.sessionStorage.getItem("username");

            // Kiểm tra nếu đã đăng nhập, gọi API lấy thông tin account
            if (username) {
                this.getByUsername(username)
                    .then(function (response) {
                        var account = response.data;
                        deferred.resolve(account); // Trả về account khi thành công
                    })
                    .catch(function (error) {
                        console.error('Lỗi khi lấy thông tin tài khoản:', error);
                        deferred.reject(error); // Trả về lỗi khi không thành công
                    });
            } else {
                deferred.reject("Người dùng chưa đăng nhập"); // Trả về lỗi khi chưa đăng nhập
            }

            return deferred.promise; // Trả về promise
        },
        getByUsername: function (username) {
            return $http.get(baseUrl + '/' + username);
        },
        getByEmail: function (email) {
            return $http.get(baseUrl + '/email/' + email);
        },
        createAccount: function (user) {
            return $http.post(baseUrl + '/create', user);
        }
    };
});
