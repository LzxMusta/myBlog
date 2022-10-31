package com.lzxmusta.myblog.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lzxmusta.myblog.dao.mapper.ArticleMapper;
import com.lzxmusta.myblog.dao.pojo.Article;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
/**
 * TODO 多线程 阅读数量更新
 * @Author lzxmusta刘朝旭
 * @Date 17:45 2022/10/29
 * @param
 * @return null
 **/

@Component
public class ThreadService {
//操作在线程池  执行   不影响原线程
    @Async("taskExecutor")
    public void updateViewCount(ArticleMapper articleMapper, Article article){

        Article articleUpdate = new Article();
        articleUpdate.setViewCounts(article.getViewCounts() + 1);
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //根据id查询
        queryWrapper.eq(Article::getId,article.getId());
        //为了在多线程环境下，线程安全
        //类似乐观锁的设置，版本控制
        queryWrapper.eq(Article::getViewCounts,article.getViewCounts());
        articleMapper.update(articleUpdate,queryWrapper);
        try {
            Thread.sleep(5000);
            //睡眠5秒 证明不会影响主线程的使用
            System.out.println("线程池---执行阅读数量更新-- 完成");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
