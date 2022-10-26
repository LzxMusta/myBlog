package com.lzxmusta.myblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzxmusta.myblog.dao.pojo.Tag;
import com.lzxmusta.myblog.dao.mapper.TagMapper;
import com.lzxmusta.myblog.service.TagService;
import com.lzxmusta.myblog.vo.Result;
import com.lzxmusta.myblog.vo.TagVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*标签*/
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Autowired
    private TagMapper tagMapper;

    public TagVo copy(Tag tag) {
        TagVo tagVo = new TagVo();
        BeanUtils.copyProperties(tag, tagVo);
        return tagVo;
    }

    public List<TagVo> copyList(List<Tag> tagList) {
        List<TagVo> tagVoList = new ArrayList<>();
        for (Tag tag : tagList) {
            tagVoList.add(copy(tag));
        }
        return tagVoList;
    }

    @Override
    public List<TagVo> findTagByArticleId(Long articleId) {
//        多表查询
        List<Tag> tags = tagMapper.findTagByArticleId(articleId);
        return copyList(tags);


    }

    @Override
    public Result host(int limit) {
        /*
         * 最热标签
         * 1.文章数量最多
         * 2.根据tag_id 分组 计数 从大到小排列 取前limit个
         * */
        List<Long> tagIds = tagMapper.findHostTagIds(limit);
//        System.out.println("_____________________findHostTagIds_______________________________________________________"+tagIds);
//        为空值不查
        if (CollectionUtils.isEmpty(tagIds)) {
            return Result.success(Collections.emptyList());
        }
//        需要tagId 和 tagName Tag对象
//select * from tag where id(1,2,3)

        List<Tag> tags = tagMapper.findTagsByTagIds(tagIds);
//        System.out.println("_____________________findTagsByTagIds_______________________________________________________"+tags);

        return Result.success(tags);
    }

    @Override
    public Result findAllTags() {
      LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
      queryWrapper.select(Tag::getId,Tag::getTagName);
      List<Tag>  tagList=tagMapper.selectList(queryWrapper);
        return Result.success(copyList(tagList));
    }

  /**
   * 查询所有的标签
   * @return
   */
  @Override
  public Result finfAllTagDetails() {
    List<Tag>  tagList=tagMapper.selectList(new LambdaQueryWrapper<>());
    return Result.success(copyList(tagList));
  }

  /**
   * 通过id获取标签文章列表
   * @param id
   * @return
   */
  @Override
  public Result findAllTagById(Long id) {
    Tag tag = tagMapper.selectById(id);
    TagVo tagVo=copy(tag);
    return Result.success(tagVo);

  }
}
