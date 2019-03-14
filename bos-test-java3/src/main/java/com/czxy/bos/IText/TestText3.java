package com.czxy.bos.IText;

import com.lowagie.text.*;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by 10254 on 2018/10/11.
 */
public class TestText3 {
    public static void main(String[] args) throws Exception {
        //创建文档
        Document document=new Document();

        //确定写入位置
        PdfWriter.getInstance(document,new FileOutputStream("3.pdf"));

        //打开
        document.open();

        //绘制一个表格
        Table table=new Table(3);
        table.setBorder(1);

        //水平方式对齐
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        //垂直方式对齐
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_TOP);

        //设置支持中文字体及编码
        BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);

        Font font=new Font(bfChinese);

        //设置表头
        table.addCell(new Paragraph("编号",font));
        table.addCell(new Paragraph("姓名",font));
        table.addCell(new Paragraph("爱好",font));

        //模拟数据
        for(int i=1;i<=10;i++){
            table.addCell(new Paragraph("bh",font));
            table.addCell(new Paragraph("jack",font));
            table.addCell(new Paragraph("学习",font));
        }
        document.add(table);
        document.close();
    }

}
