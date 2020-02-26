package com.baizhi.usermanager.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisUtils {

    //JedisPoolConfig  配置连接池的相关参数
    //JedisPool        连接池对象
    //jedis            操作redis数据库的连接对象

    private static JedisPool jedisPool;  //jedis连接池对象，只需要创建一次

    static {
        //参数1：连接池配置参数  参数2：连接池redis数据库服务器ip 参数3：redis数据库端口号
        JedisPoolConfig  config = new JedisPoolConfig();
        config.setMaxTotal(20);  //最大连接数,默认是16
        config.setMaxIdle(8);     //最大闲置数,默认是8
        config.setMaxWaitMillis(5000); //最大等待时间
        jedisPool = new JedisPool(config,"192.168.145.9",6379);
    }


    public static Jedis getJedis(){
        return jedisPool.getResource();  //从连接池中获取连接对象
    }

}
