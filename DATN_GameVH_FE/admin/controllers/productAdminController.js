app.controller("ProductController", function(ProductAdminService, $http, $scope, $routeParams, $timeout, $rootScope, $filter) {

    $scope.product = {}; // Thông tin sản phẩm sẽ được hiển thị trên trang chi tiết
    $scope.thumbnails = []; // Mảng hình ảnh thumbnail của sản phẩm

    $scope.editProduct = {};

    $scope.categories = [];
    $scope.feedback = [];
    $scope.thumbnails = [];

    $scope.posters = [];
    $scope.thumbnails = [];
    $scope.editMode = false; // Mặc định là chế độ "Add"

    var productId;



    // // Lấy thông tin sản phẩm theo ID
    // ProductAdminService.getProductDTO(productId)
    //     .then(function(response) {
    //         $scope.product = response.data;

    //     })
    //     .catch(function(error) {
    //         console.error('Lỗi khi lấy thông tin sản phẩm:', error);
    //     });



    // Lấy danh sách category
    ProductAdminService.getAllCategories()
        .then(function(response) {
            $scope.categories = response.data;
        })
        .catch(function(error) {
            console.error('Lỗi khi lấy danh sách category:', error);
        });


    $scope.getFeedbackForProduct = function(productId) {
        ProductAdminService.getFeedbackProduct(productId)
            .then(function(response) {
                $scope.feedback = response.data;
                $('#comment_Product').modal('show');
                // Gọi hàm loadProductFeedback để hiển thị dữ liệu phản hồi lên form
                $scope.loadProductFeedback();
            })
            .catch(function(error) {
                console.error('Lỗi khi lấy danh sách phản hồi:', error);
            });
    };

    $scope.loadProductFeedback = function() {
        var feedbackTableBody = $('#feedbackTableBody');
        feedbackTableBody.empty();

        $scope.feedback.forEach(function(feedback) {
            var starIcons = '';
            for (var i = 1; i <= 5; i++) {
                starIcons += '<span class="fa fa-star' + (feedback.star >= i ? ' checked' : '') + '"></span>';
            }

            var feedbackRow = '<tr>' +
                '<td>' + feedback.id + '</td>' +
                '<td>' + feedback.account.username + '</td>' +
                '<td class="d-flex text-wrap">' + feedback.content + '</td>' +
                '<td>' + starIcons + '</td>' +
                '<td>' + feedback.createDate + '</td>' +
                '<td>' +
                '<div class="dropdown action-label">' +
                '<a class="btn btn-white btn-sm btn-rounded dropdown-toggle" href="#" data-toggle="dropdown" aria-expanded="false">' +
                '<i class="fa fa-dot-circle-o text-' + (feedback.status ? 'success' : 'danger') + '"></i> ' +
                (feedback.status ? 'Đã Duyệt' : 'Chưa Duyệt') +
                '</a>' +
                '<div class="dropdown-menu">' +
                '<a class="dropdown-item" href="#" ng-click="updateFeedbackStatus(true, feedback.id)" ><i class="fa fa-dot-circle-o text-success"></i>Đã Duyệt</a>' +
                '<a class="dropdown-item" href="#" ng-click="updateFeedbackStatus(false, feedback.id)"><i class="fa fa-dot-circle-o text-danger"></i>Chưa Duyệt</a>' +
                '</div>' +
                '</div>' +
                '</td>' +
                '<td class="text-right">' +
                '<div class=" text-right">' +
                '<button href="#" data-toggle="modal" data-target="#delete_comment" class="border btn-danger">Delete</button>' +
                '</div>' +
                '</td>' +
                '</tr>';

            feedbackTableBody.append(feedbackRow);
        });
    };


    $scope.updateFeedbackStatus = function(status, feedbackId) {
        // Gọi phương thức trong Service để cập nhật trạng thái feedback
        ProductAdminService.updateFeedbackStatus(feedbackId, status)
            .then(function(response) {
                console.log("Feedback đã được cập nhật:", response.data);
                // Cập nhật trạng thái của feedback sau khi nhận phản hồi thành công từ API
                // Chắc chắn rằng biến feedback chứa danh sách các feedback và feedback có id tương ứng được cập nhật
                var feedback = $scope.feedback.find(function(item) {
                    return item.id === feedbackId;
                });
                if (feedback) {
                    feedback.status = status;
                }
            })
            .catch(function(error) {
                console.error("Lỗi khi cập nhật feedback:", error);
                // Xử lý lỗi nếu có
            });
    };


    // Lấy tất cả sản phẩm
    ProductAdminService.getAllProducts().then(function(response) {
        $scope.product = response.data;
        console.log(response.data)
        $(document).ready(function() {
            $scope.loadDataTableProduct($scope.product);
        });
    }).catch(function(error) {
        console.error('Lỗi khi lấy danh sách sản phẩm:', error);
    });

    $scope.loadDataTableProduct = function(products) {
        var table = $('#tableProduct');

        if ($.fn.DataTable.isDataTable(table)) {
            table.DataTable().destroy();
        }

        table.DataTable({
            searching: false,
            data: products, // Dữ liệu được truyền vào DataTables
            columns: [{
                    data: null,
                    render: function(data, type, row, meta) {
                        // Render giao diện cho cột "#"
                        // return meta.row + 1;
                        return row.id;
                    }
                },
                {
                    data: null, // Cột "Poster"
                    render: function(data, type, row) {
                        // Render giao diện cho cột "Poster"
                        var posterUrl = row.poster ? row.poster : '1xbZ557bXhtiEG-sPP4TRXf007THuPsns';
                        var imageUrl = 'https://drive.google.com/uc?id=' + posterUrl;
                        return '<h2 class="table-poster"><a href="#" class="poster"><img alt="" src="' + imageUrl + '" data-zoom-image="' + imageUrl + '" alt="product image"></a></h2>';
                    }

                },
                {
                    data: null, // Cột "Name"
                    render: function(data, type, row) {
                        // Render giao diện cho cột "Name"
                        return '<td><a href="#" class="text-dark" data-toggle="modal" data-target="#edit_Product">' + row.name + '</a></td>';
                    }
                },
                { data: 'originPrice', render: function(data) { return $filter('vndFormat')(data); } }, // Cột "Origin Price"
                { data: 'salePrice', render: function(data) { return $filter('vndFormat')(data); } }, // Cột "Sale Price"
                { data: 'source' }, // Cột "Source"
                {
                    data: 'createDate', // Cột "Created Date"
                    render: function(data, type, row) {
                        // Sử dụng filter date để định dạng lại giá trị ngày tạo
                        var formattedCreateDate = $filter('date')(row.createDate, 'dd/MM/yyyy');
                        return formattedCreateDate;
                    }
                },
                { data: 'category.type' }, // Cột "Type"
                {
                    data: 'status', // Cột "Status"
                    render: function(data) {
                        // Render giao diện cho cột "Status"
                        var statusText = (data === true) ? 'Còn hàng' : 'Hết hàng';
                        var badgeClass = (data === true) ? 'success' : 'danger';
                        return '<span class="badge bg-inverse-' + badgeClass + '">' + statusText + '</span>';
                    }

                },
                {
                    data: null, // Cột "Action"
                    // render: function(data, type, row) {
                    //     // Render giao diện cho cột "Action"
                    //     return '<div class="dropdown dropdown-action"><a href="#" class="action-icon dropdown-toggle" data-toggle="dropdown" aria-expanded="false"><i class="material-icons font-weight-bold">⋮</i></a><div class="dropdown-menu dropdown-menu-right"><a class="dropdown-item" href="#" ng-click="editProductClicked(' + row.id + ')" data-toggle="modal" data-target="#edit_Product"><i class="fa fa-pencil m-r-5"></i>Edit</a><a class="dropdown-item" href="#" data-toggle="modal" data-target="#delete_Product"><i class="fa fa-trash-o m-r-5"></i>Delete</a><a class="dropdown-item" href="#" data-toggle="modal" data-target="#comment_Product"><i class="fa fa-pencil-square-o m-r-5"></i>Comment</a></div></div>';
                    // }

                    render: function(data, type, row) {
                        // Render giao diện cho cột "Action"
                        return '<div class="dropdown dropdown-action">' +
                            '<a href="#" class="action-icon dropdown-toggle" data-toggle="dropdown" aria-expanded="false">' +
                            '<i class="material-icons font-weight-bold">⋮</i>' +
                            '</a>' +
                            '<div class="dropdown-menu dropdown-menu-right">' +
                            '<a class="dropdown-item edit-product" href="#" data-product-id="' + row.id + '" data-toggle="modal" data-target="#edit_Product">' +
                            '<i class="fa fa-pencil m-r-5"></i>Chỉnh Sửa' +
                            '</a>' +
                            // Call the loadProductFeedback function with the product ID
                            '<a class="dropdown-item view-feedback" href="#" data-product-id="' + row.id + '" data-toggle="modal" data-target="#comment_Product">' +
                            '<i class="fa fa-pencil-square-o m-r-5"></i>Đánh Giá' +
                            '</a>' +
                            '</div>' +
                            '</div>';
                    }

                }
            ]
        });
        // Thêm sự kiện click vào phần tử chỉnh sửa
        $(document).on('click', '.edit-product', function() {
            var productId = $(this)[0].dataset.productId;
            console.log(productId)
            $scope.editProductClicked(productId);
        });
        $(document).on('click', '.view-feedback', function() {
            var productId = $(this)[0].dataset.productId;
            console.log(productId)
            $scope.getFeedbackForProduct(productId);
        });

    }



    // Hàm xử lý khi nhấp vào nút "Edit" trong cột "Action"
    $scope.editProductClicked = function(productId) {
        ProductAdminService.getProduct(productId)
            .then(function(response) {
                $scope.editProduct = response.data;
                loadThumbnails();
                $('#edit_Product').modal('show');
            })
            .catch(function(error) {
                console.error('Lỗi khi lấy thông tin sản phẩm:', error);
            });
    };

    // Gắn kết sự kiện click cho các phần tử chỉnh sửa
    angular.element(document).on('click', '.edit-product', function() {
        var productId = angular.element(this).data('product-id');
        $scope.editProductClicked(productId);
    });

    // Hàm để trích xuất các thumbnail từ chuỗi productThumbnail
    function extractThumbnails(productThumbnail) {
        if (!productThumbnail) return [];
        const thumbnailIds = productThumbnail.split("***");
        return thumbnailIds;
    }

    // Hàm để hiển thị các thumbnail
    function displayThumbnails(thumbnails) {
        thumbnails.forEach((thumbnailId) => {
            const imageUrl = "https://drive.google.com/uc?id=" + thumbnailId.trim();
            $scope.displayThumbnail(imageUrl);
        });
    }

    // Hàm để tải các thumbnail cho editProduct
    function loadThumbnails() {
        // Đảm bảo rằng editProduct.thumbnail đã được xác định trước khi trích xuất các thumbnail
        if ($scope.editProduct && $scope.editProduct.thumbnail) {
            $scope.thumbnails = extractThumbnails($scope.editProduct.thumbnail);
            displayThumbnails($scope.thumbnails);
        }
    }

    // Cập nhật sản phẩm
    $scope.updateProduct = function() {
        ProductAdminService.updateProduct(productId, $scope.editProduct)
            .then(function(response) {
                console.log("Sản phẩm đã được cập nhật");
                // Thực hiện các hành động sau khi cập nhật thành công
            })
            .catch(function(error) {
                console.error('Lỗi khi cập nhật sản phẩm:', error);
                // Xử lý lỗi khi cập nhật sản phẩm
            });
    };


    $scope.addProduct = function() {
        var productData = {
            name: $scope.product.name,
            originPrice: $scope.product.originPrice,
            salePrice: $scope.product.salePrice,
            type: $scope.product.type,
            category: {
                id: $scope.product.category.id
            },
            status: $scope.product.status,
            qty: $scope.product.qty,
            source: $scope.product.source,
            link: $scope.product.link,
            details: $scope.product.details
        };

        ProductAdminService.createProduct(productData)
            .then(function(response) {
                // Xử lý khi thêm sản phẩm thành công
                console.log('Sản phẩm thêm thành công.');
            })
            .catch(function(error) {
                // Xử lý khi thêm sản phẩm thất bại
                console.error('Không thể thêm được sản phẩm:', error);
            });
    };

    // Tìm đến phần tử chứa thẻ <input> và thẻ <img>
    var container = document.querySelector('.body');

    // Lấy tham chiếu đến phần tử <input> và <img> bên trong container
    var input = container.querySelector('input[type="file"]');
    var img = container.querySelector('img');

    // Lắng nghe sự kiện khi người dùng chọn tệp
    input.addEventListener('change', function(event) {
        // Kiểm tra xem người dùng đã chọn tệp hình ảnh chưa
        if (event.target.files && event.target.files[0]) {
            // Đọc tệp hình ảnh dưới dạng URL Data
            var reader = new FileReader();
            reader.onload = function(e) {
                // Thiết lập thuộc tính src của thẻ <img> để hiển thị hình ảnh
                img.src = e.target.result;
            };
            reader.readAsDataURL(event.target.files[0]);
        }
    });



    $scope.addPoster = function(file) {
        var formData = new FormData();
        formData.append('image', file);

        ProductAdminService.uploadimage(formData)
            .then(function(response) {
                // Xử lý khi tải lên poster thành công
                var fileId = response.data.fileId;
                $scope.product.poster = fileId;
                console.log('Poster uploaded successfully.');
            })
            .catch(function(error) {
                // Xử lý khi tải lên poster thất bại
                console.error('Failed to upload poster:', error);
            });
    };

    $scope.initDropzoneAndUploadThumbnail = function() {
        // Cấu hình Dropzone
        Dropzone.autoDiscover = false;
        var thumbnailDropzone = new Dropzone("#thumbnailDropzone", {
            url: "/upload", // Đường dẫn xử lý tải lên thumbnail
            maxFiles: 3, // Số lượng thumbnail tối đa được chọn
            acceptedFiles: "image/*", // Chỉ chấp nhận file hình ảnh
            addRemoveLinks: true, // Hiển thị nút xóa thumbnail

            // Xử lý khi tải lên thành công
            success: function(file, response) {
                var fileId = response.fileId;
                // Xử lý khi tải lên thumbnail thành công
                console.log('Thumbnail uploaded successfully:', fileId);
                $scope.product.thumbnails.push(fileId);
            },
            // Xử lý khi tải lên thất bại
            error: function(file, errorMessage) {
                console.error('Failed to upload thumbnail:', errorMessage);
            }
        });

        $scope.addThumbnail = function(file) {
            thumbnailDropzone.processQueue();
        };
    };




    $scope.searchProducts = function() {
        var productName = $scope.searchProductName;
        var productType = $scope.searchProductType;
        var categoryName = $scope.searchCategoryName;

        // Gọi phương thức getListProductSearch từ service để thực hiện tìm kiếm sản phẩm
        ProductAdminService.getListProductSearch(productName, productType, categoryName)
            .then(function(response) {
                // Lấy danh sách sản phẩm tìm kiếm được từ phản hồi của API
                var searchResults = response.data;

                // Gán danh sách sản phẩm tìm kiếm được vào biến $scope.products
                $scope.products = searchResults;
            })
            .catch(function(error) {
                console.error('Lỗi khi tìm kiếm sản phẩm:', error);
            });
    };

    /*----IMAGE----*/
    // Hàm xử lý sự kiện khi click vào nút "upload-button".
    // Trong phạm vi AngularJS controller.
    $scope.handleUploadClick = function() {
        // Lấy ra input file-upload thông qua ID.
        var fileInput = document.getElementById('fileInput');
        // Khi click vào nút "upload-button", kích hoạt sự kiện click cho input file-upload.
        fileInput.click();
    };

    // Đăng ký sự kiện change cho input file-upload để xử lý khi người dùng chọn ảnh và thực hiện upload.
    document.getElementById('fileInput').addEventListener('change', function() {
        // Lấy danh sách các file đã chọn từ input file-upload.
        var selectedFiles = this.files;
        if (selectedFiles.length > 0) {
            // Lấy file đầu tiên trong danh sách (vì input file-upload cho phép multiple selection).
            var selectedFile = selectedFiles[0];

            // Tạo một đường dẫn tạm thời cho ảnh từ file đã chọn.
            var tempPhotoUrl = URL.createObjectURL(selectedFile);
            $scope.$apply(function() {
                // Cập nhật đường dẫn mới vào biến "currentPhotoUrl" để thay đổi ảnh hiển thị.
                $scope.currentPhotoUrl = tempPhotoUrl;
            });
        }
    });
    // // Sự kiện khi người dùng nhấn nút "Upload Thumbnail"
    // $scope.handleThumbnailUploadClick = function() {
    //     // Tìm đến phần tử chứa thẻ <input> và thẻ <img>
    //     var fileInputthumbnail = document.getElementById('fileInputthumbnail');
    //     // Khi click vào nút "upload-button", kích hoạt sự kiện click cho input file-upload.
    //     fileInputthumbnail.click();
    // };

    // // Đăng ký sự kiện change cho input file-upload để xử lý khi người dùng chọn ảnh và thực hiện upload.
    // document.getElementById('fileInputthumbnail').addEventListener('change', function() {
    //     // Lấy danh sách các file đã chọn từ input file-upload.
    //     var selectedFiles = this.files;
    //     if (selectedFiles.length > 0) {
    //         // Lấy file đầu tiên trong danh sách (vì input file-upload cho phép multiple selection).
    //         var selectedFile = selectedFiles[0];

    //         // Tạo một đường dẫn tạm thời cho ảnh từ file đã chọn.
    //         var tempPhotoUrl = URL.createObjectURL(selectedFile);
    //         $scope.$apply(function() {
    //             // Gọi hàm displayThumbnail để hiển thị ảnh thumbnail được chọn
    //             $scope.displayThumbnail(tempPhotoUrl);
    //         });
    //     }
    // });

    /*----END IMAGE----*/

    //Excel
    $scope.downloadExcel = function() {
        $http({
            url: 'http://localhost:8080/api/product/downloadExcel', // Địa chỉ API endpoint của Spring Boot backend
            method: 'GET',
            responseType: 'arraybuffer'
        }).then(function(response) {
            var blob = new Blob([response.data], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
            var url = window.URL.createObjectURL(blob);
            var a = document.createElement('a');
            a.href = url;
            // Lấy ngày hiện tại và định dạng
            var currentDate = new Date();
            var formattedDate = currentDate.getFullYear() + '-' + (currentDate.getMonth() + 1) + '-' + currentDate.getDate();

            a.download = 'ReportSanpham_' + formattedDate + '.xlsx';
            a.click();
            window.URL.revokeObjectURL(url);
        }, function(error) {
            console.error('Lỗi khi tải xuống tệp Excel:', error);
        });
    };



}).filter('vndFormat', function() {
    // Filter định dạng tiền tệ
    return function(input) {
        if (!input) return '';
        var number = parseFloat(input);
        if (isNaN(number)) return input;
        var formattedNumber = number.toLocaleString('vi-VN');
        formattedNumber += ' VND';
        return formattedNumber;
    };
}).filter('dateFormat', function() {
    // Filter định dạng ngày tháng năm
    return function(dateString) {
        var date = new Date(dateString);
        var day = date.getDate();
        var month = date.getMonth() + 1;
        var year = date.getFullYear();
        day = (day < 10) ? '0' + day : day;
        month = (month < 10) ? '0' + month : month;
        var formattedDate = day + '/' + month + '/' + year;
        return formattedDate;
    };


});