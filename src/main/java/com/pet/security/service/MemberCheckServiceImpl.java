package com.pet.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pet.security.domain.Member;
import com.pet.security.repository.MemberRepository;

@Service
public class MemberCheckServiceImpl implements MemberCheckService{

	private MemberRepository memberRepository;
	
	@Autowired
    public MemberCheckServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public boolean isUseridUnique(String userid) {
        Member existingMember = memberRepository.findByUserid(userid);
        
        return existingMember == null;
    }

    @Override
    public boolean isNicknameUnique(String nickname) {
    	Member existingMember = memberRepository.findByNickname(nickname);
        
        return existingMember == null;
    }

    @Override
    public boolean isEmailUnique(String email) {
    	Member existingMember = memberRepository.findByEmail(email);
        
        return existingMember == null;
    }

    @Override
    public boolean isPasswordValid(String password, String confirmPassword) {
        // 비밀번호와 확인 비밀번호가 일치하는지 확인하는 로직
        return password.equals(confirmPassword);
    }
}


