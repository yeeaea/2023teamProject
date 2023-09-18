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
	private String userid;
	private String nickname;
	private float reviewScore;
	private String star;
	
	public PlaceReview toEntity() {
		return PlaceReview.builder()
				.reviewContent(reviewContent)
				.userid(userid)
				.nickname(nickname)
				.reviewScore(reviewScore)
				.star(star)
				.build();
	}
}
