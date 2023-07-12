app.controller("OrderController", OrderController);

function OrderController($scope, OrderService, SweetAlert) {
  $scope.orders = [];
  $scope.pageNo = 0;
  $scope.pageSize = 5;
  $scope.orderIdFilter = "";
  $scope.usernameFilter = "";
  $scope.createDateFilter = "";
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
    console.log(page);
    OrderService.getAll({
      page: page,
      size: $scope.size,
      username: $scope.username,
      orderId: $scope.nameFilter,
      createDate: $scope.createDateFilter,
    })
      .then(function (response) {
        console.log(response);
        const data = response.data;
        $scope.orders = data;
        // $scope.pageNo = data.pageNumber;
      })
      .catch(function (error) {
        console.error("Lỗi khi lấy danh sách đơn hàng:", error);
      });
  }
}
