package com.lzxmusta.myblog.controller;

import com.lzxmusta.myblog.service.TagService;
import com.lzxmusta.myblog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tags")
//@CrossOrigin
public class TagsController {
  @Autowired
  private TagService tagService;

  //    /tags/hot
  @GetMapping("/hot")
  public Result hot() {
    int limit = 4;
    return tagService.host(limit);
  }

  @GetMapping
  public Result findAlltags() {
    return tagService.findAllTags();
  }

  /**
   * 查询所有的标签
   * @return
   */
  @GetMapping("/detail")
  public Result finfAllTagDetails() {
    return tagService.finfAllTagDetails();
  }

  /**
   * 通过标签id查此标签所有文章
   * @param id
   * @return
   */
  @GetMapping("/detail/{id}")
  public Result findAllTagById(@PathVariable("id") Long id) {
    return tagService.findAllTagById(id);
  }
}
