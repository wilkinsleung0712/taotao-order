package com.taotao.order.pojo;

import java.util.List;

import com.taotao.pojo.TbOrder;
import com.taotao.pojo.TbOrderItem;
import com.taotao.pojo.TbOrderShipping;

/**
 * Extends TbOrder to allow fe to pass information to be controller
 * 
 * @author WEIQIANG LIANG
 *
 */
public class Order extends TbOrder {
    private List<TbOrderItem> orderItems;
    private TbOrderShipping orderShipping;
    /**
     * @return the orderItems
     */
    public List<TbOrderItem> getOrderItems() {
        return orderItems;
    }
    /**
     * @param orderItems the orderItems to set
     */
    public void setOrderItems(List<TbOrderItem> orderItems) {
        this.orderItems = orderItems;
    }
    /**
     * @return the orderShipping
     */
    public TbOrderShipping getOrderShipping() {
        return orderShipping;
    }
    /**
     * @param orderShipping the orderShipping to set
     */
    public void setOrderShipping(TbOrderShipping orderShipping) {
        this.orderShipping = orderShipping;
    }

   

}
