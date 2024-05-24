import request from "@/utils/request";
export default {
  selectPage(url,method,data) {
    return request({
      url,
      method,
      params: data
    });
  },
  selectAll(url,data) {
    return request({
      url,
      method:"get",
      params: data
    });
  },
  addUpdate(url,method,data) {
    return request({
      url,
      method,
      data
    });
  },
  deleteById(url) {
    return request({
      url,
      method: "delete",
    });
  },
  deleteByList(url,data){
    return request({
      url,
      method:"delete",
      data
    })
  },

};
