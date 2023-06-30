/***AVATAR SCRIPT***/

// Lấy phần tử nút tải lên
var uploadButton = document.querySelector('.upload-button');

// Lấy phần tử input tệp
var fileInput = document.querySelector('.file-upload');

// Thêm lắng nghe sự kiện click vào nút tải lên
uploadButton.addEventListener('click', function() {
  // Kích hoạt sự kiện click trên phần tử input tệp
  fileInput.click();
});

// Thêm lắng nghe sự kiện thay đổi vào phần tử input tệp
fileInput.addEventListener('change', function() {
  // Lấy tệp đã chọn
  var file = fileInput.files[0];
  
  // Tạo một đối tượng FileReader
  var reader = new FileReader();
  
  // Thiết lập bộ xử lý sự kiện onload
  reader.onload = function(event) {
    // Lấy nguồn ảnh đã tải lên
    var src = event.target.result;
    
    // Thiết lập nguồn cho hình ảnh cá nhân
    var profilePic = document.querySelector('.profile-pic');
    profilePic.src = src;
  };
  
  // Đọc tệp đã chọn dưới dạng Data URL
  reader.readAsDataURL(file);
});

/***AVATAR SCRIPT***/

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
  }, 99000); //1s = 1000 milliseconds

  timer2 = setTimeout(() => {
    progress.classList.remove("active");
  }, 5300);
};

/***TOAST SCRIPT***/