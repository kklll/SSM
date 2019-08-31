package com.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableCaching
public class RedisConfig {

    @Bean(name = "redisTemplate")
    public RedisTemplate initRedisTemplate() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        //最大空闲数
        jedisPoolConfig.setMaxIdle(50);
        //最大连接数
        jedisPoolConfig.setMaxTotal(100);
        //最大等待毫秒
        jedisPoolConfig.setMaxWaitMillis(20000);
        //创建Jedis连接工厂
        JedisConnectionFactory connectionFactory = new JedisConnectionFactory(jedisPoolConfig);
        connectionFactory.setHostName("localhost");
        connectionFactory.setPort(6379);
        //调用后初始化方法，没有将抛出异常
        connectionFactory.afterPropertiesSet();
        //Jedis序列化器
        RedisSerializer jdkSerializer = new JdkSerializationRedisSerializer();
        RedisSerializer stringSerializer = new StringRedisSerializer();
        //定义RedisTemplate
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setDefaultSerializer(stringSerializer);
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(jdkSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(jdkSerializer);
        return redisTemplate;
    }

    @Bean(name = "redisCacheManager")
    public CacheManager initCacheManager(@Autowired RedisTemplate redisTemplate) {
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
        //设置超时时间，秒
        cacheManager.setDefaultExpiration(600);
        //设置缓存名称
        List<String> cacheNames = new ArrayList<>();
        cacheNames.add("redisCacheManager");
        cacheManager.setCacheNames(cacheNames);
        return cacheManager;
    }
}
