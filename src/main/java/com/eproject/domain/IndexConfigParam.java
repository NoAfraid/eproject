package com.eproject.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
public class IndexConfigParam implements Serializable {

    @ApiModelProperty("商品id")
    private Integer productId;
    @ApiModelProperty("商品名")
    private String productName;
    @ApiModelProperty("商品图")
    private String productImg;
    @ApiModelProperty("产品描述")
    private String description;
    @ApiModelProperty("促销价格")
    private BigDecimal promotePrice;
    @ApiModelProperty("商品标签")
    private String tag;

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPromotePrice(BigDecimal promotePrice) {
        this.promotePrice = promotePrice;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductImg() {
        return productImg;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPromotePrice() {
        return promotePrice;
    }

    public String getTag() {
        return tag;
    }
}
