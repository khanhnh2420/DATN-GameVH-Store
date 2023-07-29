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
        getAllFeedback: function() {
            return $http.get(baseUrl + '/getAllFeedback');
        },
        getFeedbackProduct: function(productId) {
            return $http.get(baseUrl + '/getProduct/' + productId);
        },
        // uploadImage: function(image) {
        //     var formData = new FormData();
        //     formData.append('image', image);

        //     return $http.post(baseUrl + '/upload', formData, {
        //         transformRequest: angular.identity,
        //         headers: { 'Content-Type': undefined }
        //     });
        // },
        uploadImage: function(imageFile) {
            return $http.post(baseUrl + '/upload', imageFile, {
                headers: {
                    'Content-Type': undefined
                }
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
        },
        getListProductSearch: function(productName, productType, categoryName) {
            return $http.get(baseUrl + '/products/search?productName=' + productName + '&productType=' + productType + '&categoryName=' + categoryName);
        },
        updateFeedbackStatus: function(feedbackId, feedbackStatus) {
            var feedbackData = {
                id: feedbackId,
                status: feedbackStatus
            };

            return $http.put(baseUrl + '/updateFeedback', feedbackData);
        },

    };
});