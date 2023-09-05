package com.pet.ques.domain;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Member {
	
	@SequenceGenerator(
			name = "MEMBER_SEQUENCE_GEN",	// 프로그램에서 사용하는 이름
			sequenceName = "Member_seq",	// sql에서 사용하는 이름
			initialValue = 1,
			allocationSize = 1)
	@Id
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "MEMBER_SEQUENCE_GEN")
	@Column(name = "mem_no", updatable = false)
	private Long memNo;
	
	@Column(name = "mem_id", nullable = false)
	private String memId;
	
	@Column(name = "mem_pw", nullable = false)
	private String memPw;
	
	@Column(name = "mem_name", nullable = false)
	private String memName;
	
//	@Column(name = "mem_birth", nullable = false)
//	private Date memBirth;
	
	@Column(name = "mem_nick", nullable = false)
	private String memNick;
	
//	@Column(name = "mem_addr", nullable = false)
//	private String memAddr;
	
	@Column(name = "mem_email", nullable = false)
	private String memEmail;
	
//	@Column(name = "mem_sex", nullable = false)
//	private String memSex;
	
//	@Column(name = "mem_tel", nullable = false)
//	private String memTel;

//	@Builder
//	public Member(String memId, String memPw, String memName, Date memBirth, String memNick, String memAddr,
//			String memEmail, String memSex, String memTel) {
//		this.memId = memId;
//		this.memPw = memPw;
//		this.memName = memName;
//		this.memBirth = memBirth;
//		this.memNick = memNick;
//		this.memAddr = memAddr;
//		this.memEmail = memEmail;
//		this.memSex = memSex;
//		this.memTel = memTel;
//	}

	
	@Builder
	public Member(String memId, String memPw, String memName, String memNick, String memEmail) {
		this.memId = memId;
		this.memPw = memPw;
		this.memName = memName;
		this.memNick = memNick;
		this.memEmail = memEmail;

	}
	
	
	
}
