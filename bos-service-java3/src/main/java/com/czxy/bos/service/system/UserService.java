package com.czxy.bos.service.system;

import com.czxy.bos.dao.system.UserMapper;
import com.czxy.bos.dao.system.UserRoleMapper;
import com.czxy.bos.domain.system.User;
import com.czxy.bos.domain.system.UserRole;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by 10254 on 2018/10/8.
 */
@Service
@Transactional
public class UserService {


    @Resource
    private UserMapper userMapper;

    @Resource
    private UserRoleMapper userRoleMapper;
    /**
     * 通过用户名查询用户
     * @param username
     * @return
     */
    public User findUserByUsername(String username){
        //1 条件
        Example example = new Example(User.class);
        example.createCriteria()
                .andEqualTo("username" , username);


        //2 查询
        return userMapper.selectOneByExample( example );

    }

    //分页
    public PageInfo<User> findAll(Integer page,Integer rows){
        PageHelper.startPage(page,rows);
        List<User> list=userMapper.selectAll();
        return new PageInfo<>(list);
    }


    //添加用户
    public void saveUser(User user,String[] roleIds){
        //保存用户
        userMapper.insert(user);
        for(String id:roleIds){
            UserRole userRole=new UserRole();
            userRole.setRoleId(Integer.parseInt(id));
            userRole.setUserId(user.getId());
            userRoleMapper.insert(userRole);
        }
    }
}
