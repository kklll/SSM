package com.controller;

import com.Pojo.PersonParam;
import com.Pojo.Person;
import com.mapper.PersonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Controller
public class PersonController {
    @Autowired
    private PersonMapper personMapper = null;

//    @RequestMapping("/person/{id}")
//    public ModelAndView person(@PathVariable("id") int id) {
//        Person person=new Person();
//        person.setName("呵呵");
//        person.setMoney((float)123.22);
//        person.setBirth("1888-1-1");
//        List<Person> list = personMapper.findRoles(null);
//        ModelAndView person2 = new ModelAndView("person");
//        person2.addObject(list);
//        person2.setView(new MappingJackson2JsonView());
//        return person2;
//    }

//    @RequestMapping("/findroles")
//    public ModelAndView findPerson(@RequestBody PersonParam personParam) {
//        List<Person> list = personMapper.findRoles(personParam);
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.addObject(list);
//        modelAndView.setView(new MappingJackson2JsonView());
//        return modelAndView;
//    }

    @RequestMapping("/from")
    public ModelAndView test2() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("from");
        return modelAndView;
    }

    @RequestMapping("/deleteRoles")
    public ModelAndView deleteRoles(@RequestBody List<Integer> idList) {
        ModelAndView mv = new ModelAndView();
        //删除角色
        for (Integer i : idList) {
            personMapper.deleteRole(i);
        }
        //绑定视图
        int total = 1;
        mv.addObject("total", total);
        //JSON视图
        mv.setView(new MappingJackson2JsonView());
        return mv;
    }

    @RequestMapping("/test2")
    public String tetsx(RedirectAttributes ra, Person person) {
        Person p = new Person();
        p.setName("张三");
        p.setMoney(person.getMoney());
        personMapper.insertRole(p);
        ra.addAttribute(p);
        return "redirect:./showrolejson";
    }

    @RequestMapping("/showrolejson")
    public ModelAndView textss(Person person) {
        ModelAndView mv = new ModelAndView();
        mv.setView(new MappingJackson2JsonView());
        mv.addObject(person);
        return mv;
    }

    @RequestMapping("/new")
    public ModelAndView reque(@RequestAttribute(value = "id") Integer id) {
        Person p=personMapper.getRole(id);
        ModelAndView mv=new ModelAndView();
        mv.setViewName("fuck");
        mv.addObject("p",p);
        mv.setView(new MappingJackson2JsonView());
        return mv;
    }

    @RequestMapping("/index22")
    public ModelAndView ss()
    {
        ModelAndView v=new ModelAndView("fuck");
        return v;
    }

}
