package com.lzxmusta.blogadmin.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @ClassName Result
 * @Description TODO
 * @Author lzxmusta
 * @Date 2022/10/17 22:28
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
public class Result {
    private boolean success;

    private int code;

    private String msg;

    private Object data;


    public static Result success(Object data) {
        return new Result(true, 200, "success", data);
    }

    public static Result fail(int code, String msg) {
        return new Result(false, code, msg, null);
    }
}
