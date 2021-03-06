package me.amr.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@RedisHash("Article")
public class Article implements Serializable {


    //제목, 작성일, 작성자, 최종수정일, 내용

    @Id
    private Long id;

    private String title;               // 제목
    private String author;              // 작성자
    private String content;             // 내용
    private LocalDateTime createDate;   // 작성일

    @Setter
    private LocalDateTime modDate;      // 수정일

    public Article() {}

    @Builder
    public Article(Long id, String title, String author, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.createDate = LocalDateTime.now();
        this.modDate = this.createDate;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
        this.modDate = LocalDateTime.now();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[Article#").append(this.id).append("] ");
        sb.append("title=").append(this.title).append(", ");
        sb.append("author=").append(this.author).append(", ");
        sb.append("createDate=").append(this.createDate);

        return sb.toString();
    }
}
