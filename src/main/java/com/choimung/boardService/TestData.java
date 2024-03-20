package com.choimung.boardService;


import com.choimung.boardService.domain.member.Grade;
import com.choimung.boardService.domain.member.Member;
import com.choimung.boardService.dto.MemberSignupDto;
import com.choimung.boardService.dto.MemberUpdateDto;
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
        MemberSignupDto memberSignupDto = new MemberSignupDto("123", "123", "테스터", null);
        memberService.join(memberSignupDto);
    }
}
