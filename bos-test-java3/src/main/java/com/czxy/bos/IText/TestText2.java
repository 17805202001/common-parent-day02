package com.czxy.bos.IText;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by 10254 on 2018/10/11.
 */
public class TestText2 {

    public static void main(String[] args) throws Exception {
        Document document=new Document();
        PdfWriter.getInstance(document,new FileOutputStream("2.pdf"));
        document.open();

        //设置支持中文字体及编码
        BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);

        document.add(new Paragraph("传智学院",new Font(bfChinese)));

        document.close();


    }
}
