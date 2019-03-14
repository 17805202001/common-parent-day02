package com.czxy.bos.service.base;

import com.czxy.bos.dao.base.AreaMapper;
import com.czxy.bos.dao.base.SubAreaMapper;
import com.czxy.bos.domain.base.Area;
import com.czxy.bos.domain.base.SubArea;
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
 * Created by 10254 on 2018/9/6.
 */
@Service
@Transactional
public class AreaService {

    @Resource
    private AreaMapper areaMapper;

    @Resource
    private SubAreaMapper subAreaMapper;

    /**
     * 对上传数据进行校验，如果存在忽略，只记录成功个数。
     * @param list
     * @return
     */
    //导入
    public Integer saveAreas(List<Area> list){
        int count = 0;
        for(Area area : list){
            //TODO 没有校验是否重复，如果重复忽略，方法返回值整数（本次添加个数）
            Area temp = areaMapper.selectByPrimaryKey( area.getId());
            if(temp != null){
                continue;
            }
            count ++;
            areaMapper.insert(area);
        }
        return count;
    }

    //添加
    public Integer save(Area area){
        area.setId(UUID.randomUUID().toString());
        return areaMapper.insert(area);
    }
    //分页查询
    public PageInfo<Area> findAll(Area area , Integer page , Integer rows){
        //1 拼凑条件
        Example example = new Example(Area.class);
        Example.Criteria criteria = example.createCriteria();
        if( area != null ){
            //省
            if(StringUtils.isNotBlank(area.getProvince() )){
                criteria.andLike("province" , "%"+area.getProvince()+"%");

            }
            //市
            if(StringUtils.isNotBlank(area.getCity())){
                criteria.andLike("city" , "%"+area.getCity()+"%");

            }
            //县
            if(StringUtils.isNotBlank(area.getDistrict())){
                criteria.andLike("district" , "%"+area.getDistrict()+"%");

            }
        }
        //2 分页设置
        PageHelper.startPage( page ,rows);
        //3 查询所有(带条件)
        List<Area> list = areaMapper.selectByExample( example );
        //4 封装
        return new PageInfo<>( list );
    }

    //修改
    public Integer update(Area area){
        return areaMapper.updateByPrimaryKey(area);
    }

    //删除
    public void delete(String[] ids){

        for(String i:ids){

            areaMapper.deleteByPrimaryKey(i);
        }
    }

    public List<SubArea> findArea(String fixedAreaId) {
        Example example=new Example(SubArea.class);
        Example.Criteria criteria=example.createCriteria();
        criteria.andEqualTo("fixedAreaId",fixedAreaId);

        List<SubArea> list=subAreaMapper.selectByExample(example);

        for(SubArea subArea:list){
            String areaId = subArea.getAreaId();
            Area area = areaMapper.selectByPrimaryKey(areaId);

            subArea.setArea(area);
        }

        return list;
    }

    /**
     * 通过省市县名称，查询对应区域对象，获得id
     * @param area
     * @return
     */
    public Area selectByArea(Area area){
        return areaMapper.selectByArea(area);
    }



}
