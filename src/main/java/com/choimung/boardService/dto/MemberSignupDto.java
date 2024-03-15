package com.choimung.boardService.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class MemberSignupDto {

    @NotBlank
    private String loginId;
    @NotBlank
    private String password;
    @NotBlank
    private String nickName;

    private MultipartFile image;

}
