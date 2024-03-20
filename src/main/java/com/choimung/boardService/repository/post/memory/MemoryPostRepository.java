package com.choimung.boardService.repository.post.memory;

import com.choimung.boardService.domain.post.Post;
import com.choimung.boardService.dto.PostUpdateDto;
import com.choimung.boardService.repository.post.PostRepository;
import com.choimung.boardService.repository.post.PostSearchCond;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
public class MemoryPostRepository implements PostRepository {

    private static Map<Long, Post> store = new HashMap<>();
    private static Long sequence = 0L;

    @Override
    public Post save(Post post) {
        post.setId(++sequence);
        post.setCreateDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")));
        store.put(post.getId(), post);
        return post;
    }

    @Override
    public Post update(Long postId, Post postUpdateDto) {
        Post post = findById(postId).get();
        post.setTitle(postUpdateDto.getTitle());
        post.setContent(postUpdateDto.getContent());
        post.setImage(postUpdateDto.getImage());
        post.setCreateDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")));
        return post;
    }

    @Override
    public Optional<Post> findById(Long postId) {
        return Optional.ofNullable(store.get(postId));
    }

    @Override
    public List<Post> findAll(PostSearchCond postSearchCond) {

        String title = postSearchCond.getTitle();

        if(!StringUtils.hasText(title)) {
            return new ArrayList<>(store.values());
        }

        return store.values()
                .stream()
                .filter(p -> p.getTitle().contains(title)).toList();

    }

    @Override
    public void delete(Long postId) {
        store.remove(postId);
    }
}
