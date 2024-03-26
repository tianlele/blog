package com.tianll.blog.web.admin;

import com.github.pagehelper.PageInfo;
import com.tianll.blog.model.domain.Article;
import com.tianll.blog.model.domain.Comment;
import com.tianll.blog.model.responseData.ArticleResponseData;
import com.tianll.blog.model.responseData.StaticticsBo;
import com.tianll.blog.service.ArticleService;
import com.tianll.blog.service.SiteService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author tianll
 * @date 2024/3/21
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
    private static final String ADMIN_PATH = "/admin";

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private SiteService siteServiceImpl;

    @Autowired
    private ArticleService articleService;


    /**
     * 跳转到后台管理首页
     * @param request 请求对象
     * @return 跳转到后台管理首页
     */
    @GetMapping(value = {"","/index"})
    public String index(HttpServletRequest request) {
        //获取最新的5篇博客、评论和统计信息
        List<Article> articles = siteServiceImpl.recentArticles(5);
        List<Comment> comments = siteServiceImpl.recentComments(5);
        StaticticsBo statisticsBo = siteServiceImpl.getStatictics();
        request.setAttribute("articleList", articles);
        request.setAttribute("comments", comments);
        request.setAttribute("statistics", statisticsBo);
        request.setAttribute("articleCount", statisticsBo.getArticles());
        request.setAttribute("commentCount", statisticsBo.getComments());
        //遍历list
        for (Article article : articles) {
            System.out.println(article);
        }
        for (Comment comment : comments) {
            System.out.println(comment);
        }
        System.out.println(statisticsBo);
        return "back/index";
    }

    /**
     * 跳转到文章管理页面-编辑文章
     * @return 跳转到文章管理页面-编辑文章
     */
    @GetMapping(value = "/article/toEditPage")
    public String newArticle() {
        return "back/article_edit";
    }

    /**
     * 发布文章
     * @param article 文章对象
     * @return 返回发布结果
     */
    @PostMapping(value = "/article/publish")
    @ResponseBody
    public ArticleResponseData publishArticle(Article article) {
        if (StringUtils.isBlank(article.getCategories())) {
            article.setCategories("默认分类");
        }
        try {
            articleService.publish(article);
            logger.info("发布文章成功");
            return ArticleResponseData.ok();
        } catch (Exception e) {
            logger.error("文章发表失败，错误信息："+e.getMessage());
            return ArticleResponseData.fail("文章发表失败");
        }
    }

    /**
     * 返回指定位数的文章列表
     * @param page 页码
     * @param count 文章数量
     * @param request 请求对象
     * @return 返回文章列表
     */
    @GetMapping(value = "/article")
    public String index(@RequestParam(value = "page",defaultValue = "1") int page ,
                        @RequestParam(value = "count",defaultValue = "10") int count ,
                         HttpServletRequest request) {
        PageInfo<Article> pageInfo = articleService.selectArticleWithPage(page, count);
        request.setAttribute("articles", pageInfo);
        return "back/article_list";
    }

    /**
     * 跳转到文章编辑页面
     * @param id 文章id,string还是integer?
     * @param request 请求对象
     * @return 跳转到文章编辑页面
     */
    @GetMapping(value = "/article/{id}")
    public String editArticle(@PathVariable(value = "id") Integer id, HttpServletRequest request) {
        Article article = articleService.selectArticleById(id);
        request.setAttribute("contents",article);
        request.setAttribute("categories",article.getCategories());
        return "back/article_edit";
    }

    /**
     * 修改文章
     * @param article 文章对象
     * @return 返回修改结果
     */
    @ResponseBody
    @PostMapping(value = "/article/modify")
    public ArticleResponseData modifyArticle(Article article) {
        try {
//            这里updateArticleById有问题，可以更新create时间为null,已经修改
            articleService.updateByPrimaryKeySelective(article);
            logger.info("修改文章成功");
            return ArticleResponseData.ok();
        } catch (Exception e) {
            logger.error("修改文章失败，错误信息："+e.getMessage());
            return ArticleResponseData.fail("修改文章失败");
        }
    }

    /**
     * 通过文章id删除文章
     * @param id 文章id
     * @return 返回删除结果
     */
    @PostMapping(value = "/article/delete")
    @ResponseBody
    public ArticleResponseData delete(@RequestParam int id) {
        try {
            articleService.deleteArticleById(id);
            logger.info("删除文章成功");
            return ArticleResponseData.ok();
        } catch (Exception e) {
            logger.error("删除文章失败，错误信息："+e.getMessage());
            return ArticleResponseData.fail("删除文章失败");
        }
    }
}
