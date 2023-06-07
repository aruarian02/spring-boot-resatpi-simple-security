package com.example.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "로그인 DTO")
public class MemberLoginDTO {
    @Schema(description = "로그인 이메일")
    @NotBlank
    @Email(message = "잘못된 이메일 형식입니다.")
    private String email;

    @Schema(description = "로그인 비밀번호")
    @Size(min = 6, message = "비밀번호는 6자리 이상입니다.")
    @NotBlank
    private String password;
}
