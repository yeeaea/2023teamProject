package com.pet.map.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.pet.map.domain.Place;
import com.pet.map.domain.PlaceReview;
import com.pet.map.dto.ReviewRequest;
// import com.pet.map.dto.UpdateReviewRequest;
import com.pet.map.repository.PlaceRepository;
import com.pet.map.repository.ReviewRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ReviewService {

	private final ReviewRepository reviewRepo;
	private final PlaceRepository placeRepo;
	
	// 리뷰 등록
	public PlaceReview saveReview(ReviewRequest reviewRequest, Long no) {
		// no에 해당하는 병원정보에 리뷰를 저장하는 로직 구현
		Place place = placeRepo.findById(no)
				.orElseThrow(() -> new RuntimeException("해당 병원을 찾을 수 없음"));
		PlaceReview review = reviewRequest.toEntity();
		review.setPlace(place);
		return reviewRepo.save(review);
	}
	
	// 리뷰 전체 가져오기
	public List<PlaceReview> findAll(){
		return reviewRepo.findAll();
	}
	
	// 리뷰 가져오기
	public PlaceReview findByReviewNo(long reviewNo) {
		return reviewRepo.findById(reviewNo)
				.orElseThrow(()->
					new IllegalArgumentException("not found : " + reviewNo));
	}
	
	// 리뷰 삭제하기
	public void delete(long reviewNo) {
		reviewRepo.deleteById(reviewNo);
	}
	
	// 해당 병원에 리뷰 목록 보여주기
	public List<PlaceReview> getReviewsByNo(Long no){
		return reviewRepo.findByPlace_No(no);
	}
	
//	// 리뷰 수정
//	@Transactional
//	public PlaceReview update(long no, UpdateReviewRequest request) {
//		PlaceReview placeReview = reviewRepo.findById(no)
//				.orElseThrow(()->
//					new IllegalArgumentException("수정할 리뷰가 없습니다."));
//		
//		placeReview.setReviewRdate(LocalDateTime.now());
//		placeReview.update(request.getReviewContent());
//		
//		return placeReview;
//	}
	
}
