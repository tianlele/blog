package com.tianll.blog.model.responseData;

/**
 * 全站服务统计类
 * 只有博客文章和评论数 articles comments
 */
public class StaticticsBo {
    private Integer articles;
    private Integer comments;

    public Integer getArticles() {
        return articles;
    }

    public void setArticles(Integer articles) {
        this.articles = articles;
    }

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "StaticticsBo{" +
                "articles=" + articles +
                ", comments=" + comments +
                '}';
    }
}
