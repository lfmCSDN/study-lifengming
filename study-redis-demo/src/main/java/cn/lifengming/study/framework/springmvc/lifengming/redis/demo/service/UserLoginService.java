package cn.lifengming.study.framework.springmvc.lifengming.redis.demo.service;

import cn.lifengming.study.framework.springmvc.lifengming.redis.demo.config.JedisConfig;
import cn.lifengming.study.framework.springmvc.lifengming.redis.demo.dao.UserDao;
import cn.lifengming.study.framework.springmvc.lifengming.redis.demo.dto.UserLoginDto;
import cn.lifengming.study.framework.springmvc.lifengming.redis.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

/**
 * @author lifengming
 * @version 1.0
 * @date 2018/12/17 16:35
 */
@Service
public class UserLoginService {

    @Autowired
    private UserDao userRepository;

    @Autowired
    JedisConfig jedisConfig;

    public UserLoginDto login(String username, String password) {

        Jedis jedis = null;
        try {
            jedis = jedisConfig.redisPoolFactory().getResource();
//            jedis.auth( "lfm" );
            jedis.set( "username", username );
            jedis.set( "password", password );
        } finally {
            jedis.close();
        }
        UserLoginDto userLoginDTO = new UserLoginDto();
        User user = userRepository.findByUsername( username );
        if (null != user ) {
            userLoginDTO.setUser( user,"数据库成功！" );
        } else {
            User user1 = new User();
            user1.setUsername( jedis.get( "username" ) );
            user1.setPassword( jedis.get( "password" ) );
            userLoginDTO.setUser( user1,"redis成功! " );
        }
        return userLoginDTO;
    }
}
