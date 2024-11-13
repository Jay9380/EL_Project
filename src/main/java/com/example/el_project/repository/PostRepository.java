package com.example.el_project.repository;

import com.example.el_project.model.Post;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface PostRepository extends ElasticsearchRepository<Post, String> {
    List<Post> findByTitleContaining(String title);
    List<Post> findByContentContaining(String content);
    List<Post> findByAuthorContaining(String author);
    List<Post> findByTitleContainingOrContentContainingOrAuthorContaining(String title, String content, String author);
}

/*

public interface PostRepository extends ElasticsearchRepository<Post, String> {

    @Query("{\"match\": {\"title\": {\"query\": \"?0\", \"operator\": \"and\"}}}")
    List<Post> searchByTitle(String title);

    @Query("{\"match\": {\"content\": {\"query\": \"?0\", \"operator\": \"and\"}}}")
    List<Post> searchByContent(String content);

    @Query("{\"match\": {\"author\": {\"query\": \"?0\", \"operator\": \"and\"}}}")
    List<Post> searchByAuthor(String author);

    @Query("{\"multi_match\": {\"query\": \"?0\", \"fields\": [\"title\", \"content\", \"author\"]}}")
    List<Post> searchByAllFields(String keyword);
}

 */