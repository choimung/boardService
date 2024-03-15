package com.choimung.boardService.repository.member.memory;

import com.choimung.boardService.domain.member.Member;
import com.choimung.boardService.dto.MemberUpdateDto;
import com.choimung.boardService.repository.member.MemberRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class MemberMemberRepository implements MemberRepository {

    private static Map<Long, Member> store = new HashMap<>();
    private static Long sequence = 0L;


    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public void update(Long memberId, MemberUpdateDto memberUpdateDto) {

        Optional<Member> findMember = findById(memberId);

        if(findMember.isPresent()) {
            Member member = findMember.get();
            member.setPassword(memberUpdateDto.getPassword());
            member.setNickname(memberUpdateDto.getNickname());
            member.setImage(memberUpdateDto.getImage());
        }
    }

    @Override
    public Optional<Member> findById(Long memberId) {
        return Optional.ofNullable(store.get(memberId));
    }

    @Override
    public Optional<Member> findByLoginId(String loginId) {
        return findAll().stream()
                .filter(m -> m.getLoginId().equals(loginId))
                .findFirst();
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public void delete(Long memberId) {
        store.remove(memberId);
    }
}
