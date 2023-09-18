package com.pet.ques.domain;

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
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)

public class QuesComment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ques_cmt_no", nullable = false)
	private Long quesCmtNo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ques_no")
	private QuesBoard quesBoard;

	@Column(name = "ques_cmt_content")
	private String quesCmtContent;

	@CreatedDate
	@Column(name = "ques_cmt_rdate")
	private LocalDateTime quesCmtRdate;
	
	@LastModifiedDate
	@Column(name= "ques_cmt_udate")
	private LocalDateTime quesCmtUdate;
	
	private String userid;
	
	private String nickname;

	@Builder
	public QuesComment(String quesCmtContent, String userid, String nickname) {
		this.quesCmtContent = quesCmtContent;
		this.userid = userid;
		this.nickname = nickname;
	}

	public void update(String quesCmtContent) {
		this.quesCmtContent = quesCmtContent;
	}

}
