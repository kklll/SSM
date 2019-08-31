package com.main;

import com.config.RedisConfig;
import com.config.RootConfig;
import com.mapper.PersonMapper;
import com.pojo.Person;
import com.server.PersonServer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Test {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(RootConfig.class, RedisConfig.class);
        PersonServer personServer = applicationContext.getBean(PersonServer.class);
        Person person = new Person();
        person.setName("李四");
        person.setBirth("1998-1-1");
        person.setMoney((float) 10000.21);
        personServer.insertRole(person);
        Person person1 = personServer.getRole(person.getId());
        System.out.println(person);
        System.out.println(person1);
        personServer.updateRole(person1);
    }

    @org.junit.Test
    public void test() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(RootConfig.class,RedisConfig.class);
        PersonMapper personMapper = applicationContext.getBean(PersonMapper.class);
        Person person = personMapper.getRole(1);
        System.out.println(person);
    }
}
