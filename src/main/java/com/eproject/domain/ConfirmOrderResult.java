package com.eproject.domain;

import com.eproject.entity.Cart;
import com.eproject.entity.UserReceiveAddress;

import java.math.BigDecimal;
import java.util.List;

/**
 * 确认订单信息封装
 */
public class ConfirmOrderResult {
    // 用户收货地址列表
    private List<UserReceiveAddress> userReceiveAddressList;

    //购物车信息
    private List<Cart> cartItemList;

    //计算金额
    private CalcAmount calcAmount;

    public void setCalcAmount(CalcAmount calcAmount) {
        this.calcAmount = calcAmount;
    }
    public List<Cart> getCartPromotionItemList() {
        return cartItemList;
    }

    public void setCartPromotionItemList(List<Cart> cartItemList) {
        this.cartItemList = cartItemList;
    }
    public List<UserReceiveAddress> getMemberReceiveAddressList() {
        return userReceiveAddressList;
    }

    public void setMemberReceiveAddressList(List<UserReceiveAddress> userReceiveAddressList) {
        this.userReceiveAddressList = userReceiveAddressList;
    }
    public CalcAmount getCalcAmount() {
        return calcAmount;
    }
    public static class CalcAmount{
        //订单商品总金额
        private BigDecimal totalAmount;
        //运费
        private BigDecimal freightAmount;
        //应付金额
        private BigDecimal payAmount;

        public BigDecimal getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(BigDecimal totalAmount) {
            this.totalAmount = totalAmount;
        }


        public BigDecimal getFreightAmount() {
            return freightAmount;
        }

        public void setFreightAmount(BigDecimal freightAmount) {
            this.freightAmount = freightAmount;
        }

        public BigDecimal getPayAmount() {
            return payAmount;
        }

        public void setPayAmount(BigDecimal payAmount) {
            this.payAmount = payAmount;
        }

    }
}
