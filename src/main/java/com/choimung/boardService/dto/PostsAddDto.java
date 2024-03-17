package com.choimung.boardService.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class PostsAddDto {

    private String title;

    private MultipartFile image;

    private String content;
}
