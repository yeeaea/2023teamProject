package com.pet.map.dto;

import com.pet.map.domain.PlaceReview;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReviewRequest {

	private Long no;
	private String reviewContent;
	
	public PlaceReview toEntity() {
		return PlaceReview.builder()
				.reviewContent(reviewContent)
				.build();
	}
}
