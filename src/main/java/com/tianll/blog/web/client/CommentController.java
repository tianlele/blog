package com.tianll.blog.web.client;


import com.tianll.blog.model.domain.Article;
import com.tianll.blog.model.domain.Comment;
import com.tianll.blog.model.responseData.ArticleResponseData;
import com.tianll.blog.service.CommentService;
import com.tianll.blog.utils.MyUtils;
import com.vdurmont.emoji.EmojiParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;


/**
 * @author 马俊卿
 * @date 2023/11/1 14:48
 */

@Controller
@RequestMapping("/comments")
public class CommentController {
    private static final Logger log = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    private CommentService commentService;

    /**
     * 评论
     */
    @PostMapping("/publish")
    @ResponseBody
    public ArticleResponseData publishComment(HttpServletRequest request
            , @RequestParam Integer aid,@RequestParam String text) {
//        去除js脚本
        text = MyUtils.cleanXSS(text);
        text = EmojiParser.parseToUnicode(text);
//        获取当前登录用户
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        封装评论信息
        Comment comment = new Comment();
        comment.setArticleId(aid);
//        0:0:0:0:0:0:0:1 表示本机127.0.0.1
        if(request.getRemoteAddr().equals("0:0:0:0:0:0:0:1")){
            comment.setIp("127.0.0.1");
        }else {
            comment.setIp(request.getRemoteAddr());
        }
        comment.setCreated(new Date());
        comment.setAuthor(user.getUsername());
        comment.setContent(text);
        try {
            log.info("评论成功,对应文章id为："+aid+"，评论内容为："+text);
            commentService.pushComment(comment);
            return ArticleResponseData.ok();
        } catch (Exception e) {
            log.error("评论失败,对应文章id为:"+aid+"     ;错误描述："+ e.getMessage());
            return ArticleResponseData.fail();
        }
    }

}
