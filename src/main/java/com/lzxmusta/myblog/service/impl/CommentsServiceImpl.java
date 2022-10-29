package com.lzxmusta.myblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lzxmusta.myblog.dao.mapper.CommentMapper;
import com.lzxmusta.myblog.dao.pojo.Comment;
import com.lzxmusta.myblog.dao.pojo.SysUser;
import com.lzxmusta.myblog.service.CommentsService;
import com.lzxmusta.myblog.service.SysUserService;
import com.lzxmusta.myblog.util.UserThreadLocal;
import com.lzxmusta.myblog.vo.CommentVo;
import com.lzxmusta.myblog.vo.Result;
import com.lzxmusta.myblog.vo.UserVo;
import com.lzxmusta.myblog.vo.params.CommentsParams;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Long.valueOf;

@Service
public class CommentsServiceImpl implements CommentsService {

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private SysUserService sysUserService;

    /**
     * 获取评论数据
     * @param id
     * @return
     */
    @Override
    public Result findCommentsById(Long id) {
        /**
         * 1. 根据文章id 查询 评论列表 从 comment 表中查询
         * 2. 根据作者的id 查询作者的信息
         * 3. 判断 如果 level = 1 要去查询它有没有子评论
         * 4. 如果有 根据评论id 进行查询 （parent_id）
         */
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        //根据文章id进行查询
        queryWrapper.eq(Comment::getArticleId, id);
        //根据层级关系进行查询
        queryWrapper.eq(Comment::getLevel, 1);
        List<Comment> comments = commentMapper.selectList(queryWrapper);

        List<CommentVo> commentVoList = copyList(comments);
        return Result.success(commentVoList);
    }

    /**
     * 添加评论
     * @param commentsParams
     * @return
     */
    @Override
    public Result setComments(CommentsParams commentsParams) {
        SysUser sysUser = UserThreadLocal.get();
        Comment comment = new Comment();
//        BeanUtils.copyProperties(sysUser,comment);
        comment.setArticleId(commentsParams.getArticleId());
        comment.setAuthorId(sysUser.getId());
        comment.setContent(commentsParams.getContent());
        comment.setCreateDate(System.currentTimeMillis());

        Long parent = commentsParams.getParent();


        if (parent == null || parent == 0) {
            comment.setLevel(1);
        }else{
            comment.setLevel(2);
        }
        //如果是空，parent就是0
        comment.setParentId(parent == null ? 0 : parent);
        Long toUserId = commentsParams.getToUserId();
        comment.setToUid(toUserId == null ? 0 : toUserId);
        this.commentMapper.insert(comment);
        return Result.success(null);

    }
/**
 * TODO 通过文章id获取评论数
 * @Author lzxmusta刘朝旭
 * @Date 15:37 2022/10/26
 * @param articleId
 * @return Long
 **/
    @Override
    public Long findCommentsByArticleId(Long articleId) {
        Long coms=commentMapper.findCommentsByArticleId(articleId);
        return coms;
    }

    //对list表中的comment进行判断
    public List<CommentVo> copyList(List<Comment> commentList) {
        List<CommentVo> commentVoList = new ArrayList<>();
        for (Comment comment : commentList) {
            commentVoList.add(copy(comment));
        }
        return commentVoList;
    }

    private CommentVo copy(Comment comment) {
        CommentVo commentVo = new CommentVo();
        // 相同属性copy
        BeanUtils.copyProperties(comment, commentVo);
        commentVo.setId(valueOf(comment.getId()));
        //作者信息
        Long authorId = comment.getAuthorId();
        UserVo userVo = this.sysUserService.findUserVoById(authorId);
        commentVo.setAuthor(userVo);
        //子评论
        Integer level = comment.getLevel();
        if (1 == level) {
            Long id = comment.getId();
//            System.out.println(id+"================ Long id = comment.getId();================================");
            List<CommentVo> commentVoList = findCommentsByParentId(id);
            commentVo.setChildrens(commentVoList);
        }
        //to User 给谁评论
        if (level > 1) {
            Long toUid = comment.getToUid();
//            System.out.println(toUid+"============   Long toUid = comment.getToUid();======================");
            UserVo toUserVo = this.sysUserService.findUserVoById(toUid);
//            System.out.println(toUserVo+"==========UserVo toUserVo = this.sysUserService.findUserVoById(toUid);==========");
            commentVo.setToUser(toUserVo);
        }
        return commentVo;
    }

    private List<CommentVo> findCommentsByParentId(Long id) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getParentId, id);
        queryWrapper.eq(Comment::getLevel, 2);
        List<Comment> comments = this.commentMapper.selectList(queryWrapper);
        return copyList(comments);
    }
}
