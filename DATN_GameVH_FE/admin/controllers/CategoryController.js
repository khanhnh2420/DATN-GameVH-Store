app.controller("CategoryController", CategoryController);

function CategoryController($scope, CategoryService, SweetAlert) {
  $scope.categories = [];
  $scope.selectedCategory;
  $scope.init = function () {
    CategoryService.getAll()
      .then(function (response) {
        const data = response.data;
        $scope.categories = data;
      })
      .catch(function (error) {
        console.error("Lỗi khi lấy danh sách danh mục:", error);
      });
  };
  $scope.selectCategory = function (category) {
    $scope.formType = category.type;
    $scope.formCategoryId = category.categoryId;
    $scope.formName = category.name;
    $scope.formId = category.id;
  };
  $scope.init();

  //   Form Handling
  $scope.formType;
  $scope.formId;
  $scope.formCategoryId;
  $scope.formName;

  $scope.openAddModal = function () {
    $scope.formType = "";
    $scope.formCategoryId = "";
    $scope.formName = "";
  };
  $scope.submitAdd = function () {
    if (!$scope.formType || !$scope.formCategoryId || !$scope.formName) {
      SweetAlert.info("Invalid form", "Please check your info again");
      return;
    }
    CategoryService.createCategory({
      categoryId: $scope.formCategoryId,
      type: $scope.formType,
      name: $scope.formName,
    })
      .then((response) => {
        console.log(response);
        SweetAlert.success(
          "New category added",
          `Category: ${response.data.name} added!`
        );
        $scope.init();
      })
      .catch((err) =>
        SweetAlert.error("Error occured, please try again later!", "")
      );
  };

  $scope.submitUpdate = function () {
    if (!$scope.formType || !$scope.formCategoryId || !$scope.formName) {
      SweetAlert.info("Invalid form", "Please check your info again");
      return;
    }
    CategoryService.editCategory($scope.formCategoryId, {
      categoryId: $scope.formCategoryId,
      type: $scope.formType,
      name: $scope.formName,
    })
      .then((response) => {
        console.log(response);
        SweetAlert.success("New category updated", "");
        $scope.init();
      })
      .catch((err) =>
        SweetAlert.error("Error occured, please try again later!", "")
      );
  };
  $scope.delete = function () {
    CategoryService.deleteCategory($scope.formCategoryId)
      .then((response) => {
        SweetAlert.success("Deleted!", "");
        $scope.init();
      })
      .catch((err) =>
        SweetAlert.error("Error occured, please try again later!", "")
      );
  };
}
