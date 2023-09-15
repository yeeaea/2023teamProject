package com.pet.ques.domain;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "ques_board")
@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuesBoard {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ques_no", nullable = false)
	private Long quesNo;

	@Column(name = "ques_title", nullable = false)
	private String quesTitle;

	@Column(name = "ques_content", nullable = false)
	private String quesContent;

	@CreatedDate
	@Column(name = "ques_rdate")
	private LocalDateTime quesRdate;

	@LastModifiedDate
	@Column(name = "ques_udate")
	private LocalDateTime quesUdate;

	@Column(name = "ques_filename")
	private String quesFilename;

	@Column(name = "ques_filepath")
	private String quesFilepath;

	@Column(name = "ques_visit")
	private int quesVisit;

	@OneToMany(mappedBy = "quesBoard", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<QuesComment> comments;
	
	private String userid;
	
	private String nickname;
	
	@Builder
	public QuesBoard(String quesTitle, String quesContent, String quesFilename, String quesFilepath, String userid, String nickname) {
		this.quesTitle = quesTitle;
		this.quesContent = quesContent;
		this.quesFilename = quesFilename;
		this.quesFilepath = quesFilepath;
		this.userid = userid;
		this.nickname = nickname;
	}

	// 수정 메서드
	public void update(String quesTitle, String quesContent) {
		this.quesTitle = quesTitle;
		this.quesContent = quesContent;
	}

}
