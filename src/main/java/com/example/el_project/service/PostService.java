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

    public List<Post> searchByAllFields(String keyword, String option) {
        Criteria criteria = buildCriteria("title", keyword, option)
                .or(buildCriteria("content", keyword, option))
                .or(buildCriteria("author", keyword, option));
        return executeSearch(criteria);
    }

    public List<Post> searchByTitle(String keyword, String option) {
        Criteria criteria = buildCriteria("title", keyword, option);
        return executeSearch(criteria);
    }

    public List<Post> searchByContent(String keyword, String option) {
        Criteria criteria = buildCriteria("content", keyword, option);
        return executeSearch(criteria);
    }

    public List<Post> searchByAuthor(String keyword, String option) {
        Criteria criteria = buildCriteria("author", keyword, option);
        return executeSearch(criteria);
    }

    private Criteria buildCriteria(String field, String keyword, String option) {
        switch (option) {
            case "match":
                return Criteria.where(field).matches(keyword); //match의 경우 text
            case "term":
                return Criteria.where(field).is(keyword); // is 의 경우 keyword
            case "expression":
                return Criteria.where(field).expression("*" + keyword + "*");
            default:
                return Criteria.where(field).matches(keyword);
        }
    }

    private List<Post> executeSearch(Criteria criteria) {
        CriteriaQuery query = new CriteriaQuery(criteria);
        SearchHits<Post> searchHits = elasticsearchOperations.search(query, Post.class);

        List<Post> posts = new ArrayList<>();
        for (var hit : searchHits.getSearchHits()) {
            posts.add(hit.getContent());
        }
        return posts;
    }
    /*
        Criteria 클래스의 where 메서드가 정적(static) 메서드로 정의되어 있음
        matches -> text , is -> keyword
        검색어의 경우 현재 코드는 matches로 되어있기 때문에 검색어도 text타입으로 검색이됨
        CriteriaQuery는 Criteria 외의 타입을 허용하지 않음
        SearchHits =>  검색 결과의 모음을 나타내는 클래스
        elasticsearchOperations => Spring Data Elasticsearch 인터페이스
     */
    //

}