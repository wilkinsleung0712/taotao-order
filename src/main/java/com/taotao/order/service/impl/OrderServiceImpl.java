package com.taotao.order.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.mapper.TbOrderItemMapper;
import com.taotao.mapper.TbOrderMapper;
import com.taotao.mapper.TbOrderShippingMapper;
import com.taotao.order.dao.JedisClient;
import com.taotao.order.service.OrderService;
import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbOrder;
import com.taotao.pojo.TbOrderItem;
import com.taotao.pojo.TbOrderShipping;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private TbOrderMapper orderMapper;

    @Autowired
    private TbOrderItemMapper orderItemMapper;

    @Autowired
    private TbOrderShippingMapper orderShippingMapper;

    @Autowired
    private JedisClient jediusClient;

    @Value("${ORDER_GEN_KEY}")
    private String ORDER_GEN_KEY;

    @Value("${ORDER_INIT_ID}")
    private String ORDER_INIT_ID;

    @Value("${ORDER_DETAIL_GEN_KEY}")
    private String ORDER_DETAIL_GEN_KEY;

    @Override
    public TaotaoResult createOrder(TbOrder order, List<TbOrderItem> orderList,
            TbOrderShipping orderShipping) {

        // 向订单表中插入记录
        // 获得订单号,补全pojo属性
        // 先取一下检验是否有值
        String key = jediusClient.get(ORDER_GEN_KEY);
        if (StringUtils.isBlank(key)) {
            // 如果之前没有订单号,需要首先设置一个初始化订单号
            jediusClient.set(ORDER_GEN_KEY, ORDER_INIT_ID);
        }
        // 提取一个自增的订单号
        Long orderId = jediusClient.incr(ORDER_GEN_KEY);

        order.setOrderId(orderId + "");
        // 状态：1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭
        order.setStatus(1);
        // 0：未评价 1：已评价
        order.setBuyerRate(0);
        order.setCreateTime(new Date());
        order.setUpdateTime(new Date());

        orderMapper.insert(order);
        // 插入订单明细
        for (TbOrderItem orderItem : orderList) {
            // 补全订单明细
            // 补全订单明细编号
            long orderDetailId = jediusClient.incr(ORDER_DETAIL_GEN_KEY);
            orderItem.setId(orderDetailId + "");
            orderItem.setOrderId(orderId + "");
            orderItemMapper.insert(orderItem);
        }
        // 插入物流表
        // 补全物流表属性
        orderShipping.setCreated(new Date());
        orderShipping.setUpdated(new Date());
        orderShipping.setOrderId(orderId + "");
        orderShippingMapper.insert(orderShipping);

        return TaotaoResult.ok(orderId);
    }

}
