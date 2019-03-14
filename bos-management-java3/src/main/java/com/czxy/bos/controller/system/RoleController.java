package com.czxy.bos.controller.system;

import com.czxy.bos.domain.system.Role;
import com.czxy.bos.service.system.RoleService;
import com.czxy.bos.vo.EasyUIResult;
import com.github.pagehelper.PageInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by 10254 on 2018/10/8.
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Resource
    public RoleService roleService;


    @GetMapping
    public ResponseEntity<EasyUIResult<Role>> findAll(Integer page,Integer rows){
        PageInfo<Role> pageInfo=roleService.findAll(page,rows);
        EasyUIResult result=new EasyUIResult(pageInfo.getTotal(),pageInfo.getList());
        return new ResponseEntity<EasyUIResult<Role>>(result, HttpStatus.OK);
    }

    /**
     *
     * @param role 角色基本数据
     * @param menuIds 关联的所有菜单，使用逗号分隔
     * @param permissionIds 关联的所有权限
     * @return
     */
    @PostMapping
    public ResponseEntity<String> saveRole(Role role, String menuIds, @RequestParam("permissionIds[]")String[] permissionIds){
        //保存
        roleService.save(role,menuIds,permissionIds);
        return new ResponseEntity<String>("成功保存",HttpStatus.OK);
    }


    //查询用户
    @GetMapping("/list")
    public ResponseEntity<List<Role>> findRoleList(){
        List<Role> list = roleService.findAllRoleList();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

}
