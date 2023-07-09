app.factory('ProductAdminService', function($http) {
    var baseUrl = host + '/api/product';

    return {
        getProductDTO: function(productId) {
            return $http.get(baseUrl + '/getProductDTO/' + productId);
        },
        getProduct: function(productId) {
            return $http.get(baseUrl + '/' + productId);
        },
        getAllProducts: function() {
            return $http.get(baseUrl + '/getAll');
        },
        createProduct: function(productData) {
            return $http.post(baseUrl, productData);
        },
        updateProduct: function(productId, productData) {
            return $http.put(baseUrl + '/update' + productId, productData);
        },
        deleteProduct: function(productId) {
            return $http.delete(baseUrl + '/delete' + productId);
        }
    };
});