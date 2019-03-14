package com.czxy.bos.controller.base;


import com.czxy.bos.domain.base.Area;
import com.czxy.bos.domain.base.SubArea;
import com.czxy.bos.service.base.AreaService;
import com.czxy.bos.util.PinYin4jUtils;
import com.czxy.bos.vo.EasyUIResult;
import com.github.pagehelper.PageInfo;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by 10254 on 2018/9/6.
 */
@RestController
@RequestMapping("/area")
public class AreaController {

    @Resource
    private AreaService areaService;

    //导入
    @PostMapping("/batchImport")
    public ResponseEntity<String> batchImport(MultipartFile file) throws IOException {
        //处理excl文件      (必须上传提供的文件)
        File newfile=new File("D:\\upload", UUID.randomUUID()+file.getOriginalFilename());
        //将excl文件保存到服务器
        file.transferTo(newfile);
        //解析excl文件
        //加载excl文件，获得工作库
        Workbook workbook=new HSSFWorkbook(new FileInputStream(newfile));
        //获得工作表
        Sheet sheet = workbook.getSheetAt(0);
       //获得所有的行
        int lastRow=sheet.getLastRowNum();

        // 存放所有解析后的area对象的
        List<Area> areaList = new ArrayList<>();
        for(int i=1;i<=lastRow;i++){
            Row row=sheet.getRow(i);
            String id = row.getCell(0).getStringCellValue();
            String province = row.getCell(1).getStringCellValue();
            String city = row.getCell(2).getStringCellValue();
            //String citycode = row.getCell(2).getStringCellValue();
            String district = row.getCell(3).getStringCellValue();
            String postcode = row.getCell(4).getStringCellValue();

           // String shortcode = row.getCell(5).getStringCellValue();

            //将数据封装到area对象
            Area area = new Area();
            area.setId(id);
            area.setProvince(province);
            area.setCity(city);
            area.setDistrict(district);
            area.setPostcode(postcode);

            // citycode , shortchode 程序自动生成的
            // * 去除 省市县 3个汉字
            province = province.substring(0 , province.length() - 1);
            city = city.substring(0 , city.length() - 1);
            district = district.substring(0 , district.length() - 1);

            // * 简化，省名 + 市名 + 县名 首字
            String[] arr = PinYin4jUtils.getHeadByString(province + city + district);
            StringBuilder sb = new StringBuilder();
            for(String a : arr){
                sb.append(a);
            }
            area.setShortcode(sb.toString());

            // * 城市码
            String cityCode = PinYin4jUtils.hanziToPinyin(city, "");
            area.setCitycode(cityCode);

            //添加数据到集合
            areaList.add(area);
        }
        //2 保存所有数据
        int count = areaService.saveAreas(areaList);

        //删除和关闭
        workbook.close();
        newfile.delete();
        System.out.println(count);
        return  new ResponseEntity<String>("本次上传成功"+count+"条", HttpStatus.OK);
    }

    //添加
    @PostMapping
    public ResponseEntity<String> save(Area area){
        int count = areaService.save(area);
        if(count==1){
            return new ResponseEntity<String>("添加成功",HttpStatus.OK);
        }
        return new ResponseEntity<String>("添加失败",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //分页查询
    @GetMapping
    public ResponseEntity<EasyUIResult<Area>> findAll(Area area , Integer page , Integer rows){
        //查询
        PageInfo<Area> pageInfo = areaService.findAll( area , page ,rows );
        //封装datagrid
        EasyUIResult result = new EasyUIResult( pageInfo.getTotal() , pageInfo.getList() );
        //封装状态码
        return new ResponseEntity<EasyUIResult<Area>>( result , HttpStatus.OK );
    }
    //修改
    @PostMapping("/update")
    public ResponseEntity<String> update(Area area){
        try {
            int count = areaService.update(area);
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
            String[] idAll = ids.split(",");
            areaService.delete(idAll);
            return new ResponseEntity<>("删除成功",HttpStatus.OK);

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("删除失败",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/findArea")
    public ResponseEntity<List<SubArea>> findArea(String id){

        List<SubArea> list=areaService.findArea(id);

        return new ResponseEntity<List<SubArea>>(list,HttpStatus.OK);
    }

 }
