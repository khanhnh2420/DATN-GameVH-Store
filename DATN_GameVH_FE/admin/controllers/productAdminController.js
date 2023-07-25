app.controller("ProductController", function(ProductAdminService, $scope, $routeParams, $timeout, $rootScope, $filter) {

    $scope.product = {}; // Thông tin sản phẩm sẽ được hiển thị trên trang chi tiết
    $scope.thumbnails = []; // Mảng hình ảnh thumbnail của sản phẩm


    $scope.categories = [];

    $scope.posters = [];
    $scope.thumbnails = [];
    $scope.editMode = false; // Mặc định là chế độ "Add"

    var productId;
    // Lấy thông tin sản phẩm theo ID
    ProductAdminService.getProductDTO(productId)
        .then(function(response) {
            $scope.product = response.data;

        })
        .catch(function(error) {
            console.error('Lỗi khi lấy thông tin sản phẩm:', error);
        });



    // Lấy danh sách category
    ProductAdminService.getAllCategories()
        .then(function(response) {
            $scope.categories = response.data;
        })
        .catch(function(error) {
            console.error('Lỗi khi lấy danh sách category:', error);
        });


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
                    // render: function(data, type, row) {
                    //         // Render giao diện cho cột "Poster"
                    //         return '<h2 class="table-poster"><a href="#" class="poster"><img alt="" src="/assets/img/products/' + row.poster + '" data-zoom-image="/assets/img/products/' + row.poster + '" alt="product image"></a></h2>';
                    //     }
                    render: function(data, type, row) {
                            // Render giao diện cho cột "Poster"
                            var posterUrl = row.poster ? row.poster : '1xbZ557bXhtiEG-sPP4TRXf007THuPsns';
                            var imageUrl = 'https://drive.google.com/uc?id=' + posterUrl;
                            return '<h2 class="table-poster"><a href="#" class="poster"><img alt="" src="' + imageUrl + '" data-zoom-image="' + imageUrl + '" alt="product image"></a></h2>';
                        }
                        // render: function(data, type, row) {
                        //     // Render giao diện cho cột "Poster"
                        //     var posterUrl = row.poster ? row.poster : '1xbZ557bXhtiEG-sPP4TRXf007THuPsns';
                        //     var imageUrl = 'https://drive.google.com/uc?id=' + posterUrl;

                    //     // Kiểm tra xem có giá trị posterUrl không
                    //     if (posterUrl) {
                    //         // Đặt hình ảnh vào phần hiển thị trong form edit
                    //         $('#posterPreview').show(); // Hiển thị phần hiển thị hình ảnh
                    //         $('#posterImage').attr('src', imageUrl); // Đặt đường dẫn hình ảnh
                    //         console.log(imageUrl)
                    //         $('#posterInput').val(posterUrl); // Đặt giá trị của hình ảnh vào input hidden

                    //     } else {
                    //         // Ẩn phần hiển thị hình ảnh nếu không có giá trị posterUrl
                    //         $('#posterPreview').hide();
                    //         $('#posterImage').attr('src', ''); // Đặt đường dẫn rỗng
                    //         $('#posterInput').val(''); // Đặt giá trị rỗng cho input hidden

                    //     }

                    //     return '<h2 class="table-poster"><a href="#" class="poster"><img alt="" src="' + imageUrl + '" data-zoom-image="' + imageUrl + '" alt="product image"></a></h2>';
                    // }



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
                            '<i class="fa fa-pencil m-r-5"></i>Edit' +
                            '</a>' +
                            '<a class="dropdown-item" href="#" data-toggle="modal" data-target="#comment_Product">' +
                            '<i class="fa fa-pencil-square-o m-r-5"></i>Feedback' +
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

    }




    // Hàm xử lý khi nhấp vào nút "Edit" trong cột "Action"
    $scope.editProductClicked = function(productId) {
        ProductAdminService.getProduct(productId)
            .then(function(response) {
                $scope.product = response.data;
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

    // $scope.product.name = null;
    // $scope.product.type = null;
    // $scope.categoryName = "0";

    // $scope.searchProduct = function() {
    //     if ($scope.productName == null || $scope.productName == undefined) {
    //         $scope.productName = "";
    //     }
    //     if ($scope.productType == null || $scope.productType == undefined) {
    //         $scope.productType = "";
    //     }

    //     ProductService.getListProductSearch($scope.productName, $scope.productType, $scope.categoryName)
    //         .then(function(response) {
    //             $scope.products = response.data;
    //             $(document).ready(function() {
    //                 $scope.loadDataTableProduct($scope.products);
    //             });
    //         })
    //         .catch(function(error) {
    //             console.error('Lỗi khi tìm kiếm sản phẩm:', error);
    //         });
    // };



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