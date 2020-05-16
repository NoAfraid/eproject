package com.eproject.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * history_search
 *
 * @author
 */
public class HistorySearch implements Serializable {
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 产品id
     */
    private Integer productId;

    /**
     * 商品名
     */
    private String productName;

    private Integer historySearch;

    /**
     * 搜索次数
     */
    private Integer searchCount;

    /**
     * 创建时间
     */
    private Date creatTime;

    /**
     * 更新时间
     */
    private Date updateTime;


    /**
     * 搜索名
     */
    private String searchName;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getHistorySearch() {
        return historySearch;
    }

    public void setHistorySearch(Integer historySearch) {
        this.historySearch = historySearch;
    }

    public Integer getSearchCount() {
        return searchCount;
    }

    public void setSearchCount(Integer searchCount) {
        this.searchCount = searchCount;
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

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    public String getSearchName() {
        return searchName;
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
        HistorySearch other = (HistorySearch) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
                && (this.getProductId() == null ? other.getProductId() == null : this.getProductId().equals(other.getProductId()))
                && (this.getProductName() == null ? other.getProductName() == null : this.getProductName().equals(other.getProductName()))
                && (this.getHistorySearch() == null ? other.getHistorySearch() == null : this.getHistorySearch().equals(other.getHistorySearch()))
                && (this.getSearchCount() == null ? other.getSearchCount() == null : this.getSearchCount().equals(other.getSearchCount()))
                && (this.getCreatTime() == null ? other.getCreatTime() == null : this.getCreatTime().equals(other.getCreatTime()))
                && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
                && (this.getSearchName() == null ? other.getSearchName() == null : this.getSearchName().equals(other.getSearchName()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getProductId() == null) ? 0 : getProductId().hashCode());
        result = prime * result + ((getProductName() == null) ? 0 : getProductName().hashCode());
        result = prime * result + ((getHistorySearch() == null) ? 0 : getHistorySearch().hashCode());
        result = prime * result + ((getSearchCount() == null) ? 0 : getSearchCount().hashCode());
        result = prime * result + ((getCreatTime() == null) ? 0 : getCreatTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getSearchName() == null) ? 0 : getSearchName().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", productId=").append(productId);
        sb.append(", productName=").append(productName);
        sb.append(", historySearch=").append(historySearch);
        sb.append(", searchCount=").append(searchCount);
        sb.append(", creatTime=").append(creatTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", searchName=").append(searchName);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}