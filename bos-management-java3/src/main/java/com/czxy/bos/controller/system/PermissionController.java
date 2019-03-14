package com.czxy.bos.controller.system;

import com.czxy.bos.domain.system.Permission;
import com.czxy.bos.service.system.PermissionService;
import com.czxy.bos.vo.EasyUIResult;
import com.github.pagehelper.PageInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by 10254 on 2018/10/9.
 */
@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Resource
    private PermissionService permissionService;

    //分页查询
    @GetMapping
    public ResponseEntity<EasyUIResult<Permission>> findAll(Integer page, Integer rows){
        PageInfo<Permission> pageInfo=this.permissionService.findAll(page,rows);
        EasyUIResult result=new EasyUIResult(pageInfo.getTotal(),pageInfo.getList());
        return new ResponseEntity<EasyUIResult<Permission>>(result, HttpStatus.OK);
    }

    //添加
    @PostMapping
    public ResponseEntity<String> save(Permission permission){
        permissionService.save(permission);
        return new ResponseEntity<String>("添加成功",HttpStatus.OK);
    }

    //查询所有
    @GetMapping("/list")
    public ResponseEntity<List<Permission>> findAllPermissionList(){
        List<Permission> list=permissionService.findAllPermissionList();
        return new ResponseEntity<List<Permission>>(list,HttpStatus.OK);
    }
}
