package com.lzxmusta.myblog.controller;

import com.lzxmusta.myblog.service.CommentsService;
import com.lzxmusta.myblog.vo.Result;
import com.lzxmusta.myblog.vo.params.CommentsParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
@CrossOrigin
public class CommentsController {
    @Autowired
    private CommentsService commentsService;

    @GetMapping("/article/{id}")
    public Result getComment(@PathVariable("id") Long id){
            return commentsService.findCommentsById(id);
    }

    @PostMapping("/create/change")
    public Result setComments(@RequestBody CommentsParams commentsParams){
        return commentsService.setComments(commentsParams);

    }
}
