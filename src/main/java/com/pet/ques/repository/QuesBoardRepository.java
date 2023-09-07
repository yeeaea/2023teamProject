package com.pet.ques.repository;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pet.ques.domain.QuesBoard;

public interface QuesBoardRepository extends JpaRepository<QuesBoard, Long>{

//	 @Query("SELECT qb FROM Ques_Board qb ORDER BY qb.ques_no DESC")
//	 List<QuesBoard> findAllDesc();
	
	Page<QuesBoard> findAll(Pageable pageable);
	
	Page<QuesBoard> findByquesTitleContaining(String keyword, Pageable pageable);

}
