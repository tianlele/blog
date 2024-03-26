package com.tianll.blog.model.mapper;

import com.tianll.blog.model.domain.Article;
import com.tianll.blog.model.domain.Statistic;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
@SpringBootTest
@RunWith(SpringRunner.class)
public class StatisticMapperTest {

    @Autowired
    private StatisticMapper statisticMapper;

    @Test
    public void addStatistic() {
        Article article = new Article();
        article.setId(2);
        statisticMapper.addStatistic(article);
    }

    @Test
    public void deleteByPrimaryKey() {
        System.out.println(statisticMapper.deleteByPrimaryKey(2L));
    }

    @Test
    public void deleteStatisticByArticleId() {
        statisticMapper.deleteStatisticByArticleId(2);
    }

    @Test
    public void selectStatisticByArticleId() {
        Statistic statistic = statisticMapper.selectStatisticByArticleId(1);
        System.out.println(statistic);
    }

    @Test
    public void listStatistics() {
        System.out.println(statisticMapper.listStatistics());
    }

    @Test
    public void updateArticleHitsById() {
        Statistic statistic = new Statistic();
        statistic.setId(2);
        statistic.setArticleId(1);
        statistic.setHits(10);
        statistic.setCommentsNum(10);
        statisticMapper.updateArticleHitsById(statistic);
    }

    @Test
    public void updateArticleCommentsNumById() {
        Statistic statistic = new Statistic();
        statistic.setId(2);
        statistic.setArticleId(1);
        statistic.setHits(10);
        statistic.setCommentsNum(10);
        statisticMapper.updateArticleCommentsNumById(statistic);
    }

    @Test
    public void getTotalVisit() {
        System.out.println(statisticMapper.getTotalVisit());
    }

    @Test
    public void getTotalComment() {
        System.out.println(statisticMapper.getTotalComment());
    }

    @Test
    public void insert() {
        Statistic statistic = new Statistic();
        statistic.setArticleId(1);  // 文章id
        statistic.setHits(10);  // 点击量
        statistic.setCommentsNum(10);  // 评论数
        statisticMapper.insert(statistic);
    }

    @Test
    public void insertSelective() {
        Statistic statistic = new Statistic();
//        statistic.setArticleId(1);  // 文章id
        statistic.setHits(10);  // 点击量
        statistic.setCommentsNum(10);  // 评论数
        statisticMapper.insertSelective(statistic);
    }

    @Test
    public void selectByPrimaryKey() {
        System.out.println(statisticMapper.selectByPrimaryKey(1L));
    }

    @Test
    public void updateByPrimaryKeySelective() {
        Statistic statistic = new Statistic();
        statistic.setId(1);  // 文章id
        statistic.setArticleId(2);  // 点击量
        statistic.setHits(11);  // 评论数
        statisticMapper.updateByPrimaryKeySelective(statistic);
    }

    @Test
    public void updateByPrimaryKey() {
        Statistic statistic = new Statistic();
        statistic.setId(1);  // 文章id
        statistic.setArticleId(2);  // 点击量
//        statistic.setCommentsNum(12);  // 评论数
        statistic.setHits(12);  // 评论数
        statisticMapper.updateByPrimaryKey(statistic);
    }
}