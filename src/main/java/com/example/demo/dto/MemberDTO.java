package com.example.demo.dto;

import com.example.demo.entity.Member;
import com.example.demo.entity.MemberRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDTO {
    private String email;
    private String password;
    private String name;
    private boolean fromSocial;
    private Set<MemberRole> roleSet = new HashSet<>();

    public Member dtoToEntity() {
        return Member.builder()
                .email(email)
                .password(password)
                .name(name)
                .fromSocial(fromSocial)
                .build();
    }
}
