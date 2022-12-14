package com.lzxmusta.myblog.controller;


import com.lzxmusta.myblog.common.aop.LogAnnotation;
import com.lzxmusta.myblog.common.cache.Cache;
import com.lzxmusta.myblog.service.ArticleService;
import com.lzxmusta.myblog.vo.Result;
import com.lzxmusta.myblog.vo.params.ArticleParams;
import com.lzxmusta.myblog.vo.params.PageParams;
import com.lzxmusta.myblog.vo.params.SearchParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/articles")
@CrossOrigin
public class ArticleController {
    @Autowired
    private ArticleService articleService;

//    /articles/search

    /**
     * 根据文章标题查询文章列表
     * @param searchParams
     * @return
     */
    @PostMapping("/search")
    public  Result searchArticle(@RequestBody SearchParams searchParams){
        System.out.println("进入查询接口"+searchParams);
        return articleService.searchArticleList(searchParams);
    }

/**
 *
 * TODO 更新编辑文章
 * @Author lzxmusta刘朝旭
 * @Date 19:05 2022/11/1
 * @param articleParams
 * @return Result
 **/
    @PostMapping("/update")
    public Result updateArt(@RequestBody ArticleParams articleParams){
        return  articleService.upArticleById(articleParams);
    }


    /**
     * 根据文章id删除文章所有信息
     * @param id
     * @return
     */
    @PostMapping("/delArticle/{id}")
    public Result delArticle(@PathVariable("id") Long id){
        articleService.delArticleById(id);
        return articleService.delArticleById(id);
    }

    /**
     * 修改文章的查询
     * @param id
     * @return
     */
    @PostMapping("/{id}")
    public Result editArticle(@PathVariable("id") Long id){
        Result result = articleService.finfArticleById(id);

        return  result;
    }

    /*
     * 首页文章列表
     * lzxmusta
     * */
    @PostMapping()
//    加上注解  代表要对此接口记录日志
    @LogAnnotation(module="文章",operator="获取文章列表")
//    @Cache(expire = 5*60*1000,name = "listArticle")
    public Result listArticle(@RequestBody PageParams pageParams) {
//        IPage<Article> page = articleService.getPage(pageParams);
//        页面分页显示

        return articleService.listArticle(pageParams);
    }

    /*
     * 首页  最热文章
     *
     * */
    @PostMapping("/hot")
//    @Cache(expire = 5*60*1000,name = "hotArticle")
    public Result hotArticle() {
        int limit = 5;
        return articleService.hotArticle(limit);
    }

    /*
     * 首页  最新 文章
     *
     * */
    @PostMapping("/new")
//    @Cache(expire = 5*60*1000,name = "newArticle")
    public Result newArticle() {
        int limit = 5;
        return articleService.newArticle(limit);
    }

    /*
     * 首页  文章归档
     *
     * */
    @PostMapping("/listArchives")
    public Result listArchivesArticle() {
        return articleService.listArchivesArticle();
    }

    /**
     * 文章详情
     *
     * @param articleId
     * @return
     */
    @PostMapping("/view/{id}")
    public Result finfArticleById(@PathVariable("id") Long articleId) {
        return articleService.finfArticleById(articleId);
    }

    @PostMapping("/publish")
    public Result publish(@RequestBody ArticleParams articleParams) {
        return  articleService.publish(articleParams);
    }

}
