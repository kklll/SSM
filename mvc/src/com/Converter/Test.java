package com.Converter;

import com.Pojo.Person;
import com.exception.MyException;
import com.mapper.PersonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class Test {
    @Autowired
    private PersonMapper personMapper = null;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        //针对日期的格式化，其中CustomDateEditor是客户自定义的编辑器
        //Boolean参数表示是否允许为空
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), false));
    }

    @ModelAttribute("person")
    public Person initPerson(@RequestParam(value = "id", required = false) Integer id) {
        if (id == null || id < 1) {
            return null;
        }
        Person person = personMapper.getRole(id);
        return person;
    }

    @RequestMapping("/testss")
    @ResponseBody
    public Person tsdsjab(@ModelAttribute("person") Person person) {
        return person;
    }

    @RequestMapping("/myerror")
    @ResponseBody
    public Person myError(int id) {
        Person person = personMapper.getRole(id);
        if (person == null) {
            throw new MyException();
        }
        return person;
    }

    @ExceptionHandler(MyException.class)
    public String re() {
        return "exception";
    }
}
