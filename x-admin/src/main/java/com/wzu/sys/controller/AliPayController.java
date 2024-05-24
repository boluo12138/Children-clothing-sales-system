package com.wzu.sys.controller;

import cn.hutool.json.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.wzu.common.vo.Result;
import com.wzu.config.AlipayConfig;
import com.wzu.sys.entity.Orders;
import com.wzu.sys.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
/**
 * 功能：阿里支付前端控制器
 * 作者：菠萝
 * 日期：2024/4/14 20:21
 */
@RestController
@RequestMapping("/alipay")
public class AliPayController {

    // 支付宝沙箱网关地址
    private static final String GATEWAY_URL = "https://openapi-sandbox.dl.alipaydev.com/gateway.do";
    private static final String FORMAT = "JSON";
    private static final String CHARSET = "UTF-8";
    private static final String SIGN_TYPE = "RSA2";
    @Resource
    private AlipayConfig alipayConfig;
    @Resource
    private OrderService ordersService;

    @GetMapping("/pay") //alipay/pay?orderNo=xxx
    public Result pay(String oid,HttpServletResponse httpResponse) throws Exception {
        //查询订单信息
        Orders orders = ordersService.selectByOrderID(oid);
        if(orders==null) return Result.fail("订单不存在");
        //1. 创建CLinet，通过SDK提供的Client，负责调用支付宝的API
        AlipayClient alipayClient = new DefaultAlipayClient(GATEWAY_URL, alipayConfig.getAppId(),
                alipayConfig.getAppPrivateKey(), FORMAT, CHARSET, alipayConfig.getAppPublicKey(), SIGN_TYPE);

        //2. 创建Request并设置Request参数
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest(); //发送请求的类
        request.setNotifyUrl(alipayConfig.getNotifyUrl());
        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", orders.getOrderId());  //订单编号
        bizContent.put("total_amount", orders.getPrice());   //订单总金额
        bizContent.put("subject", orders.getBusinessId());  //支付的酒店名
        bizContent.put("product_code", "FAST_INSTANT_TRADE_PAY");
        request.setBizContent(bizContent.toString());
        request.setReturnUrl("http://localhost:8888/#/orders"); //支付成功返回至订单界面
        orders.setStatus("待发货");
        ordersService.update(orders);
        //执行请求，拿到响应的结果，返回浏览器
        String form = "";
        try {
            form = alipayClient.pageExecute(request).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        httpResponse.setContentType("text/html;charset=" + CHARSET);
        httpResponse.getWriter().write(form);
        httpResponse.getWriter().flush();
        httpResponse.getWriter().close();
        return Result.success(form);
    }
    @PostMapping("/notify")  // 注意这里必须是POST接口
    public void payNotify(HttpServletRequest request) throws Exception {
        if (request.getParameter("trade_status").equals("TRADE_SUCCESS")) {
            System.out.println("=========支付宝异步回调========");
            Map<String, String> params = new HashMap<>();
            Map<String,String[]>requestParams = request.getParameterMap();
            for (String name : requestParams.keySet()) {
                params.put(name,request.getParameter(name));
            }
            String sign = params.get("sign");
            String content = AlipaySignature.getSignCheckContentV1(params);
            boolean checkSignature =AlipaySignature.rsa256CheckContent(content,sign,alipayConfig.getAppPublicKey(),"UTF-8");
            //支付宝验证
            if(checkSignature){
                //输出一些支付信息
                System.out.println("交易金额"+params.get("total_amount"));
                String oid = params.get("out_trade_no");
                Orders orders = ordersService.selectByOrderID(oid);
                orders.setStatus("待发货");
                ordersService.update(orders);
            }
        }

    }
}
