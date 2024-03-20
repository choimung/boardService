package com.choimung.boardService.config;

import com.choimung.boardService.repository.member.MemberRepository;
import com.choimung.boardService.repository.member.memory.MemoryMemberRepository;
import com.choimung.boardService.repository.post.PostRepository;
import com.choimung.boardService.repository.post.memory.MemoryPostRepository;
import com.choimung.boardService.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class MemoryConfig {

    private final FileService fileService;

    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository(fileService);
    }

    @Bean
    public PostRepository postRepository() {
        return new MemoryPostRepository(fileService);
    }
}
