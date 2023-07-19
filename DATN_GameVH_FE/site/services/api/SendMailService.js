app.factory('SendMailService', function ($http) {
    var baseUrl = host + '/api/mail';

    return {
        sendMailCheckout: function (orderId, data) {
            return $http.post(baseUrl + '/send/checkout/' + orderId, data);
        }
    };
});
