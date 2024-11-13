package com.example.el_project.controller;

import com.example.el_project.model.Post;
import com.example.el_project.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }


    // 전체 게시글 조회 및 검색
    @GetMapping("/view")
    public String viewPosts(Model model,
                            @RequestParam(value = "keyword", required = false) String keyword,
                            @RequestParam(value = "searchType", required = false, defaultValue = "all") String searchType) {

        if (keyword != null && !keyword.isEmpty()) {
            switch (searchType) {
                case "title":
                    model.addAttribute("posts", postService.searchByTitle(keyword));
                    break;
                case "content":
                    model.addAttribute("posts", postService.searchByContent(keyword));
                    break;
                case "author":
                    model.addAttribute("posts", postService.searchByAuthor(keyword));
                    break;
                case "all":
                    model.addAttribute("posts", postService.searchByAllFields(keyword));
                    break;
                default:
                    model.addAttribute("posts", postService.findAll());
            }
            model.addAttribute("keyword", keyword);
            model.addAttribute("searchType", searchType);
        } else {
            model.addAttribute("posts", postService.findAll());
        }

        return "posts";
    }

    // 게시글 작성 폼
    @GetMapping("/new")
    public String createPostForm(Model model) {
        model.addAttribute("post", new Post());
        return "postForm";
    }

    // 게시글 작성
    @PostMapping
    public String createPost(@ModelAttribute Post post) {
        String customId = "1"; // 원하는 ID 값
        post.setId(customId);
        //post.setId(UUID.randomUUID().toString()); // 또는 원하는 ID 설정
        postService.savePost(post);
        return "redirect:/posts/view";
    }

    // 게시글 수정 폼
    @GetMapping("/edit/{id}")
    public String editPostForm(@PathVariable String id, Model model) {
        Post post = postService.findById(id).orElse(null);
        if (post == null) {
            throw new IllegalArgumentException("Invalid post Id:" + id);
        }
        model.addAttribute("post", post);
        return "postForm";
    }

    // 게시글 수정
    @PostMapping("/update/{id}")
    public String updatePost(@PathVariable String id, @ModelAttribute Post post) {
        post.setId(id);
        postService.savePost(post);
        return "redirect:/posts/view";
    }

    // 게시글 삭제
    @GetMapping("/delete/{id}")
    public String deletePost(@PathVariable String id) {
        postService.deletePostById(id);
        return "redirect:/posts/view";
    }
}