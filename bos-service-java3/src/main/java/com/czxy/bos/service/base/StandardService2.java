package com.czxy.bos.service.base;

import com.czxy.bos.dao.base.StandardMapper2;
import com.czxy.bos.domain.base.Standard;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by 10254 on 2018/9/3.
 */
@Service
@Transactional
public class StandardService2 {

    @Resource
    private StandardMapper2 standardMapper2;

    //查询所有
    public PageInfo<Standard> findAll(Integer page,Integer rows){
        //分页
        PageHelper.startPage(page,rows);
        //查询所有
        List<Standard> list=standardMapper2.selectAll();
        //封装对象
        return new PageInfo<>(list);

    }
    //添加
    public Integer save(Standard standard){
        return standardMapper2.insert(standard);

    }
    //修改
    public Integer update(Standard standard){
        return standardMapper2.updateByPrimaryKey(standard);
    }

    //删除
    public void delete(String[] ids){
        for(String id:ids){
            Integer i=Integer.parseInt(id);
            standardMapper2.deleteByPrimaryKey(i);
        }
    }
}
