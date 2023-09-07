package com.pet.ques.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.pet.ques.domain.QuesComment;

public interface QuesCommentRepository extends JpaRepository<QuesComment, Long> {

}