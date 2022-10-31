package com.lzxmusta.myblog.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.lzxmusta.myblog.dao.pojo.Comment;
import com.lzxmusta.myblog.vo.Result;
import com.lzxmusta.myblog.vo.params.CommentsParams;

public interface CommentsService extends IService<Comment> {

    Result findCommentsById(Long id);

    Result setComments(CommentsParams commentsParams);

    Long findCommentsByArticleId(Long articleId);

}
