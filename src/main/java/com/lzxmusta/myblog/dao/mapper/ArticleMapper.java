package com.lzxmusta.myblog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzxmusta.myblog.dao.dos.Archives;
import com.lzxmusta.myblog.dao.pojo.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
  /**
   * @return dos  Archives文章归档
   */
  List<Archives> listArchivesArticle();

  IPage<Article> listArticle(@Param("page") Page<Article> page,
                             @Param("categoryId") Long categoryId,
                             @Param("tagId") Long tagId,
                             @Param("year") String year,
                             @Param("month") String month);
}
