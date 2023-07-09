app.factory('BcryptService', function ($http) {
    var baseUrl = host + '/api/bcrypt';

    return {
        encrypt: function (data) {
            return $http.get(baseUrl + '/encrypt/' + data);
        },
        compare: function (data, encryptData) {
            return $http.get(baseUrl + '/compare/' + data + '/' + encryptData);
        }
    };
});
