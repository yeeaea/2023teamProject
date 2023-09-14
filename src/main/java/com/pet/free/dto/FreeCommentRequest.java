package com.pet.free.dto;

import com.pet.free.domain.FreeComment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FreeCommentRequest {
	private Long freeNo;
	private String freeCmtContent;

	public FreeComment toEntity() {
		return FreeComment.builder()
				.freeCmtContent(freeCmtContent)
				.build();
	}

}
