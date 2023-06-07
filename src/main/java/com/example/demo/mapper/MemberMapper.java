package com.example.demo.mapper;

import com.example.demo.dto.MemberRoleDTO;
import com.example.demo.entity.Member;
import com.example.demo.entity.MemberRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface MemberMapper {
    List<Member> getMembers();
    Member getMemberByEmail(@Param("email") String email);
    void insertMember(@Param("member") Member member);
    void insertMemberRole(@Param("email") String email, @Param("role") int role);
    List<MemberRoleDTO> findByEmail(@Param("email") String email, @Param("social") boolean social);
}
