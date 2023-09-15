package com.pet.map.dto;

import java.time.LocalDateTime;

import com.pet.map.domain.PlaceReview;

import lombok.Getter;

@Getter
public class ReviewResponse {

	private String reviewContent;
	private LocalDateTime reviewRdate;
	
	public ReviewResponse(PlaceReview placeReview) {
		this.reviewContent = placeReview.getReviewContent();
		this.reviewRdate = placeReview.getReviewRdate();
	}
	
}
