package com.lzxmusta.myblog.service;


import com.lzxmusta.myblog.vo.Result;
import com.lzxmusta.myblog.vo.params.CommentsParams;

public interface CommentsService {

    Result findCommentsById(Long id);

    Result setComments(CommentsParams commentsParams);

    Long findCommentsByArticleId(Long articleId);

}
