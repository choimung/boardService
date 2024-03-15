package com.choimung.boardService.service;

import com.choimung.boardService.domain.member.Member;
import com.choimung.boardService.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public void join(Member member){
        memberRepository.save(member);
    }
}
