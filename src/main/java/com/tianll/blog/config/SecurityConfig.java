package com.tianll.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;

/**
 * @author tianll
 * @date 2024/3/23
 */

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 有效时间 单位秒
     */
    @Value(value = "${COOKIE.VALIDITY}")
    private int validity;

    @Autowired
    private DataSource dataSource;

    @Override
    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers("/css/**", "/js/**");
//        web.ignoring().antMatchers("/img/**");
//        web.ignoring().antMatchers("/favicon.ico");
//        web.ignoring().antMatchers("/blog/login");
//        web.ignoring().antMatchers("/blog/register");
//        web.ignoring().antMatchers("/blog/logout");
//        web.ignoring().antMatchers("/blog/admin");
//        web.ignoring().antMatchers("/blog/admin/**");


    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        用户访问控制
        http.authorizeRequests()
                .antMatchers("/", "/page/**", "/article/**", "/login").permitAll()
                .antMatchers("/back/**", "/assets/**", "/user/**", "/article_img/**").permitAll()
                //  给WebMvcConfig中的图片路径/img/**放行
                .antMatchers("/img/**").permitAll()

                .antMatchers("/admin/**").hasRole("admin")
                .anyRequest().authenticated();

        // 登录配置
        http.formLogin()
                .loginPage("/login")
                .usernameParameter("username").passwordParameter("password")
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                        String url = request.getParameter("url");
                        //获取被拦截登录的原始访问路径
                        RequestCache requestCache = new HttpSessionRequestCache();
                        SavedRequest savedRequest = requestCache.getRequest(request, response);
                        if (savedRequest != null) {
                            //如果存在被拦截的路径，登录后跳转到原始访问路径
                            response.sendRedirect(savedRequest.getRedirectUrl());
                        } else if (url != null && !url.isEmpty()) {
                            //跳转到之前所在页面
                            URL fullUrl = new URL(url);
                            response.sendRedirect(fullUrl.getPath());
                        } else {
                            //直接登录的用户：根据角色和权限跳转到相应的页面（后台首页或者博客首页）
                            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
                            for (GrantedAuthority authority : authorities) {
                                if (authority.getAuthority().contains("ROLE_ADMIN")) {
                                    response.sendRedirect("/admin");
                                } else if (authority.getAuthority().contains("ROLE_USER")) {
                                    response.sendRedirect("/");
                                }
                            }
                        }

                    }
                })
                .failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                        String url = request.getParameter("url");
                        response.sendRedirect("/login?error&url="+url);
                    }
                });
        //cookie有效期配置
        http.rememberMe().alwaysRemember(true).tokenValiditySeconds(validity);
        // 注销配置
        http.logout()
                .logoutUrl("/logout").logoutSuccessUrl("/");
        // 跨域配置
//        http.cors();
//        403错误页面定制
        http.exceptionHandling()
//                .accessDeniedPage("/errorPage/comm/error_403")
                .accessDeniedHandler(new AccessDeniedHandler() {
                    @Override
                    public void handle(HttpServletRequest request,
                                       HttpServletResponse response,
                                       AccessDeniedException accessDeniedException)
                            throws IOException, ServletException {
                        //访问权限异常，则进行拦截到指定403错误页面
                        RequestDispatcher dispatcher = request.getRequestDispatcher("/errorPage/comm/error_403");
                        dispatcher.forward(request, response);
                    }
                });

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String userSql = "select username, password, valid from t_user where username = ?";
//        朝招指定用户名对应的权限
        String authoritySql = "select u.username ,a.authority " +
                "from " +
                "t_user u,t_authority a,t_user_authority ua " +
                "where " +
                "ua.user_id = u.id " +
                "and ua.authority_id = a.id " +
                "and u.username = ?";
        auth.jdbcAuthentication().passwordEncoder(encoder)
                .dataSource(dataSource)
                .usersByUsernameQuery(userSql)
                .authoritiesByUsernameQuery(authoritySql);
    }
}
