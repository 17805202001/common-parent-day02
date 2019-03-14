package com.czxy.bos.controller.system;

import com.czxy.bos.domain.system.User;
import com.czxy.bos.service.system.UserService;
import com.czxy.bos.vo.EasyUIResult;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * Created by 10254 on 2018/10/8.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/login")
    public ResponseEntity<String> login(User user, HttpSession session) {
        //使用工具类进行登录
        SecurityUtils.getSubject().login(new UsernamePasswordToken(user.getUsername(), user.getPassword()));

        //经登录用户的信息存放到session
        User loginUser = (User) SecurityUtils.getSubject().getPrincipal();
        session.setAttribute("user", loginUser);

        //3 提示
        return new ResponseEntity<String>("登录成功", HttpStatus.OK);

    }

    //分页
    @GetMapping
    public ResponseEntity<EasyUIResult<User>> findAll(Integer page, Integer rows) {
        PageInfo<User> pageInfo = userService.findAll(page, rows);
        EasyUIResult result = new EasyUIResult(pageInfo.getTotal(), pageInfo.getList());
        return new ResponseEntity<EasyUIResult<User>>(result,HttpStatus.OK);
    }

    //添加用户
    @PostMapping
    public ResponseEntity<Void> saveUser(User user){
        String[] roleIds=user.getRoleIds();

        userService.saveUser(user,roleIds);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
