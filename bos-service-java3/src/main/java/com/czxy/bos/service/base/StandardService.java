package com.czxy.bos.service.base;
import com.czxy.bos.dao.base.StandardMapper;
import com.czxy.bos.domain.base.Standard;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by liangtong on 2018/9/3.
 */
@Service
@Transactional
public class StandardService {

    //注入mapper
    @Resource
    private StandardMapper standardMapper;

    /**
     *
     * @param page 第几页
     * @param rows 每页显示个数
     * @return
     */
    //分页查询
    public PageInfo<Standard> queryStandardByPage(int page , int rows){
        //1 分页
        PageHelper.startPage(page ,rows);
        //2 查询所有--内含分页
        List<Standard> list = standardMapper.selectAll();
        //3 封装数据
        return new PageInfo<>( list );
    }
    //添加
    public Integer saveStandard( Standard standard ){
        return standardMapper.insert(standard);
    }

    //修改
    public Integer update( Standard standard ){
        return standardMapper.updateByPrimaryKey(standard);
    }

    //删除
    public void delete(String[] ids){
        //循环遍历
        for(String id:ids){
            Integer i=Integer.parseInt(id);
            standardMapper.deleteByPrimaryKey(i);

        }
    }

    //查询所有收派信息
    public List<Standard> findAll(){
       return this.standardMapper.selectAll();
    }


}