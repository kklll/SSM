package com.Inter;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;

public class RedisMessageListener implements MessageListener {
    RedisTemplate redisTemplate;
    @Override
    public void onMessage(Message message, byte[] pattern) {
        byte[] body=message.getBody();
        String msgBody= (String) getRedisTemplate().getValueSerializer().deserialize(body);
        System.out.println(msgBody);
        //获取信道
        byte[] channel=message.getChannel();
        //转换为字符串
        String channelString= (String) getRedisTemplate().getStringSerializer().deserialize(channel);
        System.out.println(channelString);
        String byteString=new String(pattern);
        System.out.println(byteString);
    }

    public RedisTemplate getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}
