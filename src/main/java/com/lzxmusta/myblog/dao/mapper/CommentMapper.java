package com.lzxmusta.myblog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lzxmusta.myblog.dao.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
    @Select("SELECT COUNT(*) FROM ms_comment WHERE article_id =#{articleId}")
    Long findCommentsByArticleId(Long articleId);

}
