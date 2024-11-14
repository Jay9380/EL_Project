package com.example.el_project.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;


@Document(indexName = "posts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    //타입 지정하지 않으면 기본값으로 text로 지정
    //@Field(type = FieldType.Text) , @Field(type = FieldType.Keyword) -> 지정가능
    @Id
    private String id;
    private String title;
    private String content;
    private String author;

    // Getters, Setters
}