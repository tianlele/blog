package com.tianll.blog.service;

import com.tianll.blog.model.domain.Article;
import com.tianll.blog.model.domain.Comment;
import com.tianll.blog.model.responseData.StaticticsBo;

import java.util.List;

/**
 * @author tianll
 * @date 2024/3/16
 */
public interface SiteService {

    /**
     * 获取最近评论
     * @return 最近评论
     */
    List<Comment> recentComments(int count);

    /**
     * 最新发表的文章
     * @param count 数量
     * @return 文章列表
     */
    List<Article> recentArticles(int count);

    /**
     * 获取后台统计数据
     * @return 统计数据
     */
    StaticticsBo getStatictics();

    /**
     * 更新某文章统计数据
     * @param article 文章
     */
    void updateStstistics(Article article);
}
