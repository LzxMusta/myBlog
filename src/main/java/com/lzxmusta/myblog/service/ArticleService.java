package com.lzxmusta.myblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lzxmusta.myblog.dao.pojo.Article;
import com.lzxmusta.myblog.vo.Result;
import com.lzxmusta.myblog.vo.params.ArticleParams;
import com.lzxmusta.myblog.vo.params.PageParams;
import com.lzxmusta.myblog.vo.params.SearchParams;

public interface ArticleService extends IService<Article> {
    /*
     * 分页查询文章列表*/
    Result listArticle(PageParams pageParams);

    /*
     * 最热文章*/
    Result hotArticle(int limit);

    Result newArticle(int limit);

    /*
     * 首页  文章归档
     *
     * */
    Result listArchivesArticle();

    /**
     * 根据文章id
     * 查询文章详情
     *
     * @param articleId
     * @return
     */
    Result finfArticleById(Long articleId);

    /**
     * 发布文章
     * @param articleParams
     * @return
     */
    Result publish(ArticleParams articleParams);

    /**
     * 通过id删除文章所有信息
     * @param id
     * @return
     */
    Result delArticleById(Long id);

    /**
     * 通过id修改文章信息
     * @param id
     * @return
     */
    Result upArticleById(ArticleParams articleParams);

    /**
     * 标题查询文章列表
     * @param searchParams
     * @return
     */
    Result searchArticleList(SearchParams searchParams);
}
