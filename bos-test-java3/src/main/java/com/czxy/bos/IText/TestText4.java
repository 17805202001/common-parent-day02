package com.czxy.bos.IText;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;

import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by 10254 on 2018/10/11.
 */
public class TestText4 {
    public static void main(String[] args) throws Exception {
        //创建文档
        Document document=new Document();

        //确定写入位置
        PdfWriter.getInstance(document,new FileOutputStream("1.pdf"));

        //打开
        document.open();

        //绘制表格
        Table table=new Table(3);
        table.setBorder(1);

        //水平对齐方式
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        //垂直对齐方式
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_TOP);

        //设置表头
        table.addCell(getCell("编号"));
        table.addCell(getCell("姓名"));
        table.addCell(getCell("爱好"));

        //模拟数据
        for(int i=1;i<=10;i++){
            table.addCell(getCell("bh"+i));
            table.addCell(getCell("jack"+i));
            table.addCell(getCell("学习"+i));
        }
        //写入数据
        document.add(table);

        //关闭
        document.close();
    }

    public static Cell getCell(String content) throws IOException, DocumentException {
        //支持中文
        BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);

        Font font=new Font(bfChinese);

        Cell cell=new Cell(new Paragraph(content,font));
        cell.setBackgroundColor(Color.pink);

        return cell;
    }
}
