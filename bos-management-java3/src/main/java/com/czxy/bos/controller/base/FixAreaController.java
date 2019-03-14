package com.czxy.bos.controller.base;

import com.czxy.bos.domain.base.Area;
import com.czxy.bos.domain.base.FixedArea;
import com.czxy.bos.service.base.FixedAreaService;
import com.czxy.bos.vo.EasyUIResult;
import com.github.pagehelper.PageInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
/**
 * Created by 10254 on 2018/9/7.
 */
@RestController
@RequestMapping("/fixedArea")
public class FixAreaController {

    @Resource
    private FixedAreaService fixedAreaService;

    @Resource
    private RestTemplate restTemplate;
    //添加
    @PostMapping
    public ResponseEntity<String> save(FixedArea fixedArea){
        //添加
        int r=fixedAreaService.save(fixedArea);
        //提示
        if(r==1){
            return  new ResponseEntity<String>("添加成功", HttpStatus.CREATED);
        }
        return new ResponseEntity<String>("添加失败",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //分页查询
    @GetMapping
    public ResponseEntity<EasyUIResult<Area>> findAll(FixedArea fixedArea , Integer page , Integer rows){
        //查询
        PageInfo<FixedArea> pageInfo = fixedAreaService.findAll(fixedArea, page ,rows );
        //封装datagrid
        EasyUIResult result = new EasyUIResult( pageInfo.getTotal() , pageInfo.getList() );
        //封装状态码
        return new ResponseEntity<EasyUIResult<Area>>( result , HttpStatus.OK );
    }

    //查询未关联用户
    @GetMapping("/findNoAssociationCustomers")
    public ResponseEntity<String> findNoAssociationCustomers(){
        //请求crm项目，获得所有未关联定区的客户
        ResponseEntity<String> entity = restTemplate.getForEntity("http://localhost:8090/crmCustomer/findNoAssociationCustomers",String.class);
        //返回
        return entity;
    }

    //查询已关联用户
    @GetMapping("/findHasAssociationFixedAreaCustomers")
    public ResponseEntity<String> findHasAssociationFixedAreaCustomers(String fixedAreaId){
        //请求crm项目，获得所有关联定区的客户
        ResponseEntity<String> entity = restTemplate.getForEntity("http://localhost:8090/crmCustomer/findHasAssociationFixedAreaCustomers?fixedAreaId="+fixedAreaId,String.class);
        //返回
        return entity;
    }
    //定区关联客户
    @PostMapping("/associationCustomersToFixedArea")
    public ResponseEntity<String> associationCustomersToFixedArea(String fixedAreaId, String customerIds){
        //向crm系统发送post请求
        return restTemplate.postForEntity("http://localhost:8090/crmCustomer/associationCustomersToFixedArea?fixedAreaId="+fixedAreaId+"&customerIds=" + customerIds ,null , String.class);
    }


    //关联快递员
    @PostMapping("/associationCourierToFixedArea")
    public ResponseEntity<String> associationCourierToFixedArea(String fixedAreaId , Integer courierId, Integer takeTimeId){
        //1 关联操作
        fixedAreaService.associationCourierToFixedArea(fixedAreaId, courierId , takeTimeId);
        //2 提示
        return new ResponseEntity<String>("操作成功",HttpStatus.OK);
    }



}
