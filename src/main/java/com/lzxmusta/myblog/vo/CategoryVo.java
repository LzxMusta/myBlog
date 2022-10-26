package com.lzxmusta.myblog.vo;

import lombok.Data;

@Data
public class CategoryVo {
  //id，图标路径，图标名称
  private Long id;

  private String avatar;

  private String categoryName;

  /**
   * 查询所有的文章分类
   */
  private String description;
}
