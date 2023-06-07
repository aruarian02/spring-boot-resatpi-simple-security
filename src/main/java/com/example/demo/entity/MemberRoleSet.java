package com.example.demo.entity;

import lombok.*;

import java.util.Set;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberRoleSet {
    private String memberEmail;
    private MemberRole roleSet;
}
