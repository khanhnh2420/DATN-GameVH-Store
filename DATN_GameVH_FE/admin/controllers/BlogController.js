app.controller('BlogController', function ($scope, $filter, BlogService) {
    $scope.blogs = [];
    $scope.comments = [];
    $scope.blogData = {};

    BlogService.getAllBlog()
        .then(function (resp) {
            $scope.blogs = resp.data;
            $(document).ready(function () {
                $scope.loadDataTableContract($scope.blogs);
            });
        })
        .catch(function (error) {
            console.error("Lỗi khi lấy danh sách bài viết:", error);
        });

    $scope.loadDataTableContract = function (blogs) {
        var table = $('#tableBlog');

        if ($.fn.DataTable.isDataTable(table)) {
            table.DataTable().destroy();
        }

        table.DataTable({
            searching: false,
            data: blogs, // Dữ liệu được truyền vào DataTables
            columns: [
                {
                    data: null,
                    render: function (data, type, row, meta) {
                        // Render giao diện cho cột "#"
                        return meta.row + 1;
                    }
                },
                {
                    data: 'tittle', // Chỉnh lại thành 'tittle' để đúng với key trong dữ liệu
                    render: function (data, type, row) {
                        // Kiểm tra nếu tiêu đề dài hơn 52 ký tự, thì hiển thị chỉ 52 ký tự và thêm dấu '...'
                        var maxLength = 52;
                        var trimmedTitle = data.length > maxLength ? data.substring(0, maxLength) + '...' : data;
                        return trimmedTitle;
                    }
                }, // Cột "Tittle"
                { data: 'username', class: 'text-center' }, // Cột "Contract ID"
                {
                    data: 'createDate', class: 'text-center', // Cột "createDate"
                    render: function (data, type, row) {
                        // Sử dụng filter date để định dạng lại giá trị
                        var fmtCreateDate = $filter('date')(row.createDate, 'dd/MM/yyyy');
                        return fmtCreateDate;
                    }
                }, // Cột "createDate"
                {
                    data: 'status', class: 'text-center', // Chỉnh lại thành 'status' để đúng với key trong dữ liệu
                    render: function (data, type, row) {
                        // Kiểm tra giá trị của status và hiển thị thích hợp
                        if (data === true) {
                            return '<span class="badge bg-inverse-success">Public</span>';
                        } else {
                            return '<span class="badge bg-inverse-danger">Private</span>';
                        }
                    }
                }, // Cột "Status"
                {
                    data: null, class: 'text-center', // Cột "Action"
                    render: function (data, type, row) {
                        // Render giao diện cho cột "Action"
                        return '<div class="dropdown dropdown-action"><a href="#" class="action-icon dropdown-toggle" data-toggle="dropdown"aria-expanded="false"><i class="material-icons font-weight-bold">⋮</i></a><div class="dropdown-menu dropdown-menu-right"><a class="dropdown-item" href="#" data-toggle="modal" data-target="#edit_Blog"><i class="fa fa-pencil m-r-5"></i>Edit</a><a class="dropdown-item" href="#" data-toggle="modal" data-target="#delete_Blog"><i class="fa fa-trash-o m-r-5"></i>Delete</a><a class="dropdown-item" href="#" data-toggle="modal"data-target="#comment_Product"><i class="fa fa-pencil-square-o m-r-5"></i> Comment</a></div></div>';
                    }
                }
            ]
        });
        // Gắn trình xử lý sự kiện click vào phần tử chỉnh sửa
        $(document).on('click', '#edit-blog', function () {
            var blogId = $(this).data('blog-id');
            $scope.editBlogById(blogId);
        });
        $(document).on('click', '#delete-blog', function () {
            var blogId = $(this).data('blog-id');
            $scope.deleteBlogById(blogId);
        });
        $(document).on('click', '#comment-blog', function () {
            var blogId = $(this).data('blog-id');
            $scope.getCommentByBlogId(blogId);
        });
    };


    $scope.newBlog = function () {
        $scope.blogData.createDate = new Date();
    };
})