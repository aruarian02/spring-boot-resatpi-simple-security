package com.example.demo.dto;

import com.example.demo.entity.Member;
import com.example.demo.entity.MemberRole;
import com.example.demo.entity.MemberRoleSet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberRoleDTO {
    private String email;
    private String password;
    private String name;
    private boolean fromSocial;
    private int roleSet;
}
