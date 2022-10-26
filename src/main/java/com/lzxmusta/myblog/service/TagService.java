package com.lzxmusta.myblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lzxmusta.myblog.dao.pojo.Tag;
import com.lzxmusta.myblog.vo.Result;
import com.lzxmusta.myblog.vo.TagVo;

import java.util.List;


public interface TagService extends IService<Tag> {
    List<TagVo> findTagByArticleId(Long articleId);

    Result host(int limit);

    Result findAllTags();
//查询所有的标签
  Result finfAllTagDetails();


  Result findAllTagById(Long id);
}
