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
}
