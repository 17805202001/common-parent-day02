package com.czxy.bos.elasticsearch.service;

import com.czxy.bos.elasticsearch.dao.BookRepository;
import com.czxy.bos.elasticsearch.domain.Book;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by liangtong on 2018/9/27.
 */
@Service
@Transactional
public class BookService {

    @Resource
    private BookRepository bookRepository;

    /**
     * 添加数据 （如果id重复，为更新数据）
     * @param book
     */
    public void save(Book book ){
        this.bookRepository.save(book);
    }

    public Page<Book> findAll(int page , int rows){
        Pageable pageable = PageRequest.of( page-1,rows);
        return this.bookRepository.findAll(pageable);
    }

    public Book findByTtile(String title){
        return this.bookRepository.findByTitle(title);
    }

    public List<Book> findByTitleLike(String title){
        return this.bookRepository.findByTitleLike(title);
    }

    public Page<Book> findByTitleLike(String title,int page, int size){
        return this.bookRepository.findByTitleLike(title , PageRequest.of(page-1,size));
    }

    public List<Book> findByTitleLikeOrderById(String title){
        return this.bookRepository.findByTitleLikeOrderById( title );
    }

    public List<Book> findByTitleLikeOrderByIdDesc(String title){
        return this.bookRepository.findByTitleLikeOrderByIdDesc( title );
    }

    public Iterable<Book> findOrderById(){
        //排序基本语法：Sort.by(...) 进行排序
        //   指定字段升序：Sort.Order.asc("字段名")
        //   指定字段降序：Sort.Order.desc("字段名")
        return this.bookRepository.findAll(Sort.by(Sort.Order.asc("id")));
    }

    /**
     * 多条件查询模板
     * @param page
     * @param rows
     * @return
     */
    public Page<Book> searchQuery(Integer page ,Integer rows){

        //1 可以将多个条件组装成一起，查询对象
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();

        //2 封装查询信息对象
        SearchQuery searchQuery = new NativeSearchQuery(queryBuilder);  //2.1 查询条件
        //2.2 分页数据
        //2.3 排序数据


        //3 条件查询
        return this.bookRepository.search( searchQuery );
    }

    public Page<Book> searchQuery2(Integer page ,Integer rows){

        //1 可以将多个条件组装成一起，查询对象
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        //1.1 等值查询
        //queryBuilder.must( QueryBuilders.matchPhraseQuery("content","Java语法学习"));

        //1.2 模糊查询
        //queryBuilder.must( QueryBuilders.matchQuery("title","java"));
        //queryBuilder.must( QueryBuilders.matchQuery("title","Java基础"));

        //1.3 多字段模糊查询
        //queryBuilder.must( QueryBuilders.multiMatchQuery("java","title","content"));

        //1.4 id查询
        //queryBuilder.must( QueryBuilders.idsQuery().addIds("1", "5"));

        //1.5 范围内查询
        queryBuilder.must( QueryBuilders.rangeQuery("id").from(2).to(5));

        //2 封装查询信息对象
        SearchQuery searchQuery = new NativeSearchQuery(queryBuilder);  //2.1 查询条件
        //2.2 分页数据
        //searchQuery.setPageable( PageRequest.of( page-1,rows) );
        //2.3 排序数据
        //searchQuery.addSort( Sort.by( Sort.Order.desc("title")));   //正常
        searchQuery.addSort( Sort.by( Sort.Order.desc("content")));   //错误


        //3 条件查询
        return this.bookRepository.search( searchQuery );
    }

}
