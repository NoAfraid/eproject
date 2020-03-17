package com.eproject.common;

public enum Result {
    ERROR("error"),

    SUCCESS("success"),

    PRODUCT_EXIT("商品已经存在"),

    DATA_NOT_EXIST("未查询到记录！"),

    OPERTE_ERROR("操作错误"),

    DB_ERROR("database error");

    private String result;
    Result(String result){
        this.result = result;
    }
    public String getResult(){ return result; }
    public void setResult(String result){ this.result = result; }
}
