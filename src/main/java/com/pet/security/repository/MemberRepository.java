package com.pet.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pet.security.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
    
    Member findByUserid(String userid);
    Member findByNickname(String nickname);
    Member findByEmail(String email);
    
    Member findPasswordByUserid(String userid);
    
    // 이미 존재하는 닉네임인지 확인하는 메서드
    boolean existsByNickname(String nickname);

    // 이미 존재하는 이메일인지 확인하는 메서드
    boolean existsByEmail(String email);
}
