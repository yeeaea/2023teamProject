package com.pet.ques.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pet.ques.domain.QuesBoard;

public interface QuesBoardRepository extends JpaRepository<QuesBoard, Long>{

//	 @Query("SELECT qb FROM Ques_Board qb ORDER BY qb.ques_no DESC")
//	 List<QuesBoard> findAllDesc();
	
	Page<QuesBoard> findAll(Pageable pageable);
	
	Page<QuesBoard> findByquesTitleContaining(String keyword, Pageable pageable);
	 
	Page<QuesBoard> findAllByOrderByQuesVisitDesc(Pageable pageable);
	
    // 현재 글의 이전 글 가져오기
    @Query("SELECT q FROM QuesBoard q WHERE q.quesNo < :quesNo ORDER BY q.quesNo DESC")
    Optional<QuesBoard> findPreviousQuesNo(@Param("quesNo") Long quesNo);

    // 현재 글의 다음 글 가져오기
    @Query("SELECT q FROM QuesBoard q WHERE q.quesNo > :quesNo ORDER BY q.quesNo ASC")
    Optional<QuesBoard> findNextQuesNo(@Param("quesNo") Long quesNo);

}
