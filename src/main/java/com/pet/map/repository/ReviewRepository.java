package com.pet.map.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pet.map.domain.PlaceReview;

public interface ReviewRepository extends JpaRepository<PlaceReview, Long>{

	List<PlaceReview> findByPlace_No(Long no);
}
