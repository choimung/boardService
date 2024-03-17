package com.choimung.boardService.web.controller;

import com.choimung.boardService.service.FileService;
import java.net.MalformedURLException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final FileService fileService;

    @GetMapping("/user/profile/{fileName}")
    @ResponseBody
    public UrlResource showProfileImage(@PathVariable String fileName) throws MalformedURLException {
        return new UrlResource("file:" + fileService.getPullPath(fileName));
    }
}
