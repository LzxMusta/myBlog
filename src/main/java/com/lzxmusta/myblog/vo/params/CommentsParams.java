package com.lzxmusta.myblog.vo.params;

import lombok.Data;

@Data
public class CommentsParams {

    private Long articleId;

    private String content;

    private Long parent;

    private Long toUserId;
}
