package com.tianll.blog;

import com.tianll.blog.model.domain.Statistic;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
public class BlogApplicationTests {


    @Test
    public void contextLoads() {
        System.out.println(System.getProperty("user.dir"));
    }

    @Test
    public void test() {
        Assert.assertEquals(1, 2);
    }


}
