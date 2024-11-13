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

    public List<Post> searchByTitle(String title) {
        Criteria criteria = Criteria.where("title").matches(title);
        CriteriaQuery query = new CriteriaQuery(criteria);
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
        return posts;
    }
}