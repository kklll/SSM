package com.Converter;

import com.Pojo.Person;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

public class StringToMy implements Converter<String,Person> {
    @Override
    public Person convert(String s) {
        //判断空串
        if (StringUtils.isEmpty(s)) {
            try {
                throw new Exception("wocao");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (s.indexOf("-") == -1) {
            return null;
        }
        String[] arr = s.split("-");
        if (arr.length != 3) {
            return null;
        }
        Person person = new Person();
        person.setName(arr[0]);
        person.setBirth(arr[1]);
        person.setMoney(Float.parseFloat(arr[2]));
        return person;
    }
}
