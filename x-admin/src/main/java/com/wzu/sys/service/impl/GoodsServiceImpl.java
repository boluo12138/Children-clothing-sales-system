package com.wzu.sys.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.base.MPJBaseServiceImpl;
import com.github.yulichang.toolkit.JoinWrappers;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.wzu.common.vo.Result;
import com.wzu.sys.entity.*;
import com.wzu.sys.mapper.*;
import com.wzu.sys.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 功能：
 * 作者：菠萝
 * 日期：2024/4/4 14:09
 */
@Service
public class GoodsServiceImpl extends MPJBaseServiceImpl<GoodsMapper, Goods> implements GoodsService {
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private UserServiceImpl userService;

    @Override
    public Result<?> add(Goods goods) {
//        goods.setTypeId(2);
        int insert = this.goodsMapper.insert(goods);
        if(insert == 1){
            return Result.success(goods,"添加成功");
        }
        return Result.fail("添加失败");
    }

    @Override
    public Result<?> update(Goods goods) {
        if(goods.getId() == null || goods.getId() <= 0){
            return Result.fail("查询商品id不合法");
        }
        int result = this.goodsMapper.updateById(goods);
        if(result == 1){
            return Result.success(goods,"更新商品成功");
        }
        return Result.fail("更新商品失败");
    }

    @Override
    public void delete(Integer id) {
        this.goodsMapper.deleteById(id);
    }

    @Override
    public Result<?> deleteList(List<Integer> ids) {
        for(Integer id : ids){
            this.delete(id);
        }
        return Result.success("批量删除成功");
    }

    @Override
    public Result<?> selectPage(Goods goods, Integer pageNum, Integer pageSize) {
        MPJLambdaWrapper wrapper = JoinWrappers.lambda(Goods.class)
                .selectAll(Goods.class)
                .selectAs(Type::getName,"typeName")
                .selectAs(User::getName,"businessName")
                .selectAs(Activity::getDiscount,"activityDiscount")
                .selectAs(Activity::getPrice,"activityPrice")
                .selectAs(Activity::getName,"activityName")
                .selectAs(Activity::getDescription,"activityDescription")
                .leftJoin(User.class,User::getId,Goods::getBusinessId)
                .leftJoin(Type.class,Type::getId,Goods::getTypeId)
                .leftJoin(Activity.class,Activity::getId,Goods::getActivityId)
//                .eq(Goods::getBusinessId,goods.getBusinessId())
                .like(Goods::getName,goods.getName());
        User userById = userService.getUserById(goods.getBusinessId());
        if(userById.getRoleIdList().get(0) != 1){
            wrapper.eq("t.business_id",goods.getBusinessId());
        }
        Page page = goodsMapper.selectJoinPage(new Page(pageNum, pageSize), Goods.class, wrapper);
        if (page.getTotal() == 0) {
            return Result.fail("没有查询到你所需要的信息");
        }
        return Result.success(page, "获取所有信息成功");
    }

    @Override
    public List<Goods> selectByBusinessId(Integer id) {
        return goodsMapper.selectByBusinessId(id);
    }

    @Override
    public Goods selectById(Integer id) {

        return  goodsMapper.selectById(id);
    }

    @Override
    public List<Goods> selectByName(String name) {
        return goodsMapper.selectByName(name);
    }

    @Override
    public List<Goods> selectByTypeId(Integer id) {
        return goodsMapper.selectByTypeId(id);
    }

//    public List<Goods> recommend() {
//
//        // 用户的哪些行为可以认为他跟商品产生了关系？收藏、加入购物车、下单、评论
//        // 1. 获取所有的收藏信息
////        List<Collect> allCollects =
//        Result result1 = collectService.selectPage(null, 1, 10);
//        Object data1 = result1.getData();
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

}
