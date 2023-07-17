app.factory('OrderService', function ($http) {
    var baseUrl = host + '/api/orderdata';

    return {
        createOrder: function (data) {
            return $http.post(baseUrl + '/create', data);
        }
    };
});
