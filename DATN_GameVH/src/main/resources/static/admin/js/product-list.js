function previewImage(event) {
  var input = event.target;
  var reader = new FileReader();

  reader.onload = function () {
    var imgPreview = document.getElementById("img-preview");
    imgPreview.src = reader.result;
  };

  reader.readAsDataURL(input.files[0]);
}

function previewImages(event) {
  var input = event.target;
  var previewList = document.getElementById("image-preview-list");
  previewList.innerHTML = ""; // Xóa các ảnh hiện có trong preview list trước khi thêm mới

  // Kiểm tra số lượng file đã chọn và thông báo nếu vượt quá giới hạn
  if (input.files.length > 4) {
    alert("Vui lòng chọn tối đa 4 tệp.");
    input.value = ""; // Xóa tất cả các tệp đã chọn
    previewList.innerHTML = ""; // Xóa các ảnh đã hiển thị
    return;
  }

  var fileCount = Math.min(input.files.length, 4); // Chỉ xử lý tối đa 4 file

  if (fileCount === 0) {
    return; // Không có file nào được chọn, thoát khỏi hàm
  }

  for (var i = 0; i < fileCount; i++) {
    var reader = new FileReader();

    reader.onload = function (e) {
      var imgContainer = document.createElement("div");
      imgContainer.classList.add("thumbnail");
      previewList.appendChild(imgContainer);

      var img = document.createElement("img");
      img.src = e.target.result;
      imgContainer.appendChild(img);
    };

    reader.readAsDataURL(input.files[i]);
  }

}
