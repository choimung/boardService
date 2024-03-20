package com.choimung.boardService.web.controller;

import com.choimung.boardService.domain.member.Member;
import com.choimung.boardService.dto.MemberUpdateDto;
import com.choimung.boardService.service.FileService;
import com.choimung.boardService.service.MemberService;
import java.net.MalformedURLException;
import lombok.RequiredArgsConstructor;
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

@RequiredArgsConstructor
@Controller
public class UserController {

    private final MemberService memberService;
    private final FileService fileService;

    @GetMapping("/user/profile/{fileName}")
    @ResponseBody
    public UrlResource showProfileImage(@PathVariable String fileName) throws MalformedURLException {
        return new UrlResource("file:" + fileService.getPullPath(fileName));
    }

    @GetMapping("/user/edit")
    public String userUpdateForm(@SessionAttribute("loginMember") Member member, Model model) {
        model.addAttribute("member", member);
        return "login/memberEdit";
    }

    @PostMapping("/user/edit")
    public String userUpdate(@SessionAttribute("loginMember") Member member, @ModelAttribute MemberUpdateDto memberUpdateDto) {
        memberService.update(member.getId(), memberUpdateDto);
        return "redirect:/posts";
    }
}
