package com.tianll.blog.web.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author tianll
 * @date 2019/3/26
 * 登录控制器
 */
@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(HttpServletRequest request, Map<String, Object> map) {
        String referer = request.getHeader("referer");
        String url = request.getParameter("url");
        if (url != null && !url.isEmpty()) {
            map.put("url", url);
        } else if (referer != null && referer.contains("/login")) {
            map.put("url", "");
        }else {
            map.put("url", referer);
        }
        System.out.println("tianll----LoginController----login---");
        return "comm/login";
    }

    @GetMapping("/errorPage/{page}/{code}")
    public String AccessExceptionHandler(@PathVariable("page") String page,
                                         @PathVariable("code") String code) {
        return page + "/" + code;
    }
}

