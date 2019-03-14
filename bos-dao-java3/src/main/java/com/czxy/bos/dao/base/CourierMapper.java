package com.czxy.bos.dao.base;

import com.czxy.bos.domain.base.Courier;
import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * Created by 10254 on 2018/9/4.
 */
@org.apache.ibatis.annotations.Mapper
public interface  CourierMapper extends Mapper<Courier>{


//    #已经关联快递员
//    SELECT courier_id FROM t_fixedarea_courier
//    #所有快递员
//    SELECT * FROM t_courier
//    #没有关联的快递员
//    SELECT * FROM t_courier WHERE id NOT IN (1,9,12,15)
//    SELECT * FROM t_courier WHERE id NOT IN (SELECT courier_id FROM t_fixedarea_courier)

    @Select("select * from t_courier where id not in (select DISTINCT(courier_id) from t_fixedarea_courier)")
    @Results({
            @Result(id=true,property="id",column="ID")

    })
    List<Courier> findNoAssociation();

    /**
     * 查询指定定区管理的快递员
     * @return
     */
    @Select("SELECT * FROM t_courier c , t_fixedarea_courier fc " +
            "WHERE c.id = fc.courier_id AND fc.fixed_area_id = #{fixedAreaId}")
    @Results({
            @Result(property="courierNum",column="COURIER_NUM"),
            @Result(property="standard",column="standard_id",one=@One(select="com.czxy.bos.dao.base.StandardMapper.selectByPrimaryKey")),
            @Result(property="takeTime",column="taketime_id",one=@One(select="com.czxy.bos.dao.base.TakeTimeMapper.selectByPrimaryKey"))
    }
    )
    List<Courier> findAssociationCourier(@Param("fixedAreaId") String fixedAreaId);
}
