app.factory('WishlistService', function($http) {
    var baseUrl = host + '/api/favorite';

    return {
        getWishlist: function(accountId) {
            return $http.get(baseUrl + '/getAll/' + accountId);
        },

        addWishlist: function() {
            return $http.post(baseUrl + '/addWishlist/', favoriteData);
        },

        updatedFavorite: function() {
            return $http.post(baseUrl + '/addWishlist/?isUpdate=true', favoriteData);;
        },
    };
});