package com.czxy.bos.controller.take_delivery;

/**
 * Created by 10254 on 2018/9/20.
 */
import com.czxy.bos.domain.take_delivery.Promotion;
import com.czxy.bos.service.take_delivery.PromotionService;
import com.czxy.bos.vo.EasyUIResult;
import com.github.pagehelper.PageInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/promotion")
public class PromotionController {
    @Resource
    private PromotionService promontionService;

    @Resource
    private HttpServletRequest request;

    @Resource
    private HttpServletResponse response;


    //编辑器
    @PostMapping
    public ResponseEntity<Void> add(Promotion promotion, MultipartFile titleImgFile) throws IOException {

        //文件上传
        //上传位置
        String savePath = request.getServletContext().getRealPath("/upload/");
        // 相对路径（用于保存数据库）项目名+固定目录
        String saveUrl = request.getContextPath()+ "/upload/";

        // 生成随机图片名
        String uuid = UUID.randomUUID().toString().replace("-","");
        String name = titleImgFile.getOriginalFilename();
        String ext = name.substring(name.lastIndexOf(".")+1);

        String randomFileName = uuid + ext;

        // 保存图片 (绝对路径)
        File destFile = new File(savePath ,randomFileName);
        if(!destFile.getParentFile().exists()){
            destFile.getParentFile().mkdirs();
        }
        System.out.println(destFile.getAbsolutePath());
        titleImgFile.transferTo(destFile);


        // 将保存路径 相对工程web访问路径，保存model中
        //TODO 是否需要删除http前缀
        promotion.setTitleImg(saveUrl + randomFileName);


        //调用业务层
        promontionService.save(promotion);

        response.sendRedirect("/pages/take_delivery/promotion");
      return new ResponseEntity<Void>(HttpStatus.OK);
    }

    //查询所有
    @GetMapping("/findAll")
    public ResponseEntity<EasyUIResult<Promotion>> findAll(Integer page,Integer rows){
        //查询客户
        PageInfo<Promotion> pageInfo=promontionService.findAll(page,rows);
        //封装
        EasyUIResult<Promotion> result=new EasyUIResult<>(pageInfo.getTotal(),pageInfo.getList());

        return new ResponseEntity<EasyUIResult<Promotion>>(result,HttpStatus.OK);
    }

    //查询用户
    @GetMapping("/findPromotion")
    public ResponseEntity<List<Promotion>> findPromotion(){
        List<Promotion> list=promontionService.findPromotion();
        return new ResponseEntity<List<Promotion>>(list,HttpStatus.OK);
    }

}
