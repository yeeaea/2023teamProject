package com.pet.security.service;

import com.pet.security.domain.Member;
import com.pet.security.repository.MemberRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private final MemberRepository repository;

    @Autowired
    public MemberService(MemberRepository repository) {
        this.repository = repository;
    }

    public Member findOne(String userid) {
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
}
