import request from "@/utils/request";
export default {
  selectPage(data) {
    return request({
      url: "/notice/selectPage",
      method: "post",
      data,
    });
  },
  selectAll() {
    return request({
      url: "/notice/selectAll",
      method: "get",
    });
  },
  addUpdate(data) {
    return request({
      url: data.id ? "/notice/update" : "/notice/add",
      method: data.id ? "put" : "post",
      data: data,
    });
  },
  deleteById(id) {
    return request({
      url: `/notice/delete/${id}`,
      method: "delete",
    });
  },
  deleteByList(data){
    return request({
      url:"/notice/delete/batch",
      method:"delete",
      data
    })
  }
};
