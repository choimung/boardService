package com.choimung.boardService.service;

import com.choimung.boardService.domain.member.Member;
import com.choimung.boardService.domain.post.Post;
import com.choimung.boardService.dto.PostUpdateDto;
import com.choimung.boardService.dto.PostsAddDto;
import com.choimung.boardService.repository.post.PostRepository;
import com.choimung.boardService.repository.post.PostSearchCond;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostRepository postRepository;
    private final FileService fileService;

    public Post save(PostsAddDto postsAddDto, Member member) throws IOException {

        Post post = new Post();
        post.setTitle(postsAddDto.getTitle());
        post.setAuthor(member.getNickname());
        post.setContent(postsAddDto.getContent());
        post.setImage(fileService.storeFile(postsAddDto.getImage()));

        return postRepository.save(post);
    }

    public List<Post> findAll(PostSearchCond postSearchCond){
        return postRepository.findAll(postSearchCond);
    }

    public void update(Long postId, PostUpdateDto postUpdateDto){
        postRepository.update(postId, postUpdateDto);
    }

    public void delete(Long postId){
        postRepository.delete(postId);
    }
    public Post findById(Long postId) {
        return postRepository.findById(postId).get();
    }
}
