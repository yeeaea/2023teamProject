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
	private String freeCmtContent;
	private String userid;
	private String nickname;

	public FreeComment toEntity() {
		return FreeComment.builder()
				.freeCmtContent(freeCmtContent)
				.userid(userid)
				.nickname(nickname)
				.build();
	}

}
