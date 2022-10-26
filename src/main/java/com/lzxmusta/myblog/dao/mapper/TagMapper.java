package com.lzxmusta.myblog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lzxmusta.myblog.dao.pojo.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TagMapper extends BaseMapper<Tag> {


    /**
     * @param articleId 根据文章id找标签
     * @return
     */
    List<Tag> findTagByArticleId(Long articleId);

    /**
     * @param limit 查询最热标签前n条
     * @return
     */
    List<Long> findHostTagIds(int limit);

    List<Tag> findTagsByTagIds(@Param("tagIds") List<Long> tagIds);

}
