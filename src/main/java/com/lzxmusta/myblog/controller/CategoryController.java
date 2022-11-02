package com.lzxmusta.myblog.controller;

import com.lzxmusta.myblog.service.CategoryService;
import com.lzxmusta.myblog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categorys")
@CrossOrigin
public class CategoryController {
  @Autowired
  private CategoryService categoryService;

  @GetMapping
  public Result findAllCategorys() {
    return categoryService.findAllCategorys();
  }

  /**
   * 获取所有分类
   * @return
   */
  @GetMapping("/detail")
  public Result categoriesDetail() {
    return categoryService.findAllDetail();
  }

  /**
   * 通过分类id获取分类列表
   * @param id
   * @return
   */
  @GetMapping("/detail/{id}")
  public Result findAllCategorysById(@PathVariable("id") Long id) {
    return categoryService.findAllCategorysById(id);
  }
}
