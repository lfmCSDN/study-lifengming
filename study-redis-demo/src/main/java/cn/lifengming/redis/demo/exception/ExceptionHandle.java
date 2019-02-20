package cn.lifengming.redis.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author lifengming
 */
@ControllerAdvice
@ResponseBody
public class ExceptionHandle {
    @ExceptionHandler(UserLoginException.class)
    public ResponseEntity<String> handleException(Exception e) {

        return new ResponseEntity(e.getMessage(), HttpStatus.OK);
    }
}
