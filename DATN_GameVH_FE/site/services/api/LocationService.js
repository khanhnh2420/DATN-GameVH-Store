app.factory('LocationService', function ($http) {
    var baseUrl = host + '/api/location';

    return {
        getByUsername: function (username) {
            return $http.get(baseUrl + '/username/' + username);
        }
    };
});
