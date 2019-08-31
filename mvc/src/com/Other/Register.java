package com.Other;

import com.Converter.StringToMy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;

import java.util.ArrayList;
import java.util.List;

public class Register {
    //自定义转换器列表
    private List<Converter> myConverter = null;
    //依赖注入FormattingConversionServiceFactoryBean
    //此类在MVC初始化时载入
    @Autowired
    FormattingConversionServiceFactoryBean fcsfb = null;

    @Bean(name = "myConverter")
    public List<Converter> init() {
        if (myConverter == null) {
            myConverter = new ArrayList<>();
        }
        //自定义的角色转换器
        Converter personConverter=new StringToMy();
        myConverter.add(personConverter);
        //向转换服务类注册转换器哦
        fcsfb.getObject().addConverter(personConverter);
        return myConverter;
    }
}
