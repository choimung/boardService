package com.choimung.boardService.repository.post.memory;

import com.choimung.boardService.domain.post.Post;
import com.choimung.boardService.dto.PostUpdateDto;
import com.choimung.boardService.repository.post.PostRepository;
import com.choimung.boardService.repository.post.PostSearchCond;
import com.choimung.boardService.service.FileService;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
@Repository
public class MemoryPostRepository implements PostRepository {

    private final FileService fileService;

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
    public void update(Long postId, PostUpdateDto postUpdateDto) {
        Post findPost = findById(postId).get();

        findPost.setTitle(postUpdateDto.getTitle());
        findPost.setContent(postUpdateDto.getContent());
        findPost.setCreateDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")));

        try {
            findPost.setImage(fileService.storeFile(postUpdateDto.getImage()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
