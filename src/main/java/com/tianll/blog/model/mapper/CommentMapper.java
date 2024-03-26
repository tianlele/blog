package com.tianll.blog.model.mapper;

import com.tianll.blog.model.domain.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author tianll
* @description 针对表【t_comment】的数据库操作Mapper
* @createDate 2024-03-14 16:19:54
* @Entity com.tianll.blog.model.domain.Comment
*/
@Mapper
public interface CommentMapper {

    /**
     * 通过id删除评论
     * @param id 评论id
     * @return 删除行数
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 发表评论
     * @param record
     * @return
     */
    int insert(Comment record);

    int insertSelective(Comment record);

    Comment selectByPrimaryKey(Long id);

    /**
     * 分页展示某个文章下的评论
     * @param aid 文章id
     * @return 该文章的评论列表
     */
    @Select("select * from t_comment where article_id=#{aid} order by id desc")
    List<Comment> selectCommentWithPage(Integer aid);

    /**
     * 获取所有评论，通过id倒序排序
     * @return 所有评论列表
     */
    @Select("select * from t_comment order by id desc ")
    List<Comment> selectNewComment();


    int updateByPrimaryKeySelective(Comment record);

    int updateByPrimaryKey(Comment record);

    /**
     * 站点服务统计，统计评论数量
     * @return 评论数量
     */
    @Select("select count(1) from t_comment")
    Integer countComment();

}
