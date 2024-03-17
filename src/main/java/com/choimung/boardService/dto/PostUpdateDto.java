package com.choimung.boardService.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class PostUpdateDto {

    private String title;

    private String image;

    private String content;
}
