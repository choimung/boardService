package com.choimung.boardService.repository.member.memory;

import com.choimung.boardService.domain.member.Member;
import com.choimung.boardService.dto.MemberSignupDto;
import com.choimung.boardService.dto.MemberUpdateDto;
import com.choimung.boardService.repository.member.MemberRepository;
import com.choimung.boardService.service.FileService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class MemoryMemberRepository implements MemberRepository {

    private final FileService fileService;

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
            try {
                member.setImage(fileService.storeFile(memberUpdateDto.getImage()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
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

    public void clareStore() {
        store.clear();
    }
}
