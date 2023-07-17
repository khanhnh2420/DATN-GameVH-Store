app.factory('CouponOwnerService', function ($http) {
    var baseUrl = host + '/api/couponowner';

    return {
        getCoupon: function (username, couponcode) {
            return $http.get(baseUrl + '/getcoupon/' + username + "/" + couponcode);
        }
    };
});
