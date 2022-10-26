package com.lzxmusta.myblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzxmusta.myblog.dao.dos.Archives;
import com.lzxmusta.myblog.dao.mapper.ArticleBodyMapper;
import com.lzxmusta.myblog.dao.mapper.ArticleTagMapper;
import com.lzxmusta.myblog.dao.pojo.Article;
import com.lzxmusta.myblog.dao.mapper.ArticleMapper;
import com.lzxmusta.myblog.dao.pojo.ArticleBody;
import com.lzxmusta.myblog.dao.pojo.ArticleTag;
import com.lzxmusta.myblog.dao.pojo.SysUser;
import com.lzxmusta.myblog.service.*;
import com.lzxmusta.myblog.util.UserThreadLocal;
import com.lzxmusta.myblog.vo.ArticleBodyVo;
import com.lzxmusta.myblog.vo.ArticleVo;
import com.lzxmusta.myblog.vo.Result;
import com.lzxmusta.myblog.vo.TagVo;
import com.lzxmusta.myblog.vo.params.ArticleParams;
import com.lzxmusta.myblog.vo.params.PageParams;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Long.valueOf;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
  @Autowired
  private ArticleMapper articleMapper;
  @Autowired
  private TagService tagService;
  @Autowired
  private SysUserService sysUserService;
  @Override
  public  Result listArticle(PageParams pageParams) {
    Page<Article> page = new Page<>(pageParams.getPage(),pageParams.getPageSize());
    IPage<Article> articleIPage = this.articleMapper.listArticle(page,pageParams.getCategoryId(),pageParams.getTagId(),pageParams.getYear(),pageParams.getMonth());
    return Result.success(copyList(articleIPage.getRecords(),true,true));
  }

  /*
   *分页
   * 获取文章列表*/
//  @Override
//  public Result listArticle(PageParams pageParams) {
//    Page<Article> page = new Page<Article>(pageParams.getPage(), pageParams.getPageSize());
//    LambdaQueryWrapper<Article> querWapper = new LambdaQueryWrapper<Article>();
//    if (pageParams.getCategoryId() != null) {
////        and category_id=#{categoryId}
//      querWapper.eq(Article::getCategoryId, pageParams.getCategoryId());
//
//    }
//    List<Long> articleIdList = new ArrayList<>();
//    if (pageParams.getTagId() != null) {
//      /**
//       * 加入标签  条件查询
//       * article表中  没有tag字段  一篇文章有多个标签
//       * articlg_tag  article_id   1 : n  tag_ig
//       */
//
//      LambdaQueryWrapper<ArticleTag> articleTagLambdaQueryWrapper = new LambdaQueryWrapper<>();
//      articleTagLambdaQueryWrapper.eq(ArticleTag::getTagId, pageParams.getTagId());
//      List<ArticleTag> articleTags = articleTagMapper.selectList(articleTagLambdaQueryWrapper);
//      for (ArticleTag articleTag : articleTags) {
//        articleIdList.add(articleTag.getArticleId());
//      }
//      if (articleIdList.size() > 0) {
//        querWapper.in(Article::getId, articleIdList);
//      }
//    }
////        是否置顶排序
////        按时间倒叙排列 order by create_date desc
//    querWapper.orderByDesc(Article::getWeight, Article::getCreateDate);
//    Page<Article> articlePage = articleMapper.selectPage(page, querWapper);
//    List<Article> records = articlePage.getRecords();
////        不直接返回做vo处理
//    List<ArticleVo> articleVoList = copyList(records, true, true);
//
//    return Result.success(articleVoList);

//  }

  /*
   * 获取最热文章*/
  @Override
  public Result hotArticle(int limit) {
    LambdaQueryWrapper<Article> querWapper = new LambdaQueryWrapper<Article>();
    querWapper.orderByDesc(Article::getViewCounts);
    querWapper.select(Article::getId, Article::getTitle);
    querWapper.last("limit " + limit);
//        select id,title from article order by view_count desc limit 5
    List<Article> articles = articleMapper.selectList(querWapper);
    return Result.success(copyList(articles, false, false));
  }

  /*
   *
   *
   * 获取最新文章*/
  @Override
  public Result newArticle(int limit) {
    LambdaQueryWrapper<Article> querWapper = new LambdaQueryWrapper<Article>();
    querWapper.orderByDesc(Article::getCreateDate);
    querWapper.select(Article::getId, Article::getTitle);
    querWapper.last("limit " + limit);
    List<Article> articles = articleMapper.selectList(querWapper);

    return Result.success(copyList(articles, false, false));
  }


  /**
   * 首页  文章归档
   * lzxmusta
   */
  @Override
  public Result listArchivesArticle() {
    List<Archives> archives = articleMapper.listArchivesArticle();
//        System.out.println(archives+"---------------------------------------------------------");
    return Result.success(archives);
  }

  /**
   * 根据id查文章详情
   *
   * @param articleId
   * @return
   */
  @Autowired
  private ThreadService threadService;

  @Override
  public Result finfArticleById(Long articleId) {
    /**
     *1 .根据id查详情
     * 2.根据bodyId和categoryId 做关联查询
     */
    Article article = articleMapper.selectById(articleId);
//    System.out.println(article+"======================= article=======================================");

    ArticleVo articleVo = copy(article, true, true, true, true);
//        System.out.println(articleVo+"======================= copy(article, true, true, true, true)=======================================");
    /**
     * 看完文章，新增阅读数
     * 看完后本应该直接返回数据增加阅读数，这事做这个更新（更新数据库加写锁）的话，阻塞其他的读操作，性能降低
     * 更新增加了此次接口的耗时  如果更新出问题，不能影响查看文章操作
     *1.使用线程池  把更新放到线程池中执行，与主线程分开
     */
    threadService.updateViewCount(articleMapper, article);
    return Result.success(articleVo);

  }

  @Autowired
  private ArticleTagMapper articleTagMapper;

  @Override
  @Transactional
  public Result publish(ArticleParams articleParams) {
//注意想要拿到数据必须将接口加入拦截器
    SysUser sysUser = UserThreadLocal.get();

    /**
     * 1. 发布文章 目的 构建Article对象
     * 2. 作者id  当前的登录用户
     * 3. 标签  要将标签加入到 关联列表当中
     * 4. body 内容存储 article bodyId
     */
    Article article = new Article();
    article.setAuthorId(sysUser.getId());
    article.setCategoryId(articleParams.getCategory().getId());
    article.setCreateDate(System.currentTimeMillis());
    article.setCommentCounts(0);
    article.setSummary(articleParams.getSummary());
    article.setTitle(articleParams.getTitle());
    article.setViewCounts(0);
    article.setWeight(0);
    article.setBodyId(-1L);
    //插入之后 会生成一个文章id（因为新建的文章没有文章id所以要insert一下
    //官网解释："insart后主键会自动'set到实体的ID字段。所以你只需要"getid()就好
//        利用主键自增，mp的insert操作后id值会回到参数对象中
    this.articleMapper.insert(article);

    //tags
    List<TagVo> tags = articleParams.getTags();
    if (tags != null) {
      for (TagVo tag : tags) {
        ArticleTag articleTag = new ArticleTag();
        articleTag.setArticleId(article.getId());
        articleTag.setTagId(tag.getId());
        this.articleTagMapper.insert(articleTag);
      }
    }
    //body
    ArticleBody articleBody = new ArticleBody();
    articleBody.setContent(articleParams.getBody().getContent());
    articleBody.setContentHtml(articleParams.getBody().getContentHtml());
    articleBody.setArticleId(article.getId());
    articleBodyMapper.insert(articleBody);
    //插入完之后再给一个id
    article.setBodyId(articleBody.getId());
    //MybatisPlus中的save方法什么时候执行insert，什么时候执行update
    //只有当更改数据库时才插入或者更新，一般查询就可以了
    articleMapper.updateById(article);

    ArticleVo articleVo = new ArticleVo();
    articleVo.setId(article.getId());
    return Result.success(articleVo);
  }


  private List<ArticleVo> copyList(List<Article> records, boolean isTag, boolean isAuthor) {
    List<ArticleVo> articleVoList = new ArrayList<>();
    for (Article record : records) articleVoList.add(copy(record, isTag, isAuthor, false, false));
    return articleVoList;
  }

  private List<ArticleVo> copyList(List<Article> records, boolean isTag, boolean isAuthor, boolean isBody) {
    List<ArticleVo> articleVoList = new ArrayList<>();
    for (Article record : records) articleVoList.add(copy(record, isTag, isAuthor, isBody, false));
    return articleVoList;
  }

  private List<ArticleVo> copyList(List<Article> records, boolean isTag, boolean isAuthor, boolean isBody, boolean isCategory) {
    List<ArticleVo> articleVoList = new ArrayList<>();
    for (Article record : records) {
      articleVoList.add(copy(record, isTag, isAuthor, isBody, isCategory));
    }
    return articleVoList;
  }


  /**
   * 对象转移
   * 添加判断属性
   *
   * @param article
   * @param isTag
   * @param isAuthor
   * @return
   */


  @Autowired
  private CategoryService categoryService;

  //带body信息，带category信息
  private ArticleVo copy(Article article, boolean isTag, boolean isAuthor, boolean isBody, boolean isCategory) {
    ArticleVo articleVo = new ArticleVo();
    articleVo.setId(article.getId());
    BeanUtils.copyProperties(article, articleVo);
    //时间没法copy因为是long型
    articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
    //并不是所有的接口 都需要标签 ，作者信息
    if (isTag) {
      Long articleId = article.getId();
      articleVo.setTags(tagService.findTagByArticleId(articleId));
    }
    if (isAuthor) {
      Long authorId = article.getAuthorId();
//      System.out.println(authorId +"===============article.getAuthorId()======authorId============================");
      String nickname = sysUserService.findUserById(authorId).getNickname();
//      System.out.println(nickname+"===============sysUserService.findUserById(authorId).getNickname()======nickname============================");
      articleVo.setAuthor(sysUserService.findUserById(authorId).getNickname());
    }
    if (isBody) {
      Long bodyId = article.getBodyId();
      articleVo.setBody(findArticleBodyById(bodyId));
    }
    if (isCategory) {
      Long categoryId = article.getCategoryId();
      articleVo.setCategory(categoryService.findCategoryById(categoryId));
    }
    return articleVo;
  }

//    private CategoryVo findCategory(Long categoryId) {
//        return categoryService.findCategoryById(categoryId);
//    }

  //构建ArticleBodyMapper
  @Autowired
  private ArticleBodyMapper articleBodyMapper;

  private ArticleBodyVo findArticleBodyById(Long bodyId) {
    ArticleBody articleBody = articleBodyMapper.selectById(bodyId);
    ArticleBodyVo articleBodyVo = new ArticleBodyVo();
//    articleBodyVo.setContent(articleBody.getContent());
    BeanUtils.copyProperties(articleBody,articleBodyVo);
    return articleBodyVo;
  }


}

