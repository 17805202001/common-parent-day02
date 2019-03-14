package com.czxy.bos.service.take_delivery;

import com.czxy.bos.dao.take_delivery.PromotionMapper;
import com.czxy.bos.domain.take_delivery.Promotion;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by 10254 on 2018/9/20.
 */
@Service
@Transactional
public class PromotionService {


    @Resource
    private PromotionMapper promotionMapper;

    //编辑器
    public void save(Promotion promotion){
        promotionMapper.insert(promotion);
    }

    //查询列表
    public PageInfo<Promotion> findAll(Integer page,Integer rows){
        PageHelper.startPage(page,rows);
        //查询所有
        List<Promotion> list=promotionMapper.selectAll();

        //封装对象
        return new PageInfo<>(list);
    }

    //查询没有过期的
    public List<Promotion> findPromotion(){
        Example example=new Example(Promotion.class);
        Example.Criteria criteria=example.createCriteria();
        criteria.andEqualTo("status","1");

        return promotionMapper.selectByExample(example);
    }

    public void updateWithEndDate() {
        promotionMapper.updateWithEndDate();
    }

}
