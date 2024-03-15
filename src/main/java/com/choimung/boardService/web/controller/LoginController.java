package com.choimung.boardService.web.controller;

import com.choimung.boardService.dto.MemberLoginDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/user")
@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("loginDto", new MemberLoginDto());
        return "login/loginForm";
    }

}
