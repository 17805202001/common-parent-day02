package com.czxy.bos.controller.system;

import com.czxy.bos.domain.system.Menu;
import com.czxy.bos.domain.system.User;
import com.czxy.bos.service.system.MenuService;
import com.czxy.bos.vo.EasyUIResult;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by 10254 on 2018/10/8.
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Resource
    private MenuService menuService;

    @GetMapping
    public ResponseEntity<EasyUIResult<Menu>> findAll(Integer page,Integer rows){
        //分页
        PageInfo<Menu> pageInfo=menuService.findAll(page,rows);
        //封装
        EasyUIResult result=new EasyUIResult(pageInfo.getTotal(),pageInfo.getList());
        //封装状态码
        return new ResponseEntity<EasyUIResult<Menu>>(result, HttpStatus.OK);

    }

    //查询所有
    @GetMapping("/list")
    public ResponseEntity<List<Menu>> findAll(){
        List<Menu> list=menuService.findAll();
        return new ResponseEntity<List<Menu>>(list,HttpStatus.OK);
    }

    /**
     * 添加菜单
     * @param menu
     * @return
     */
    @PostMapping
    public ResponseEntity<String> save(Menu menu){
        //保存
        this.menuService.save(menu);
        //提示
        return new ResponseEntity<String>("保存成功",HttpStatus.OK);
    }

    //查询所有
    @GetMapping("/showMenu")
    public ResponseEntity<List<Menu>> showMenu(){
        //获得当前登录用户
        User user= (User) SecurityUtils.getSubject().getPrincipal();

        List<Menu> list=menuService.findByUser(user);

        return new ResponseEntity<List<Menu>>(list,HttpStatus.OK);
    }
}
