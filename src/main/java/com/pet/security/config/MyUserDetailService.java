package com.pet.security.config;

import com.pet.security.domain.Member;
import com.pet.security.service.MemberService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MyUserDetailService implements UserDetailsService {
    private final MemberService memberService;

    @Autowired
    public MyUserDetailService(MemberService memberService) {
        this.memberService = memberService;
    }

    @Override
    public UserDetails loadUserByUsername(String insertedUserid) throws UsernameNotFoundException {
        Member member = memberService.findOne(insertedUserid);

        if (member == null) {
            throw new UsernameNotFoundException("존재하지 않는 회원입니다.");
        }

        return User.builder()
                .username(member.getUserid())
                .password(member.getPassword())
                .roles(member.getRole())
                .build();
    }
}
