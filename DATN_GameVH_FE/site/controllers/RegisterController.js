// Angular js
app.controller("RegisterController", function (AccountService, AuthorityService, $scope, $http, $location) {
	$scope.form = {};
	$scope.passwordMatchError = false;
	$scope.messageSuccess = "";
	$scope.messageFailed = "";

	$scope.Register = function () {
		$scope.checkPasswordMatch();
		$scope.messageSuccess = "";
		$scope.messageFailed = "";

		if ($scope.RegisterForm.$valid && $scope.passwordMatchError === false) {
			// Xử lý logic khi form đã được điền đúng
			$scope.account = {
				'username': $scope.form.username,
				'password': $scope.form.password,
				'fullname': $scope.form.fullname,
				'email': $scope.form.email,
				'photo': "user.png",
				'status': true
			}
			AccountService.getByUsername($scope.account.username).then(function (userByUsername) {
				if (!userByUsername.data) {
					AccountService.getByEmail($scope.account.email).then(function (userByEmail) {
						if (!userByEmail.data) {
							AccountService.createAccount($scope.account).then(function (userCreate) {
								AccountService.getByUsername($scope.account.username).then(function (user) {
									var auth = {
										"account": user.data,
										"role": {
											"id": "CUST",
											"name": "Người Dùng"
										}
									}
									AuthorityService.createAuthority(auth).then(function (authority) {
										$scope.messageSuccess = "Đăng ký tài khoản thành công!";
										// Reset form và biến
										$scope.RegisterForm.$setPristine();
										$scope.RegisterForm.$setUntouched();
										$scope.form = {}; // Reset dữ liệu form
										$scope.confirmPassword = "";
									}).catch(function (error) {
										console.error('Lỗi khi tạo role cho tài khoản:', error);
										$scope.messageFailed = "Đăng ký tài khoản thất bại!";
									});
								}).catch(function (error) {
									console.error('Lỗi khi tạo tài khoản:', error);
									$scope.messageFailed = "Đăng ký tài khoản thất bại!";
								});
							}).catch(function (error) {
								console.error('Lỗi khi tạo tài khoản:', error);
								$scope.messageFailed = "Đăng ký tài khoản thất bại!";
							});
						} else {
							$scope.messageFailed = "Email đã được sử dụng vui lòng nhập email khác!";
						}
					}).catch(function (error) {
						console.error('Lỗi khi tạo tài khoản:', error);
						$scope.messageFailed = "Đăng ký tài khoản thất bại!";
					});
				} else {
					$scope.messageFailed = "Tài khoản đã tồn tại!";
				}
			}).catch(function (error) {
				console.error('Lỗi khi tạo tài khoản:', error);
				$scope.messageFailed = "Đăng ký tài khoản thất bại!";
			});
		}
	}

	$scope.checkPasswordMatch = function () {
		if ($scope.form.password !== $scope.confirmPassword) {
			$scope.passwordMatchError = true;
		} else {
			$scope.passwordMatchError = false;
		}
	};
});