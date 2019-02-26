package cn.lifengming.study.framework.springmvc.lifengming.redis.demo.exception;


/**
 * @author lifengming
 */
public class UserLoginException extends RuntimeException{

    public UserLoginException(String message) {
        super(message);
    }

}
