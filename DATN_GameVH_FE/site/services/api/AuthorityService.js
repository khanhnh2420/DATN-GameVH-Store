app.factory('AuthorityService', function ($http) {
    var baseUrl = host + '/api/authority';

    return {
        creatAuthority: function (data) {
            return $http.post(baseUrl + '/create', data);
        }
    };
});
