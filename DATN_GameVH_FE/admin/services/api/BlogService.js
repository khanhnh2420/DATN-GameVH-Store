app.factory('BlogService', function ($http) {
    var baseUrl = host + '/api/blog';

    return {
        getAllBlog: function () {
            return $http.get(baseUrl + '/getAll');
        }

    };
});