package com.example.demo.entity;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Member {
    private String email;
    private String password;
    private String name;
    private boolean fromSocial;
}
