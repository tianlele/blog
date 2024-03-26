package com.tianll.blog.web.client;



import com.tianll.blog.model.domain.Article;
import com.tianll.blog.model.domain.Comment;
import com.github.pagehelper.PageInfo;
import com.tianll.blog.service.ArticleService;
import com.tianll.blog.service.CommentService;
import com.tianll.blog.service.SiteService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


/**
 * @BelongsProject: Springboot  //项目名
 * @BelongsPackage: com.example.springboot  //包名
 * @ClassName IndexController                //类名
 * @Author: laozaza                   //作者
 * @CreateTime: 2023-10-26  20:31  //时间
 **/
@Controller
public class IndexController {
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private ArticleService articleServiceImpl;

    @Autowired
    private CommentService commentServiceImpl;

    @Autowired
    private SiteService siteServiceImpl;

    // 博客首页，会自动跳转到文章页
    @GetMapping(value = "/")
    private String index(HttpServletRequest request) {
        System.out.println("【IndexController-index(/)】");
        return this.index(request, 1, 5);
    }

    // 文章页
    @GetMapping(value = "/page/{p}")
    public String index(HttpServletRequest request, @PathVariable("p") int page, @RequestParam(value = "count", defaultValue = "5") int count) {

        System.out.println("【IndexController-index(/page/p)】");

        PageInfo<Article> articles = articleServiceImpl.selectArticleWithPage(page, count);
        System.out.println("【IndexController-index(/page/p) articles ------:"+articles.getList().size());
        // 获取文章热度统计信息
        List<Article> articleList = articleServiceImpl.getHeatArticles();
        System.out.println("【IndexController-index(/page/p) articleList ------:"+articleList.size());
        request.setAttribute("articles", articles);
        request.setAttribute("articleList", articleList);
        logger.info("分页获取文章信息: 页码 "+page+",条数 "+count);
        return "client/index";
    }

    @GetMapping(value = "/article/{id}")
    public String getArticleById(@PathVariable("id") Integer id, HttpServletRequest request) {
        System.out.println("【IndexController-getArticleById(id)】");
        // 获取文章信息
        Article article = articleServiceImpl.selectArticleById(id);
        if (article == null || StringUtils.isBlank(article.getContent())) {
            logger.warn("文章不存在,查询文章ID："+id);
            return "comm/error_404";
        }else {
            getArticleComments(request, article);
            siteServiceImpl.updateStstistics(article);
            request.setAttribute("article", article);
            return "client/articleDetails";
        }

    }

    // 查询文章的评论信息，并补充到文章详情里面
    private void getArticleComments(HttpServletRequest request, Article article) {
        if (article.getAllowComment()) {
            // cp表示评论页码，commentPage
            String cp = request.getParameter("cp");
            cp = StringUtils.isBlank(cp) ? "1" : cp;
            request.setAttribute("cp", cp);
            PageInfo<Comment> comments = commentServiceImpl.getComments(article.getId(),Integer.parseInt(cp),3);
            request.setAttribute("cp", cp);
            request.setAttribute("comments", comments);
        }
    }

    @GetMapping(value = "/lxw")
    private String lxw(){
        return"/client/lxw";
    }

    @GetMapping(value = "/video")
    private String video(){
        return"/client/video";
    }


}

