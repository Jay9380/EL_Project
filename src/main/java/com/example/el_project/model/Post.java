package com.example.el_project.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;


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

    @Field(type = FieldType.Date, format = {}, pattern = "uuuu-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt; // 등록 시간

    @LastModifiedDate
    private LocalDateTime updatedAt; // 수정 시간
    // Getters, Setters

    @Field(type = FieldType.Integer, name = "viewCount")
    private int views = 0; // 조회수 기본값 0
}