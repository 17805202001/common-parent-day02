package com.czxy.bos.controller.base;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * Created by 10254 on 2018/9/20.
 */
@RestController
@RequestMapping("/forePromotion")
public class ForePromotionController {

    @Resource
    private RestTemplate restTemplate;

    @GetMapping
    public ResponseEntity<String> queryPromotionByPage(){
        String url="http://localhost:8088/promotion/findPromotion";
        return restTemplate.getForEntity(url,String.class);
    }
}
