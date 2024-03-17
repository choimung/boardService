package com.choimung.boardService.web.controller;

import com.choimung.boardService.domain.member.Member;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@RequestMapping("/posts")
@Controller
public class PostsController {
    @GetMapping
    public String posts(@SessionAttribute(value = "loginMember", required = false) Member member, Model model) {

        model.addAttribute("loginMember", member);

        return "posts/posts";
    }
}
