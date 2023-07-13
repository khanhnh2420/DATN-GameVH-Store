app.controller("BlogDetailController", function (BlogService, CommentService, TimeService, $scope, $routeParams, $timeout, $rootScope) {
    $scope.blog = {}; // Thông tin bài viết sẽ được hiển thị trên trang chi tiết
    $scope.comments = []; // Tất cả các conmment đã được duyệt của bài viết
    $scope.blogIdPrev; // Bài viết trước đó
    $scope.blogIdNext; // Bài viết sau đó
    $scope.blogPopular = []; // Top 4 bài viết nhiều bình luận nhất
    var blogId = $routeParams.blogId; // ID của bài viết được truyền vào qua URL

    // Lấy bài viết theo ID để hiển thị trên trang chi tiết
    BlogService.getBlogById(blogId).then(function (resp) {
        $scope.blog = resp.data;
        BlogService.getAllBlog()
            .then(function (resp) {
                $scope.lstBlog = resp.data;
                CommentService.getAllCommentByBlogId(blogId).then(function (resp) {
                    $scope.comments = resp.data;
                }).catch(function (error) {
                    console.error('Lỗi khi lấy danh sách bình luận:', error);
                });
                //Lấy top 4 bài viết nhiều bình luận nhất
                BlogService.getTop4BlogPopular().then(function (resp) {
                    $scope.blogPopular = resp.data;
                }).catch(function (error) {
                    console.error('Lỗi khi lấy top 4 blog:', error);
                });
                //Xử lí chuyển bài viết tiếp theo
                $scope.checkPrevAndNextBlog($scope.lstBlog);
            }).catch(function (error) {
                console.error('Lỗi khi lấy tất cả bài viết :', error);
            });
    }).catch(function (error) {
        console.error('Lỗi khi lấy bài viết :', error);
    });

    // Lấy bài viết trước và sau của bài viết (previous and next)
    $scope.checkPrevAndNextBlog = function (listBLog) {
        if (listBLog != null && listBLog != undefined) {
            for (let i = 0; i < listBLog.length; i++) {
                if (listBLog[i].id == blogId) {
                    if (i == 0) {
                        $scope.blogIdPrev = listBLog[listBLog.length - 1].id; // Bài viết trước đó
                        $scope.blogIdNext = listBLog[i + 1].id; // Bài viết sau đó
                    } else if (i == (listBLog.length - 1)) {
                        $scope.blogIdPrev = listBLog[i - 1].id; // Bài viết trước đó
                        $scope.blogIdNext = listBLog[0].id; // Bài viết sau đó
                    } else {
                        $scope.blogIdPrev = listBLog[i - 1].id; // Bài viết trước đó
                        $scope.blogIdNext = listBLog[i + 1].id; // Bài viết sau đó
                    }
                }
            }
        }
    };

    $scope.calculateTimeAgo = TimeService.calculateTimeAgo;

    // // Lấy tất cả các feedback đã được duyệt của sản phẩm
    // FeedbackService.getFeedbackByProduct(productId).then(function (response) {
    //     $scope.feedbacks = response.data;
    // }).catch(function (error) {
    //     console.error('Lỗi khi lấy feedback:', error);
    // });

});

