package cn.lifengming.redis.demo.web;

import cn.lifengming.redis.demo.dto.UserLoginDto;
import cn.lifengming.redis.demo.service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author lifengming
 * @version 1.0
 * @date 2018/12/17 16:38
 */
@Controller
@RequestMapping
@ResponseBody
public class UserLoginController {
    @Autowired
    UserLoginService loginService;

    @RequestMapping("/login")
    public UserLoginDto Login(@RequestParam("username") String username , @RequestParam("password") String password){
        return loginService.login( username,password );
    }
}
