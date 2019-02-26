package cn.lifengming.study.framework.springmvc.lifengming.redis.demo.dao;

import cn.lifengming.study.framework.springmvc.lifengming.redis.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author lifengming
 * @version 1.0
 * @date 2018/12/17 16:32
 */
public interface UserDao extends JpaRepository<User, String> {

    User findByUsername(String username);

}
