package com.czxy.bos.controller;


import org.springframework.web.bind.annotation.*;

/**
 * Created by 10254 on 2018/9/3.
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping
    public String demo1(){
        System.out.print("get");
        return null;
    }

    @PostMapping
    public String demo2(){
        System.out.print("post");
        return null;
    }

    @PutMapping
    public String demo3(){
        System.out.print("put");
        return null;
    }

    @DeleteMapping
    public String demo4(){
        System.out.print("delete");
        return null;
    }

    /** 通过id查询时，建议在路径后面追加子路径，最后的内容就是id
     * http://localhost:8086/mytest/12345
     * @return
     */

    @GetMapping(value = "/{id}")
    public String demo5(@PathVariable("id") String  id){
        System.out.print("id:"+id);
        return null;
    }

}
