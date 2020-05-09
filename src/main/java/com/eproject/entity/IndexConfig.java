package com.eproject.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * index_config
 * @author 
 */
public class IndexConfig implements Serializable {
    /**
     * 首页配置项主键id
     */
    private Integer id;

    /**
     * 显示字符(配置搜索时不可为空，其他可为空)
     */
    private String configName;

    /**
     * 1-搜索框热搜 2-搜索下拉框热搜 3-(首页)热销商品 4-(首页)新品上线 5-(首页)为你推荐
     */
    private Byte configType;

    /**
     * 商品id 默认为0
     */
    private Integer productId;

    /**
     * 点击后的跳转地址(默认不跳转)
     */
    private String redirectUrl;

    /**
     * 排序值(字段越大越靠前)
     */
    private Integer configRank;

    /**
     * 删除标识字段(0-未删除 1-已删除)
     */
    private Byte isDeleted;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建者id
     */
    private Integer createUser;

    /**
     * 最新修改时间
     */
    private Date updateTime;

    /**
     * 修改者id
     */
    private Integer updateUser;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }

    public Byte getConfigType() {
        return configType;
    }

    public void setConfigType(Byte configType) {
        this.configType = configType;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public Integer getConfigRank() {
        return configRank;
    }

    public void setConfigRank(Integer configRank) {
        this.configRank = configRank;
    }

    public Byte getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Byte isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
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
        IndexConfig other = (IndexConfig) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getConfigName() == null ? other.getConfigName() == null : this.getConfigName().equals(other.getConfigName()))
            && (this.getConfigType() == null ? other.getConfigType() == null : this.getConfigType().equals(other.getConfigType()))
            && (this.getProductId() == null ? other.getProductId() == null : this.getProductId().equals(other.getProductId()))
            && (this.getRedirectUrl() == null ? other.getRedirectUrl() == null : this.getRedirectUrl().equals(other.getRedirectUrl()))
            && (this.getConfigRank() == null ? other.getConfigRank() == null : this.getConfigRank().equals(other.getConfigRank()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getCreateUser() == null ? other.getCreateUser() == null : this.getCreateUser().equals(other.getCreateUser()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getUpdateUser() == null ? other.getUpdateUser() == null : this.getUpdateUser().equals(other.getUpdateUser()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getConfigName() == null) ? 0 : getConfigName().hashCode());
        result = prime * result + ((getConfigType() == null) ? 0 : getConfigType().hashCode());
        result = prime * result + ((getProductId() == null) ? 0 : getProductId().hashCode());
        result = prime * result + ((getRedirectUrl() == null) ? 0 : getRedirectUrl().hashCode());
        result = prime * result + ((getConfigRank() == null) ? 0 : getConfigRank().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getCreateUser() == null) ? 0 : getCreateUser().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getUpdateUser() == null) ? 0 : getUpdateUser().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", configName=").append(configName);
        sb.append(", configType=").append(configType);
        sb.append(", productId=").append(productId);
        sb.append(", redirectUrl=").append(redirectUrl);
        sb.append(", configRank=").append(configRank);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", createTime=").append(createTime);
        sb.append(", createUser=").append(createUser);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", updateUser=").append(updateUser);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}