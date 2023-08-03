app.directive('dropzone', function () {
    var dropzoneInitialized = false;

    return {
        restrict: 'E',
        scope: {
            thumbnails: '='
        },
        template: '<form action="#" class="dropzone d-flex justify-content-center flex-wrap" id="thumbnailDropzone"></form>',
        link: function (scope, element, attrs) {
            if (!dropzoneInitialized) {
                Dropzone.autoDiscover = false;
                var myDropzone = new Dropzone("#thumbnailDropzone", {
                    maxFiles: 3,
                    init: function () {
                        var dropzone = this;
                        var completedFiles = 0; // Biến đếm số file đã hoàn thành

                        this.on("addedfile", function (file) {
                            var thumbnails = dropzone.getAcceptedFiles();
                            scope.$apply(function () {
                                scope.thumbnails = thumbnails;
                            });

                            scope.thumbnails.push(file);
                        });

                        this.on("success", function (file, response) {
                            var thumbnails = dropzone.getAcceptedFiles();
                            scope.$apply(function () {
                                scope.thumbnails = thumbnails;
                            });
                        });

                        this.on("error", function (file) {
                            // Kiểm tra nếu file không được chấp nhận (ví dụ: quá dung lượng), xóa khỏi dropzone
                            dropzone.removeFile(file);
                            // Cập nhật lại danh sách thumbnails
                            var thumbnails = dropzone.getAcceptedFiles();
                            scope.$apply(function () {
                                scope.thumbnails = thumbnails;
                            });
                        });

                        // Xử lý sự kiện khi nhấn nút để xóa tất cả các file
                        document.getElementById("refreshThumbnail").addEventListener('click', function () {
                            myDropzone.removeAllFiles(true); // Xóa tất cả các file trong dropzone
                            completedFiles = 0; // Reset biến đếm file đã hoàn thành
                            scope.$apply(function () {
                                scope.thumbnails = []; // Cập nhật lại data trả về là một mảng trống
                            });
                        });
                    }
                });
                dropzoneInitialized = true;
            }
        }
    };
});
