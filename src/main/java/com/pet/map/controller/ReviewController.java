package com.pet.map.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.pet.map.domain.PlaceReview;
import com.pet.map.dto.ReviewRequest;
import com.pet.map.dto.ReviewResponse;
import com.pet.map.service.ReviewService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class ReviewController {

    private final ReviewService reviewService;

    // 리뷰 추가 엔드포인트
    @PostMapping("/api/reviews")
    public ResponseEntity<ReviewResponse> addReview(@RequestBody ReviewRequest dto) {
        // no를 이용하여 리뷰가 속한 병원을 찾아서 저장
        PlaceReview savedReview = reviewService.saveReview(dto, dto.getNo());

        // 새로운 리뷰를 저장하고, 그 리뷰의 응답을 반환
        return ResponseEntity.status(HttpStatus.CREATED).body(new ReviewResponse(savedReview));
    }

    // 모든 리뷰 조회 엔드포인트
    @GetMapping("/api/reviews")
    public ResponseEntity<List<ReviewResponse>> findAllReviews() {
        List<ReviewResponse> reviews = reviewService.findAll().stream().map(ReviewResponse::new).toList();

        return ResponseEntity.ok().body(reviews);
    }

    // 특정 리뷰 조회 엔드포인트
    @GetMapping("/api/reviews/{reviewNo}")
    public ResponseEntity<ReviewResponse> PlaceReview(@PathVariable long reviewNo) {
        PlaceReview placeReview = reviewService.findByReviewNo(reviewNo);

        return ResponseEntity.ok().body(new ReviewResponse(placeReview));
    }

    // 리뷰 삭제 엔드포인트
    @DeleteMapping("/api/reviews/{reviewNo}")
    public ResponseEntity<Void> deleteReview(@PathVariable long reviewNo) {
        reviewService.delete(reviewNo);

        return ResponseEntity.ok().build();
    }

    // 리뷰 수정 엔드포인트 (주석 처리된 코드는 사용되지 않는 부분입니다)
    // @PutMapping("/api/reviews/{reviewNo}")
    // public ResponseEntity<PlaceReview> updateReview(@PathVariable long reviewNo,
    // @RequestBody UpdateReviewRequest request) {
    // PlaceReview updateReview = reviewService.update(reviewNo, request);
    //
    // return ResponseEntity.ok().body(updateReview);
    // }
}
