package com.choimung.boardService.repository.post;


import com.choimung.boardService.domain.post.Post;
import com.choimung.boardService.dto.PostUpdateDto;
import java.util.List;
import java.util.Optional;

public interface PostRepository {
    Post save(Post post);

    Post update(Long postId, Post post);

    List<Post> findAll(PostSearchCond postSearchCond);

    Optional<Post> findById(Long postId);

    void delete(Long postId);


}
