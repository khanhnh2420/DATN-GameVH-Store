app.factory('VNPayService', function ($http) {
    return {
        createOrder: function (totalPrice, userIP) {
            return $http.get('http://localhost:8080/api/vnpay/create/' + totalPrice + '/' + userIP);
        }
    }
});