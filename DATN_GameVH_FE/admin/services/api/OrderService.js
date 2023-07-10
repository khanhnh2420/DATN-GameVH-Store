app.factory("OrderService", function ($http) {
  const baseUrl = host + "/api/orderdata";
  return {
    getAll: function (filters) {
      const queries = {
        page: filters.page || 0,
        size: filters.size || 5,
        username: filters.username || null,
        orderId: filters.orderId || null,
        createDate: filters.createDate || null,
      };
      return $http.get(baseUrl, queries);
    },
    getOne(id) {
      return $http.get(`${baseUrl}/full/${id}`);
    },
  };
});
