package com.czxy.bos.scheduler;

import com.czxy.bos.service.take_delivery.PromotionService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by 10254 on 2018/9/20.
 */
@Component
public class PromotionTask {
    @Resource
    private PromotionService promotionService;
    //每分钟执行一次，从0分钟开始
    @Scheduled(cron="0 0/1 * * * ?")
    public void process(){
        promotionService.updateWithEndDate();
    }
}
