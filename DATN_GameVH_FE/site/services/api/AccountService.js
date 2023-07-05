app.factory('AccountService', function ($http) {
    var baseUrl = host + '/api/account';

    return {
        getByUsername: function (username) {
            return $http.get(baseUrl + '/' + username);
        }
    };
});
