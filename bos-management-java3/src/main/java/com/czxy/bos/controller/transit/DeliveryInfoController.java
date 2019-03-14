package com.czxy.bos.controller.transit;

import com.czxy.bos.domain.transit.DeliveryInfo;
import com.czxy.bos.service.transit.DeliveryInfoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by 10254 on 2018/10/12.
 */
@RestController
@RequestMapping("/deliveryInfo")
public class DeliveryInfoController {
    @Resource
    private DeliveryInfoService deliveryInfoService;

    /**
     * 保存“配送信息”
     * @param transitInfoId
     * @param deliveryInfo
     * @return
     */
    @PostMapping
    public ResponseEntity<String> save(String transitInfoId, DeliveryInfo deliveryInfo){
        //保存
        this.deliveryInfoService.save( transitInfoId , deliveryInfo );
        //提示
        return new ResponseEntity<String>("配送信息设置成功", HttpStatus.OK);
    }
}
