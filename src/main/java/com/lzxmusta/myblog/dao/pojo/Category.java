package com.lzxmusta.myblog.dao.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * 文章分类表
 */
@Data
public class Category {
//    @JsonSerialize(using = ToStringSerializer.class)
    @TableId
    private Long id;
//    avata分类图标路径
    private String avatar;
//    category_name图标分类的名称
    private String categoryName;
//  description分类的描述
    private String description;
}
