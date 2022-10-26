package com.lzxmusta.myblog.dao.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

@Data
public class ArticleBody {
    @TableId
//    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    //content存放makedown格式的信息
    private String content;
    //content_html存放html格式的信息
    private String contentHtml;

    private Long articleId;

}
