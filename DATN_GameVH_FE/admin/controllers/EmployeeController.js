app.controller('EmployeeController', function ($scope, EmployeeService) {
    // Gọi API để lấy danh sách nhân viên
    $scope.employees = [];
    
    EmployeeService.getEmployees().then(function (response) {
        $scope.employees = response.data;
    }).catch(function (error) {
        console.error('Lỗi khi lấy danh sách nhân viên:', error);
    });

    // // Gọi API để tạo mới nhân viên
    // $scope.createEmployee = function (employeeData) {
    //     EmployeeService.createEmployee(employeeData).then(function (response) {
    //         // Xử lý sau khi tạo nhân viên thành công
    //     }).catch(function (error) {
    //         console.error('Lỗi khi tạo mới nhân viên:', error);
    //     });
    // };

    // // Gọi API để cập nhật thông tin nhân viên
    // $scope.updateEmployee = function (employeeId, employeeData) {
    //     EmployeeService.updateEmployee(employeeId, employeeData).then(function (response) {
    //         // Xử lý sau khi cập nhật thông tin nhân viên thành công
    //     }).catch(function (error) {
    //         console.error('Lỗi khi cập nhật thông tin nhân viên:', error);
    //     });
    // };

    // // Gọi API để xóa nhân viên
    // $scope.deleteEmployee = function (employeeId) {
    //     EmployeeService.deleteEmployee(employeeId).then(function (response) {
    //         // Xử lý sau khi xóa nhân viên thành công
    //     }).catch(function (error) {
    //         console.error('Lỗi khi xóa nhân viên:', error);
    //     });
    // };
});
