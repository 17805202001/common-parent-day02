package com.czxy.bos.controller.base;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by 10254 on 2018/8/31.
 */
@Controller
public class IndexController {
    //访问一级目录
    @RequestMapping("/{path}")
    public String show(@PathVariable("path")String p){
        return p;
    }

    //访问一级目录
    @RequestMapping("/pages/{dir}/{path}")
    public String show2(@PathVariable("dir")String d,@PathVariable("path")String p){
        return "/pages/"+d+"/"+p;
    }


}
