package com.lzxmusta.blogadmin.params;

import lombok.Data;

/**
 * @ClassName PageParam
 * @Description TODO
 * @Author lzxmusta
 * @Date 2022/10/17 22:21
 * @Version 1.0
 **/
@Data
public class PageParam {
    private Integer currentPage;

    private Integer pageSize;

    private String queryString;
}
