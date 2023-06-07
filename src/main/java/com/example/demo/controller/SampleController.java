package com.example.demo.controller;

import com.example.demo.dto.MemberLoginDTO;
import com.example.demo.security.dto.AuthMemberDTO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Log4j2
@RequestMapping("/sample")
public class SampleController {

    private final AuthenticationManager authenticationManager;

    @Autowired
    public SampleController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    // 로그인하지 않아도 접근 가능
    @GetMapping("/all")
    public void exAll() {
        log.info("exAll..........");
    }

    @GetMapping("/auth")
    @Secured({"isAuthenticated()"})
    public void exAuth(@AuthenticationPrincipal AuthMemberDTO authMemberDTO) {
        log.info("exAuth..........");
        log.info("--------------------------------");

        log.info(authMemberDTO);
    }

    // MANAGER 권한 사용자만 접근 가능
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @GetMapping("/member")
    public String exMember() {
        log.info("exMember.........");

        log.info("--------------------------------");

        return "hello";
    }

    // 유효성 체크
    @Operation(summary = "login", description = "로그인")
    @PostMapping("/login")
    public void exLogin(@Valid @RequestBody MemberLoginDTO memberLoginDTO) {
        // email, password 인증을 수동으로
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        memberLoginDTO.getEmail(),
                        memberLoginDTO.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        System.out.println(memberLoginDTO);
    }

    // 관리자 권한이 있는 사용자만 접근 가능
    @Secured({"ROLE_ADMIN"})
    @GetMapping("/admin")
    public void exAdmin() {
        log.info("exAdmin..........");
    }

    @GetMapping("/denied")
    public void exDenied() {
        log.info("exDenied.........");
    }
}
