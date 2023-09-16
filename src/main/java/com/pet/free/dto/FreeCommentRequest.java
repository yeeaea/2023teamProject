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
	private String cmtUserid;
	private String cmtNickname;

	public FreeComment toEntity() {
		return FreeComment.builder()
				.freeCmtContent(freeCmtContent)
				.cmtUserid(cmtUserid)
				.cmtNickname(cmtNickname)
				.build();
	}

}
