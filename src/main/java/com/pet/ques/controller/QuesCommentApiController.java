package com.pet.ques.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pet.ques.domain.QuesComment;
import com.pet.ques.dto.QuesBoardResponse;
import com.pet.ques.dto.QuesCommentRequest;
import com.pet.ques.dto.QuesCommentResponse;
import com.pet.ques.service.QuesCommentService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController

public class QuesCommentApiController {

	private final QuesCommentService quesCommentService;

	@PostMapping("/api/quescomments")
	public ResponseEntity<QuesComment> addQuesComment(@RequestBody QuesCommentRequest dto) {
	    //Long quesNo = dto.setQuesNo(quesNo);

	    QuesComment savedQuesComment = quesCommentService.save(dto);

	    return ResponseEntity.status(HttpStatus.CREATED)
	        .body(savedQuesComment);
	}

	
	@GetMapping("/api/quescomments")
	public ResponseEntity<List<QuesCommentResponse>> findAllComments(){
		List<QuesBoardResponse> comments = quesCommentService.findAll()
				.stream()
				.map(QuesBoardResponse::new)
				.toList();  // 최종 리턴값을 List로 바꿔라
		return ResponseEntity.ok().body(comments);
	}
	
	
}
