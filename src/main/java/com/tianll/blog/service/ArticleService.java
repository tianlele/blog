package com.tianll.blog.service;

import com.github.pagehelper.PageInfo;
import com.tianll.blog.model.domain.Article;

import java.util.List;

/**
* @author tianll
* @description 针对表【t_article】的数据库操作Service
* @createDate 2024-03-14 16:18:12
*/
public interface ArticleService {

    /**
     * 分页查找【文章】信息
     * @param pageNum 页码
     * @param pageSize 页大小
     * @return 返回分页结果
     */
    public PageInfo<Article> selectArticleWithPage(Integer pageNum, Integer pageSize);

    /**
     * 获取热门【文章】信息
     * @return 返回【文章】信息
     */
    public List<Article> getHeatArticles();

    /**
     * 通过分账【文章】ID查询【文章】信息,先通过redis查询，如果redis没有则通过mysql查询
     * @param id 文章ID
     * @return 返回【文章】信息
     */
    public Article selectArticleById(Integer id);

    /**
     * 发布【文章】
     * @param article 文章信息
     */
    public void publish(Article article);

    /**
     * 更新【文章】信息
     * @param article 文章信息
     */
    void updateArticleById(Article article);

    /**
     * 更新【文章】信息,只更新不为null的字段
     * @param record 文章信息
     */
    void updateByPrimaryKeySelective(Article record);

    /**
     * 通过文章id删除【文章】信息
     * @param id 文章ID
     */
    void deleteArticleById(Integer id);
}
