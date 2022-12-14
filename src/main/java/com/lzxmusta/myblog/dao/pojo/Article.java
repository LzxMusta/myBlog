package com.lzxmusta.myblog.dao.pojo;

import com.baomidou.mybatisplus.annotation.SqlCondition;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
/*文章*/
@Data
public class Article {

    public static final int Article_TOP = 1;

//    public static final int Article_Common = 0;
//@JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    @TableField(value = "TITLE",condition = SqlCondition.LIKE)
    private String title;

    private String summary;

    private Integer commentCounts;

    private Integer viewCounts;

    /**
     * 作者id
     */
    private Long authorId;
    /**
     * 内容id
     */
    private Long bodyId;
    /**
     *类别id
     */
    private Long categoryId;

    /**
     * 置顶
     */
    private Integer weight;


    /**
     * 创建时间
     */
    private Long createDate;


}
