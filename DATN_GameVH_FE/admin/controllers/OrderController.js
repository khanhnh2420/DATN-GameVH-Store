app.controller("OrderController", OrderController);

function OrderController($scope, OrderService, SweetAlert) {
  $scope.orders = [];
  $scope.pageNo = 0;
  $scope.pageSize = 5;
  $scope.phoneFilter = "";
  $scope.userNameFilter = "";
  $scope.createdAtFilter = "";
  $scope.init = function () {
    getPage(0);
  };
  $scope.init();

  $scope.filterData = function () {
    getPage(0);
  };

  $scope.selectedOrderData;
  $scope.selectedorderDetail;
  $scope.viewData = function (order) {
    OrderService.getOne(order.orderId)
      .then((response) => {
        console.log(response);
        $scope.selectedOrderData = response.data.orderData;
        $scope.selectedorderDetail = response.data.orderDetail.map(
          (e, index) => ({ ...e, index })
        );
      })
      .catch((err) =>
        SweetAlert.error("Error occured!", "Please try again later!")
      );
  };

  function getPage(page) {
    OrderService.getAll({
      page: page,
      size: $scope.size,
      username: $scope.userNameFilter,
      phone: $scope.phoneFilter,
      createdAt:
        $scope.createdAtFilter._d &&
        formatDateToString(new Date($scope.createdAtFilter)),
    })
      .then(function (response) {
        const data = response.data;
        $scope.orders = data.content;
        // $scope.pageNo = data.pageNumber;
      })
      .catch(function (error) {
        console.error("Lỗi khi lấy danh sách đơn hàng:", error);
      });
  }
}

function formatDateToString(date) {
  // Ensure the input is a valid Date object
  if (!(date instanceof Date) || isNaN(date)) {
    return "Invalid Date";
  }

  // Extract day, month, and year from the Date object
  const day = date.getDate().toString().padStart(2, "0");
  const month = (date.getMonth() + 1).toString().padStart(2, "0"); // Months are zero-based, so add 1
  const year = date.getFullYear().toString();

  // Concatenate the formatted parts with the "-" separator
  return `${year}-${month}-${day}`;
}
