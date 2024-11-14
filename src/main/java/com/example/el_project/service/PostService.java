package com.example.el_project.service;


import com.example.el_project.model.Post;
import com.example.el_project.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    public Post savePost(Post post) {
        return postRepository.save(post);
    }

    public Iterable<Post> findAll() {
        return postRepository.findAll();
    }

    public Optional<Post> findById(String id) {
        return postRepository.findById(id);
    }

    public void deletePostById(String id) {
        postRepository.deleteById(id);
    }

    //Criteria 클래스의 where 메서드가 정적(static) 메서드로 정의되어 있음
    public List<Post> searchByTitle(String title) {
        Criteria criteria = Criteria.where("title").matches(title);
        //CriteriaQuery는 Criteria 외의 타입을 허용하지 않음
        CriteriaQuery query = new CriteriaQuery(criteria);

        //SearchHits =>  검색 결과의 모음을 나타내는 클래스
        //elasticsearchOperations => Spring Data Elasticsearch 인터페이스
        SearchHits<Post> searchHits = elasticsearchOperations.search(query, Post.class);

        List<Post> posts = new ArrayList<>();
        for (var hit : searchHits) {
            posts.add(hit.getContent());
        }
        return posts;
    }

    public List<Post> searchByContent(String content) {
        Criteria criteria = Criteria.where("content").matches(content);
        CriteriaQuery query = new CriteriaQuery(criteria);
        SearchHits<Post> searchHits = elasticsearchOperations.search(query, Post.class);

        List<Post> posts = new ArrayList<>();
        for (var hit : searchHits) {
            posts.add(hit.getContent());
        }
        return posts;
    }

    public List<Post> searchByAuthor(String author) {
        Criteria criteria = Criteria.where("author").matches(author);
        CriteriaQuery query = new CriteriaQuery(criteria);
        SearchHits<Post> searchHits = elasticsearchOperations.search(query, Post.class);

        List<Post> posts = new ArrayList<>();
        for (var hit : searchHits) {
            posts.add(hit.getContent());
        }
        return posts;
    }

    public List<Post> searchByAllFields(String keyword) {
        Criteria criteria = new Criteria().or("title").matches(keyword)
                .or("content").matches(keyword)
                .or("author").matches(keyword);
        CriteriaQuery query = new CriteriaQuery(criteria);
        SearchHits<Post> searchHits = elasticsearchOperations.search(query, Post.class);

        List<Post> posts = new ArrayList<>();
        for (var hit : searchHits) {
            posts.add(hit.getContent());
        }

        /*
            for (int i = 0; i < searchHits.getSearchHits().size(); i++) {
                posts.add(searchHits.getSearchHits().get(i).getContent());
            }
         */
        return posts;
    }
}