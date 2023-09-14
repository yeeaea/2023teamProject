package com.pet.security.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.pet.security.domain.Member;
import com.pet.security.domain.Role;
import com.pet.security.repository.MemberRepository;

@Component
public class MyUserDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Autowired
    public MyUserDetailService(MemberRepository memberRepository) {

		this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userid) throws UsernameNotFoundException {
    	// 사용자 정보 조회
        Member member = memberRepository.findByUserid(userid);
        
		if (member == null) {
	        throw new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + userid);
	    } else {
	    	System.out.println("로그인 성공" + userid);
	    }
		
		// 권한 정보 설정
	    List<GrantedAuthority> authorities = new ArrayList<>();
	    if ("admin".equals(userid)) {
	        authorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
	    } else {
	        authorities.add(new SimpleGrantedAuthority(Role.USER.getValue()));
	    }

	    // UserDetails 객체 생성
	    UserDetails userDetails = User.builder()
	            .username(member.getUserid())
	            .password(member.getPassword()) // 패스워드는 암호화된 형태로 저장되어야 합니다.
	            .authorities(authorities)
	            .build();

	    return userDetails;
         
     }
}
