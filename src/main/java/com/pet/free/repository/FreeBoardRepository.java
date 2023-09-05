package com.pet.free.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pet.free.domain.FreeBoard;

public interface FreeBoardRepository extends JpaRepository<FreeBoard, Long>{

	Page<FreeBoard> findAll(Pageable pageable);
	
	Page<FreeBoard> findByfreeTitleContaining(String keyword, Pageable pageable);
}
