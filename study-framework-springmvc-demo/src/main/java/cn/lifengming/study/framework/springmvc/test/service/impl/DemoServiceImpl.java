package cn.lifengming.study.framework.springmvc.test.service.impl;

import cn.lifengming.study.framework.springmvc.annnotation.Service;
import cn.lifengming.study.framework.springmvc.test.service.DemoService;

/**
 * create by Lfm
 * on 2018-12-18 21:00
 * @author lifengming
 */
@Service
public class DemoServiceImpl implements DemoService {
    @Override
    public String get(String name){
        return "my name is"+name;
    }
}
