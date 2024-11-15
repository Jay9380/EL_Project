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


    @GetMapping("/view")
    public String viewPosts(Model model,
                            @RequestParam(value = "keyword", required = false) String keyword,
                            @RequestParam(value = "searchType", required = false, defaultValue = "all") String searchType,
                            @RequestParam(value = "option", required = false, defaultValue = "match") String option) {

        if (keyword != null && !keyword.isEmpty()) {
            if ("all".equals(searchType)) {
                // 전체 필드에서 검색
                model.addAttribute("posts", postService.searchByAllFields(keyword, option));
            } else {
                // 개별 필드에서 검색
                switch (searchType) {
                    case "title":
                        model.addAttribute("posts", postService.searchByTitle(keyword, option));
                        break;
                    case "content":
                        model.addAttribute("posts", postService.searchByContent(keyword, option));
                        break;
                    case "author":
                        model.addAttribute("posts", postService.searchByAuthor(keyword, option));
                        break;
                    default:
                        model.addAttribute("posts", postService.findAll());
                }
            }
            model.addAttribute("keyword", keyword);
            model.addAttribute("searchType", searchType);
            model.addAttribute("option", option);
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

    @PostMapping
    public String createPost(@ModelAttribute Post post) {
        // 매번 새로운 UUID를 생성하여 ID로 설정
        //post.setId(UUID.randomUUID().toString()); // 또는 원하는 ID 설정
        post.setId(UUID.randomUUID().toString());
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