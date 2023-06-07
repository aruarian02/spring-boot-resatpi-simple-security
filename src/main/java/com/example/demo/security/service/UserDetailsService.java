package com.example.demo.security.service;

import com.example.demo.dto.MemberDTO;
import com.example.demo.dto.MemberRoleDTO;
import com.example.demo.entity.MemberRole;
import com.example.demo.mapper.MemberMapper;
import com.example.demo.security.dto.AuthMemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Log4j2
@Service
@RequiredArgsConstructor
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final MemberMapper mapper;

    private MemberDTO setMemberRole(List<MemberRoleDTO> m) {
        return MemberDTO.builder()
                .email(m.get(0).getEmail())
                .password(m.get(0).getPassword())
                .name(m.get(0).getName())
                .fromSocial(m.get(0).isFromSocial())
                .roleSet(m.stream().map(i -> MemberRole.values()[i.getRoleSet()]).collect(Collectors.toSet()))
                .build();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username.isBlank()) {
            throw new UsernameNotFoundException("빈 칸과 공백은 입력할 수 없습니다.");
        }

        log.info("UserDetailsService loadUserByUsername " + username);

        List<MemberRoleDTO> memberList = mapper.findByEmail(username, false);

        if (memberList.isEmpty()) {
            throw new UsernameNotFoundException("해당하는 유저가 없습니다.");
        }

        MemberDTO result = setMemberRole(memberList);

        log.info("------------------------------");
        log.info(result);

        AuthMemberDTO authMember = new AuthMemberDTO(
                result.getEmail(),
                result.getPassword(),
                result.isFromSocial(),
                result.getRoleSet().stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name())).collect(Collectors.toSet())
        );
        authMember.setName(result.getName());
        authMember.setFromSocial(result.isFromSocial());

        return authMember;
    }
}
