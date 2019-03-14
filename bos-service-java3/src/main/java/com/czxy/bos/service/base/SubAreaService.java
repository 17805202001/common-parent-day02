package com.czxy.bos.service.base;

import com.czxy.bos.dao.base.SubAreaMapper;
import com.czxy.bos.domain.base.SubArea;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by 10254 on 2018/9/25.
 */
@Service
@Transactional
public class SubAreaService {

    @Resource
    private SubAreaMapper subAreaMapper;

    /**
     * 查找指定区域所有的分区（子区域）
     * @param areaId
     * @return
     */

    public List<SubArea> findAllByAreaId(String areaId){
        //1 条件
        Example example = new Example(SubArea.class);
        example.createCriteria().andEqualTo("areaId"  ,areaId);
        //2 查询
        return this.subAreaMapper.selectByExample( example );
    }
}
