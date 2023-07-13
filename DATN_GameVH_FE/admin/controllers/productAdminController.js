app.controller("ProductController", function(ProductAdminService, $scope, $routeParams, $timeout, $rootScope, $filter) {
    $scope.product = {}; // Thông tin sản phẩm sẽ được hiển thị trên trang chi tiết
    $scope.thumbnails = []; // Mảng hình ảnh thumbnail của sản phẩm

    $scope.sameProduct = []; // Mảng sản phẩm cùng loại
    $scope.productIdPrev; // Sản phẩm trước đó
    $scope.productIdNext; // Sản phẩm sau đó

    $scope.editProduct = {};



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
                        return meta.row + 1;
                        // return row.id;
                    }
                },
                {
                    data: null, // Cột "Poster"
                    render: function(data, type, row) {
                            // Render giao diện cho cột "Poster"
                            return '<h2 class="table-poster"><a href="#" class="poster"><img alt="" src="/assets/img/products/' + row.poster + '" data-zoom-image="/assets/img/products/' + row.poster + '" alt="product image"></a></h2>';
                        }
                        // render: function(data, type, row) {
                        //     // Render giao diện cho cột "Poster"
                        //     var posterUrl = row.poster ? row.poster : '1xbZ557bXhtiEG-sPP4TRXf007THuPsns';
                        //     var imageUrl = 'https://drive.google.com/uc?id=' + posterUrl;
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
                            '<a class="dropdown-item" href="#" ng-click="editProductClicked(' + row.id + ')" data-toggle="modal" data-target="#edit_Product">' +
                            '<i class="fa fa-pencil m-r-5"></i>Edit' +
                            '</a>' +
                            '<a class="dropdown-item" href="#" data-toggle="modal" data-target="#delete_Product">' +
                            '<i class="fa fa-trash-o m-r-5"></i>Delete' +
                            '</a>' +
                            '<a class="dropdown-item" href="#" data-toggle="modal" data-target="#comment_Product">' +
                            '<i class="fa fa-pencil-square-o m-r-5"></i>Comment' +
                            '</a>' +
                            '</div>' +
                            '</div>';
                    }

                }
            ]
        });

    }

    // Hàm xử lý khi nhấp vào nút "Edit" trong cột "Action"
    $scope.editProductClicked = function(productId) {
        ProductAdminService.getProduct(productId).then(function(response) {
            $scope.editProduct = response.data;
            // Mở modal chỉnh sửa sản phẩm ở đây
            $('#edit_Product').modal('show');
        }).catch(function(error) {
            console.error('Lỗi khi lấy thông tin sản phẩm:', error);
        });
    };


    // Lấy thông tin sản phẩm theo ID
    var productId = $routeParams.id;
    ProductAdminService.getProduct(productId).then(function(response) {
        $scope.product = response.data;
    }).catch(function(error) {
        console.error('Lỗi khi lấy thông tin sản phẩm:', error);
    });

    // Cập nhật sản phẩm
    $scope.updateProduct = function() {
        ProductAdminService.updateProduct(productId, $scope.product)
            .then(function(response) {
                console.log("Sản phẩm đã được cập nhật");
                // Thực hiện các hành động sau khi cập nhật thành công
            })
            .catch(function(error) {
                console.error('Lỗi khi cập nhật sản phẩm:', error);
                // Xử lý lỗi khi cập nhật sản phẩm
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