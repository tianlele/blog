package com.tianll.blog.utils;


import com.tianll.blog.model.domain.Article;
import com.vdurmont.emoji.EmojiParser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @BelongsProject: Springboot  // 项目名
 * @BelongsPackage: com.example.springboot  // 包名
 * @ClassName Commons                // 类名
 * @Author: laozaza                   // 作者
 * @CreateTime: 2023-10-26  20:31  // 时间
 **/

/**
 * 页面数据展示封装类
 */
@Component
public class Commons {
    /**
     * 网站链接
     *
     * @return
     */
    public static String site_url() {
        return site_url("/page/1");
    }

    /**
     * 返回网站链接下的全址
     *
     * @param sub 后面追加的地址
     * @return
     */
    public static String site_url(String sub) {
        return site_option("site_url") + sub;
    }

    /**
     * 网站配置项
     *
     * @param key
     * @return
     */
    public static String site_option(String key) {
        return site_option(key, "");
    }

    /**
     * 网站配置项
     *
     * @param key
     * @param defalutValue 默认值
     * @return
     */
    public static String site_option(String key, String defalutValue) {
        if (StringUtils.isBlank(key)) {
            return "";
        }
        return defalutValue;
    }

    /**
     * 截取字符串
     *
     * @param str
     * @param len
     * @return
     */
    public static String substr(String str, int len) {
        if (str.length() > len) {
            return str.substring(0, len);
        }
        return str;
    }

    /**
     * 返回日期
     *
     * @return
     */
    public static String dateFormat(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    /**
     * 返回文章链接地址
     *
     * @param aid
     * @return
     */
    public static String permalink(Integer aid) {
        return site_url("/article/" + aid.toString());
    }

    /**
     * 生成文章的摘要文本。如果文章包含分隔符"<!--more-->"，摘要将是分隔符之前的内容；如果没有分隔符，摘要将是全文的前 len 个字符，并附加省略号以指示文本被截断。
     *
     * @param article  文章对象，包含文章内容
     * @param len      摘要的最大长度
     * @return 生成的摘要文本
     */
    public static String intro(Article article, int len) {
        // 从文章对象中获取文章内容
        String value = article.getContent();
        if (value == null) {
            return "内容为空，请更新";
        }

        // 检查文章内容是否包含分隔符"<!--more-->"
        int pos = value.indexOf("<!--more-->");

        if (pos != -1) {
            // 如果包含分隔符
            // 从文章内容的开头截取到分隔符位置之前的部分，形成一个子字符串
            String html = value.substring(0, pos);

            // 将子字符串转换为HTML格式，然后再转换为纯文本，去除HTML标签和其他格式
            return MyUtils.htmlToText(MyUtils.mdToHtml(html));
        } else {
            // 如果不包含分隔符
            // 将整篇文章的Markdown内容转换为HTML格式，然后再转换为纯文本，去除HTML标签和其他格式
            String text = MyUtils.htmlToText(MyUtils.mdToHtml(value));

            if (text.length() > len) {
                // 如果纯文本长度超过了 len，截取前 len 个字符，然后在末尾添加省略号"..."
                return text.substring(0, len) + "......";
            } else {
                // 如果纯文本长度未超过 len，返回生成的全文纯文本
                return text;
            }
        }
    }



    /**
     * 对文章内容进行格式转换，将Markdown为Html
     * @param value
     * @return
     */
    public static String article(String value) {
        if (StringUtils.isNotBlank(value)) {
            value = value.replace("<!--more-->", "\r\n");
            return MyUtils.mdToHtml(value);
        }
        return "";
    }

    /**
     * 显示文章缩略图，顺序为：文章第一张图 -> 随机获取
     *
     * @return
     */
    public static String show_thumb(Article article) {
        // 1. 检查文章对象是否包含有效的缩略图信息
        if (StringUtils.isNotBlank(article.getThumbnail())) {
            // 2. 如果缩略图不为空，直接返回缩略图路径
            return article.getThumbnail();
        }

        // 3. 从文章对象获取ID
        int cid = article.getId();

        // 4. 计算缩略图的大小（size）：ID对24取余
        int size = cid % 24;

        // 5. 如果size等于0，将其设置为1，确保不会为零
        size = size == 0 ? 1 : size;

        //设置一个随机数如果文章id大于16则采用图片库里随机图片
        if (size>16){
            Random random = new Random();
            int randomNumber = random.nextInt(17);
            size=randomNumber;
            // 5. 如果size等于0，将其设置为1，确保不会为零
            size = size == 0 ? 1 : size;
        }

        //  返回最终的缩略图路径
        return "/user/img/rand/" + size + ".png";
    }

    /**
     * 这种格式的字符转换为emoji表情
     *
     * @param value
     * @return
     */
    public static String emoji(String value) {
        return EmojiParser.parseToUnicode(value);
    }
}
