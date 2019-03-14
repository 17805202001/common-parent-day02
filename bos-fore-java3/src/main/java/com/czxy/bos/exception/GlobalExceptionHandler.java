package com.czxy.bos.exception;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Created by 10254 on 2018/9/13.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

 /*   @ExceptionHandler(Exception.class)
    public ResponseEntity<String> defaultErrorHandler(HttpServletRequest request , Exception e){
        //自定义异常
        if(e instanceof BosException){
            return new ResponseEntity<String>(e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
        }
        //其他异常
        return new ResponseEntity<String>("服务器异常，请联系管理员。", HttpStatus.INTERNAL_SERVER_ERROR);
    }
*/

}
