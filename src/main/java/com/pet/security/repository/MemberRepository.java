package com.pet.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pet.security.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
    
    Member findByUserid(String userid);
    Member findByNickname(String nickname);
    Member findByEmail(String email);
}
