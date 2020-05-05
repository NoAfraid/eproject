package com.eproject.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

/**
 * product
 *
 * @author
 */
public class Product implements Serializable {
    private Integer id;

    private Integer categoryId;

    /**
     * 产品名
     */
    private String productName;

    /**
     * 产品图
     */
    private String productImg;

    /**
     * 产品编号
     */
    private String productSn;

    /**
     * 详情
     */
    private String detail;

    /**
     * 销量
     */
    private Integer sale;

    /**
     * 库存
     */
    private Integer stock;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 促销价格
     */
    private BigDecimal promotePrice;

    /**
     * 排序
     */
    private String sort;

    /**
     * 产品描述
     */
    private String description;

    /**
     * 推荐状态；0->不推荐；1->推荐
     */
    private Integer recomandStatus;

    /**
     * 单位
     */
    private String unit;

    /**
     * 是否删除（1是，0否）
     */
    private Integer deleteStatus;

    /**
     * 审核状态：0->未审核；1->审核通过
     */
    private Integer verifyStatus;

    /**
     * 上架状态：0->下架；1->上架
     */
    private Integer publishStatus;

    /**
     * 搜索次数
     */
    private Integer searchCount;

    private static final long serialVersionUID = 1L;

//    public Product(Map<String, Object> params) {
//    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public String getProductSn() {
        return productSn;
    }

    public void setProductSn(String productSn) {
        this.productSn = productSn;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Integer getSale() {
        return sale;
    }

    public void setSale(Integer sale) {
        this.sale = sale;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPromotePrice() {
        return promotePrice;
    }

    public void setPromotePrice(BigDecimal promotePrice) {
        this.promotePrice = promotePrice;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getRecomandStatus() {
        return recomandStatus;
    }

    public void setRecomandStatus(Integer recomandStatus) {
        this.recomandStatus = recomandStatus;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(Integer deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public Integer getVerifyStatus() {
        return verifyStatus;
    }

    public void setVerifyStatus(Integer verifyStatus) {
        this.verifyStatus = verifyStatus;
    }

    public Integer getPublishStatus() {
        return searchCount;
    }

    public void setPublishStatus(Integer publishStatus) {
        this.searchCount = searchCount;
    }

    public Integer getSearchCount() {
        return searchCount;
    }

    public void setSearchCount(Integer searchCount) {
        this.searchCount = searchCount;
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
        Product other = (Product) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getProductName() == null ? other.getProductName() == null : this.getProductName().equals(other.getProductName()))
                && (this.getProductImg() == null ? other.getProductImg() == null : this.getProductImg().equals(other.getProductImg()))
                && (this.getProductSn() == null ? other.getProductSn() == null : this.getProductSn().equals(other.getProductSn()))
                && (this.getDetail() == null ? other.getDetail() == null : this.getDetail().equals(other.getDetail()))
                && (this.getSale() == null ? other.getSale() == null : this.getSale().equals(other.getSale()))
                && (this.getStock() == null ? other.getStock() == null : this.getStock().equals(other.getStock()))
                && (this.getPrice() == null ? other.getPrice() == null : this.getPrice().equals(other.getPrice()))
                && (this.getPromotePrice() == null ? other.getPromotePrice() == null : this.getPromotePrice().equals(other.getPromotePrice()))
                && (this.getSort() == null ? other.getSort() == null : this.getSort().equals(other.getSort()))
                && (this.getDescription() == null ? other.getDescription() == null : this.getDescription().equals(other.getDescription()))
                && (this.getRecomandStatus() == null ? other.getRecomandStatus() == null : this.getRecomandStatus().equals(other.getRecomandStatus()))
                && (this.getUnit() == null ? other.getUnit() == null : this.getUnit().equals(other.getUnit()))
                && (this.getDeleteStatus() == null ? other.getDeleteStatus() == null : this.getDeleteStatus().equals(other.getDeleteStatus()))
                && (this.getVerifyStatus() == null ? other.getVerifyStatus() == null : this.getVerifyStatus().equals(other.getVerifyStatus()))
                && (this.getPublishStatus() == null ? other.getPublishStatus() == null : this.getPublishStatus().equals(other.getPublishStatus()))
                && (this.getCategoryId() == null ? other.getCategoryId() == null : this.getCategoryId().equals(other.getCategoryId()))
                && (this.getSearchCount() == null ? other.getSearchCount() == null : this.getSearchCount().equals(other.getSearchCount()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getProductName() == null) ? 0 : getProductName().hashCode());
        result = prime * result + ((getProductImg() == null) ? 0 : getProductImg().hashCode());
        result = prime * result + ((getProductSn() == null) ? 0 : getProductSn().hashCode());
        result = prime * result + ((getDetail() == null) ? 0 : getDetail().hashCode());
        result = prime * result + ((getSale() == null) ? 0 : getSale().hashCode());
        result = prime * result + ((getStock() == null) ? 0 : getStock().hashCode());
        result = prime * result + ((getPrice() == null) ? 0 : getPrice().hashCode());
        result = prime * result + ((getPromotePrice() == null) ? 0 : getPromotePrice().hashCode());
        result = prime * result + ((getSort() == null) ? 0 : getSort().hashCode());
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
        result = prime * result + ((getRecomandStatus() == null) ? 0 : getRecomandStatus().hashCode());
        result = prime * result + ((getUnit() == null) ? 0 : getUnit().hashCode());
        result = prime * result + ((getDeleteStatus() == null) ? 0 : getDeleteStatus().hashCode());
        result = prime * result + ((getVerifyStatus() == null) ? 0 : getVerifyStatus().hashCode());
        result = prime * result + ((getPublishStatus() == null) ? 0 : getPublishStatus().hashCode());
        result = prime * result + ((getCategoryId() == null) ? 0 : getCategoryId().hashCode());
        result = prime * result + ((getSearchCount() == null) ? 0 : getSearchCount().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", productName=").append(productName);
        sb.append(", productImg=").append(productImg);
        sb.append(", productSn=").append(productSn);
        sb.append(", detail=").append(detail);
        sb.append(", sale=").append(sale);
        sb.append(", stock=").append(stock);
        sb.append(", price=").append(price);
        sb.append(", promotePrice=").append(promotePrice);
        sb.append(", sort=").append(sort);
        sb.append(", description=").append(description);
        sb.append(", recomandStatus=").append(recomandStatus);
        sb.append(", unit=").append(unit);
        sb.append(", deleteStatus=").append(deleteStatus);
        sb.append(", verifyStatus=").append(verifyStatus);
        sb.append(", publishStatus=").append(publishStatus);
        sb.append(", CategoryId=").append(categoryId);
        sb.append(", searchCount=").append(searchCount);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}