package com.tianll.blog.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author tianll
 * @date 2024/3/23
 * 加密工具类,和本项目无关，仅用于生成明文的加密密码
 */
public class BCryptPasswordEncoderUtils {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("123456"));
    }
}
