package com.pet.free.domain;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name="free_comment")
@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FreeComment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "free_cmt_no", nullable = false)
	private Long freeCmtNo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "free_no")
	private FreeBoard freeBoard;

	@Column(name = "free_cmt_content")
	private String freeCmtContent;

	@CreatedDate
	@Column(name = "free_cmt_rdate")
	private LocalDateTime freeCmtRdate;

	@LastModifiedDate
	@Column(name = "free_cmt_udate")
	private LocalDateTime freeCmtUdate;
	
	private String userid;
	
	private String nickname;

	@Builder
	public FreeComment(String freeCmtContent, String userid, String nickname) {
		this.freeCmtContent = freeCmtContent;
		this.userid = userid;
		this.nickname = nickname;
	}

	public void update(String freeCmtContent) {
		this.freeCmtContent = freeCmtContent;
	}

}
