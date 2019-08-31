//package com.Other;
//
//import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
//
//import javax.servlet.ServletRegistration;
//import javax.servlet.MultipartConfigElement;
//
//public class MyWebAppInit extends AbstractAnnotationConfigDispatcherServletInitializer {
//    //spring IOC容器配置
//    @Override
//    protected Class<?>[] getRootConfigClasses() {
//        //配置spring的Java配置文件数组
//        return new Class<?>[]{};
//    }
//
//    //Servlet的映射关系配置
//    @Override
//    protected Class<?>[] getServletConfigClasses() {
//        return new Class[0];
//    }
//
//
//    @Override
//    protected String[] getServletMappings() {
//        return new String[]{"*"};
//    }
//
//    @Override
//    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
//        String filePath="F:/uploads";
//        long singMax= (long) (5*Math.pow(2,20));
//        long totalMax= (long) (10*Math.pow(2,20));
//        registration.setMultipartConfig(new MultipartConfigElement(filePath,singMax,totalMax,0));
//    }
//}
