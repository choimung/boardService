package com.choimung.boardService.web.controller;

import com.choimung.boardService.domain.member.Grade;
import com.choimung.boardService.domain.member.Member;
import com.choimung.boardService.dto.MemberLoginDto;
import com.choimung.boardService.dto.MemberSignupDto;
import com.choimung.boardService.service.FileService;
import com.choimung.boardService.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 로그인부터 회원가입까지 담당하는 컨트롤러입니다.
 */

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/user")
@Controller
public class LoginController {

    private final MemberService memberService;
    private final FileService fileService;

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("loginDto", new MemberLoginDto());
        return "login/loginForm";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("loginDto") MemberLoginDto memberLoginDto, BindingResult bindingResult, HttpServletRequest request) {

        Member loginMember = memberService.login(memberLoginDto.getLoginId(), memberLoginDto.getPassword());

        if (loginMember == null) {
            bindingResult.reject("loginFail", "ID 또는 PW가 일치하지 않습니다.");
        }

        if (bindingResult.hasErrors()) {
            log.info("error = {}", bindingResult);
            return "login/loginForm";
        }
        HttpSession session = request.getSession(true);
        session.setAttribute("loginMember", loginMember);

        return "redirect:/posts";

    }

    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("signupDto", new MemberSignupDto());
        return "login/memberSignup";
    }

    @PostMapping("/signup")
    public String signup(@Validated @ModelAttribute("signupDto") MemberSignupDto memberSignupDto,
                         BindingResult bindingResult) throws IOException {

        if (bindingResult.hasErrors()) {
            log.info("error = {}", bindingResult);
            return "login/memberSignup";
        }

        Member member = new Member();
        member.setLoginId(memberSignupDto.getLoginId());
        member.setPassword(memberSignupDto.getPassword());
        member.setNickname(memberSignupDto.getNickName());
        member.setGrade(Grade.USER);
        member.setImage(fileService.storeFile(memberSignupDto.getImage()));

        memberService.join(member);
        return "redirect:/";
    }


}
