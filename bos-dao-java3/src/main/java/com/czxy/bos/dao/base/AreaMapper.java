package com.czxy.bos.dao.base;

import com.czxy.bos.domain.base.Area;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

/**
 * Created by 10254 on 2018/9/6.
 */
@org.apache.ibatis.annotations.Mapper
public interface AreaMapper extends Mapper<Area> {
    /**
     * 通过省市县名称，查询对应区域对象，获得id
     * @param area
     * @return
     */
    @Select("SELECT * FROM t_area WHERE province=#{province} AND city =#{city} AND district=#{district}")
    public Area selectByArea(Area area);
}
