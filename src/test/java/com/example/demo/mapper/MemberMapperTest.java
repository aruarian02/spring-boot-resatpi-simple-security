package com.example.demo.mapper;

import com.example.demo.dto.MemberDTO;
import com.example.demo.dto.MemberRoleDTO;
import com.example.demo.entity.Member;
import com.example.demo.entity.MemberRole;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberMapperTest {

    @Autowired
    private MemberMapper mapper;

    @Autowired
    private PasswordEncoder encoder;

    @Test
    public void getMembers() {
        List<Member> members = mapper.getMembers();
        for (Member member : members) {
            System.out.println(member);
        }
    }

    @Test
    public void findByEmail() {
        List<MemberRoleDTO> members = mapper.findByEmail("user95@naver.com", false);

        // roleSet 처리
        MemberDTO dto = MemberDTO.builder()
                .email(members.get(0).getEmail())
                .password(members.get(0).getPassword())
                .name(members.get(0).getName())
                .fromSocial(members.get(0).isFromSocial())
                .roleSet(members.stream().map(i -> MemberRole.values()[i.getRoleSet()]).collect(Collectors.toSet()))
                .build();

        System.out.println(dto);
    }

    @Test
    public void insertDummies() {
        // 1~80 까지는 User만 저장
        // 81~90 까지는 User, Manager
        // 91~100 까지는 User, Manager, Admin

        IntStream.rangeClosed(1, 100).forEach(i -> {
            MemberDTO dto = MemberDTO.builder()
                    .email("user" + i + "@naver.com")
                    .password(encoder.encode("123123"))
                    .name("사용자" + i)
                    .fromSocial(false)
                    .build();

            Member member = dto.dtoToEntity();

            mapper.insertMember(member);

            mapper.insertMemberRole(member.getEmail(), MemberRole.USER.ordinal());

            if (i > 80) {
                mapper.insertMemberRole(member.getEmail(), MemberRole.MANAGER.ordinal());
            }

            if (i > 90) {
                mapper.insertMemberRole(member.getEmail(), MemberRole.ADMIN.ordinal());
            }
        });
    }
}