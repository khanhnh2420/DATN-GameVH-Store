app.controller("MomoController", function (MomoService, $scope) {

    $scope.createMomoData = function () {
        // Thông tin của momo business
        var accessKey = "hglxIRUGLnBd5h50";
        var partnerCode = "MOMOX7V920220806";
        var secretKey = "UMJQIJnOpNPnzQrDtWcRdLYu4oVt4UrV";
        var partnerName = "GamesVH";

        var date = new Date().getTime();

        // Thông tin của đơn hàng
        var requestId = date + "id";
        var orderId = date + ":0123456778"; // Mã hóa đơn
        var autoCapture = true;
        var requestType = "captureWallet";
        var notifyUrl = "http://localhost:3000";
        var returnUrl = "http://localhost:3000"; // thanh toán thành công về đâu
        var amount = "153000"; // Giá đơn hàng
        var orderInfo = "Thanh toán đơn hàng 140376444"; // Thông tin thanh toán
        var extraData = "ew0KImVtYWlsIjogImh1b25neGRAZ21haWwuY29tIg0KfQ==";
        var lang = "vi";

        // Tạo signature
        var signature = "accessKey=" + accessKey + "&amount=" + amount + "&extraData=" + extraData + "&ipnUrl=" + notifyUrl + "&orderId=" + orderId + "&orderInfo=" + orderInfo + "&partnerCode=" + partnerCode + "&redirectUrl=" + returnUrl + "&requestId=" + requestId + "&requestType=" + requestType;
        var hash = CryptoJS.HmacSHA256(signature, secretKey);
        signature = CryptoJS.enc.Hex.stringify(hash);

        // Tạo data gửi về cho server
        var data = {
            "partnerCode": partnerCode,
            "partnerName": partnerName,
            "storeId": partnerCode,
            "requestType": requestType,
            "ipnUrl": notifyUrl,
            "redirectUrl": returnUrl,
            "orderId": orderId,
            "amount": amount,
            "lang": lang,
            "autoCapture": autoCapture,
            "orderInfo": orderInfo,
            "requestId": requestId,
            "extraData": extraData,
            "signature": signature
        }

        // Call API Momo
        MomoService.createOrder(data).then(function (response) {
            $scope.momo = response.data;
            console.log($scope.momo);
        }).catch(function (error) {
            console.error('Lỗi khi tạo order Momo:', error);
        });
    }

    $scope.createMomoData();
});
