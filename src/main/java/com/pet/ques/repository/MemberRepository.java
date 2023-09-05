package com.pet.ques.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pet.ques.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
