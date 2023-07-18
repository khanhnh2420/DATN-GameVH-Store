app.factory('ProductAdminService', function($http) {
    var baseUrl = host + '/api/product';
    var baseUrlCa = host + '/api/category';

    return {
        getProductDTO: function(productId) {
            return $http.get(baseUrl + '/getProductAdminDTO/' + productId);
        },
        getProduct: function(productId) {
            return $http.get(baseUrl + '/' + productId);
        },
        getAllCategories: function() {
            return $http.get(baseUrlCa + '/getAll');
        },
        getAllProducts: function() {
            return $http.get(baseUrl + '/getAll');
        },
        uploadImage: function(image) {
            var formData = new FormData();
            formData.append('image', image);

            return $http.post(baseUrl + '/upload', formData, {
                transformRequest: angular.identity,
                headers: { 'Content-Type': undefined }
            });
        },
        createProduct: function(productData) {
            return $http.post(baseUrl, productData);
        },
        updateProduct: function(productId, productData) {
            return $http.post(baseUrl + '/updateProduct/' + productId, productData);
        },

        deleteProduct: function(productId) {
            return $http.delete(baseUrl + '/delete' + productId);
        }
    };
});