package com.pet.ques.dto;

import com.pet.ques.domain.Member;

public class MemberSaveRequestDto {
	private String memId;
	private String memPw;
	private String memEmail;
	private String memNick;
	
	public Member toEntity() {
		return Member.builder()
				.memId(memId)
				.memPw(memPw)
				.memEmail(memEmail)
				.build();
	}
}
