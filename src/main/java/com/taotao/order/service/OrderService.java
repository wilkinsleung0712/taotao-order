package com.taotao.order.service;

import java.util.List;

import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbOrder;
import com.taotao.pojo.TbOrderItem;
import com.taotao.pojo.TbOrderShipping;

public interface OrderService {

    /**
     * 新建一个新的ORDER
     * 
     * @param order - 对应订单表的pojo。
     * @param orderList - 订单明细表对应的商品列表。每个元素是订单明细表对应的pojo
     * @param orderShipping - 物流表对应的pojo
     * @return
     */
    public TaotaoResult createOrder(TbOrder order, List<TbOrderItem> orderList,
            TbOrderShipping orderShipping);
}
