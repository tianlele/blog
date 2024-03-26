package com.tianll.blog.service;

import com.github.pagehelper.PageInfo;
import com.tianll.blog.model.domain.Comment;

/**
* @author tianll
* @description 针对表【t_comment】的数据库操作Service
* @createDate 2024-03-14 16:18:12
*/
public interface CommentService {

    /**
     * 通过文章ID查询该文章的评论，分页
     * @param articleId 文章ID
     * @param pageNum   页码
     * @param pageSize  每页大小
     * @return  评论列表
     */
    PageInfo<Comment> getComments(Integer articleId, Integer pageNum , Integer pageSize);


    /**
     * 用户发表评论
     * @param comment 评论
     */
    void pushComment(Comment comment);
}
