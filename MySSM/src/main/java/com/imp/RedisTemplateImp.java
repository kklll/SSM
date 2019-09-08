package com.imp;

import com.inter.RedisTemplateServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class RedisTemplateImp implements RedisTemplateServer {

    @Autowired
    private RedisTemplate redisTemplate=null;
    /*
    使用SessionCallBack接口来实现多个命令在同一个Redis连接中执行
     */
    @Override
    public void execMultiCommand() {
        Object obj=redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations ops) throws DataAccessException {
                ops.boundValueOps("key1").set("abc");
                ops.boundHashOps("hash").put("hash-key-1","hash-value-1");
                return ops.boundValueOps("key1").get();
            }
        });
        System.out.println(obj);
    }

    /*
    失误在一个Redis连接中执行
     */
    @Override
    public void execTransaction() {
        List list= (List) redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations ops) throws DataAccessException {
                ops.watch("key1");
                //开启事务
                ops.multi();
                //此时所有的命令都会放到队列中，不会立即执行
                ops.boundValueOps("key1").set("abc");
                ops.boundHashOps("hash").put("hash-key-1", "hash-value-1");
                List result = ops.exec();
                return result;
            }
        });
        System.out.println(list);
    }

    @Override
    public void execPipeline() {
        List list=redisTemplate.executePipelined(new SessionCallback<Object>() {
            @Override
            public <K, V> Object execute(RedisOperations<K, V> ops) throws DataAccessException {
                ops.opsForValue().set((K)"key",(V)"value");
                ops.opsForHash().put((K)"hash","key-hash-1","value-hash-1");
                ops.opsForValue().get("key1");
                return null;
            }
        });
        System.out.println(list);
    }
}
