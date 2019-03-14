package com.czxy.bos.service.system;

import com.czxy.bos.dao.system.MenuMapper;
import com.czxy.bos.domain.system.Menu;
import com.czxy.bos.domain.system.User;
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
public class MenuService {

    @Resource
    private MenuMapper menuMapper;

    //分页
    public PageInfo<Menu> findAll(Integer page, Integer rows) {
        //设置分页
        PageHelper.startPage(page, rows);
        //查询所有
        List<Menu> list = menuMapper.selectAll();
        //封装
        return new PageInfo<>(list);

    }

    /**
     * 查询所有
     *
     * @return
     */
    public List<Menu> findAll() {
        return menuMapper.selectAll();
    }

    /**
     * 保存菜单
     *
     * @param menu
     */
    public void save(Menu menu) {
        //如果没有设置优先级priority，将使用当前分类中最大值+1
        if (menu.getPriority() == null) {
            //当前菜单父id 可能为null，可能有值
            Example example = new Example(Menu.class);
            Example.Criteria criteria = example.createCriteria();
            if (menu.getPid() == null) {
                criteria.andIsNull("pid");
            } else {
                criteria.andEqualTo("pid", menu.getPid());
            }

            List<Menu> list = menuMapper.selectByExample(example);
            menu.setPriority(list.size() + 1);

        }
        menuMapper.insert(menu);
    }

    //擦好像所有
    public List<Menu> findByUser(User user) {
        if ("admin".equals(user.getUsername())) {
            return menuMapper.selectAll();
        }
        return menuMapper.findByUser(user.getId());
    }
}
