app.factory('WishlistService', function($http) {
    var baseUrl = host + '/api/favorite';

    return {
        getWishlist: function(accountid) {
            return $http.get(baseUrl + '/getAll/' + accountid);
        },

        addWishlist: function() {
            return $http.post(baseUrl + '/addWishlist/');
        }
    };
});