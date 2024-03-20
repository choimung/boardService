package com.choimung.boardService.repository.member;

import com.choimung.boardService.domain.member.Member;
import com.choimung.boardService.dto.MemberSignupDto;
import com.choimung.boardService.dto.MemberUpdateDto;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository {

    Member save(Member member);

    void update(Long memberId, MemberUpdateDto memberUpdateDto);

    Optional<Member> findById(Long memberId);

    Optional<Member> findByLoginId(String loginId);

    List<Member> findAll();

    void delete(Long memberId);
}
