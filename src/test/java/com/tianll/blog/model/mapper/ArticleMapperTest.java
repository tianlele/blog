package com.tianll.blog.model.mapper;

import com.tianll.blog.model.domain.Article;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ArticleMapperTest {

    @Autowired
    private ArticleMapper articleMapper;

    @Test
    public void deleteByPrimaryKey() {
        System.out.println(articleMapper.deleteByPrimaryKey(4L));
    }

    @Test
    public void insert() {
        Article article = new Article();
        article.setTitle("测试");
        article.setContent("测试文章内容");
        article.setCreated(new Date());
        article.setModified(new Date());
        article.setCategories("1,2");
        article.setTags("1,2");
        article.setAllowComment(true);
        article.setThumbnail("http://www.baidu.com");
        System.out.println(articleMapper.insert(article));
    }

    @Test
    public void insertSelective() {
        Article article = new Article();
        article.setTitle("测试");
        article.setContent("测试文章内容");
        article.setCreated(new Date());
        article.setModified(new Date());
        article.setCategories("1,2");
        article.setTags("1,2");
        article.setAllowComment(true);
        article.setThumbnail("http://www.baidu.com");
        System.out.println(articleMapper.insertSelective(article));
    }

    @Test
    public void selectByPrimaryKey() {
        System.out.println(articleMapper.selectByPrimaryKey(1L));
    }

    @Test
    public void updateByPrimaryKeySelective() {
        Article article = new Article();
        article.setId(1);
        article.setTitle("测试");
        article.setContent("updateByPrimaryKeySelective方法进行");
        article.setCreated(new Date());
        article.setModified(new Date());
        article.setCategories("1,2");
        article.setTags("1,2");
        article.setAllowComment(true);
        article.setThumbnail("http://www.baidu.com");
        System.out.println(articleMapper.updateByPrimaryKeySelective(article));
    }

    @Test
    public void updateByPrimaryKey() {
        Article article = new Article();
        article.setId(2);
        article.setTitle("测试");
        article.setContent("updateByPrimaryKey方法进行");
        article.setCreated(new Date());
        article.setModified(new Date());
        article.setCategories("1,2");
        article.setTags("1,2");
        article.setAllowComment(true);
        article.setThumbnail("http://www.baidu.com");
        articleMapper.updateByPrimaryKey(article);
    }

    @Test
    public void publishArticle() {
        Article article = new Article();
        article.setTitle("测试");
        article.setContent("publishArticle方法进行");
        article.setCreated(new Date());
        article.setModified(new Date());
        article.setCategories("1,2");
        article.setTags("1,2");
        article.setAllowComment(true);
        article.setThumbnail("http://www.baidu.com");
        articleMapper.publishArticle(article);
    }

    @Test
    public void listArticlesOrderByIdWithPage() {
        System.out.println(articleMapper.listArticlesOrderByIdWithPage());
    }

    @Test
    public void countArticle() {
        System.out.println(articleMapper.countArticle());
    }
}