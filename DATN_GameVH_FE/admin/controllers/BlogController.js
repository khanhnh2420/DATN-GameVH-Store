app.controller('BlogController', function ($scope, $filter, $document, BlogService, ToastService) {
    $scope.blogs = [];
    $scope.comments = [];
    $scope.submitButtonText = "Add";
    $scope.blogData = {
        username: "lethithuy",
        image: null
    };

    $scope.getDataBlog = function () {
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
    };
    $scope.getDataBlog();

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
                    data: 'title', // Chỉnh lại thành 'tittle' để đúng với key trong dữ liệu
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
                        if (data === 1) {
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
                        return '<div class="dropdown dropdown-action"><a href="#" class="action-icon dropdown-toggle" data-toggle="dropdown"aria-expanded="false"><i class="material-icons font-weight-bold">⋮</i></a><div class="dropdown-menu dropdown-menu-right"><a class="dropdown-item" id="edit-blog" data-toggle="modal" data-target="#edit_Blog" data-blog-id="' + row.id + '"><i class="fa fa-pencil-square-o" aria-hidden="true"></i> Edit</a><a class="dropdown-item" id="delete-blog" data-toggle="modal" data-target="#delete_Blog" data-blog-id="' + row.id + '"><i class="fa fa-trash" aria-hidden="true"></i> Delete</a><a class="dropdown-item" id="comment-blog" data-toggle="modal"data-target="#comment_Blog" data-blog-id="' + row.id + '"><i class="fa fa-comments" aria-hidden="true"></i> Comment</a></div></div>';
                    }
                }
            ]
        });
        // Gắn trình xử lý sự kiện click vào phần tử chỉnh sửa
        $(document).on('click', '#edit-blog', function () {
            var blogId = $(this).data('blog-id');
            $scope.getBlogById(blogId);
        });
        $(document).on('click', '#delete-blog', function () {
            var blogId = $(this).data('blog-id');
            $scope.selectBlogtoDelete(blogId);
        });
        $(document).on('click', '#comment-blog', function () {
            var blogId = $(this).data('blog-id');
            $scope.getCommentByBlogId(blogId);
        });
    };

    $scope.newBlog = function () {
        // Đánh dấu form là untouched và pristine để xóa thông báo lỗi

        $scope.blogData = {
            createDate: new Date(),
            username: "lethithuy",
            image: null
        };
        $scope.formattedDate = $filter('date')($scope.blogData.createDate, 'dd/MM/yyyy');
        $scope.showBlogContentError = false;
        $scope.BlogForm.$setPristine();
        $scope.BlogForm.$setUntouched();
        $scope.BlogForm.$setValidity('required', true);
    };



    // Khởi tạo biến để kiểm tra tính hợp lệ của trường "Summernote" và điều khiển hiển thị thông báo
    $scope.isBlogContentValid = true;
    $scope.showBlogContentError = false;

    // Hàm kiểm tra tính hợp lệ của trường "Summernote" và lấy giá trị
    $scope.checkAndSetBlogContent = function () {
        // Lấy đối tượng trình soạn thảo Summernote
        var summernoteElement = $('#blogContent');

        // Lấy nội dung (giá trị) của trình soạn thảo Summernote dưới dạng mã HTML
        var content = summernoteElement.summernote('code');

        // Kiểm tra nội dung có dữ liệu hay không
        $scope.isBlogContentValid = !!content.trim() && content !== '<p><br></p>';

        // Nếu nội dung hợp lệ, gán giá trị vào biến $scope.blogData.content
        if ($scope.isBlogContentValid) {
            $scope.blogData.content = content;
            // Ẩn thông báo lỗi nếu trường "Summernote" hợp lệ
            $scope.showBlogContentError = false;
        } else {
            // Hiển thị thông báo lỗi nếu trường "Summernote" không hợp lệ
            $scope.showBlogContentError = true;
        }
    };

    

    $scope.addOrUpdate = function () {
        if ($scope.submitButtonText === "Edit") {
            // Gọi hàm xử lý cập nhật nhân viên
            updateBlog();
        } else {
            // Gọi hàm xử lý thêm mới nhân viên
            addBlog();
        }
    };

    var BlogIMG;
    // Hàm xử lý khi người dùng chọn file
    $document.find('#BlogIMG').on('change', function (event) {
        // Lấy file từ sự kiện change
        BlogIMG = event.target.files[0];

        // Bắt buộc phải sử dụng $apply() để thông báo rằng $scope đã thay đổi
        $scope.$apply();
    });

    function addBlog() {
        $scope.checkAndSetBlogContent();
        if (BlogIMG) {
            BlogService.uploadImage(BlogIMG)
                .then(function (resp) {
                    $scope.blogData.image = resp.data.fileId
                    // Cập nhật dữ liệu ảnh và tiến hành thêm  data vào DB
                    if ($scope.blogData.image != null) {
                        BlogService.createBlog($scope.blogData)
                            .then(function (resp) {
                                // Xử lý thành công, hiển thị thông báo thành công
                                ToastService.showSuccessToast("Tạo bài viết thành công")
                                $scope.getDataBlog();
                                setTimeout(function () {
                                    $('#add_Blog').modal('hide');
                                }, 1000);
                            })
                            .catch(function (error) {
                                ToastService.showErrorToast("Xảy ra lỗi khi tạo bài viết")
                            });
                    }
                })
                .catch(function (error) {
                    ToastService.showErrorToast("Xảy ra lỗi khi đăng tải ảnh")
                });
        } else {
            // If there is no image to upload, return an error message
            ToastService.showWarningToast("Vui lòng tải lên ảnh bìa")// Log thông báo lỗi từ service
        }
    }

    // Khởi tạo biến để lưu trữ blogId mà bạn muốn xóa
    $scope.blogIdToDelete = null;

    // Hàm để chọn blog mà bạn muốn xóa
    $scope.selectBlogtoDelete = function (blogId) {
        $scope.blogIdToDelete = blogId;
    };

    $scope.deleteBlog = function () {
        if ($scope.blogIdToDelete) {
            BlogService.deleteBlog($scope.blogIdToDelete)
                .then(function (resp) {
                    // Xử lý thành công, data chứa dữ liệu thành công từ service
                    ToastService.showSuccessToast("Xóa bài viết thành công"); // Hiển thị thông báo thành công
                    $scope.getDataBlog();
                    setTimeout(function () {
                        $('#delete_Blog').modal('hide');
                    }, 500);
                })
                .catch(function (error) {
                    // Xử lý lỗi, error chứa dữ liệu lỗi từ service
                    ToastService.showErrorToast("Lỗi khi xóa bài viết")// Log thông báo lỗi từ service
                });
        } else {
            ToastService.showWarningToast('Vui lòng chọn blog để xóa.')
        }
    };

    $scope.editImg = false;
    $scope.isHovered = false;

    $scope.showEditImage = function() {
        $scope.isHovered = true;
    };
    
    $scope.hideEditImage = function() {
        $scope.isHovered = false;
    };
    $scope.editImage = function () {
        $scope.editImg = false;
    }

    $scope.getBlogById = function (blogId) {
        BlogService.getBlogById(blogId)
            .then(function (response) {
                // Xử lý thành công, lấy dữ liệu blog từ response.data
                $scope.submitButtonText = "Edit";
                $scope.blogData = response.data;
                $scope.formattedDate = $filter('date')($scope.blogData.createDate, 'dd/MM/yyyy');
                $scope.editImg = true;
                // Đặt nội dung vào Summernote
                $('#blogContent').summernote('code', $scope.blogData.content);
                // Hiển thị modal để chỉnh sửa blog
                $('#add_Blog').modal('show');
            })
            .catch(function (error) {
                // Xử lý lỗi, hiển thị thông báo lỗi từ response.data
                console.error(error);
                ToastService.showErrorToast(error.data);
            });
    };

    function updateBlog() {

    }
    
})