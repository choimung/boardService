package com.choimung.boardService.repository.member.memory;

import static org.assertj.core.api.Assertions.assertThat;

import com.choimung.boardService.domain.member.Grade;
import com.choimung.boardService.domain.member.Member;
import com.choimung.boardService.dto.MemberUpdateDto;
import com.choimung.boardService.repository.member.MemberRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class MemoryMemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @BeforeEach
    void beforeEach() {
        log.info("\n 📌[memberRepository instance = {}]", memberRepository.getClass());
    }

    @AfterEach
    void afterEach() {
        if(memberRepository instanceof MemoryMemberRepository) {
            ((MemoryMemberRepository)memberRepository).clareStore();
        }
    }

    @Test
    @DisplayName("Member Save Test")
    void save() {
        //when
        Member member = new Member("neo", "1234", "NEO", Grade.USER, "testImage");

        //given
        Member savedMember = memberRepository.save(member);

        //then
        assertThat(savedMember.getId()).isEqualTo(member.getId());
        assertThat(savedMember.getLoginId()).isEqualTo(member.getLoginId());
        assertThat(savedMember.getPassword()).isEqualTo(member.getPassword());
        assertThat(savedMember.getNickname()).isEqualTo(member.getNickname());
        assertThat(savedMember.getGrade()).isEqualTo(member.getGrade());
        assertThat(savedMember.getImage()).isEqualTo(member.getImage());
    }

    @Test
    @DisplayName("Member Update Test")
    void update() {
        //given
        Member member = new Member("neo", "1234", "NEO", Grade.USER, "testImage");
        Member savedMember = memberRepository.save(member);

        //when
        MemberUpdateDto memberUpdateDto = new MemberUpdateDto("4321", "ONE", "updateImage");
        memberRepository.update(savedMember.getId(), memberUpdateDto);

        //then
        assertThat(savedMember.getPassword()).isEqualTo(memberUpdateDto.getPassword());
        assertThat(savedMember.getNickname()).isEqualTo(memberUpdateDto.getNickname());
        assertThat(savedMember.getImage()).isEqualTo(memberUpdateDto.getImage());
    }

    @Test
    @DisplayName("Member find Test")
    void findMember() {
        //given
        Member member = new Member("neo", "1234", "NEO", Grade.USER, "testImage");
        Member savedMember = memberRepository.save(member);

        Member memberB = new Member("reo", "1234", "REO", Grade.USER, "testImage");
        Member savedMemberB = memberRepository.save(memberB);

        //when
        Member findByIdMember = memberRepository.findById(savedMember.getId()).get();
        Member findByLoginIdMember = memberRepository.findByLoginId(savedMember.getLoginId()).get();
        List<Member> members = memberRepository.findAll();

        //then
        assertThat(findByIdMember.getId()).isEqualTo(member.getId());
        assertThat(findByIdMember.getLoginId()).isEqualTo(member.getLoginId());
        assertThat(findByIdMember.getPassword()).isEqualTo(member.getPassword());
        assertThat(findByIdMember.getNickname()).isEqualTo(member.getNickname());
        assertThat(findByIdMember.getGrade()).isEqualTo(member.getGrade());
        assertThat(findByIdMember.getImage()).isEqualTo(member.getImage());

        assertThat(findByLoginIdMember.getId()).isEqualTo(member.getId());
        assertThat(findByLoginIdMember.getLoginId()).isEqualTo(member.getLoginId());
        assertThat(findByLoginIdMember.getPassword()).isEqualTo(member.getPassword());
        assertThat(findByLoginIdMember.getNickname()).isEqualTo(member.getNickname());
        assertThat(findByLoginIdMember.getGrade()).isEqualTo(member.getGrade());
        assertThat(findByLoginIdMember.getImage()).isEqualTo(member.getImage());

        assertThat(members.size()).isEqualTo(2);

    }

    @Test
    @DisplayName("Member Delete Test")
    void delete() {
        //given
        Member member = new Member("neo", "1234", "NEO", Grade.USER, "testImage");
        Member savedMember = memberRepository.save(member);

        //when
        memberRepository.delete(savedMember.getId());

        //then
        assertThat(memberRepository.findById(savedMember.getId())).isEqualTo(Optional.empty());
    }

}