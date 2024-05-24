import request from "@/utils/request";
export default {
  selectByBusinessId(id) {
    return request({
      url: "/goods/selectByBusinessId",
      method: "get",
      params: {
        id: id,
      },
    });
  },
  // 加载商品详情
  selectById(id) {
    return request({
      url: "/goods/selectById",
      method: "get",
      params: {
        id: id,
      },
    });
  },
  // 加载评论
  selectCommentByGoodsId(id) {
    return request({
      url: "/comment/selectByGoodsId",
      method: "get",
      params: {
        id: id,
      },
    });
  },
  // 根据商品名称查询
  selectByName(name) {
    return request({
      url: "/goods/selectByName",
      method: "get",
      params: {
        name: name
      }
    });
  },
  // 新增购物车
  addCart(data) {
    return request({
      url: "/cart/add",
      method: "post",
      data: data
    });
  }
}
