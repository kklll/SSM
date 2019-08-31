package com.controller;


import com.Pojo.Person;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisListCommands;

import java.util.*;

import org.springframework.data.redis.connection.RedisZSetCommands.Range;
import org.springframework.data.redis.connection.RedisZSetCommands.Limit;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.*;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Pipeline;

public class Test {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        RedisTemplate redisTemplate = applicationContext.getBean(RedisTemplate.class);
        Person person = new Person();
        person.setMoney((float) 100.22);
        person.setBirth("2019-1-1");
        person.setName("张三");
        person.setId(1);
        redisTemplate.opsForValue().set("person", person);
        Person person1 = (Person) redisTemplate.opsForValue().get("person");
        System.out.println(person1.getMoney());
    }

    @org.junit.Test
    public void test() {
        ApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("applicationContext.xml");
        RedisTemplate redisTemplate = applicationContext.getBean(RedisTemplate.class);
        Person person = new Person();
        person.setMoney((float) 100.22);
        person.setBirth("2019-1-1");
        person.setName("张三");
        person.setId(1);
        SessionCallback callback = new SessionCallback() {
            @Override
            public Object execute(RedisOperations redisOperations) throws DataAccessException {
                redisOperations.boundValueOps("person").set(person);
                return redisOperations.boundValueOps("person").get();
            }
        };
        Person savedPerson = (Person) redisTemplate.execute(callback);
        System.out.println(savedPerson.getBirth());
    }

    @org.junit.Test
    public void testString() {
        ApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("applicationContext.xml");
        RedisTemplate redisTemplate = applicationContext.getBean(RedisTemplate.class);
        redisTemplate.opsForValue().set("key", "value");
        redisTemplate.opsForValue().set("key2", "value2");
        String value = (String) redisTemplate.opsForValue().get("key");
        System.out.println(value);
        redisTemplate.delete("key");
        int length = Math.toIntExact(redisTemplate.opsForValue().size("key2"));
        System.out.println(length);
        String old = (String) redisTemplate.opsForValue().getAndSet("key2", "new_value");
        System.out.println(old);
        String value2 = (String) redisTemplate.opsForValue().get("key2");
        System.out.println(value2);
        int newline = redisTemplate.opsForValue().append("key2", "haha");
        System.out.println(newline);
        System.out.println(redisTemplate.opsForValue().get("key2"));
    }

    @org.junit.Test
    public void testForCal() {
        ApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("applicationContext.xml");
        RedisTemplate redisTemplate = applicationContext.getBean(RedisTemplate.class);
        redisTemplate.opsForValue().set("i", "9");
        print(redisTemplate, "i");
        redisTemplate.opsForValue().increment("i", 1);
        print(redisTemplate, "i");
        redisTemplate.getConnectionFactory().getConnection().decr(redisTemplate.getKeySerializer().serialize("i"));
        print(redisTemplate, "i");
        redisTemplate.getConnectionFactory().getConnection().decrBy(redisTemplate.getKeySerializer().serialize("i"), 6);
        print(redisTemplate, "i");
        redisTemplate.opsForValue().increment("i", 2.3);
        print(redisTemplate, "i");

    }

    public static void print(RedisTemplate redisTemplate, String key) {
        String result = (String) redisTemplate.opsForValue().get(key);
        System.out.println(result);
    }

    @org.junit.Test
    public void testForHash() {
        ApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("applicationContext.xml");
        RedisTemplate redisTemplate = applicationContext.getBean(RedisTemplate.class);
        String key = "hash";
        Map<String, String> map = new HashMap<>();
        map.put("f1", "value1");
        map.put("f2", "value2");
        //放入数据，相当于hmset
        redisTemplate.opsForHash().putAll(key, map);
        //相当于hset
        redisTemplate.opsForHash().put(key, "f3", "3");
        printHash(redisTemplate, key, "f3");
        boolean exists = redisTemplate.opsForHash().hasKey(key, "f3");
        System.out.println(exists);
        //相当于hgetAll命令
        Map keyValueMap = redisTemplate.opsForHash().entries(key);
        //相当于hincyby
        redisTemplate.opsForHash().increment(key, "f3", 2);
        printHash(redisTemplate, key, "f3");
        //相当于hincyfloat
        redisTemplate.opsForHash().increment(key, "f3", 0.88);
        printHash(redisTemplate, key, "f3");
        //相当于hvals
        List<String> list = redisTemplate.opsForHash().values(key);
        //相当于hkeys
        Set keylist = redisTemplate.opsForHash().keys(key);
        List<String> fields = new ArrayList<>();
        fields.add("f1");
        fields.add("f2");
        //相当与hmget命令
        List valueList2 = redisTemplate.opsForHash().multiGet(key, keylist);
        boolean success = redisTemplate.opsForHash().putIfAbsent(key, "f4", "value4");
        System.out.println(success);
        Long result = redisTemplate.opsForHash().delete(key, "f1", "f2");
        System.out.println(result);
    }

    public void printHash(RedisTemplate redisTemplate, String key, String field) {
        String index = (String) redisTemplate.opsForHash().get(key, field);
        System.out.println(index);
    }

    @org.junit.Test
    public void testForLinked() {
        ApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("applicationContext.xml");
        RedisTemplate redisTemplate = applicationContext.getBean(RedisTemplate.class);
        try {
            //删除链表
            redisTemplate.delete("list");
            redisTemplate.opsForList().leftPush("list", "node3");
            List<String> nodeList = new ArrayList<>();
            for (int i = 2; i >= 1; i--) {
                nodeList.add("node" + i);
            }
            redisTemplate.opsForList().leftPushAll("list", nodeList);
            redisTemplate.opsForList().rightPush("list", "node4");
            //获取节点值
            String node = (String) redisTemplate.opsForList().index("list", 0);
            long size = redisTemplate.opsForList().size("list");
            //从左边弹出节点
            String lpop = (String) redisTemplate.opsForList().leftPop("list");
            //右边弹出
            String rpop = (String) redisTemplate.opsForList().rightPop("list");
            //需要底层命令才能操作linsert命令
            //使用linsert命令在node2前插入一个节点
            redisTemplate.getConnectionFactory().getConnection().lInsert("list".getBytes("UTF-8"),
                    RedisListCommands.Position.BEFORE,
                    "node2".getBytes("UTF-8"), "before_node".getBytes("UTF-8"));
            redisTemplate.opsForList().leftPushIfPresent("list", "head");
            redisTemplate.opsForList().rightPushIfPresent("list", "end");
            //从左到右，或者下表从0到10的节点
            List valueList = redisTemplate.opsForList().range("list", 0, 10);
            nodeList.clear();
            for (int i = 0; i <= 3; i++) {
                nodeList.add("node");
            }
            redisTemplate.opsForList().leftPushAll("list", nodeList);
            //删除三个节点，从左到右
            redisTemplate.opsForList().remove("list", 3, "node");
            redisTemplate.opsForList().set("list", 0, "new_head_list");

        } catch (Exception e) {
            e.printStackTrace();
        }
        printLinked(redisTemplate, "list");
    }

    public static void printLinked(RedisTemplate redisTemplate, String key) {
        Long size = redisTemplate.opsForList().size(key);
        List valueList = redisTemplate.opsForList().range(key, 0, size);
        System.out.println(valueList);
        System.out.println("size=" + size);
    }

    @org.junit.Test
    public void testList() {
        ApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("applicationContext.xml");
        RedisTemplate redisTemplate = applicationContext.getBean(RedisTemplate.class);
        redisTemplate.delete("list1");
        redisTemplate.delete("list2");
        List<String> nodeList = new ArrayList<>();
        for (int i = 0; i <= 5; i++) {
            nodeList.add("node" + i);
        }
        redisTemplate.opsForList().leftPushAll("list1", nodeList);
        //spring使用超时时间作为阻塞命令区分，等价于blpop命令，并且可以设置时间参数
        redisTemplate.opsForList().leftPop("list1", 1, TimeUnit.SECONDS);
        redisTemplate.opsForList().rightPop("list1", 1, TimeUnit.SECONDS);
        nodeList.clear();
        for (int i = 1; i <= 3; i++) {
            nodeList.add("data" + i);
        }
        redisTemplate.opsForList().leftPushAll("list2", nodeList);
        //相当于rpoplpush命令，弹出list1最右边的节点，插入到list2的最左边
        redisTemplate.opsForList().rightPopAndLeftPush("list1", "list2");
        //相当于brpoplpush命令，注意在spring中使用超时参数区分
        redisTemplate.opsForList().rightPopAndLeftPush("list1", "list2",
                1, TimeUnit.SECONDS);
        printLinked(redisTemplate, "list1");
        printLinked(redisTemplate, "list2");
    }

    @org.junit.Test
    public void testForSet() {
        ApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("applicationContext.xml");
        RedisTemplate redisTemplate = applicationContext.getBean(RedisTemplate.class);
        Set set = null;
        //将元素加入列表
        redisTemplate.boundSetOps("set1").add("v1", "v2", "v3", "v4", "v5", "v6");
        redisTemplate.boundSetOps("set2").add("v0", "v2", "v4", "v6", "v8");
        //求集合长度
        redisTemplate.boundSetOps("set1").size();
        //求差集
        set = redisTemplate.opsForSet().difference("set1", "set2");
        System.out.println(set);
        //求并集
        set = redisTemplate.opsForSet().intersect("set1", "set2");
        System.out.println(set);
        //判断是否存在集合中的元素
        boolean exists = redisTemplate.opsForSet().isMember("set1", "v1");
        System.out.println(exists);
        set = redisTemplate.opsForSet().members("set1");
        System.out.println(set);
        //随机弹出一个元素从集合中
        String val = (String) redisTemplate.opsForSet().pop("set1");
        System.out.println(val);
        String val2 = (String) redisTemplate.opsForSet().randomMember("set1");
        System.out.println(val2);
        //随机获取两个集合的元素
        List list = redisTemplate.opsForSet().randomMembers("set1", 2);
        System.out.println(list);
        //删除一个集合的元素，参数可以是多个
        redisTemplate.opsForSet().remove("set1", "v1");
        //求并集
        set = redisTemplate.opsForSet().union("set1", "set2");
        System.out.println(set);
        //求两个集合的差集，并将结果保存到diff_set中
        redisTemplate.opsForSet().differenceAndStore("set1", "set2", "diff_set");
        System.out.println(redisTemplate.opsForSet().members("diff_set"));
        //求两个集合的交集
        redisTemplate.opsForSet().intersectAndStore("set1", "set2", "inter_set");
        System.out.println(redisTemplate.opsForSet().members("inter_set"));
        //两个集合的并集，并保存
        redisTemplate.opsForSet().unionAndStore("set1", "set2", "union_set");
        System.out.println(redisTemplate.opsForSet().members("union_set"));
    }

    @org.junit.Test
    public void testZset() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        RedisTemplate redisTemplate = applicationContext.getBean(RedisTemplate.class);
        // Spring提供接口TypedTuple操作有序集合
        Set<TypedTuple> set1 = new HashSet<TypedTuple>();
        Set<TypedTuple> set2 = new HashSet<TypedTuple>();
        int j = 9;
        for (int i = 1; i <= 9; i++) {
            j--;
            // 计算分数和值
            Double score1 = Double.valueOf(i);
            String value1 = "x" + i;
            Double score2 = Double.valueOf(j);
            String value2 = j % 2 == 1 ? "y" + j : "x" + j;
            // 使用Spring提供的默认TypedTuple——DefaultTypedTuple
            TypedTuple typedTuple1 = new DefaultTypedTuple(value1, score1);
            set1.add(typedTuple1);
            TypedTuple typedTuple2 = new DefaultTypedTuple(value2, score2);
            set2.add(typedTuple2);
        }
        // 将元素插入有序集合zset1
        redisTemplate.opsForZSet().add("zset1", set1);
        redisTemplate.opsForZSet().add("zset2", set2);
        // 统计总数
        Long size = null;
        size = redisTemplate.opsForZSet().zCard("zset1");
        // 计分数为score，那么下面的方法就是求3<=score<=6的元素
        size = redisTemplate.opsForZSet().count("zset1", 3, 6);
        Set set = null;
        // 从下标一开始截取5个元素，但是不返回分数,每一个元素是String
        set = redisTemplate.opsForZSet().range("zset1", 1, 5);
        printSet(set);
        // 截取集合所有元素，并且对集合按分数排序，并返回分数,每一个元素是TypedTuple
        set = redisTemplate.opsForZSet().rangeWithScores("zset1", 0, -1);
        printTypedTuple(set);
        // 将zset1和zset2两个集合的交集放入集合inter_zset
        size = redisTemplate.opsForZSet().intersectAndStore("zset1", "zset2", "inter_zset");
        // 区间
        Range range = Range.range();
        range.lt("x8");// 小于
        range.gt("x1");// 大于
        set = redisTemplate.opsForZSet().rangeByLex("zset1", range);
        printSet(set);
        range.lte("x8");// 小于等于
        range.gte("x1");// 大于等于
        set = redisTemplate.opsForZSet().rangeByLex("zset1", range);
        printSet(set);
        // 限制返回个数
        Limit limit = Limit.limit();
        // 限制返回个数
        limit.count(4);
        // 限制从第五个开始截取
        limit.offset(5);
        // 求区间内的元素，并限制返回4条
        set = redisTemplate.opsForZSet().rangeByLex("zset1", range, limit);
        printSet(set);
        // 求排行，排名第1返回0，第2返回1
        Long rank = redisTemplate.opsForZSet().rank("zset1", "x4");
        System.err.println("rank = " + rank);
        // 删除元素，返回删除个数
        size = redisTemplate.opsForZSet().remove("zset1", "x5", "x6");
        System.err.println("delete = " + size);
        // 按照排行删除从0开始算起，这里将删除第排名第2和第3的元素
        size = redisTemplate.opsForZSet().removeRange("zset2", 1, 2);
        // 获取所有集合的元素和分数，以-1代表全部元素
        set = redisTemplate.opsForZSet().rangeWithScores("zset2", 0, -1);
        printTypedTuple(set);
        // 删除指定的元素
        size = redisTemplate.opsForZSet().remove("zset2", "y5", "y3");
        System.err.println(size);
        // 给集合中的一个元素的分数加上11
        Double dbl = redisTemplate.opsForZSet().incrementScore("zset1", "x1", 11);
        redisTemplate.opsForZSet().removeRangeByScore("zset1", 1, 2);
        set = redisTemplate.opsForZSet().reverseRangeWithScores("zset2", 1, 10);
        printTypedTuple(set);
    }

    /**
     * 打印TypedTuple集合
     */
    public static void printTypedTuple(Set<
            TypedTuple> set) {
        if (set != null && set.isEmpty()) {
            return;
        }
        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {

            TypedTuple val = (TypedTuple) iterator.next();
            System.err.print("{value = " + val.getValue() + ", score = " + val.getScore() + "}\n");
        }
    }

    /**
     * 打印普通集合
     */
    public static void printSet(Set set) {
        if (set != null && set.isEmpty()) {
            return;
        }
        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            Object val = iterator.next();
            System.out.print(val + "\t");
        }
        System.out.println();
    }

    @org.junit.Test
    public void TestForHyperLogLog() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        RedisTemplate redisTemplate = applicationContext.getBean(RedisTemplate.class);
        redisTemplate.opsForHyperLogLog().add("log", "a", "b", "c");
        redisTemplate.opsForHyperLogLog().add("log2", "a");
        redisTemplate.opsForHyperLogLog().add("log2", "z");
        Long size = redisTemplate.opsForHyperLogLog().size("log");
        System.out.println(size);
        size = redisTemplate.opsForHyperLogLog().size("log2");
        System.out.println(size);
        redisTemplate.opsForHyperLogLog().union("des_key", "log1", "log2");
        size = redisTemplate.opsForHyperLogLog().size("des_key");
        System.out.println(size);
    }

    @org.junit.Test
    public void TestForAffair() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        RedisTemplate redisTemplate = applicationContext.getBean(RedisTemplate.class);
//        SessionCallback sessionCallback = (RedisOperations ops) -> {
//            ops.multi();
//            ops.boundValueOps("key1").set("value1");
////            注意命令进入了队列但是没有被执行，所以此时value的值时null
//            String value = (String) redisTemplate.boundValueOps("key1").get();
//            System.out.println("在队列的时候：value=" + value);
//            //此时使用List来接受之前队列中执行完成后的所有命令的结果
//            List list = ops.exec();
//            value = (String) redisTemplate.opsForValue().get("key1");
//            System.out.println("执行后的value:" + value);
//            return value;
//        };
        //上面为Lambda表达式，下面的是接口实现逻辑
        SessionCallback sessionCallback1 = new SessionCallback() {
            @Override
            public Object execute(RedisOperations ops) throws DataAccessException {
                ops.multi();
                ops.boundValueOps("key1").set("value1");
//            注意命令进入了队列但是没有被执行，所以此时value的值时null
                String value = (String) redisTemplate.boundValueOps("key1").get();
                System.out.println("在队列的时候：value=" + value);
                //此时使用List来接受之前队列中执行完成后的所有命令的结果
                List list = ops.exec();
                value = (String) redisTemplate.opsForValue().get("key1");
                System.out.println("执行后的value:" + value);
                return value;
            }
        };
        String value = (String) redisTemplate.execute(sessionCallback1);
        System.out.println(value);
    }

    @org.junit.Test
    public void TestForPipe() {
        Jedis jedis = getPool().getResource();
        long start = System.currentTimeMillis();
        //开启流水线
        Pipeline pipeline = jedis.pipelined();
        //测试10万读/写两个操作
        for (int i = 0; i < 100000; i++) {
            int j = i + 1;
            pipeline.set("pipeline_key" + j, "pipeline_value" + j);
            pipeline.get("pipeline_key" + j);
        }
//        pipeline.sync();//这里只执行同步，不返回结果
//        pipeline.syncAndReturnAll();//将执行的命令同步且全部返回（List）;
        List list = pipeline.syncAndReturnAll();
        long end = System.currentTimeMillis();
        System.out.println("耗时" + (end - start) + "毫秒");
    }

    public static JedisPool getPool() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        JedisPoolConfig jedisPoolConfig = applicationContext.getBean(JedisPoolConfig.class);
        JedisPool jedisPool = new JedisPool(jedisPoolConfig, "localhost");
        return jedisPool;
    }

    @org.junit.Test
    public void TestPipe() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        RedisTemplate redisTemplate = applicationContext.getBean(RedisTemplate.class);
        SessionCallback callback = new SessionCallback() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                for (int i = 0; i < 100000; i++) {
                    int j = i + 1;
                    operations.boundValueOps("pipeline_key" + j).set("pipeline_value" + j);
                    operations.boundValueOps("pipeline_key" + j).get();
                }
                return null;
            }
        };
        long start = System.currentTimeMillis();
        List resultList = redisTemplate.executePipelined(callback);
        long end = System.currentTimeMillis();
        System.out.println(end - start);
//        System.out.println(resultList);
    }

    @org.junit.Test
    public void TestForListener() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        RedisTemplate redisTemplate = applicationContext.getBean(RedisTemplate.class);
        String channel = "chat";
        redisTemplate.convertAndSend(channel, "i am good !");
    }

//    @org.junit.Test
//    public void testForTimeout() {
//        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
//        RedisTemplate redisTemplate = applicationContext.getBean(RedisTemplate.class);
//        redisTemplate.execute((RedisOperations ops) ->
//        {
//            ops.boundValueOps("key1").set("value1");
//            String keyValue = (String) ops.boundValueOps("key1").get();
//            System.out.println(keyValue);
//            Long expSecond = ops.getExpire("key1");
//            System.out.println(expSecond);
//            boolean b=false;
//            b=ops.expire("key1",120L,TimeUnit.SECONDS);
//            System.out.println(b);
//            b=ops.persist("key1");
//            System.out.println(b);
//            long l=0L;
//            l=ops.getExpire("key1");
//            long time=ops.getExpire("key1");
//            System.out.println(time);
//            System.out.println(l);
//            long now=System.currentTimeMillis();
//            Date date=new Date();
//            date.setTime(now+120000);
//            ops.expireAt("key",date);
//            return null;
//        });
//    }
}