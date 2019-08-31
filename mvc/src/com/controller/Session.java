package com.controller;

import com.Pojo.Person;
import com.mapper.PersonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@SessionAttributes(value = "id", types = {Person.class})
public class Session {
    @Autowired
    private PersonMapper personMapper = null;

    @RequestMapping("/haha")
    public ModelAndView session(@SessionAttribute("id") int id) {
        ModelAndView mv = new ModelAndView();
        Person p = personMapper.getRole(id);
        mv.addObject("person", p);
        mv.addObject("id", id);
        mv.setViewName("shax");
        return mv;
    }

    @RequestMapping("/123")
    public String xx() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("fuck");
        return "Sucess";
    }

    @RequestMapping("234")
    public void xsxs(@RequestHeader(value = "User-Agent", required = false, defaultValue = "morende")
                             String userAgent, @CookieValue(value = "JSESSIONID", required = false, defaultValue = "mys" +
            "") String cookie) {
        System.out.println(userAgent);
        System.out.println(cookie);
    }

}
