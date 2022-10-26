package com.lzxmusta.myblog.service;

import com.lzxmusta.myblog.vo.CategoryVo;
import com.lzxmusta.myblog.vo.Result;


public interface CategoryService {


  CategoryVo findCategoryById(Long id);

  Result findAllCategorys();

  Result findAllDetail();

  Result findAllCategorysById(Long id);
}
