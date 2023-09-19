package com.pet.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pet.security.domain.Member;
import com.pet.security.repository.MemberRepository;

@Service
public class MemberService {
	private final PasswordEncoder passwordEncoder;
    private final MemberRepository repository;

    @Autowired
    public MemberService(MemberRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public Member findByUserid(String userid) {
        return repository.findByUserid(userid);
    }
    
    public String getNickname(String userid) {
    	// 사용자 아이디로 사용자 정보 조회
    	Member member = repository.findByUserid(userid);
    	
    	if (member != null) {
    		// 사용자 정보에서 닉네임 가져오기
    		return member.getNickname();
    	}
    	// 사용자 정보가 없을 경우
        return "사용자 닉네임 없음";
    }
    
    @Transactional
    public void updateMember(Member member) {
    	Member persistance = repository.findByUserid(member.getUserid());
    	
    	String rawPassword = member.getPassword();
    	String encPassword = passwordEncoder.encode(rawPassword);
    	
    	persistance.setPassword(encPassword);
    	persistance.setNickname(member.getNickname());
    	persistance.setEmail(member.getEmail());
    }
    
    public void deleteMember(String userid) {
    	Member member = repository.findByUserid(userid);
    	if (member != null) {
    		repository.delete(member);
    	}
    }
    
    public String getPasswordByUserid(String userid) {
    	Member member = repository.findPasswordByUserid(userid);
    	
    	 return member != null ? member.getPassword() : null;
    }
    
    
}
