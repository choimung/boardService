package com.choimung.boardService.dto;

import lombok.Data;

@Data
public class MemberUpdateDto {

    private String password;

    private String nickname;

    private String image;
}
