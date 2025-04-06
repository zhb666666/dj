package com.mdd.common.enums;

public enum LikeTypeEnum {
    VIDEO(1,"视频");
    private final int code;
    private final String msg;
    LikeTypeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    /*
     * 获取状态码
     *
             * @author fzr
     * @return Long
     */
    public int getCode() {
        return this.code;
    }

    /**
     * 获取提示
     *
     * @author fzr
     * @return String
     */
    public String getMsg() {
        return this.msg;
    }
}
