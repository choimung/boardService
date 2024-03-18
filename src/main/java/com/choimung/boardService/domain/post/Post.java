package com.choimung.boardService.domain.post;

import lombok.Data;

@Data
public class Post {

    private Long id;

    private String title;

    private String author;

    private String content;

    private String createDate;

    private String image;

    private Long views;

    public Post() {
    }

    public Post(String title, String author, String content, String createData, String image, Long views) {
        this.title = title;
        this.author = author;
        this.content = content;
        this.createDate = createData;
        this.image = image;
        this.views = views;
    }
}
