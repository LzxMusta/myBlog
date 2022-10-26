package com.lzxmusta.myblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lzxmusta.myblog.dao.mapper.CategoryMapper;
import com.lzxmusta.myblog.dao.pojo.Category;
import com.lzxmusta.myblog.service.CategoryService;
import com.lzxmusta.myblog.vo.CategoryVo;
import com.lzxmusta.myblog.vo.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Long.valueOf;

@Service
public class CategoryServiceImpl implements CategoryService {
  @Autowired
  private CategoryMapper categoryMapper;

  /**
   * 通过findCategoryById查文章名称类别
   *
   * @param categoryId
   * @return
   */

  @Override
  public CategoryVo findCategoryById(Long categoryId) {
    Category category = categoryMapper.selectById(categoryId);
//        System.out.println(category+"===============================categoryMapper.selectById(categoryId)===================");
    CategoryVo categoryVo = new CategoryVo();
    BeanUtils.copyProperties(category, categoryVo);
    return categoryVo;
  }

  /**
   * 查询所有文章类别
   *
   * @return
   */
  @Override
  public Result findAllCategorys() {
    LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.select(Category::getCategoryName, Category::getId);
    List<Category> categories = this.categoryMapper.selectList(new LambdaQueryWrapper<>());
    //页面交互的对象
    return Result.success(copyList(categories));
  }

  /**
   * 所有类别
   *
   * @return
   */
  @Override
  public Result findAllDetail() {
    LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
    List<Category> categoryList = categoryMapper.selectList(queryWrapper);
    return Result.success(copyList(categoryList));
  }

  /**
   * 通过分类id获取分类文章列表
   *
   * @param id
   * @return
   */
  @Override
  public Result findAllCategorysById(Long id) {
    Category category = categoryMapper.selectById(id);
    CategoryVo categoryVo = copy(category);
    return Result.success(categoryVo);
  }

  //id不一致要重新设立
  public CategoryVo copy(Category category) {
    CategoryVo categoryVo = new CategoryVo();
    BeanUtils.copyProperties(category, categoryVo);
    //id不一致要重新设立
    categoryVo.setId(category.getId());
    return categoryVo;
  }

  public List<CategoryVo> copyList(List<Category> categoryList) {
    List<CategoryVo> categoryVoList = new ArrayList<>();
    for (Category category : categoryList) {
      categoryVoList.add(copy(category));
    }
    return categoryVoList;
  }


}
