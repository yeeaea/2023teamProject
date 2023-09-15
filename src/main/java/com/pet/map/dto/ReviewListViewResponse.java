package com.pet.map.dto;

import java.time.LocalDateTime;

import com.pet.map.domain.PlaceReview;

import lombok.Getter;

@Getter
public class ReviewListViewResponse {

	private final Long reviewNo;
	private final Long no;
	private final String reviewContent;
	private final LocalDateTime reviewRdate;
	
	public ReviewListViewResponse(PlaceReview placeReview) {
		this.no = placeReview.getPlace().getNo();
		this.reviewNo = placeReview.getReviewNo();
		this.reviewContent = placeReview.getReviewContent();
		this.reviewRdate = placeReview.getReviewRdate();
	}
}
