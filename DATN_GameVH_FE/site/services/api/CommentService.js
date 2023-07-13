app.factory('CommentService', function ($http) {
    var baseUrl = host + '/api/comment';

    return {
        getAllCommentByBlogId: function (blogId) {
            return $http.get(baseUrl + '/getComments/' + blogId);
        },
    };
});