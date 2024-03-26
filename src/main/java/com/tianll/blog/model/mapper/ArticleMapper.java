package com.tianll.blog.model.mapper;

import com.tianll.blog.model.domain.Article;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author tianll
* @description 针对表【t_article】的数据库操作Mapper
* @createDate 2024-03-14 16:19:54
* @Entity com.tianll.blog.model.domain.Article
*/
@Mapper
public interface ArticleMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Article record);

    int insertSelective(Article record);

    Article selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Article record);

    int updateByPrimaryKey(Article record);


    /**
     * 发布文章
     * @param article 文章
     * @return 文章id
     */
    @Insert("insert into t_article " +
            "(title, content, created, modified, categories, tags, allow_comment, thumbnail)" +
            "values " +
            "(#{title},#{content},#{created},#{modified},#{categories},#{tags},#{allowComment},#{thumbnail})")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    Integer publishArticle(Article article);
    /**
     * 查找出所有文章
     * @return 文章list
     */
    @Select("select * from t_article order by id desc")
    List<Article> listArticlesOrderByIdWithPage();

    /**
     * 统计文章数量
     * @return 文章数量
     */
    @Select("select count(1) from t_article")
    Integer countArticle();

}
