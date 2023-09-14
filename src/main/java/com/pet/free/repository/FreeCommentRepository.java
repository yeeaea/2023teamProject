package com.pet.free.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pet.free.domain.FreeComment;

public interface FreeCommentRepository extends JpaRepository<FreeComment, Long> {

	List<FreeComment> findByFreeBoard_FreeNo(Long freeNo);

}