package com.choimung.boardService.domain.member;

import lombok.Data;

@Data
public class Member {
    private Long id;

    private String loginId;

    private String password;

    private String nickname;

    private Grade grade;

    private String image;

    public Member() {
    }

    public Member(String loginId, String password, String nickname, Grade grade, String image) {
        this.loginId = loginId;
        this.password = password;
        this.nickname = nickname;
        this.grade = grade;
        this.image = image;
    }
}
