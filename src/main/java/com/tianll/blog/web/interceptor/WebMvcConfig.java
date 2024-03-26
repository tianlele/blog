package com.tianll.blog.web.interceptor;

import com.sun.xml.bind.v2.TODO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 通过WebMvcConfigurer，添加mvc配置，注册自定义的interceptor拦截器
 * @author tianll
 * @date 2024/3/7
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    String uploadPath = "data/";

    @Autowired
    private BaseInterceptor baseInterceptor;



    /**
     * 添加拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(baseInterceptor);
    }

    /**
     * 添加静态资源映射
     * @param registry 资源映射
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if(System.getProperty("os.name").toLowerCase().contains("windows")) {
            System.out.println(System.getProperty("os.name")+"-------------------------");
            uploadPath = "E:/img/";
            registry.addResourceHandler("/img/**").addResourceLocations("file:///" + uploadPath);
            registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
        } else {
//            todo linux映射路径
        }
    }
}
