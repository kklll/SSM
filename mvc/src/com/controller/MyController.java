package com.controller;

import com.Pojo.Person;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.portlet.ModelAndView;

@Controller("myController")
@RequestMapping("/my/")
public class MyController {
    @RequestMapping(value = "/index")
    public ModelAndView index(@RequestParam(value = "id",required = false) int id) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("index");
        mv.addObject("id", id);
//        System.out.println(id);
        return mv;
    }

    @RequestMapping("/index2")
    public ModelAndView index2() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("index2");
        return mv;
    }

    @RequestMapping("/test")
    public ModelAndView test(Person person) {
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("test");
        modelAndView.addObject("name",person.getName());
        modelAndView.addObject("money",person.getMoney());
        return modelAndView;
    }

}
