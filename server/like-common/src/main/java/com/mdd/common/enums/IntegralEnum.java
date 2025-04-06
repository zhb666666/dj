package com.mdd.common.enums;

public enum IntegralEnum {
    STATUS_INC(1,"积分增加"),
    STATUS_DEC(2,"积分减少"),
    TYPE_LOGIN(10,"登录奖励"),
    TYPE_ANSWER(20,"答题奖励"),
    TYPE_SHOP(30,"积分兑换");

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    private final int code;
    private final String msg;

    IntegralEnum(int code,String msg){
        this.code = code;
        this.msg = msg;
    }
}