function previewImage(event) {
  var input = event.target;
  var reader = new FileReader();
  var imgPreview = document.getElementById("img-preview");

  reader.onload = function () {
    imgPreview.classList.add("img-thumbnail");
    imgPreview.src = reader.result;
  };

  if (input.files && input.files[0]) {
    reader.readAsDataURL(input.files[0]);
  } else {
    imgPreview.src = "img/new-product/No-Image-poster.png";
  }
}

/***TOAST SCRIPT***/
const toast = document.querySelector(".toast")
closeIcon = document.querySelector(".close"),
  progress = document.querySelector(".progress");

let timer1, timer2;

closeIcon.addEventListener("click", () => {
  toast.classList.remove("active");

  setTimeout(() => {
    progress.classList.remove("active");
  }, 4000);

  clearTimeout(timer1);
  clearTimeout(timer2);
});

function toastActive() {
  toast.classList.add("active");
  progress.classList.add("active");

  timer1 = setTimeout(() => {
    toast.classList.remove("active");
  }, 5000); //1s = 1000 milliseconds

  timer2 = setTimeout(() => {
    progress.classList.remove("active");
  }, 5300);
};

/***TOAST SCRIPT***/

function previewImages(event) {
  var input = event.target;
  var previewList = document.getElementById("image-preview-list");
  previewList.innerHTML = ""; // Xóa các ảnh hiện có trong preview list trước khi thêm mới

  // Kiểm tra số lượng file đã chọn và thông báo nếu vượt quá giới hạn
  if (input.files.length > 4) {

    toastActive();

    input.value = ""; // Xóa tất cả các tệp đã chọn
    previewList.innerHTML = ""; // Xóa các ảnh đã hiển thị
    return;
  }

  var fileCount = Math.min(input.files.length, 4); // Chỉ xử lý tối đa 4 file

  if (fileCount === 0) {
    // Hiển thị hình ảnh mặc định khi không có file nào được chọn
    for (var i = 0; i < 4; i++) {
      var placeholderImg = document.createElement("img");
      placeholderImg.src = "img/new-product/no-image.jpg";
      placeholderImg.classList.add("img-thumbnail");
      previewList.appendChild(placeholderImg);
    }
    return;
  }

  for (var i = 0; i < fileCount; i++) {
    var reader = new FileReader();

    reader.onload = function (e) {
      var imgContainer = document.createElement("div");
      imgContainer.classList.add("thumbnailPic");
      previewList.appendChild(imgContainer);

      var img = document.createElement("img");
      img.classList.add("img-thumbnail");
      img.src = e.target.result;
      imgContainer.appendChild(img);
    };

    reader.readAsDataURL(input.files[i]);
  }
}


