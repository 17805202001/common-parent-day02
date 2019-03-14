package com.czxy.bos.controller.base;

import com.czxy.bos.domain.base.Standard;
import com.czxy.bos.service.base.StandardService2;
import com.czxy.bos.vo.EasyUIResult;
import com.github.pagehelper.PageInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by 10254 on 2018/9/3.
 */
@RestController
@RequestMapping("/standard2")
public class StandardController2 {

    @Resource
    private StandardService2 standardService2;

    //分页查询
    @GetMapping
    public ResponseEntity<EasyUIResult<Standard>> findAll(Integer page,Integer rows){
        //读取分页数据
        PageInfo<Standard> pageInfo=standardService2.findAll(page,rows);
        //封装数据
        EasyUIResult result=new EasyUIResult<>(pageInfo.getTotal(),pageInfo.getList());

        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    //添加
    @PostMapping
    public ResponseEntity<String> save(Standard standard){
        try{
            int a=standardService2.save(standard);
            if(a==1){
                return new ResponseEntity<>("创建好了", HttpStatus.OK);
            }
            return new ResponseEntity<>("创建失败", HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("创建失败", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //修改
    @PutMapping
    public ResponseEntity<String> update(Standard standard){
        try{
            int u=standardService2.update(standard);
            if(u==1){
                return new ResponseEntity<>("修改好了", HttpStatus.OK);
            }
            return new ResponseEntity<>("修改失败", HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("修改失败", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //删除
    @DeleteMapping
    public ResponseEntity<String> delete(String ids){
        try{
            String[] idAll=ids.split(",");
            standardService2.delete(idAll);

            return new ResponseEntity<>("删除成功", HttpStatus.OK);

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("无法删除", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
