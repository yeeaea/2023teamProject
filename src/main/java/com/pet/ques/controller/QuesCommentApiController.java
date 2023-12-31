package com.pet.ques.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pet.ques.domain.QuesComment;
import com.pet.ques.dto.QuesCommentRequest;
import com.pet.ques.dto.QuesCommentResponse;
import com.pet.ques.dto.UpdateQuesCommentRequest;
import com.pet.ques.service.QuesCommentService;
import com.pet.security.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController

public class QuesCommentApiController {
	
	private final MemberService memberService;
	private final QuesCommentService quesCommentService;

	// 댓글 등록
	@PostMapping("/api/quescomments")
	public ResponseEntity<QuesCommentResponse> addQuesComment(@RequestBody QuesCommentRequest dto, Principal principal) {
		if (principal != null) {
			String userid = principal.getName();
			String nickname = memberService.getNickname(userid);
			
			dto.setUserid(userid);
			dto.setNickname(nickname);
		
			QuesComment savedQuesComment = quesCommentService.saveComment(dto, dto.getQuesNo());

			return ResponseEntity.status(HttpStatus.CREATED).body(new QuesCommentResponse(savedQuesComment));
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
	}

	// 댓글 리스트
	@GetMapping("/api/quescomments")
	public ResponseEntity<List<QuesCommentResponse>> findAllQuesComments() {
		List<QuesCommentResponse> quescomments = quesCommentService.findAll().stream().map(QuesCommentResponse::new)
				.toList();

		return ResponseEntity.ok().body(quescomments);
	}

	// 댓글 가져오기
	@GetMapping("/api/quescomments/{quesCmtNo}")
	public ResponseEntity<QuesCommentResponse> QuesComment(@PathVariable Long quesCmtNo) {
		QuesComment quesComment = quesCommentService.findByQuesCmtNo(quesCmtNo);

		return ResponseEntity.ok().body(new QuesCommentResponse(quesComment));
	}

	// 댓글 삭제
	@DeleteMapping("/api/quescomments/{quesCmtNo}")
	public ResponseEntity<Void> deleteQuesComment(@PathVariable Long quesCmtNo) {
		quesCommentService.delete(quesCmtNo);

		return ResponseEntity.ok().build();
	}

	// 댓글 수정
	@PutMapping("/api/quescomments/{quesCmtNo}")
	public ResponseEntity<QuesComment> updateQuesComment(@PathVariable Long quesCmtNo,
			@RequestBody UpdateQuesCommentRequest dto) {
		QuesComment updatedQuesComment = quesCommentService.update(quesCmtNo, dto);

		return ResponseEntity.ok().body(updatedQuesComment);
	}

}