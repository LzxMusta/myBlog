package com.lzxmusta.myblog.vo.params;

import lombok.Data;

/**
 * @Author: Lzxmusta
 * @Date: 2022-10-16-1:00
 * @Description:发布文章的编辑数据
 */
@Data
public class ArticleBodyParam {
    private String content;

    private String contentHtml;
}
