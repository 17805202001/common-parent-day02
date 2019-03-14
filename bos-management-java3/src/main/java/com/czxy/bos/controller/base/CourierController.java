package com.czxy.bos.controller.base;

import com.czxy.bos.domain.base.Courier;
import com.czxy.bos.service.base.CourierService;
import com.czxy.bos.service.base.StandardService;
import com.czxy.bos.vo.EasyUIResult;
import com.github.pagehelper.PageInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sun.plugin.cache.CacheUpdateHelper;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by 10254 on 2018/9/4.
 */
@RestController
@RequestMapping("/courier")
public class CourierController {

    @Resource
    private CourierService courierService;


    //添加
    @PostMapping
    public ResponseEntity<String> save(Courier courier){
        int count=courierService.save(courier);
        if(count==1){
            return new ResponseEntity<>("创建号了",HttpStatus.CREATED);
        }
        return new ResponseEntity<>("数据添加失败",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //分页查询
    @GetMapping
    public ResponseEntity<EasyUIResult<Courier>> findCourierByPage(Courier courier,Integer page,Integer rows){
        PageInfo<Courier> pageInfo=courierService.queryCourierListByPage(courier,page,rows);

        EasyUIResult<Courier> result=new EasyUIResult<>(pageInfo.getTotal(),pageInfo.getList());
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    //批量作废
    @DeleteMapping
    public ResponseEntity<String> delete(@RequestParam(name="ids[]") Integer[] ids){
        //删除
        int count=courierService.delete(ids);
        //提示
        return new ResponseEntity<String>("本次作废"+count+"条",HttpStatus.OK);

    }

    //还原
    @PutMapping
    public ResponseEntity<String> restore(@RequestParam(name="ids[]") Integer[] ids){
        //删除
        int count=courierService.restore(ids);
        //提示
        return new ResponseEntity<String>("本次还原"+count+"条",HttpStatus.OK);

    }

    //修改
    @PutMapping("/update")
    public ResponseEntity<String> update(Courier courier){
        try {
            int count=courierService.update(courier);
            if(count==1){
                return new ResponseEntity<String>("修改成功",HttpStatus.OK);
            }
            return new ResponseEntity<String>("修改失败",HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<String>("修改失败",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //查询未关联快递员
    @GetMapping("/findNoAssociation")
    public ResponseEntity<List<Courier>> findNoAssociation(){
        //查询
        List<Courier> list=courierService.findNoAssociation();
        //封装
        return new ResponseEntity<List<Courier>>(list,HttpStatus.OK);
    }

    //查询指定定区快递员
    @GetMapping(value = "/findAssociationCourier")
    public ResponseEntity<List<Courier>> findAssociationCourier(@RequestParam("fixedAreaId")String fixedAreaId){

        // 调用业务层，查询关联快递员 列表
        List<Courier> result = courierService.findAssociationCourier(fixedAreaId);
        // 将查询快递员列表 压入值栈
        return new ResponseEntity<List<Courier>>(result,HttpStatus.OK);

    }

}
