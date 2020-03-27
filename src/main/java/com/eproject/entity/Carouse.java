package com.eproject.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * carouse
 * @author 
 */
public class Carouse implements Serializable {
    private Integer id;

    /**
     * 轮播图URL
     */
    private String carouseUrl;

    /**
     * 跳转URL
     */
    private String redirectUrl;

    /**
     * 轮播图名
     */
    private String carouseName;

    /**
     * 轮播图比重，比重越大，越靠前
     */
    private Integer carouseRank;

    /**
     * 管理员id
     */
    private Integer managerId;

    /**
     * 商品id
     */
    private Integer productId;

    /**
     * 商品图
     */
    private String productImg;

    /**
     * 创建时间
     */
    private Date creatTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 删除状态0未删除，1删除
     */
    private Integer deleteStatus;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCarouseUrl() {
        return carouseUrl;
    }

    public void setCarouseUrl(String carouseUrl) {
        this.carouseUrl = carouseUrl;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getCarouseName() {
        return carouseName;
    }

    public void setCarouseName(String carouseName) {
        this.carouseName = carouseName;
    }

    public Integer getCarouseRank() {
        return carouseRank;
    }

    public void setCarouseRank(Integer carouseRank) {
        this.carouseRank = carouseRank;
    }

    public Integer getManagerId() {
        return managerId;
    }

    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(Integer deleteStatus) {
        this.deleteStatus = deleteStatus;
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
        Carouse other = (Carouse) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getCarouseUrl() == null ? other.getCarouseUrl() == null : this.getCarouseUrl().equals(other.getCarouseUrl()))
            && (this.getRedirectUrl() == null ? other.getRedirectUrl() == null : this.getRedirectUrl().equals(other.getRedirectUrl()))
            && (this.getCarouseName() == null ? other.getCarouseName() == null : this.getCarouseName().equals(other.getCarouseName()))
            && (this.getCarouseRank() == null ? other.getCarouseRank() == null : this.getCarouseRank().equals(other.getCarouseRank()))
            && (this.getManagerId() == null ? other.getManagerId() == null : this.getManagerId().equals(other.getManagerId()))
            && (this.getProductId() == null ? other.getProductId() == null : this.getProductId().equals(other.getProductId()))
            && (this.getProductImg() == null ? other.getProductImg() == null : this.getProductImg().equals(other.getProductImg()))
            && (this.getCreatTime() == null ? other.getCreatTime() == null : this.getCreatTime().equals(other.getCreatTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getDeleteStatus() == null ? other.getDeleteStatus() == null : this.getDeleteStatus().equals(other.getDeleteStatus()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getCarouseUrl() == null) ? 0 : getCarouseUrl().hashCode());
        result = prime * result + ((getRedirectUrl() == null) ? 0 : getRedirectUrl().hashCode());
        result = prime * result + ((getCarouseName() == null) ? 0 : getCarouseName().hashCode());
        result = prime * result + ((getCarouseRank() == null) ? 0 : getCarouseRank().hashCode());
        result = prime * result + ((getManagerId() == null) ? 0 : getManagerId().hashCode());
        result = prime * result + ((getProductId() == null) ? 0 : getProductId().hashCode());
        result = prime * result + ((getProductImg() == null) ? 0 : getProductImg().hashCode());
        result = prime * result + ((getCreatTime() == null) ? 0 : getCreatTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getDeleteStatus() == null) ? 0 : getDeleteStatus().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", carouseUrl=").append(carouseUrl);
        sb.append(", redirectUrl=").append(redirectUrl);
        sb.append(", carouseName=").append(carouseName);
        sb.append(", carouseRank=").append(carouseRank);
        sb.append(", managerId=").append(managerId);
        sb.append(", productId=").append(productId);
        sb.append(", productImg=").append(productImg);
        sb.append(", creatTime=").append(creatTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", deleteStatus=").append(deleteStatus);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}