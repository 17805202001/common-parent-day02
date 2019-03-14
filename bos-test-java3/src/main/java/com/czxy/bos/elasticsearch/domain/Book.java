package com.czxy.bos.elasticsearch.domain;

import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Id;

/**
 * Created by 10254 on 2018/9/27.
 */
@Document(indexName = "book",type = "bookType")
public class Book {
    @Id
    private Integer id;
    private String title;
    private String content;

    public Book() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
