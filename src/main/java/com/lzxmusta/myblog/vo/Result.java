package com.lzxmusta.myblog.vo;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
//添加全部的构造函数
@AllArgsConstructor
public class Result {
    private Boolean success;
    private int code;
    private String msg;
    private Object data;

    //定义成功的方法
    public static Result success(Object data) {
        return new Result(true, 200, "success", data);
    }
    //定义失败的方法
    public static Result fail(int code, String msg) {
        return new Result(false, code, msg, null);
    }

}
