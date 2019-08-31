//package com.advice;
//
//import org.springframework.beans.propertyeditors.CustomDateEditor;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.WebDataBinder;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.InitBinder;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import sun.util.calendar.BaseCalendar;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//@ControllerAdvice(basePackages = {"com.controller"})
//public class MyAdvice {
//    @InitBinder
//    public void initBinder(WebDataBinder binder) {
//        //针对日期的格式化，其中CustomDateEditor是客户自定义的编辑器
//        //Boolean参数表示是否允许为空
//        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), false));
//    }
//
//    @ModelAttribute
//    public void populateModel(Model model) {
//        model.addAttribute("projectName", "test");
//    }
//
//    @ExceptionHandler(RuntimeException.class)
//    public String exception() {
//        return "exception";
//    }
//}
