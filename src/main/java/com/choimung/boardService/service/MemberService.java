package com.choimung.boardService.service;

import com.choimung.boardService.domain.member.Grade;
import com.choimung.boardService.domain.member.Member;
import com.choimung.boardService.dto.MemberSignupDto;
import com.choimung.boardService.dto.MemberUpdateDto;
import com.choimung.boardService.dto.PostUpdateDto;
import com.choimung.boardService.repository.member.MemberRepository;
import java.io.IOException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final FileService fileService;

    public void join(MemberSignupDto memberSignupDto){

        Member member = new Member();
        member.setLoginId(memberSignupDto.getLoginId());
        member.setPassword(memberSignupDto.getPassword());
        member.setNickname(memberSignupDto.getNickName());
        member.setGrade(Grade.USER);

        try {
            member.setImage(fileService.storeFile(memberSignupDto.getImage()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

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
