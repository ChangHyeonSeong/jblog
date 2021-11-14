package com.douzone.jblog.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

import com.douzone.config.web.FileUploadConfig;
import com.douzone.config.web.MessageConfig;
import com.douzone.config.web.MvcConfig;
import com.douzone.config.web.SecurityConfig;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan({"com.douzone.jblog.controller", "com.douzone.jblog.exception", "com.douzone.jblog.aspect"})
@Import({MvcConfig.class , MessageConfig.class , FileUploadConfig.class , SecurityConfig.class})
public class WebConfig {// MvcConfig.class 에
																								// @EnableWebMvc 이게
																								// 달려있어서 맨처음에 있어야한다

}