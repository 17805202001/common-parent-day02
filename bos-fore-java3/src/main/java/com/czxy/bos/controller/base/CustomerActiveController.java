package com.czxy.bos.controller.base;

import com.czxy.crm.domain.Customer;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * Created by 10254 on 2018/9/18.
 */
@RestController
@RequestMapping("/customer")
public class CustomerActiveController {

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private StringRedisTemplate redisTemplate;

    /**
     * 邮件激活后，到登录页面显示激活提示信息
     * @param telephone
     * @param activecode
     * @return
     */
    @GetMapping("/activeMail")
    public String activeMail(String telephone , String activecode, Model model){
        System.out.println("激活");
        //如果判断用户提供激活码正确的？
        String redisActiveCode = redisTemplate.opsForValue().get(telephone);
        //判断激活码是否相同
        if(redisActiveCode==null||!redisActiveCode.equals(activecode)){
            model.addAttribute("msg","激活码无效");
            return "login";
        }

        //删除redis存放的激活码，已经使用
        redisTemplate.delete(telephone);

        //使用telephone通过crm系统查询用户，如果状态不是0，提示该账户不能激活
        String url="http://localhost:8090/crmCustomer/findByTelephone?telephone="+telephone;
        ResponseEntity<Customer> entity=restTemplate.getForEntity(url,Customer.class);
        Customer customer=entity.getBody();
        if(customer.getType()!=0){
            model.addAttribute("msg","该账户不能激活");
            return  "login";
        }

        //通过crm系统，激活账号（修改状态未1），提示用户账号激活成功
        url="http://localhost:8090/crmCustomer/updateType?telephone=" + telephone;
        ResponseEntity<String> entry2=restTemplate.getForEntity(url,String.class);

        model.addAttribute("msg",entry2.getBody());
        return "login";
    }

}
