package com.czxy.crm.controller;

import com.czxy.crm.domain.Customer;
import com.czxy.crm.service.CrmCustomerService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by 10254 on 2018/9/10.
 */
@RestController
@RequestMapping("/crmCustomer")
public class CrmCustomerController {

    @Resource
    private CrmCustomerService crmCustomerService;

    //查询未关联定区的客户(客户的外键为null)
    @GetMapping("/findNoAssociationCustomers")
    public ResponseEntity<List<Customer>> findNoAssociationCustomers(){
        //查询
        List<Customer> list=crmCustomerService.findNoAssociationCustomers();
        //封装对象
        return new ResponseEntity<List<Customer>>(list, HttpStatus.OK);
    }

    //查询指定关联的客户
    @GetMapping("/findHasAssociationFixedAreaCustomers")
    public ResponseEntity<List<Customer>> findHasAssociationFixedAreaCustomers(@RequestParam("fixedAreaId") String fixedAreaId){
        //查询
        List<Customer> list=crmCustomerService.findHasAssociationFixedAreaCustomers(fixedAreaId);
        //封装对象
        return new ResponseEntity<List<Customer>>(list, HttpStatus.OK);
    }

    //进行定区客户关联操作
    @PostMapping("/associationCustomersToFixedArea")
    public ResponseEntity<String> associationCustomersToFixedArea(String fixedAreaId,String customerIds){
        //操作
        crmCustomerService.associationCustomersToFixedArea(fixedAreaId,customerIds);
        return new ResponseEntity<String>("操作成功", HttpStatus.OK);
    }

    /**
     * http://localhost:8090/customer/telephone?telephone=136...
     * @param telephone
     * @return
     */
    @GetMapping("findByTelephone")
    public ResponseEntity<Customer> findByTelephone(String telephone){
        //1查询
        Customer customer = crmCustomerService.findByTelephone(telephone);
        //2 封装
        return new ResponseEntity<Customer>(customer ,HttpStatus.OK);

    }

    /**
     *
     * @param customer  远程前端系统访问，发送的为json数据，需通过@RequestBody确定请求数据为json
     * @return
     */
    @PostMapping("/saveCustomer")
    public ResponseEntity<String> saveCustomer(@RequestBody  Customer customer){

        try {
            //通过service进行操作
            //设置状态为1
            customer.setType(1);
            crmCustomerService.saveCustomer(customer);
            return new ResponseEntity<String>("注册成功",HttpStatus.CREATED); //201
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(),HttpStatus.ALREADY_REPORTED); //208
        }
    }

    /**
     * 激活
     * @param telephone
     * @return
     */
    @GetMapping("/updateType")
    public ResponseEntity<String> updateType(String telephone){
        try {
            //更新
            crmCustomerService.updateType(telephone);
            return new ResponseEntity<String>("账号激活成功",HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<String>("激活失败",HttpStatus.ALREADY_REPORTED);
        }
    }

    //根据手机号和密码查询用户
    @GetMapping("/findByPage")
    public ResponseEntity<Customer> findByPage(String telephone,String password){
        Customer customer = crmCustomerService.findByPage(telephone, password);
        return new ResponseEntity<Customer>(customer,HttpStatus.OK);
    }


    /** http://localhost:8090/crmCustomer/findFixedAreaIdByAddressAndId?address=北京天安门&customerId=1
     * 通过地址和id查询客户
     * @param address
     * @param customerId
     * @return
     */
    @GetMapping("/findFixedAreaIdByAddressAndId")
    public ResponseEntity<String> findFixedAreaIdByAddressAndId(String address , Integer customerId){
        //1 查询
        String fixedAreaId = crmCustomerService.findFixedAreaIdByAddressAndId(address ,customerId);
        //2  封装
        return new ResponseEntity<String>(fixedAreaId , HttpStatus.OK);
    }



}
