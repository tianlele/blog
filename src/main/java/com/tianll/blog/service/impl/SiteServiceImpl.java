package com.tianll.blog.service.impl;

import com.github.pagehelper.PageHelper;
import com.tianll.blog.model.domain.Article;
import com.tianll.blog.model.domain.Comment;
import com.tianll.blog.model.domain.Statistic;
import com.tianll.blog.model.mapper.ArticleMapper;
import com.tianll.blog.model.mapper.CommentMapper;
import com.tianll.blog.model.mapper.StatisticMapper;
import com.tianll.blog.model.responseData.StaticticsBo;
import com.tianll.blog.service.SiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author tianll
 * @date 2024/3/16
 */
@Service
@Transactional
public class SiteServiceImpl implements SiteService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private StatisticMapper statisticMapper;

    @Override
    public List<Comment> recentComments(int limit) {
        PageHelper.startPage(1,limit>10 || limit <1 ? 10 : limit);
        List<Comment> comments = commentMapper.selectNewComment();
        return comments;
    }

    @Override
    public List<Article> recentArticles(int count) {
        PageHelper.startPage(1,count>10 || count <1 ? 10 : count);
        List<Article> articles = articleMapper.listArticlesOrderByIdWithPage();
        for(int i = 0 ; i <articles.size(); i++){
            Article article = articles.get(i);
            Statistic statistic = statisticMapper.selectStatisticByArticleId(article.getId());
            if (statistic != null){
                article.setHits(statistic.getHits());
                article.setCommentsNum(statistic.getCommentsNum());
            }else{
                article.setHits(0);
                article.setCommentsNum(0);
            }
        }
        return articles;
    }

    @Override
    public StaticticsBo getStatictics() {
        StaticticsBo staticticsBo = new StaticticsBo();
        Integer articles = articleMapper.countArticle();
        Integer comments = commentMapper.countComment();
        staticticsBo.setArticles(articles);
        staticticsBo.setComments(comments);
        return staticticsBo;
    }

    @Override
    public void updateStstistics(Article article) {
        Statistic statistic = statisticMapper.selectStatisticByArticleId(article.getId());
        statistic.setHits(statistic.getHits()+1);
        statisticMapper.updateArticleHitsById(statistic);
    }
}
