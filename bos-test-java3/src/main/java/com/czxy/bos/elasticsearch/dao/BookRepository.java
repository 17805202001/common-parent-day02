package com.czxy.bos.elasticsearch.dao;

import com.czxy.bos.elasticsearch.domain.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * Created by 10254 on 2018/9/27.
 */
public interface BookRepository extends ElasticsearchRepository<Book,Integer> {
    /**
     * 标题进行等值查询
     * @param title
     * @return
     */
    public Book findByTitle(String title);

    /**
     * 模糊查询
     * @param title
     * @return
     */
    public List<Book> findByTitleLike(String title);

    /**
     * 模糊查询 + 分页
     * @param title
     * @return
     */
    public Page<Book> findByTitleLike(String title , Pageable pageable);

    /**
     * 通过id进行排序查询 , 并添加title模糊查询（默认升序）
     * @return
     */
    public List<Book> findByTitleLikeOrderById(String title);

    /**
     * 通过id进行排序查询 , 并添加title模糊查询，采用降序
     * @param title
     * @return
     */
    public List<Book> findByTitleLikeOrderByIdDesc(String title);

}

