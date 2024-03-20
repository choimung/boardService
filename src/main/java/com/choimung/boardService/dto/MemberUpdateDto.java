package com.choimung.boardService.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class MemberUpdateDto {

    private String password;

    private String nickname;

    private MultipartFile image;

    public MemberUpdateDto() {
    }

    public MemberUpdateDto(String password, String nickname, MultipartFile image) {
        this.password = password;
        this.nickname = nickname;
        this.image = image;
    }
}
