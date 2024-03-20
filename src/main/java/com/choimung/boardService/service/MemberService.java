package com.choimung.boardService.service;

import com.choimung.boardService.domain.member.Member;
import com.choimung.boardService.dto.MemberUpdateDto;
import com.choimung.boardService.dto.PostUpdateDto;
import com.choimung.boardService.repository.member.MemberRepository;
import java.util.Optional;
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

    public void update(Long memberId, MemberUpdateDto memberUpdateDto) {
        memberRepository.update(memberId, memberUpdateDto);
    }

    public Optional<Member> findById(Long memberId) {
        return memberRepository.findById(memberId);
    }

    public Member login(String loginId, String password) {
        return memberRepository.findByLoginId(loginId)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);
    }
}
