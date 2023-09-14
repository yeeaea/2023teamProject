package com.pet.free.dto;

import com.pet.free.domain.FreeBoard;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FreeBoardRequest {
	private String freeTitle;
	private String freeContent;
	private String freeFilename;
	private String freeFilepath;
	private String userid;
	private String nickname;
	
	public FreeBoard toEntity() {
		return FreeBoard.builder()
				.freeTitle(freeTitle)
				.freeContent(freeContent)
				.freeFilename(freeFilename)
				.freeFilepath(freeFilepath)
				.userid(userid)
				.nickname(nickname)
				.build();
	}
}
