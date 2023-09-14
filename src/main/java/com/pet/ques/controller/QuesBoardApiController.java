package com.pet.ques.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pet.ques.domain.QuesBoard;
import com.pet.ques.dto.QuesBoardRequest;
import com.pet.ques.dto.QuesBoardResponse;
import com.pet.ques.service.QuesBoardService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class QuesBoardApiController {

	private final QuesBoardService quesBoardService;

	// 글이미지 업로드
	@PostMapping("/api/questions")
	public ResponseEntity<QuesBoard> addQuesBoard(@RequestParam("quesTitle") String quesTitle,
			@RequestParam("quesContent") String quesContent,
			@RequestParam(value = "file", required = false) MultipartFile file) throws Exception {
		QuesBoardRequest dto = new QuesBoardRequest();
		dto.setQuesTitle(quesTitle);
		dto.setQuesContent(quesContent);

		QuesBoard savedQuesBoard = quesBoardService.save(dto, file);

		// 요청값 생성, 저장된 블로그 글 전송
		return ResponseEntity.status(HttpStatus.CREATED).body(savedQuesBoard);
	}

	// 글 전체 조회
	@GetMapping("/api/questions")
	public ResponseEntity<List<QuesBoardResponse>> findAllQuestions() {
		List<QuesBoardResponse> questions = quesBoardService.findAll(null).stream().map(QuesBoardResponse::new)
				.toList();
		return ResponseEntity.ok().body(questions);
	}

	// 글 하나 조회
	@GetMapping("/api/questions/{quesNo}")
	public ResponseEntity<QuesBoardResponse> findQuestion(@PathVariable long quesNo) {
		QuesBoard quesBoard = quesBoardService.findById(quesNo);

		return ResponseEntity.ok().body(new QuesBoardResponse(quesBoard));
	}

	// 글 삭제
	@DeleteMapping("/api/questions/{quesNo}")
	public ResponseEntity<Void> deleteQuestion(@PathVariable long quesNo) {
		quesBoardService.delete(quesNo);

		return ResponseEntity.ok().build();
	}

	// 글 수정
	@PutMapping("/api/questions/{quesNo}")
	public ResponseEntity<QuesBoard> updateQuesBoard(@PathVariable long quesNo,
			@RequestParam("quesTitle") String quesTitle, @RequestParam("quesContent") String quesContent,
			@RequestParam(name = "file", required = false) MultipartFile file) throws IOException {
		QuesBoard updatedQuesBoard = quesBoardService.update(quesNo, quesTitle, quesContent, file);

		return ResponseEntity.ok().body(updatedQuesBoard);
	}
}
