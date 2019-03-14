package com.czxy.bos.elasticsearch;

import com.czxy.bos.TestApplication;

import com.czxy.bos.elasticsearch.domain.Book;
import com.czxy.bos.elasticsearch.service.BookService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by 10254 on 2018/9/28.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes= TestApplication.class)
public class TestBookService {

    @Resource
    private BookService bookService;

    @Test
    public void testSave(){
        Book book = new Book();
        book.setId(7);
        book.setTitle("PHP基础");
        book.setContent("Java语法学习");

        bookService.save(book);

        System.out.println("保存成功");
    }

    @Test
    public void testFindAll(){
        Page<Book> page = this.bookService.findAll(2,2);

        System.out.println("总分页：" +  page.getTotalPages());
        System.out.println("总个数：" + page.getTotalElements());
        System.out.println("每页显示个数：" +  page.getSize());

        List<Book> list = page.getContent();
        System.out.println(list.size());
    }

    @Test
    public void testFind1(){

        Book book = bookService.findByTtile("Java入门");

        System.out.println(book);
    }

    @Test
    public void testFind2(){

        List<Book> list = bookService.findByTitleLike("Java");

        for (Book book: list ) {
            System.out.println(book);
        }
    }

    @Test
    public void testFind3(){

        Page<Book> list = bookService.findByTitleLike("Java",1,2);

        for (Book book: list.getContent() ) {
            System.out.println(book);
        }
    }

    /**
     * 指定查询条件，进行排序
     */
    @Test
    public void testFind4(){

        //List<Book> list = bookService.findByTitleLikeOrderById("java");
        List<Book> list = bookService.findByTitleLikeOrderByIdDesc("java");

        for (Book book: list ) {
            System.out.println(book);
        }
    }

    /**
     * 查询所有，排序
     */
    @Test
    public void testFind5(){

        Iterable<Book> list = bookService.findOrderById();

        for (Book book: list ) {
            System.out.println(book);
        }
    }

    /**
     * 查询所有
     */
    @Test
    public void testFind6_1(){

        Page<Book> bookPage = this.bookService.searchQuery(1, 10);

        for (Book book: bookPage.getContent() ) {
            System.out.println(book);
        }
    }

    @Test
    public void testFind6_2(){

        Page<Book> bookPage = this.bookService.searchQuery2(1, 2);

        for (Book book: bookPage.getContent() ) {
            System.out.println(book);
        }
    }
}
