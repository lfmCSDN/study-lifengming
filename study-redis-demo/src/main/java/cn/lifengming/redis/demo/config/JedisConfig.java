package cn.lifengming.redis.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author lifengming
 * @version 1.0
 * @date 2018/12/17 14:19
 */

@Configuration
public class JedisConfig extends CachingConfigurerSupport {
    private Logger logger = LoggerFactory.getLogger(JedisConfig.class);

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.timeout}")
    private int timeout;

    @Value("${spring.redis.jedis.pool.max-active}")
    private int maxActive;

    @Value("${spring.redis.jedis.pool.max-idle}")
    private int maxIdle;

    @Value("${spring.redis.jedis.pool.min-idle}")
    private int minIdle;

    @Value("${spring.redis.jedis.pool.max-wait}")
    private long maxWaitMillis;

    @Bean
    public JedisPool redisPoolFactory(){
        // 获得连接池的配置对象
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        // 设置最大空闲连接数
        jedisPoolConfig.setMaxIdle(maxIdle);
        //连接池最大阻塞等待时间（使用负值表示没有限制）
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
        // 设置最大连接数
        jedisPoolConfig.setMaxTotal(maxActive);
        //设置连接池中的最小空闲连接
        jedisPoolConfig.setMinIdle(minIdle);
        // 获得连接池
        JedisPool jedisPool = new JedisPool(jedisPoolConfig,host,port,timeout,null);

        logger.info("JedisPool注入成功！");
        logger.info("redis地址：" + host + ":" + port);
        return  jedisPool ;
    }

}