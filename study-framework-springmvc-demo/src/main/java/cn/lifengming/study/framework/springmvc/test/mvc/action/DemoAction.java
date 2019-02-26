package cn.lifengming.study.framework.springmvc.test.mvc.action;

import cn.lifengming.study.framework.springmvc.annnotation.Autowired;
import cn.lifengming.study.framework.springmvc.annnotation.Controller;
import cn.lifengming.study.framework.springmvc.annnotation.RequestMapping;
import cn.lifengming.study.framework.springmvc.annnotation.RequestParam;
import cn.lifengming.study.framework.springmvc.test.service.DemoService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * create by Lfm
 * on 2018-12-18 21:00
 * @author lifengming
 */
@Controller
@RequestMapping("/a")
public class DemoAction {

    @Autowired
    private DemoService demoService;

    @RequestMapping("/b")
    public void query(HttpServletRequest req, HttpServletResponse resp,@RequestParam("name") String name){
        String result=demoService.get(name);
        try{
            resp.getWriter().write(result);
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    @RequestMapping("/add")
    public void add(HttpServletRequest req, HttpServletResponse resp,@RequestParam("a") Integer a,@RequestParam("b") Integer b){
        try{
            resp.getWriter().write(a+"+"+b+"="+(a+b));
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
