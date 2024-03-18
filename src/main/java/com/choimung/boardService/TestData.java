package com.choimung.boardService;


import com.choimung.boardService.domain.member.Grade;
import com.choimung.boardService.domain.member.Member;
import com.choimung.boardService.repository.member.MemberRepository;
import com.choimung.boardService.service.MemberService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class TestData {

    private final MemberService memberService;

    @PostConstruct
    void init() {
        Member member = new Member("123", "123", "테스터", Grade.USER, null);
        memberService.join(member);
    }
}
