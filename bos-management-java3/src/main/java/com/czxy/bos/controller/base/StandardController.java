package com.czxy.bos.controller.base;

import com.czxy.bos.domain.base.Standard;
import com.czxy.bos.service.base.StandardService;
import com.czxy.bos.vo.EasyUIResult;
import com.github.pagehelper.PageInfo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by 10254 on 2018/9/3.
 */
@RestController
@RequestMapping("/standard")
public class StandardController {

    //注入
    @Resource
    private StandardService standardService;

    /**
     * 200：正常    HttpStatus.OK
     * 201:表示创建     HttpStatus.CREATED
     * */


    //查询
    @GetMapping
    public ResponseEntity<EasyUIResult<Standard>> queryStandardByPageTemp(Integer page , Integer rows){
        //1 查询获得分页数据
        PageInfo<Standard> pageInfo = standardService.queryStandardByPage(page ,rows);
        //2 封装数据
        EasyUIResult result = new EasyUIResult<>( pageInfo.getTotal() ,pageInfo.getList());
        //3 返回含有状态的信息
        return new ResponseEntity<>(result , HttpStatus.OK);
    }

    //添加
    @PostMapping
    public ResponseEntity<String> save(Standard standard){
        try{
          int a=standardService.saveStandard(standard);
          if(a==1){
              return new ResponseEntity<String>("创建成功",HttpStatus.OK);
          }
            return new ResponseEntity<String>("创建异常", HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<String>("创建失败", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //修改
    @PutMapping
    public ResponseEntity<String> update(Standard standard){
        try {
            int count = standardService.update(standard);
            if (count == 1) {
                return new ResponseEntity<>("修改成功",HttpStatus.OK);
            }
            return new ResponseEntity<>("修改失败",HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("修改失败",HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    //删除
    @DeleteMapping
    public ResponseEntity<String> delete(String ids){
        try {
            String[] idAll=ids.split(",");
            standardService.delete(idAll);

            return new ResponseEntity<>("删除成功",HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("删除失败",HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/all")
    public ResponseEntity<List<Standard>> all(){
        List<Standard> list=standardService.findAll();
        return new ResponseEntity<List<Standard>>(list,HttpStatus.OK);
    }



}
