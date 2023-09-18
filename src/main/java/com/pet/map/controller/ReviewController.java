package com.pet.map.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pet.map.domain.PlaceReview;
import com.pet.map.dto.ReviewRequest;
import com.pet.map.dto.ReviewResponse;
import com.pet.map.service.ReviewService;
import com.pet.security.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class ReviewController {

	private final MemberService memberService;
    private final ReviewService reviewService;

    // 리뷰 추가 엔드포인트
    @PostMapping("/api/reviews")
    public ResponseEntity<ReviewResponse> addReview(@RequestBody ReviewRequest dto, Principal principal) throws Exception {
       
        if (principal != null) {
            // 현재 로그인한 사용자의 아이디
            String userid = principal.getName();
            // 현재 로그인한 사용자의 닉네임
            String nickname = memberService.getNickname(userid);

            dto.setUserid(userid);
            dto.setNickname(nickname);

            // 여기서 no를 이용하여 리뷰가 속한 병원을 찾아서 저장하는 작업을 수행
            // 이 작업을 reviewService를 사용하여 처리한다고 가정합니다.
            // reviewService의 saveReview 메서드를 사용하여 리뷰를 저장하고
            // 리뷰가 속한 병원을 찾아서 dto에 저장합니다.
            // 이 작업은 리뷰를 저장하는 비즈니스 로직에 따라 구현해야 합니다.
            PlaceReview savedReview = reviewService.saveReview(dto, dto.getNo());

            // 새로운 리뷰를 저장하고, 그 리뷰의 응답을 반환
            return ResponseEntity.status(HttpStatus.CREATED).body(new ReviewResponse(savedReview));
        } else {
            // 로그인하지 않은 사용자에 대한 처리 (예: 권한 없음 또는 로그인 필요)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
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

}
