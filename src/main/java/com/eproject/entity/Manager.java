package com.eproject.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * manager
 * @author 
 */
public class Manager implements Serializable {
    private Long id;

    /**
     * 昵称
     */
    private String nick;

    /**
     * 登录名

     */
    private String loginName;

    /**
     * 密码
     */
    private String password;

    /**
     * 登录时间
     */
    private Date loginTime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }
}