package com.tianll.blog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tianll.blog.model.domain.Article;
import com.tianll.blog.model.domain.Statistic;
import com.tianll.blog.model.mapper.CommentMapper;
import com.tianll.blog.model.mapper.StatisticMapper;
import com.tianll.blog.service.ArticleService;
import com.tianll.blog.model.mapper.ArticleMapper;
import com.tianll.blog.service.CommentService;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
* @author tianll
* @description 针对表【t_article】的数据库操作Service实现
* @createDate 2024-03-14 16:18:12
*/
@Service
@Transactional
public class ArticleServiceImpl implements ArticleService{

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private StatisticMapper statisticMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Article selectArticleById(Integer id) {
        Article article = null;
        Object o = redisTemplate.opsForValue().get("article_:" + id);
        if(o!=null){
            article = (Article) o;
        }else{
            article = articleMapper.selectByPrimaryKey(id.longValue());
            if(article!=null){
                redisTemplate.opsForValue().set("article_:" + id, article);
            }
        }
        return article;
    }

    @Override
    public PageInfo<Article> selectArticleWithPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Article> list = articleMapper.listArticlesOrderByIdWithPage();
        for (int i = 0; i < list.size(); i++) {
            Article article = list.get(i);
            Statistic statistic = statisticMapper.selectStatisticByArticleId(article.getId());
            article.setHits(statistic.getHits());
            article.setCommentsNum(statistic.getCommentsNum());
        }
        PageInfo<Article> page = new PageInfo<>(list);
        return page;
    }

    @Override
    public List<Article> getHeatArticles() {
        List<Statistic> list = statisticMapper.listStatistics();
        List<Article> articles = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Article article = articleMapper.selectByPrimaryKey(list.get(i).getArticleId().longValue());
            article.setHits(list.get(i).getHits());
            article.setCommentsNum(list.get(i).getCommentsNum());
            articles.add(article);
            if (i >= 9) {
                break;
            }

        }
        return articles;
    }

    @Override
    public void publish(Article article) {
        article.setContent(EmojiParser.parseToAliases(article.getContent()));
        article.setCreated(new Date());
        article.setHits(0);
        articleMapper.publishArticle(article);
        statisticMapper.addStatistic(article);
    }

    @Override
    public void updateArticleById(Article article) {
        article.setModified(new Date());
        articleMapper.updateByPrimaryKey(article);
        redisTemplate.delete("article_:" + article.getId());
    }

    @Override
    public void updateByPrimaryKeySelective(Article article) {
        article.setModified(new Date());
        articleMapper.updateByPrimaryKeySelective(article);
        redisTemplate.delete("article_:" + article.getId());
    }

    @Override
    public void deleteArticleById(Integer id) {
        articleMapper.deleteByPrimaryKey(id.longValue());
        redisTemplate.delete("article_:" + id);
        statisticMapper.deleteStatisticByArticleId(id);
        commentMapper.deleteByPrimaryKey(id.longValue());
    }
}
