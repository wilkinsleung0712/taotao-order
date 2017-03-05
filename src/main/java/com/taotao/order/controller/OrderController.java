package com.taotao.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.order.pojo.Order;
import com.taotao.order.service.OrderService;
import com.taotao.pojo.TaotaoResult;
import com.taotao.util.ExceptionUtil;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping("/create")
    @ResponseBody
    public TaotaoResult createOrder(@RequestBody Order order) {
        TaotaoResult result = null;
        try {
            result = orderService.createOrder(order, order.getOrderItems(),
                    order.getOrderShipping());

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            result = TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
        return result;
    }
}
