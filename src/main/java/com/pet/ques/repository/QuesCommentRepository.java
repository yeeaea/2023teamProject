package com.pet.ques.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pet.ques.domain.QuesComment;

public interface QuesCommentRepository extends JpaRepository<QuesComment, Long> {

	List<QuesComment> findByQuesBoard_QuesNo(Long quesNo);

	List<QuesComment> findByUserid(String userid);
} 