app.controller("ProductController", function(ProductAdminService, ToastService, $http, $scope, $compile, $routeParams, $timeout, $rootScope, $filter) {

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

    $scope.temProduct = {};




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
                '<a class="dropdown-item" href="#" ng-click="updateFeedbackStatus(' + feedback.id + ',' + feedback.product.id + ',1)"><i class="fa fa-dot-circle-o text-success"></i>Đã Duyệt</a>' +
                '<a class="dropdown-item" href="#" ng-click="updateFeedbackStatus(' + feedback.id + ',' + feedback.product.id + ',0)"><i class="fa fa-dot-circle-o text-danger"></i>Chưa Duyệt</a>' +
                '</div>' +
                '</div>' +
                '</td>' +
                '<td class="text-right">' +
                '<div class=" text-right">' +
                '<button href="#" ng-click="deleteFeedback(' + feedback.id + ',' + feedback.product.id + ')" data-toggle="modal" data-target="#delete_comment" class="border btn-danger">Delete</button>' +
                '</div>' +
                '</td>' +
                '</tr>';
            // Thêm feedbackRow vào feedbackTableBody
            feedbackTableBody.append(feedbackRow);

            // Biên dịch các sự kiện ng-click trong feedbackRow
            var rowElement = feedbackTableBody.find('tr:last-child');
            $compile(rowElement)($scope);
        });
    };


    $scope.updateFeedbackStatus = function(feedbackId, productId, status) {
        // Gọi phương thức trong Service để cập nhật trạng thái feedback
        ProductAdminService.updateFeedbackStatus(feedbackId, status)
            .then(function(response) {
                console.log("Feedback đã được cập nhật:", response.data);
                showSuccessToast("Đã thay đổi trạng thái đánh giá thành công");
                $scope.getFeedbackForProduct(productId);
            })
            .catch(function(error) {
                console.error("Lỗi khi cập nhật feedback:", error);
            });
    };

    $scope.deleteFeedback = function(feedbackId, productId) {
        ProductAdminService.deleteFeedbackById(feedbackId)
            .then(function(resp) {
                // Xử lý thành công, data chứa dữ liệu thành công từ service
                showSuccessToast("Đã xóa đánh giá thành công");
                $scope.getFeedbackForProduct(productId);
            })
            .catch(function(error) {
                // Xử lý lỗi, error chứa dữ liệu lỗi từ service
            });
    };


    // Thông báo Toast Success
    function showSuccessToast(message) {
        var toastMessage = message || "Sản phẩm đã được xóa thành công.";
        toast({
            title: "Thành công!",
            message: toastMessage,
            type: "success",
            duration: 5000
        });
    }

    function showErrorToast(message) {
        var toastMessage = message || "Thất bại.";
        toast({
            title: "Thất bại!",
            message: toastMessage,
            type: "error",
            duration: 5000
        });
    }

    // Toast function
    function toast({ title = "", message = "", type = "info", duration = 3000 }) {
        const main = document.getElementById("toast");
        if (main) {
            const toast = document.createElement("div");

            // Auto remove toast
            const autoRemoveId = setTimeout(function() {
                main.removeChild(toast);
            }, duration + 1000);

            // Remove toast when clicked
            toast.onclick = function(e) {
                if (e.target.closest(".toast__close")) {
                    main.removeChild(toast);
                    clearTimeout(autoRemoveId);
                }
            };

            const icons = {
                success: "fas fa-check-circle",
                info: "fas fa-info-circle",
                warning: "fas fa-exclamation-circle",
                error: "fas fa-exclamation-circle"
            };
            const icon = icons[type];
            const delay = (duration / 1000).toFixed(2);

            toast.classList.add("toastDesign", `toast--${type}`);
            toast.style.animation = `slideInLeft ease .3s, fadeOut linear 1s ${delay}s forwards`;

            toast.innerHTML = `
                      <div class="toast__icon">
                          <i class="${icon}"></i>
                      </div>
                      <div class="toast__body">
                          <h3 class="toast__title">${title}</h3>
                          <p class="toast__msg">${message}</p>
                      </div>
                      <div class="toast__close">
                          <i class="fas fa-times"></i>
                      </div>
                  `;
            main.appendChild(toast);
        }
    };






    // Lấy tất cả sản phẩm
    ProductAdminService.getAllProducts().then(function(response) {
        $scope.product = response.data;

        $scope.temProduct = $scope.product;
        // Kiểm tra nếu có search thì sẽ fill data dạng search
        var selectElement = document.getElementById("searchTitle").value;
        var searchName = document.getElementById("searchUsername").value.toLowerCase();
        var searchCreateDate = document.getElementById("searchCreateDate").value;
        console.log(selectElement || searchName)
            // if (selectElement || searchName) {
            //     $scope.searchProduct();
            // }

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

    $scope.displayThumbnails = function(thumbnails) {
        thumbnails.forEach((thumbnailId) => {
            const imageUrl = "https://drive.google.com/uc?id=" + thumbnailId.trim();
            $scope.displayThumbnail(imageUrl);
        });
    };

    // // Hàm để hiển thị các thumbnail
    // function displayThumbnails(thumbnails) {
    //     thumbnails.forEach((thumbnailId) => {
    //         const imageUrl = "https://drive.google.com/uc?id=" + thumbnailId.trim();
    //         $scope.displayThumbnail(imageUrl);
    //     });
    // }

    // Hàm để tải các thumbnail cho editProduct
    function loadThumbnails() {
        // Đảm bảo rằng editProduct.thumbnail đã được xác định trước khi trích xuất các thumbnail
        if ($scope.editProduct && $scope.editProduct.thumbnail) {
            $scope.thumbnails = extractThumbnails($scope.editProduct.thumbnail);
            displayThumbnails($scope.thumbnails);
        }
    }

    // Cập nhật sản phẩm
    $scope.updateProduct = function(productId) {
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

        ProductAdminService.uploadImage(formData)
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


    $scope.searchProduct = function() {
        var selectElement = document.getElementById("searchTitle").value;
        var searchName = document.getElementById("searchUsername").value.toLowerCase();
        var searchCreateDate = document.getElementById("searchCreateDate").value;
        $scope.product = $scope.temProduct;


        if (selectElement) {
            var result = $scope.product.filter(function(item) {
                var ProductType = item.type; // Chuyển giá trị thuộc tính title thành chữ thường
                return ProductType.indexOf(selectElement) !== -1;
            });
            $scope.product = result;
        }

        if (searchCreateDate) {
            searchCreateDate = convertDateFormat(searchCreateDate);
            console.log(searchCreateDate)
            var result = $scope.product.filter(function(item) {
                var createDate = item.createDate;
                console.log(createDate)
                return createDate.indexOf(searchCreateDate) !== -1;
            });
            $scope.product = result;
        }

        if (searchName) {
            var result = $scope.product.filter(function(item) {
                var ProductName = item.name.toLowerCase(); // Chuyển giá trị thuộc tính user created thành chữ thường
                return ProductName.indexOf(searchName) !== -1;
            });
            $scope.product = result;
        }


        $(document).ready(function() {
            $scope.loadDataTableProduct($scope.product);
        });
    }

    $scope.searchWithEnter = function(event) {
        if (event.keyCode === 13) {
            $scope.searchProduct();
        }
    }


    $scope.refreshSearch = function() {
        document.getElementById("searchTitle").value = "";
        document.getElementById("searchUsername").value = "";
        document.getElementById("searchCreateDate").value = "";

        $scope.getAllProducts();
        $scope.loadDataTableProduct($scope.product)
    }

    function convertDateFormat(inputDate) {
        // Tách ngày, tháng, năm từ chuỗi đầu vào
        const dateParts = inputDate.split('/');
        const day = dateParts[0];
        const month = dateParts[1];
        const year = dateParts[2];

        // Ghép lại thành dạng ngày/tháng/năm mới
        const newDateFormat = `${year}-${month}-${day}`;

        return newDateFormat;
    }



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
    // Khởi tạo một mảng để lưu trữ các URL tạm thời cho các hình ảnh thu nhỏ đã được tải lên
    $scope.tempPhotoUrls = [];

    // Sự kiện khi người dùng nhấn nút "Upload Thumbnail"
    $scope.handleThumbnailUploadClick = function(index) {
        // Tìm đến phần tử chứa thẻ <input> tương ứng với index của nút được nhấn
        var fileInputthumbnail = document.getElementById('fileInputthumbnail' + index);

        // Đăng ký sự kiện change cho input file-upload để xử lý khi người dùng chọn ảnh và thực hiện upload.
        fileInputthumbnail.addEventListener('change', function() {
            // Lấy danh sách các file đã chọn từ input file-upload.
            var selectedFiles = this.files;
            if (selectedFiles.length > 0) {
                // Lấy file đầu tiên trong danh sách (vì input file-upload cho phép multiple selection).
                var selectedFile = selectedFiles[0];

                // Tạo một đường dẫn tạm thời cho ảnh từ file đã chọn.
                var tempPhotoUrl = URL.createObjectURL(selectedFile);

                // Lưu đường dẫn tạm thời vào mảng tương ứng với index của input
                $scope.tempPhotoUrls[index] = tempPhotoUrl;

                $scope.$apply(function() {
                    // Gọi hàm displayThumbnail để hiển thị ảnh thumbnail được chọn
                    $scope.displayThumbnail(tempPhotoUrl);
                });
            }
        });

        // Khi click vào nút "upload-button", kích hoạt sự kiện click cho input file-upload.
        fileInputthumbnail.click();
    };

    // Đăng ký sự kiện change cho input file-upload để xử lý khi người dùng chọn ảnh và thực hiện upload.
    document.querySelectorAll('.fileInputthumbnail').forEach(function(inputElement, index) {
        inputElement.addEventListener('change', function() {
            // Lấy danh sách các file đã chọn từ input file-upload.
            var selectedFiles = this.files;
            if (selectedFiles.length > 0) {
                // Lấy file đầu tiên trong danh sách (vì input file-upload cho phép multiple selection).
                var selectedFile = selectedFiles[0];

                // Tạo một đường dẫn tạm thời cho ảnh từ file đã chọn.
                var tempPhotoUrl = URL.createObjectURL(selectedFile);

                // Lưu đường dẫn tạm thời vào mảng tương ứng với index của input
                $scope.tempPhotoUrls[index] = tempPhotoUrl;

                $scope.$apply(function() {
                    // Gọi hàm displayThumbnail để hiển thị ảnh thumbnail được chọn
                    $scope.displayThumbnail(tempPhotoUrl);
                });
            }
        });
    });

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