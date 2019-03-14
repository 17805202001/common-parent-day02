package com.czxy.bos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


/**
 * 全局异常处理类
 * Created by 10254 on 2018/9/4.
 */
@ControllerAdvice       //对当前项目的所有的controller进行增强
public class GlobalException {


//    @ExceptionHandler(Exception.class)      //只要是异常，就执行该方法
//    public ResponseEntity<String> defaultErrorHandler(Exception e){
//        //自定义异常
//        if(e instanceof BosException){
//            return new ResponseEntity<String>(e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//        //其他异常
//        return new ResponseEntity<String>("服务器异常，请联系管理员。", HttpStatus.INTERNAL_SERVER_ERROR);
//    }

}
