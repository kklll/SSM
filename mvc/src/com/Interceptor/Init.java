package com.Interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Init {
    @Bean(name = "multipartResolver")
    public MultipartResolver initMultipartResolver() {
        return new StandardServletMultipartResolver();
    }

//    @Bean("requestMappingHandlerAdapter")
//    public HandlerAdapter initHandler() {
//        //创建转换器
//        RequestMappingHandlerAdapter requestMappingHandlerAdapter = new RequestMappingHandlerAdapter();
//        //http-》Json转换器A
//        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
//        MediaType mediaType = MediaType.APPLICATION_JSON_UTF8;
//        List<MediaType> mediaTypes = new ArrayList<MediaType>();
//        mediaTypes.add(mediaType);
//        //加入转换器支持的转换格式
//        jsonConverter.setSupportedMediaTypes(mediaTypes);
//        //往适配器加入Json转换器
//        requestMappingHandlerAdapter.getMessageConverters().add(jsonConverter);
//        return requestMappingHandlerAdapter;
//    }


//    @Bean("localeResolver")
//    public LocaleResolver initCookie() {
//        CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
//        //名称
//        cookieLocaleResolver.setCookieName("lang");
//        //设置超时Cookie超时时间
//        cookieLocaleResolver.setCookieMaxAge(1800);
//        //设置默认
//        cookieLocaleResolver.setDefaultLocale(Locale.SIMPLIFIED_CHINESE);
//        return cookieLocaleResolver;
//    }

    @Bean("localeResolver")
    public LocaleResolver initSession() {
        SessionLocaleResolver sessionLocaleResolver=new SessionLocaleResolver();
        sessionLocaleResolver.setDefaultLocale(Locale.SIMPLIFIED_CHINESE);
        return sessionLocaleResolver;
    }


}