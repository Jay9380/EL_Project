package com.example.el_project;

import com.example.el_project.model.Post;
import com.example.el_project.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@SpringBootTest
public class ElProjectApplicationTests {

    @Autowired
    private PostRepository postRepository;

    // ISO 8601 포맷터 설정
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    @Test
    public void generateTestData() {
        for (int i = 1; i <= 100; i++) {
            Post post = new Post();
            post.setId(UUID.randomUUID().toString()); // 고유 ID 생성
            post.setTitle("테스트 제목 " + i); // 제목
            post.setContent("이것은 테스트 내용입니다. 데이터 번호는 " + i + "입니다."); // 내용
            post.setAuthor(getRandomAuthor()); // 작성자
            post.setCreatedAt(getRandomDate()); // 랜덤 날짜
            post.setUpdatedAt(LocalDateTime.now()); // 현재 시간
            post.setViews(getRandomViews()); // 랜덤 조회수

            postRepository.save(post); // 데이터 저장
        }
        System.out.println("100개의 테스트 데이터를 생성하였습니다.");
    }

    // 작성자 랜덤 생성
    private String getRandomAuthor() {
        String[] authors = {"홍길동", "김철수", "이영희", "박영수", "최민지"};
        return authors[ThreadLocalRandom.current().nextInt(authors.length)];
    }

    // 랜덤 조회수 생성
    private int getRandomViews() {
        return ThreadLocalRandom.current().nextInt(0, 1000); // 0~999 사이의 랜덤 조회수
    }

    // 랜덤 날짜 생성
    private LocalDateTime getRandomDate() {
        // 30일 전부터 오늘 사이의 랜덤 날짜 생성
        return LocalDateTime.now().minusDays(ThreadLocalRandom.current().nextInt(0, 30))
                .withHour(ThreadLocalRandom.current().nextInt(0, 24))
                .withMinute(ThreadLocalRandom.current().nextInt(0, 60))
                .withSecond(ThreadLocalRandom.current().nextInt(0, 60));
    }
}
