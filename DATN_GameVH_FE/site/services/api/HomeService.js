app.factory('HomeService', function ($http) {
    var baseUrl = host + '/api/homepage';

    return {
        getTop6Selling: function () {
            return $http.get(baseUrl + '/topselling');
        },
        getTop6NewRelease: function () {
            return $http.get(baseUrl + '/newrelease');
        },
        getTop6Rated: function () {
            return $http.get(baseUrl + '/toprate');
        },
        getListGame: function () {
            return $http.get(baseUrl + '/getListGame');
        },
        getListAccessory: function () {
            return $http.get(baseUrl + '/getListAccessory');
        }
    };
});
