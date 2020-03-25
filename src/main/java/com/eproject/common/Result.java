package com.eproject.common;

public enum Result {
    ERROR("error"),

    SUCCESS("success"),

    PRODUCT_EXIT("商品已经存在"),

    COLLECT_SOURCE_ID("收藏来源id不能为空"),

    ORDER_NOT_EXIST_ERROR("订单不存在！"),

    COLLECT_EXIT("已收藏"),

    DATA_NOT_EXIST("未查询到记录！"),

    SAME_LOGIN_NAME_EXIST("用户名已存在！"),

    LOGIN_NAME_NULL("请输入登录名！"),

    LOGIN_PASSWORD_NULL("请输入密码！"),

    LOGIN_VERIFY_CODE_NULL("请输入验证码！"),

    LOGIN_VERIFY_CODE_ERROR("验证码错误！"),

    OPERTE_ERROR("操作错误"),

    DB_ERROR("database error");

    private String result;
    Result(String result){
        this.result = result;
    }
    public String getResult(){ return result; }
    public void setResult(String result){ this.result = result; }
}
