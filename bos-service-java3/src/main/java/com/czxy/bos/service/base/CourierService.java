package com.czxy.bos.service.base;

import com.czxy.bos.dao.base.CourierMapper;
import com.czxy.bos.dao.base.StandardMapper;
import com.czxy.bos.domain.base.Courier;
import com.czxy.bos.domain.base.Standard;
import com.czxy.bos.exception.BosException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by 10254 on 2018/9/4.
 */
@Service
@Transactional
public class CourierService {

    @Resource
    private CourierMapper courierMapper;

    @Resource
    private StandardMapper standardMapper;
    //添加快递员
    public Integer save(Courier courier){
        //1.对数据进行校验
        //1.1 非空
        if(StringUtils.isBlank(courier.getCourierNum())){
            throw new BosException("操作的快递员工号不能为空");
        }
        //查询快递单号
        Example example=new Example(Courier.class);
        Example.Criteria criteria=example.createCriteria();
        criteria.andEqualTo("courierNum",courier.getCourierNum());
        Courier findCourier = courierMapper.selectOneByExample(example);
        if(findCourier!=null){
            throw new BosException("快递员已存在");
        }
        return courierMapper.insert(courier);
    }

    //分页查询
    public PageInfo<Courier> queryCourierListByPage(Courier courier,Integer page,Integer rows){


        //拼凑条件
        Example example=new Example(Courier.class);
        Example.Criteria criteria=example.createCriteria();
        if(courier!=null){
            //工号
            if(StringUtils.isNotBlank(courier.getCourierNum())){
                criteria.andLike("courierNum","%"+courier.getCourierNum()+"%");
            }
//            //收派标准
//            if(StringUtils.isNotBlank(courier.get)){
//                criteria.andLike("courierNum","%"+courier.getCourierNum()+"%");
//            }
            //所属单位
            if(StringUtils.isNotBlank(courier.getCompany())){
                criteria.andLike("company","%"+courier.getCompany()+"%");
            }
            //取派员类型
            if(StringUtils.isNotBlank(courier.getType())){
                criteria.andLike("type","%"+courier.getType()+"%");
            }
        }
        //分页
        PageHelper.startPage(page,rows);
        //查询
        List<Courier> list=courierMapper.selectByExample(example);
        //遍历所有的快递员，获得关联  收派标准id值
        for(Courier couriers :list){
            Integer sid=couriers.getStandardId();
            Standard standard=standardMapper.selectByPrimaryKey(sid);
            couriers.setStandard(standard);
        }
        //封装对象
        return new PageInfo<>(list);
    }

    //批量作废
    public Integer delete(Integer[] ids){
        for(Integer id: ids){
            //查询
            Courier courier=courierMapper.selectByPrimaryKey(id);
            if(courier==null){
                throw  new BosException("操作对象不存在");
            }
            //修改标记
            courier.setDeltag('1');
            //更新操作
            courierMapper.updateByPrimaryKey(courier);
        }
        return ids.length;
    }

    //还原
    public Integer  restore(Integer[] ids){
        for(Integer id:ids){
            Courier courier=courierMapper.selectByPrimaryKey(id);
            //修改标记
            courier.setDeltag(null);
            courierMapper.updateByPrimaryKey(courier);
        }
        return ids.length;
    }

    //修改
    public Integer update(Courier courier){
        return courierMapper.updateByPrimaryKey(courier);
    }




    public List<Courier> findNoAssociation() {
        List<Courier> list = courierMapper.findNoAssociation();
       //处理显示数据
        for(Courier c:list){
            c.setInfo(c.getName()+"("+c.getCompany()+")");
        }
        return list;
    }

//查询指定定区快递员
    public List<Courier> findAssociationCourier(String fixedAreaId) {
        List<Courier> list = courierMapper.findAssociationCourier(fixedAreaId);
        return list;
    }


}
