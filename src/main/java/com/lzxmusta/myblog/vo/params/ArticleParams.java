package com.lzxmusta.myblog.vo.params;

import com.lzxmusta.myblog.vo.CategoryVo;
import com.lzxmusta.myblog.vo.TagVo;
import lombok.Data;

import java.util.List;

/**
 * @Author: Lzxmusta
 * @Date: 2022-10-16-0:55
 * @Description:前端发布文章参数
 */
@Data
public class ArticleParams {

    private Long id;

    private ArticleBodyParam body;

    private CategoryVo category;

    private String summary;

    private List<TagVo> tags;

    private String title;

}
