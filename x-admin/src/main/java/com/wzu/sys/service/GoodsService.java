package com.wzu.sys.service;
import com.github.yulichang.base.MPJBaseService;
import com.wzu.common.vo.Result;
import com.wzu.sys.entity.Goods;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 功能：商品信息表业务处理
 * 作者：菠萝
 * 日期：2024/4/2 16:32
 */
@Transactional
public interface GoodsService extends MPJBaseService<Goods> {

    Result<?> add(Goods goods);
    Result<?> update(Goods goods);
    void delete(Integer id);
    Result<?> deleteList(List<Integer> ids);
    Result<?> selectPage(Goods goods,Integer pageNum, Integer pageSize);

    List<Goods> selectByBusinessId(Integer id);

    Goods selectById(Integer id);

    List<Goods> selectByName(String name);

    List<Goods> selectByTypeId(Integer id);
//    public List<Goods> selectTop15() {
//        return goodsMapper.selectTop15();
//    }
//
//    public List<Goods> selectByTypeId(Integer id) {
//        return goodsMapper.selectByTypeId(id);
//    }
//
//    public List<Goods> selectByBusinessId(Integer id) {
//        return goodsMapper.selectByBusinessId(id);
//    }
//
//    public List<Goods> selectByName(String name) {
//        return goodsMapper.selectByName(name);
//    }

//    public List<Goods> recommend() {
//        Account currentUser = TokenUtils.getCurrentUser();
//        if (ObjectUtil.isEmpty(currentUser)) {
//            // 没有用户登录
//            return new ArrayList<>();
//        }
//        // 用户的哪些行为可以认为他跟商品产生了关系？收藏、加入购物车、下单、评论
//        // 1. 获取所有的收藏信息
//        List<Collect> allCollects = collectMapper.selectAll(null);
//        // 2. 获取所有的购物车信息
//        List<Cart> allCarts = cartMapper.selectAll(null);
//        // 3. 获取所有的订单信息
//        List<Orders> allOrders = ordersMapper.selectAllOKOrders();
//        // 4. 获取所有的评论信息
//        List<Comment> allComments = commentMapper.selectAll(null);
//        // 5. 获取所有的用户信息
//        List<User> allUsers = userMapper.selectAll(null);
//        // 6. 获取所有的商品信息
//        List<Goods> allGoods = goodsMapper.selectAll(null);
//
//        // 定义一个存储每个商品和每个用户关系的List
//        List<RelateDTO> data = new ArrayList<>();
//        // 定义一个存储最后返回给前端的商品List
//        List<Goods> result = new ArrayList<>();
//
//        // 开始计算每个商品和每个用户之间的关系数据
//        for (Goods goods : allGoods) {
//            Integer goodsId = goods.getId();
//            for (User user : allUsers) {
//                Integer userId = user.getId();
//                int index = 1;
//                // 1. 判断该用户有没有收藏该商品，收藏的权重我们给 1
//                Optional<Collect> collectOptional = allCollects.stream().filter(x -> x.getGoodsId().equals(goodsId) && x.getUserId().equals(userId)).findFirst();
//                if (collectOptional.isPresent()) {
//                    index += 1;
//                }
//                // 2. 判断该用户有没有给该商品加入购物车，加入购物车的权重我们给 2
//                Optional<Cart> cartOptional = allCarts.stream().filter(x -> x.getGoodsId().equals(goodsId) && x.getUserId().equals(userId)).findFirst();
//                if (cartOptional.isPresent()) {
//                    index += 2;
//                }
//                // 3. 判断该用户有没有对该商品下过单（已完成的订单），订单的权重我们给 3
//                Optional<Orders> ordersOptional = allOrders.stream().filter(x -> x.getGoodsId().equals(goodsId) && x.getUserId().equals(userId)).findFirst();
//                if (ordersOptional.isPresent()) {
//                    index += 3;
//                }
//                // 4. 判断该用户有没有对该商品评论过，评论的权重我们给 2
//                Optional<Comment> commentOptional = allComments.stream().filter(x -> x.getGoodsId().equals(goodsId) && x.getUserId().equals(userId)).findFirst();
//                if (commentOptional.isPresent()) {
//                    index += 2;
//                }
//                if (index > 1) {
//                    RelateDTO relateDTO = new RelateDTO(userId, goodsId, index);
//                    data.add(relateDTO);
//                }
//            }
//        }
//
//        // 数据准备结束后，就把这些数据一起喂给这个推荐算法
//        List<Integer> goodsIds = UserCF.recommend(currentUser.getId(), data);
//        // 把商品id转换成商品
//        List<Goods> recommendResult = goodsIds.stream().map(goodsId -> allGoods.stream()
//                        .filter(x -> x.getId().equals(goodsId)).findFirst().orElse(null))
//                .limit(10).collect(Collectors.toList());
//
////        if (CollectionUtil.isEmpty(recommendResult)) {
////            // 随机给它推荐10个
////            return getRandomGoods(10);
////        }
////        if (recommendResult.size() < 10) {
////            int num = 10 - recommendResult.size();
////            List<Goods> list = getRandomGoods(num);
////            result.addAll(list);
////        }
//        return recommendResult;
//    }

//    private List<Goods> getRandomGoods(int num) {
//        List<Goods> list = new ArrayList<>(num);
//        List<Goods> goods = goodsMapper.selectAll(null);
//        for (int i = 0; i < num; i++) {
//            int index = new Random().nextInt(goods.size());
//            list.add(goods.get(index));
//        }
//        return list;
//    }
}
