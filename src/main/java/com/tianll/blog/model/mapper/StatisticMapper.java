package com.tianll.blog.model.mapper;

import com.tianll.blog.model.domain.Article;
import com.tianll.blog.model.domain.Statistic;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
* @author tianll
* @description 针对表【t_statistic】的数据库操作Mapper
* @createDate 2024-03-14 16:19:54
* @Entity com.tianll.blog.model.domain.Statistic
*/
@Mapper
public interface StatisticMapper {

    /**
     * 【新增】文章统计信息
     * @param article 文章信息：文章点击量和评论量设置为0
     */
    @Insert("insert into t_statistic(article_id,hits,comments_num)" +
            " values (#{id},0,0)")
    void addStatistic(Article article);

    /**
     * 通过评论id删除评论
     * @param id 评论id
     * @return
     */
    @Delete(value = "delete from t_statistic where id=#{id}")
    int deleteByPrimaryKey(Long id);

    /**
     * 根据文章id删除【统计表】中该文章的统计数据信息
     * @author tianll
     * @date 2024-3-7
     * @param articleId 文章id
     */
    @Delete(value = "delete from t_statistic where article_id=#{articleId}")
    void deleteStatisticByArticleId(int articleId);

    /**
     * 通过文章id查找此文章的统计信息
     * @author tianll
     * @date 2024-3-7
     * @param articleId 文章id
     * @return 统计信息
     */
    @Select("select * from t_statistic where article_id=#{articleId}")
    Statistic selectStatisticByArticleId(Integer articleId);

    /**
     * 查找所有统计信息，通过点赞量、评论数排序
     * @author tianll
     * @date 2024-3-7
     * @return 统计信息列表
     */
    @Select("SELECT * FROM t_statistic WHERE hits !='0' " +
            "ORDER BY hits DESC, comments_num DESC")
    List<Statistic> listStatistics();

    /**
     * 更新文章点击量
     * @param statistic 统计信息
     */
    @Update("update t_statistic set hits=#{hits}" +
            " where article_id =#{articleId}")
    void updateArticleHitsById(Statistic statistic);

    /**
     * 更新文章评论量
     * @param statistic 统计信息
     */
    @Update("update t_statistic set comments_num=#{commentsNum}" +
            " where article_id=#{articleId}")
    void updateArticleCommentsNumById(Statistic statistic);

    /**
     * 统计博客总访问量
     * @author tianll
     * @date 2024-3-7
     * @return 访问量
     */
    @Select("select sum(t_statistic.hits) from t_statistic")
    long getTotalVisit();

    /**
     * 统计博客文章总评论量
     * @author tianll
     * @date 2024-3-7
     * @return 总评论量
     */
    @Select("select sum(t_statistic.comments_num) from t_statistic")
    long getTotalComment();

    /**
     * 插入博客统计信息（信息要全面）
     * @param record 任意统计信息的插入
     * @return
     */
    int insert(Statistic record);


    /**
     * 插入博客统计信息（信息要可以不全面）
     * @param record 任意统计信息的插入
     * @return
     */
    int insertSelective(Statistic record);

    /**
     * 通过评论id查找评论
     * @param id 评论id
     * @return 评论信息
     */
    Statistic selectByPrimaryKey(Long id);

    /**
     * 根据评论id更新评论（信息可以不全）
     * @param record 评论信息
     * @return 更新条数
     */
    int updateByPrimaryKeySelective(Statistic record);

    /**
     * 根据评论id更新评论
     * @param record 评论信息
     * @return 更新条数
     */
    int updateByPrimaryKey(Statistic record);
}
