package com.czxy.bos.IText;

import com.lowagie.text.Document;

import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import java.io.FileOutputStream;

/**
 * Created by 10254 on 2018/10/11.
 */
public class TestText {
    public static void main(String[] args) throws Exception {
        //创建文档
        Document document=new Document();

        //确定写入位置
        PdfWriter.getInstance(document,new FileOutputStream("1.pdf"));

        //打开
        document.open();

        //写入数据
        document.add(new Paragraph("helloworld"));
        document.close();
    }
}
