package com.tianll.blog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tianll.blog.model.domain.Comment;
import com.tianll.blog.model.domain.Statistic;
import com.tianll.blog.model.mapper.StatisticMapper;
import com.tianll.blog.service.CommentService;
import com.tianll.blog.model.mapper.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* @author tianll
* @description 针对表【t_comment】的数据库操作Service实现
* @createDate 2024-03-14 16:18:12
*/
@Service
@Transactional
public class CommentServiceImpl implements CommentService{

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private StatisticMapper statisticMapper;


    @Override
    public PageInfo<Comment> getComments(Integer articleId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Comment> comments = commentMapper.selectCommentWithPage(articleId);
        PageInfo<Comment> commentInfo = new PageInfo<>(comments);
        return commentInfo;
    }

    @Override
    public void pushComment(Comment comment) {
        // 评论数+1
        commentMapper.insertSelective(comment);
        Statistic statistic = statisticMapper.selectStatisticByArticleId(comment.getArticleId());
        statistic.setCommentsNum(statistic.getCommentsNum() + 1);
        statisticMapper.updateArticleCommentsNumById(statistic);
    }
}
