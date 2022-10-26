package com.lzxmusta.myblog.dao.pojo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * @Author: Lzxmusta
 * @Date: 2022-10-16-1:07
 * @Description:文章标签对应表
 */
@Data
public class ArticleTag {
//    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private Long articleId;

    private Long tagId;
}
