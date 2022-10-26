package com.lzxmusta.blogadmin.vo;

import lombok.Data;

import java.util.List;

/**
 * @ClassName PageResult
 * @Description TODO
 * @Author lzxmusta
 * @Date 2022/10/17 22:29
 * @Version 1.0
 **/
@Data
public class PageResult<T> {

    private List<T> list;

    private Long total;
}
