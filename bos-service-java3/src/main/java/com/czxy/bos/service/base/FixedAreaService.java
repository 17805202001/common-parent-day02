package com.czxy.bos.service.base;

import com.czxy.bos.dao.base.CourierMapper;
import com.czxy.bos.dao.base.FixedAreaCourierMapper;
import com.czxy.bos.dao.base.FixedAreaMapper;
import com.czxy.bos.domain.base.Courier;
import com.czxy.bos.domain.base.FixedArea;
import com.czxy.bos.domain.base.FixedAreaCourier;
import com.czxy.bos.exception.BosException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

/**
 * Created by 10254 on 2018/9/7.
 */
@Service
@Transactional
public class FixedAreaService {

    @Resource
    private FixedAreaMapper fixedAreaMapper;

    @Resource
    private CourierMapper courierMapper;

    @Resource
    private FixedAreaCourierMapper fixedAreaCourierMapper;
    public Integer save(FixedArea fixedArea){
        //校验
        //名称必须唯一
        Example nameExample=new Example(FixedArea.class);
        Example.Criteria namecriteria=nameExample.createCriteria();
        namecriteria.andEqualTo("fixedAreaName",fixedArea.getFixedAreaName());

        FixedArea nameFixedArea=fixedAreaMapper.selectOneByExample(nameExample);
        if(nameFixedArea!=null){
            throw  new BosException("定区名称已存在");
        }
        //负责人必须唯一
        Example leaderExample=new Example(FixedArea.class);
        Example.Criteria leadercriteria=leaderExample.createCriteria();
        leadercriteria.andEqualTo("fixedAreaLeader",fixedArea.getFixedAreaLeader());

        FixedArea leaderFixArea=fixedAreaMapper.selectOneByExample(leaderExample);
        if(leaderFixArea!=null){
            throw  new BosException("定区负责人名称已存在");
        }

        //数据的完整
        fixedArea.setId(UUID.randomUUID().toString());
        //添加
        return fixedAreaMapper.insert(fixedArea);

    }

    //查询
    public PageInfo<FixedArea> findAll(FixedArea fixedArea,Integer page,Integer rows){
        //拼凑条件
        Example example=new Example(FixedArea.class);
        Example.Criteria criteria=example.createCriteria();

        if(fixedArea!=null){

            //地区编号
            if(StringUtils.isNotBlank(fixedArea.getId())){
                criteria.andLike("id" , "%"+fixedArea.getId()+"%");

            }
            //所属单位
            if(StringUtils.isNotBlank(fixedArea.getCompany())){
                criteria.andLike("company","%"+fixedArea.getCompany()+"%");
            }
        }
        //分页
        PageHelper.startPage(page,rows);

        List<FixedArea> list=fixedAreaMapper.selectByExample(example);

        //封装对象
        return new PageInfo<>(list);

    }


    /**
     * 关联快递员
     * @param fixedAreaId
     * @param courierId
     * @param takeTimeId
     */
    public void associationCourierToFixedArea(String fixedAreaId,Integer courierId,Integer takeTimeId){
        //1获取Courier，并且修改takeTimeId
        Courier courier = courierMapper.selectByPrimaryKey(courierId);
        courier.setTakeTimeId(takeTimeId);
        courierMapper.updateByPrimaryKey(courier);
        //2 在fixedArea_courier表中插入数据
        FixedAreaCourier fixedAreaCourier = new FixedAreaCourier();
        fixedAreaCourier.setCourierId(courierId);
        fixedAreaCourier.setFixedAreaId(fixedAreaId);
        //保存
        fixedAreaCourierMapper.insert(fixedAreaCourier);

    }

}
