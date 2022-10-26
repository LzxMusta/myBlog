package com.lzxmusta.myblog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lzxmusta.myblog.dao.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
}
