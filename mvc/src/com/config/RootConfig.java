//package com.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//import org.springframework.transaction.annotation.TransactionManagementConfigurer;
//
//import javax.sql.DataSource;
//import java.util.Properties;
//
//
//@Configuration
////定义扫描的包
//@ComponentScan("com.*")
////使用事务驱动管理器
//@EnableTransactionManagement
//public class RootConfig implements TransactionManagementConfigurer {
//    //由于在xml文件中进行了配置，则使用自动装配
//    @Autowired
//    private DataSource dataSource = null;
//
//
//    /*
//    配置数据库
//     */
//    //由于配置过数据库的配置此处省略配置
//
//    /*
//    配置SqlSessionFactoryBean
//     */
//    //此处也进行了配置，省略
//
//    /*
//    配置自动包扫描
//     */
//    //也进行了配置此处省略
//
//
//    /*
//    注解驱动配置，当使用@Transactional注释的时候残生数据库事务
//     */
//    @Bean
//    @Override
//    public PlatformTransactionManager annotationDrivenTransactionManager() {
//        DataSourceTransactionManager manager=new DataSourceTransactionManager();
//        manager.setDataSource(dataSource);
//        return manager;
//    }
//}
