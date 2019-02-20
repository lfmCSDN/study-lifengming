package cn.lifengming.redis.demo.dto;

import cn.lifengming.redis.demo.entity.User;

/**
 * @author lifengming
 * @version 1.0
 * @date 2018/12/17 16:34
 */
public class UserLoginDto {
    private User user;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user,String status) {
        this.user = user;
        this.status=status;
    }
}
