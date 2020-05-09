package com.eproject.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * alipay
 * @author 
 */
public class Alipay implements Serializable {
    private Integer id;

    /**
     * 订单id
     */
    private Integer orderId;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 产品id
     */
    private Integer productId;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 退款原因
     */
    private String refundReason;

    /**
     * 退款金额
     */
    private BigDecimal refundAmount;

    /**
     * 总金额
     */
    private BigDecimal totalPrice;

    /**
     * 标识一次退款请求，同一笔交易多次退款需要保证唯一
     */
    private String outrequestno;

    /**
     * 订单状态
     */
    private Integer orderStatus;

    /**
     * 账号余额（测试所用）
     */
    private BigDecimal accountAmount;

    /**
     * 退货时间
     */
    private Date refundTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getRefundReason() {
        return refundReason;
    }

    public void setRefundReason(String refundReason) {
        this.refundReason = refundReason;
    }

    public BigDecimal getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(BigDecimal refundAmount) {
        this.refundAmount = refundAmount;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getOutrequestno() {
        return outrequestno;
    }

    public void setOutrequestno(String outrequestno) {
        this.outrequestno = outrequestno;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public BigDecimal getAccountAmount() {
        return accountAmount;
    }

    public void setAccountAmount(BigDecimal accountAmount) {
        this.accountAmount = accountAmount;
    }

    public Date getRefundTime() {
        return refundTime;
    }

    public void setRefundTime(Date refundTime) {
        this.refundTime = refundTime;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Alipay other = (Alipay) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getOrderId() == null ? other.getOrderId() == null : this.getOrderId().equals(other.getOrderId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getProductId() == null ? other.getProductId() == null : this.getProductId().equals(other.getProductId()))
            && (this.getOrderNo() == null ? other.getOrderNo() == null : this.getOrderNo().equals(other.getOrderNo()))
            && (this.getRefundReason() == null ? other.getRefundReason() == null : this.getRefundReason().equals(other.getRefundReason()))
            && (this.getRefundAmount() == null ? other.getRefundAmount() == null : this.getRefundAmount().equals(other.getRefundAmount()))
            && (this.getTotalPrice() == null ? other.getTotalPrice() == null : this.getTotalPrice().equals(other.getTotalPrice()))
            && (this.getOutrequestno() == null ? other.getOutrequestno() == null : this.getOutrequestno().equals(other.getOutrequestno()))
            && (this.getOrderStatus() == null ? other.getOrderStatus() == null : this.getOrderStatus().equals(other.getOrderStatus()))
            && (this.getAccountAmount() == null ? other.getAccountAmount() == null : this.getAccountAmount().equals(other.getAccountAmount()))
            && (this.getRefundTime() == null ? other.getRefundTime() == null : this.getRefundTime().equals(other.getRefundTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getOrderId() == null) ? 0 : getOrderId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getProductId() == null) ? 0 : getProductId().hashCode());
        result = prime * result + ((getOrderNo() == null) ? 0 : getOrderNo().hashCode());
        result = prime * result + ((getRefundReason() == null) ? 0 : getRefundReason().hashCode());
        result = prime * result + ((getRefundAmount() == null) ? 0 : getRefundAmount().hashCode());
        result = prime * result + ((getTotalPrice() == null) ? 0 : getTotalPrice().hashCode());
        result = prime * result + ((getOutrequestno() == null) ? 0 : getOutrequestno().hashCode());
        result = prime * result + ((getOrderStatus() == null) ? 0 : getOrderStatus().hashCode());
        result = prime * result + ((getAccountAmount() == null) ? 0 : getAccountAmount().hashCode());
        result = prime * result + ((getRefundTime() == null) ? 0 : getRefundTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", orderId=").append(orderId);
        sb.append(", userId=").append(userId);
        sb.append(", productId=").append(productId);
        sb.append(", orderNo=").append(orderNo);
        sb.append(", refundReason=").append(refundReason);
        sb.append(", refundAmount=").append(refundAmount);
        sb.append(", totalPrice=").append(totalPrice);
        sb.append(", outrequestno=").append(outrequestno);
        sb.append(", orderStatus=").append(orderStatus);
        sb.append(", accountAmount=").append(accountAmount);
        sb.append(", refundTime=").append(refundTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}