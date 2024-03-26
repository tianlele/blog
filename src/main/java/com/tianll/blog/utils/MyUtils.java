package com.tianll.blog.utils;

import org.apache.commons.lang3.StringUtils;
import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.node.Link;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.AttributeProvider;
import org.commonmark.renderer.html.HtmlRenderer;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 文章处理工具类
 * @author tianll
 * @date 2024-3-8
 */
public class MyUtils {

    /**
     * 从HTML中提取纯文本
     *
     * @param html HTML字符串
     * @return 提取的纯文本
     */
    public static String htmlToText(String html) {
        if (StringUtils.isNotBlank(html)) {
            // 此方法将HTML标签和空格替换为单个空格，以获得纯文本内容。
            return html.replaceAll("(?s)<[^>]*>(\\s*<[^>]*>)*", " ");
        }
        return "";
    }

    /**
     * 将Markdown格式的文本转换为HTML
     *
     * @param markdown Markdown格式的文本
     * @return 转换后的HTML文本
     */
    public static String mdToHtml(String markdown) {
        if (StringUtils.isBlank(markdown)) {
            return "";
        }
        // 创建Markdown解析器，并添加表格扩展
        List<Extension> extensions = Arrays.asList(TablesExtension.create());
        Parser parser = Parser.builder().extensions(extensions).build();
        // 解析Markdown文本并生成相应的HTML文档
        Node document = parser.parse(markdown);
        // 创建HTML渲染器，配置属性提供者以处理链接的属性
        HtmlRenderer renderer = HtmlRenderer.builder()
                .attributeProviderFactory(context -> new LinkAttributeProvider())
                .extensions(extensions).build();
        // 渲染HTML文档，进行额外处理，例如表情替换
        String content = renderer.render(document);
        content = Commons.emoji(content); // 使用emoji方法进行额外处理
        return content;
    }

    /**
     * 清除输入值中的HTML脚本和潜在的XSS攻击
     *
     * @param value 输入值
     * @return 清除HTML脚本后的值
     */
    public static String cleanXSS(String value) {
        // 以下代码通过正则表达式替换一些潜在的XSS攻击代码。
        value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
        value = value.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");
        value = value.replaceAll("'", "&#39;");
        value = value.replaceAll("eval\\((.*)\\)", "");
        value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
        value = value.replaceAll("script", "");
        return value;
    }

    /**
     * 链接属性提供者，用于设置链接的target属性为"_blank"以在新标签页中打开
     */
    private static class LinkAttributeProvider implements AttributeProvider {
        @Override
        public void setAttributes(Node node, String tagName, Map<String, String> attributes) {
            if (node instanceof Link) {
                attributes.put("target", "_blank");
            }
        }
    }
}
