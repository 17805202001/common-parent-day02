package com.czxy.bos.dao.take_delivery;

import com.czxy.bos.domain.report.HighChart;
import com.czxy.bos.domain.take_delivery.WayBill;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * Created by 10254 on 2018/9/26.
 */
@org.apache.ibatis.annotations.Mapper
public interface WayBillMapper extends Mapper<WayBill>{

    @Select("SELECT COUNT(*) AS `data` , ( " +
            "   CASE " +
            "    WHEN wb.sign_status = 1 THEN '待发货' " +
            "    WHEN wb.sign_status = 2 THEN '收派中' " +
            "    WHEN wb.sign_status = 3 THEN '已签收' " +
            "    ELSE '异常' " +
            "   END " +
            ") AS `name` FROM t_way_bill wb " +
            "GROUP BY wb.sign_status")
    public List<HighChart> findBySignStatus();
}
