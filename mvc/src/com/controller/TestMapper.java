package com.controller;

import com.validator.TransactionValidator;
import org.springframework.stereotype.Controller;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
public class TestMapper {

    @InitBinder
    public void initBinder(DataBinder binder)
    {
        //数据绑定加入验证器
        binder.setValidator(new TransactionValidator());
    }
    @RequestMapping("/testfor")
    public ModelAndView forthis(@Valid TestForSale transactional, Errors errors) {
        if (errors.hasErrors()) {
            List<FieldError> errorList = errors.getFieldErrors();
            for (FieldError f : errorList) {
                System.out.println("fied: " + f.getField() + "\t" + " msg: " + f.getDefaultMessage());
            }
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jiaoyi");
        return modelAndView;
    }
}
