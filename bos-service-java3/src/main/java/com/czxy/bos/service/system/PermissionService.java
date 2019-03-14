package com.czxy.bos.service.system;

import com.czxy.bos.dao.system.PermissionMapper;
import com.czxy.bos.domain.system.Permission;
import com.czxy.bos.domain.system.User;
import com.czxy.bos.exception.BosException;
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
public class PermissionService {

    @Resource
    private PermissionMapper permissionMapper;


    /**
     * 查询权限，如果是admin查询所有，如果不是查询指定userId的
     * @param user
     * @return
     */
    public List<Permission> findByUser(User user){
        if(user.getUsername().equals("admin")){
            return permissionMapper.selectAll();
        }
        return permissionMapper.findByUser(user.getId());
    }

    //分页查询
    public PageInfo<Permission> findAll(Integer page,Integer rows){
        PageHelper.startPage(page,rows);
        List<Permission> list=this.permissionMapper.selectAll();
        return new PageInfo<>(list);
    }

    //添加
    public void save(Permission permission){
        //校验：名称唯一
        Example nameExample=new Example(Permission.class);
        nameExample.createCriteria().andEqualTo("name",permission.getName());
        Permission namePermission=permissionMapper.selectOneByExample(nameExample);
        if(namePermission!=null){
            throw new BosException("权限名称已存在");
        }

        //校验:关键字唯一
        Example keywordExample=new Example(Permission.class);
        keywordExample.createCriteria().andEqualTo("keyword",permission.getKeyword());
        Permission keywordPermission=permissionMapper.selectOneByExample(keywordExample);
        if(keywordPermission!=null){
            throw new BosException("关键字已存在");
        }

        permissionMapper.insert(permission);

    }

    //查询所有
    public List<Permission> findAllPermissionList(){
        return permissionMapper.selectAll();
    }
}
