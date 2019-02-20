package cn.lifengming.redis.demo.web;

import cn.lifengming.redis.demo.config.JedisConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

import java.util.Set;

/**
 * @author lifengming
 * @version 1.0
 * @date 2018/12/17 16:02
 */
@RequestMapping
@Component
public class JedisTestController {
    @Autowired
    JedisConfig jedisConfig;

    @RequestMapping("/test")
    @ResponseBody
    public Set<String> getJedisConfig() {
        Jedis jedis=jedisConfig.redisPoolFactory().getResource();
        Long dsdsd = jedis.zadd( "runoobkey ", 2.00, "redis" );
        Long dsdsd1 = jedis.zadd( "runoobkey ", 4.00, "mongodb" );
        Long dsdsd2 = jedis.zadd( "runoobkey ", 3.00, "mysql" );
        Long dsdsd3 = jedis.zadd( "runoobkey ", 3.00, "mysql" );

        Set<String> runoobkey= jedis.zrange( "runoobkey ", 0, 10 );
        jedis.close();
        return runoobkey;
    }
}
