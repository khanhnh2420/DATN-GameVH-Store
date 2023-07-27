app.factory('BlogService', function ($http) {
    var baseUrl = host + '/api/blog';

    return {
        getAllBlog: function () {
            return $http.get(baseUrl + '/getAll');
        },

        uploadImage: function (image) {
            // Check if the image is not empty before making the request
            var formData = new FormData();
            formData.append('image', image);

            // Use $http.post to send the image data as a FormData and return the promise
            return $http.post(baseUrl + '/upload-imageBlog', formData, {
                transformRequest: angular.identity,
                headers: { 'Content-Type': undefined }
            })
        },
        createBlog: function (blogData) {
            return $http.post(baseUrl + '/create', blogData)
        },
        getBlogById: function (id) {
            return $http.get(baseUrl + '/' + id);
        },
        deleteBlog: function (blogId) {
            return $http.delete(baseUrl + '/' + blogId)
        },
    };
});
