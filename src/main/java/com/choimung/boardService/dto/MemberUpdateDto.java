package com.choimung.boardService.dto;

import lombok.Data;

@Data
public class MemberUpdateDto {

    private String password;

    private String nickname;

    private String image;

    public MemberUpdateDto() {
    }

    public MemberUpdateDto(String password, String nickname, String image) {
        this.password = password;
        this.nickname = nickname;
        this.image = image;
    }
}
