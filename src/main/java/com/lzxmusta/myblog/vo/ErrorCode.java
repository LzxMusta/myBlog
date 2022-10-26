package com.lzxmusta.myblog.vo;

public enum ErrorCode {
    MYBLOG_SYSTERM_ERROR(-999,"系统访问异常请稍后再试"),
    PARAMS_ERROR(10001,"参数有误"),

    ACCOUNT_PWD_NOT_EXIST(10002,"用户名或密码不存在"),

    TOKEN_NOT_EXIST(10003,"token不合法"),

    ACCOUNT_PWD_EXIST(10004,"用户名已存在"),


    NO_PERMISSION(70001,"无访问权限"),

    SESSION_TIME_OUT(90001,"会话超时"),

    NO_LOGIN(90002,"未登录"),;

    private int code;
    private String msg;

    ErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
