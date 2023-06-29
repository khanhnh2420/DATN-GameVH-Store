app.factory('FeedbackService', function ($http) {
    var baseUrl = host + '/api/feedback';

    return {
        getFeedbackByProduct: function (productId) {
            return $http.get(baseUrl + '/getProduct/' + productId);
        }
    };
});
