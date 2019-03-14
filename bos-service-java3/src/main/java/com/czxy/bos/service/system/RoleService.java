package com.czxy.bos.service.system;

import com.czxy.bos.dao.system.RoleMapper;
import com.czxy.bos.dao.system.RoleMenuMapper;
import com.czxy.bos.dao.system.RolePermissionMapper;
import com.czxy.bos.domain.system.Role;
import com.czxy.bos.domain.system.RoleMenu;
import com.czxy.bos.domain.system.RolePermission;
import com.czxy.bos.domain.system.User;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by 10254 on 2018/10/8.
 */
@Service
@Transactional
public class RoleService {

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Resource
    private RolePermissionMapper rolePermissionMapper;

    /**
     * 查找指定用户授权的所有角色（如果是admin用户，将查询所有角色）
     *
     * @param user
     * @return
     */

    public List<Role> findByUser(User user) {
        if (user.getUsername().equals("admin")) {
            return roleMapper.selectAll();

        }
        return roleMapper.findByUser(user.getId());
    }

    //分页查询
    public PageInfo<Role> findAll(Integer page, Integer rows) {
        PageHelper.startPage(page, rows);
        List<Role> list = roleMapper.selectAll();
        return new PageInfo<>(list);
    }

    //添加
    public void save(Role role,String menuIds,String[] permissionIds) {
        // 保存角色 ，在JavaBean中设置 @GeneratedValue(generator = "JDBC")  ，保存之后role就可以直接获得id值了
        roleMapper.insert(role);

        // 保存菜单关联，将使用逗号拆分字符串，并遍历
        String[] menuArr=menuIds.split(",");
        for(String menuId:menuArr){
            RoleMenu roleMenu=new RoleMenu();
            roleMenu.setMenuId(Integer.parseInt(menuId));
            roleMenu.setRoleId(role.getId());
            roleMenuMapper.insert(roleMenu);
        }
        // 保存权限管理，遍历处理
        for(String permId:permissionIds){
            RolePermission rolePermission=new RolePermission();
            rolePermission.setPermissionId(Integer.parseInt(permId));
            rolePermission.setRoleId(role.getId());
            rolePermissionMapper.insert(rolePermission);
        }

    }

    //查询用户
    public List<Role> findAllRoleList() {
        return roleMapper.selectAll();
    }


}
