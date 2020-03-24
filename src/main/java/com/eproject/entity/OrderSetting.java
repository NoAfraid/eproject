package com.eproject.entity;

import java.io.Serializable;

/**
 * order_setting
 * @author 
 */
public class OrderSetting implements Serializable {
    private Long id;

    /**
     * 秒杀订单超时关闭时间(分)
     */
    private Integer flashOrderOvertime;

    /**
     * 正常订单超时时间(分)
     */
    private Integer normalOrderOvertime;

    /**
     * 发货后自动确认收货时间（天）
     */
    private Integer confirmOvertime;

    /**
     * 自动完成交易时间，不能申请售后（天）
     */
    private Integer finishOvertime;

    /**
     * 订单完成后自动好评时间（天）
     */
    private Integer commentOvertime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getFlashOrderOvertime() {
        return flashOrderOvertime;
    }

    public void setFlashOrderOvertime(Integer flashOrderOvertime) {
        this.flashOrderOvertime = flashOrderOvertime;
    }

    public Integer getNormalOrderOvertime() {
        return normalOrderOvertime;
    }

    public void setNormalOrderOvertime(Integer normalOrderOvertime) {
        this.normalOrderOvertime = normalOrderOvertime;
    }

    public Integer getConfirmOvertime() {
        return confirmOvertime;
    }

    public void setConfirmOvertime(Integer confirmOvertime) {
        this.confirmOvertime = confirmOvertime;
    }

    public Integer getFinishOvertime() {
        return finishOvertime;
    }

    public void setFinishOvertime(Integer finishOvertime) {
        this.finishOvertime = finishOvertime;
    }

    public Integer getCommentOvertime() {
        return commentOvertime;
    }

    public void setCommentOvertime(Integer commentOvertime) {
        this.commentOvertime = commentOvertime;
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
        OrderSetting other = (OrderSetting) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getFlashOrderOvertime() == null ? other.getFlashOrderOvertime() == null : this.getFlashOrderOvertime().equals(other.getFlashOrderOvertime()))
            && (this.getNormalOrderOvertime() == null ? other.getNormalOrderOvertime() == null : this.getNormalOrderOvertime().equals(other.getNormalOrderOvertime()))
            && (this.getConfirmOvertime() == null ? other.getConfirmOvertime() == null : this.getConfirmOvertime().equals(other.getConfirmOvertime()))
            && (this.getFinishOvertime() == null ? other.getFinishOvertime() == null : this.getFinishOvertime().equals(other.getFinishOvertime()))
            && (this.getCommentOvertime() == null ? other.getCommentOvertime() == null : this.getCommentOvertime().equals(other.getCommentOvertime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getFlashOrderOvertime() == null) ? 0 : getFlashOrderOvertime().hashCode());
        result = prime * result + ((getNormalOrderOvertime() == null) ? 0 : getNormalOrderOvertime().hashCode());
        result = prime * result + ((getConfirmOvertime() == null) ? 0 : getConfirmOvertime().hashCode());
        result = prime * result + ((getFinishOvertime() == null) ? 0 : getFinishOvertime().hashCode());
        result = prime * result + ((getCommentOvertime() == null) ? 0 : getCommentOvertime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", flashOrderOvertime=").append(flashOrderOvertime);
        sb.append(", normalOrderOvertime=").append(normalOrderOvertime);
        sb.append(", confirmOvertime=").append(confirmOvertime);
        sb.append(", finishOvertime=").append(finishOvertime);
        sb.append(", commentOvertime=").append(commentOvertime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}