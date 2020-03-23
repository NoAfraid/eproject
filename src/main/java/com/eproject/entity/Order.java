package com.eproject.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * order
 * @author 
 */
public class Order implements Serializable {
    private Integer id;

    /**
     * 订单编号
     */
    private Integer orderNo;

    /**
     * 商品id
     */
    private Integer productId;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 支付时间
     */
    private Date payTime;

    /**
     * 总价格
     */
    private BigDecimal totalPrice;

    /**
     * 快递公司
     */
    private String deliveryCompany;

    /**
     * 快递单号
     */
    private Integer deliveryNo;

    /**
     * 自动确认为时间
     */
    private Date autoConfirmDay;

    /**
     * 收票人邮箱
     */
    private String billReceiverEmail;

    /**
     * 收票人所在地编码
     */
    private String receiverPostCode;

    /**
     * 备注
     */
    private String note;

    /**
     * 收票人详细地址
     */
    private String receiverDetailAdress;

    /**
     * 收票人坐在城市
     */
    private String receiverCity;

    /**
     * 收票人姓名
     */
    private String receiverName;

    /**
     * 收票人省区
     */
    private String receiverProvince;

    /**
     * 发票类型：0->不开发票；1->电子发票；2->纸质发票
     */
    private String billType;

    /**
     * 收票人电话
     */
    private String receiverPhone;

    /**
     * 发票内容
     */
    private String billContent;

    /**
     * 发票抬头
     */
    private String billHeader;

    /**
     * 支付方式
     */
    private String payType;

    /**
     * 订单状态 0->未确认；1->已确认
     */
    private Integer orderStatus;

    /**
     * 支付状态 0->未支付；1->已支付
     */
    private Integer payStatus;

    /**
     * 发货时间
     */
    private Date deliveryTime;

    /**
     * 修改时间
     */
    private Date updataTime;

    /**
     * 删除状态：0->未删除；1->已删除
     */
    private Integer daleteStatus;

    /**
     * 确认收货时间
     */
    private Date receiveTime;

    /**
     * 运费
     */
    private BigDecimal freightAmount;

    /**
     * 商品名
     */
    private String productName;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getDeliveryCompany() {
        return deliveryCompany;
    }

    public void setDeliveryCompany(String deliveryCompany) {
        this.deliveryCompany = deliveryCompany;
    }

    public Integer getDeliveryNo() {
        return deliveryNo;
    }

    public void setDeliveryNo(Integer deliveryNo) {
        this.deliveryNo = deliveryNo;
    }

    public Date getAutoConfirmDay() {
        return autoConfirmDay;
    }

    public void setAutoConfirmDay(Date autoConfirmDay) {
        this.autoConfirmDay = autoConfirmDay;
    }

    public String getBillReceiverEmail() {
        return billReceiverEmail;
    }

    public void setBillReceiverEmail(String billReceiverEmail) {
        this.billReceiverEmail = billReceiverEmail;
    }

    public String getReceiverPostCode() {
        return receiverPostCode;
    }

    public void setReceiverPostCode(String receiverPostCode) {
        this.receiverPostCode = receiverPostCode;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getReceiverDetailAdress() {
        return receiverDetailAdress;
    }

    public void setReceiverDetailAdress(String receiverDetailAdress) {
        this.receiverDetailAdress = receiverDetailAdress;
    }

    public String getReceiverCity() {
        return receiverCity;
    }

    public void setReceiverCity(String receiverCity) {
        this.receiverCity = receiverCity;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverProvince() {
        return receiverProvince;
    }

    public void setReceiverProvince(String receiverProvince) {
        this.receiverProvince = receiverProvince;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    public String getBillContent() {
        return billContent;
    }

    public void setBillContent(String billContent) {
        this.billContent = billContent;
    }

    public String getBillHeader() {
        return billHeader;
    }

    public void setBillHeader(String billHeader) {
        this.billHeader = billHeader;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public Date getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public Date getUpdataTime() {
        return updataTime;
    }

    public void setUpdataTime(Date updataTime) {
        this.updataTime = updataTime;
    }

    public Integer getDaleteStatus() {
        return daleteStatus;
    }

    public void setDaleteStatus(Integer daleteStatus) {
        this.daleteStatus = daleteStatus;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    public BigDecimal getFreightAmount() {
        return freightAmount;
    }

    public void setFreightAmount(BigDecimal freightAmount) {
        this.freightAmount = freightAmount;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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
        Order other = (Order) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getOrderNo() == null ? other.getOrderNo() == null : this.getOrderNo().equals(other.getOrderNo()))
            && (this.getProductId() == null ? other.getProductId() == null : this.getProductId().equals(other.getProductId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getPayTime() == null ? other.getPayTime() == null : this.getPayTime().equals(other.getPayTime()))
            && (this.getTotalPrice() == null ? other.getTotalPrice() == null : this.getTotalPrice().equals(other.getTotalPrice()))
            && (this.getDeliveryCompany() == null ? other.getDeliveryCompany() == null : this.getDeliveryCompany().equals(other.getDeliveryCompany()))
            && (this.getDeliveryNo() == null ? other.getDeliveryNo() == null : this.getDeliveryNo().equals(other.getDeliveryNo()))
            && (this.getAutoConfirmDay() == null ? other.getAutoConfirmDay() == null : this.getAutoConfirmDay().equals(other.getAutoConfirmDay()))
            && (this.getBillReceiverEmail() == null ? other.getBillReceiverEmail() == null : this.getBillReceiverEmail().equals(other.getBillReceiverEmail()))
            && (this.getReceiverPostCode() == null ? other.getReceiverPostCode() == null : this.getReceiverPostCode().equals(other.getReceiverPostCode()))
            && (this.getNote() == null ? other.getNote() == null : this.getNote().equals(other.getNote()))
            && (this.getReceiverDetailAdress() == null ? other.getReceiverDetailAdress() == null : this.getReceiverDetailAdress().equals(other.getReceiverDetailAdress()))
            && (this.getReceiverCity() == null ? other.getReceiverCity() == null : this.getReceiverCity().equals(other.getReceiverCity()))
            && (this.getReceiverName() == null ? other.getReceiverName() == null : this.getReceiverName().equals(other.getReceiverName()))
            && (this.getReceiverProvince() == null ? other.getReceiverProvince() == null : this.getReceiverProvince().equals(other.getReceiverProvince()))
            && (this.getBillType() == null ? other.getBillType() == null : this.getBillType().equals(other.getBillType()))
            && (this.getReceiverPhone() == null ? other.getReceiverPhone() == null : this.getReceiverPhone().equals(other.getReceiverPhone()))
            && (this.getBillContent() == null ? other.getBillContent() == null : this.getBillContent().equals(other.getBillContent()))
            && (this.getBillHeader() == null ? other.getBillHeader() == null : this.getBillHeader().equals(other.getBillHeader()))
            && (this.getPayType() == null ? other.getPayType() == null : this.getPayType().equals(other.getPayType()))
            && (this.getOrderStatus() == null ? other.getOrderStatus() == null : this.getOrderStatus().equals(other.getOrderStatus()))
            && (this.getPayStatus() == null ? other.getPayStatus() == null : this.getPayStatus().equals(other.getPayStatus()))
            && (this.getDeliveryTime() == null ? other.getDeliveryTime() == null : this.getDeliveryTime().equals(other.getDeliveryTime()))
            && (this.getUpdataTime() == null ? other.getUpdataTime() == null : this.getUpdataTime().equals(other.getUpdataTime()))
            && (this.getDaleteStatus() == null ? other.getDaleteStatus() == null : this.getDaleteStatus().equals(other.getDaleteStatus()))
            && (this.getReceiveTime() == null ? other.getReceiveTime() == null : this.getReceiveTime().equals(other.getReceiveTime()))
            && (this.getFreightAmount() == null ? other.getFreightAmount() == null : this.getFreightAmount().equals(other.getFreightAmount()))
            && (this.getProductName() == null ? other.getProductName() == null : this.getProductName().equals(other.getProductName()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getOrderNo() == null) ? 0 : getOrderNo().hashCode());
        result = prime * result + ((getProductId() == null) ? 0 : getProductId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getPayTime() == null) ? 0 : getPayTime().hashCode());
        result = prime * result + ((getTotalPrice() == null) ? 0 : getTotalPrice().hashCode());
        result = prime * result + ((getDeliveryCompany() == null) ? 0 : getDeliveryCompany().hashCode());
        result = prime * result + ((getDeliveryNo() == null) ? 0 : getDeliveryNo().hashCode());
        result = prime * result + ((getAutoConfirmDay() == null) ? 0 : getAutoConfirmDay().hashCode());
        result = prime * result + ((getBillReceiverEmail() == null) ? 0 : getBillReceiverEmail().hashCode());
        result = prime * result + ((getReceiverPostCode() == null) ? 0 : getReceiverPostCode().hashCode());
        result = prime * result + ((getNote() == null) ? 0 : getNote().hashCode());
        result = prime * result + ((getReceiverDetailAdress() == null) ? 0 : getReceiverDetailAdress().hashCode());
        result = prime * result + ((getReceiverCity() == null) ? 0 : getReceiverCity().hashCode());
        result = prime * result + ((getReceiverName() == null) ? 0 : getReceiverName().hashCode());
        result = prime * result + ((getReceiverProvince() == null) ? 0 : getReceiverProvince().hashCode());
        result = prime * result + ((getBillType() == null) ? 0 : getBillType().hashCode());
        result = prime * result + ((getReceiverPhone() == null) ? 0 : getReceiverPhone().hashCode());
        result = prime * result + ((getBillContent() == null) ? 0 : getBillContent().hashCode());
        result = prime * result + ((getBillHeader() == null) ? 0 : getBillHeader().hashCode());
        result = prime * result + ((getPayType() == null) ? 0 : getPayType().hashCode());
        result = prime * result + ((getOrderStatus() == null) ? 0 : getOrderStatus().hashCode());
        result = prime * result + ((getPayStatus() == null) ? 0 : getPayStatus().hashCode());
        result = prime * result + ((getDeliveryTime() == null) ? 0 : getDeliveryTime().hashCode());
        result = prime * result + ((getUpdataTime() == null) ? 0 : getUpdataTime().hashCode());
        result = prime * result + ((getDaleteStatus() == null) ? 0 : getDaleteStatus().hashCode());
        result = prime * result + ((getReceiveTime() == null) ? 0 : getReceiveTime().hashCode());
        result = prime * result + ((getFreightAmount() == null) ? 0 : getFreightAmount().hashCode());
        result = prime * result + ((getProductName() == null) ? 0 : getProductName().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", orderNo=").append(orderNo);
        sb.append(", productId=").append(productId);
        sb.append(", userId=").append(userId);
        sb.append(", payTime=").append(payTime);
        sb.append(", totalPrice=").append(totalPrice);
        sb.append(", deliveryCompany=").append(deliveryCompany);
        sb.append(", deliveryNo=").append(deliveryNo);
        sb.append(", autoConfirmDay=").append(autoConfirmDay);
        sb.append(", billReceiverEmail=").append(billReceiverEmail);
        sb.append(", receiverPostCode=").append(receiverPostCode);
        sb.append(", note=").append(note);
        sb.append(", receiverDetailAdress=").append(receiverDetailAdress);
        sb.append(", receiverCity=").append(receiverCity);
        sb.append(", receiverName=").append(receiverName);
        sb.append(", receiverProvince=").append(receiverProvince);
        sb.append(", billType=").append(billType);
        sb.append(", receiverPhone=").append(receiverPhone);
        sb.append(", billContent=").append(billContent);
        sb.append(", billHeader=").append(billHeader);
        sb.append(", payType=").append(payType);
        sb.append(", orderStatus=").append(orderStatus);
        sb.append(", payStatus=").append(payStatus);
        sb.append(", deliveryTime=").append(deliveryTime);
        sb.append(", updataTime=").append(updataTime);
        sb.append(", daleteStatus=").append(daleteStatus);
        sb.append(", receiveTime=").append(receiveTime);
        sb.append(", freightAmount=").append(freightAmount);
        sb.append(", productName=").append(productName);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}