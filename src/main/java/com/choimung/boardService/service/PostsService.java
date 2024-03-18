package com.choimung.boardService.service;

import com.choimung.boardService.domain.post.Post;
import com.choimung.boardService.dto.PostUpdateDto;
import com.choimung.boardService.repository.post.PostRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostRepository postRepository;


    public Post save(Post post) {
        return postRepository.save(post);
    }

    public List<Post> findAll(){
        return postRepository.findAll();
    }

    public void update(Long postId, Post postUpdateDto){
        postRepository.update(postId, postUpdateDto);
    }

    public void delete(Long postId){
        postRepository.delete(postId);
    }
    public Post findById(Long postId) {
        return postRepository.findById(postId).get();
    }
}
