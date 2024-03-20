package com.choimung.boardService.web.controller;

import com.choimung.boardService.domain.member.Member;
import com.choimung.boardService.domain.post.Post;
import com.choimung.boardService.dto.PostUpdateDto;
import com.choimung.boardService.dto.PostsAddDto;
import com.choimung.boardService.repository.post.PostSearchCond;
import com.choimung.boardService.service.FileService;
import com.choimung.boardService.service.MemberService;
import com.choimung.boardService.service.PostsService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/posts")
@Controller
public class PostsController {

    private final PostsService postsService;
    private final FileService fileService;
    private final MemberService memberService;

    @GetMapping
    public String posts(@SessionAttribute(value = "loginMember", required = false) Member member, Model model,
                        @ModelAttribute("search") PostSearchCond postSearchCond) {
        Member loginMember = memberService.findById(member.getId()).get();
        List<Post> posts = postsService.findAll(postSearchCond);
        model.addAttribute("loginMember", loginMember);
        model.addAttribute("posts", posts);
        return "posts/posts";
    }

    @GetMapping("/add")
    public String postAddForm(@SessionAttribute(value = "loginMember", required = false) Member member, Model model) {
        Member loginMember = memberService.findById(member.getId()).get();
        model.addAttribute("loginMember", loginMember);
        model.addAttribute("postAddDto", new PostsAddDto());
        return "posts/postsAddForm";
    }

    @PostMapping("/add")
    public String postAdd(@SessionAttribute(value = "loginMember") Member member, @ModelAttribute PostsAddDto postsAddDto)
            throws IOException {
        postsService.save(postsAddDto, member);
        return "redirect:/posts";
    }

    @GetMapping("/{postId}/edit")
    public String postEditForm(@SessionAttribute(value = "loginMember") Member member, @PathVariable Long postId, Model model) {
        Post post = postsService.findById(postId);
        model.addAttribute("loginMember", member);
        model.addAttribute("post", post);
        return "posts/postsEditForm";
    }

    @PostMapping("/{postId}/edit")
    public String postEdit(@SessionAttribute(value = "loginMember") Member member, @PathVariable Long postId, Model model,
                           @ModelAttribute PostUpdateDto postUpdateDto) throws IOException {
        postsService.update(postId, postUpdateDto);
        return "redirect:/posts/{postId}";
    }

    @GetMapping("/{postId}")
    public String post(@SessionAttribute(value = "loginMember") Member member, @PathVariable Long postId, Model model) {
        Post post = postsService.findById(postId);
        Long views = post.getViews();
        post.setViews(++views);
        model.addAttribute("loginMember", member);
        model.addAttribute("post", post);
        return "posts/post";
    }

    @GetMapping("/{postId}/delete")
    public String postDelete(@PathVariable Long postId) {
        postsService.delete(postId);
        return "redirect:/posts";
    }

    @GetMapping("/image/{fileName}")
    @ResponseBody
    public UrlResource showImage(@PathVariable String fileName) throws MalformedURLException {
        return new UrlResource("file:" + fileService.getPullPath(fileName));
    }
}
