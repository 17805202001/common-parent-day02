package com.czxy.bos.controller.transit;

import com.czxy.bos.domain.transit.SignInfo;
import com.czxy.bos.service.transit.SignInfoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.xml.ws.Response;

/**
 * Created by liangtong on 2018/10/10.
 */
@RestController
@RequestMapping("/signInfo")
public class SignInfoController {

    @Resource
    private SignInfoService signInfoService;

    /**
     * 签收完成
     * @param transitInfoId
     * @param signInfo
     * @return
     */
    @PostMapping
    public ResponseEntity<String> save(String transitInfoId , SignInfo signInfo){
        //保存
        this.signInfoService.save(transitInfoId , signInfo );;
        //提示
        return new ResponseEntity<String>("签收完成", HttpStatus.OK);
    }
}
