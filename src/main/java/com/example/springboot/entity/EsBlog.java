package com.example.springboot.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;

@Document(indexName = "blog",type = "blog")
public class EsBlog implements Serializable {

    @Id
    private String id;
    private String title;
    private String content;

}
