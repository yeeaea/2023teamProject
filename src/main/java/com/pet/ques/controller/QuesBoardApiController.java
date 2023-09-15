package com.pet.ques.controller;

import java.io.IOException;
import java.security.Principal;
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
import com.pet.security.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class QuesBoardApiController {

	private final MemberService memberService;
	private final QuesBoardService quesBoardService;

	// 글 업로드
	@PostMapping("/api/questions")
	public ResponseEntity<QuesBoard> addQuesBoard(@RequestParam("quesTitle") String quesTitle,
			@RequestParam("quesContent") String quesContent,
			@RequestParam(value = "file", required = false) MultipartFile file, Principal principal) throws Exception {
		if (principal != null) {
			// 현재 로그인한 사용자의 아이디
			String userid = principal.getName();
			// 현재 로그인한 사용자의 닉네임
			String nickname = memberService.getNickname(userid);

			QuesBoardRequest dto = new QuesBoardRequest();
			dto.setQuesTitle(quesTitle);
			dto.setQuesContent(quesContent);
			dto.setUserid(userid);
			dto.setNickname(nickname);

			QuesBoard savedQuesBoard = quesBoardService.save(dto, file);

			// 요청값 생성, 저장된 블로그 글 전송
			return ResponseEntity.status(HttpStatus.CREATED).body(savedQuesBoard);
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
	}

	// 글 목록 조회
	@GetMapping("/api/questions")
	public ResponseEntity<List<QuesBoardResponse>> findAllQuestions() {
		List<QuesBoardResponse> questions = quesBoardService.findAll(null).stream().map(QuesBoardResponse::new)
				.toList();
		return ResponseEntity.ok().body(questions);
	}

	// 글 조회
	@GetMapping("/api/questions/{quesNo}")
	public ResponseEntity<QuesBoardResponse> findQuestion(@PathVariable Long quesNo) {
		QuesBoard quesBoard = quesBoardService.findById(quesNo);

		return ResponseEntity.ok().body(new QuesBoardResponse(quesBoard));
	}

	// 글 삭제
	@DeleteMapping("/api/questions/{quesNo}")
	public ResponseEntity<Void> deleteQuestion(@PathVariable Long quesNo) {
		quesBoardService.delete(quesNo);

		return ResponseEntity.ok().build();
	}

	// 글 수정
	@PutMapping("/api/questions/{quesNo}")
	public ResponseEntity<QuesBoard> updateQuesBoard(@PathVariable Long quesNo,
			@RequestParam("quesTitle") String quesTitle, @RequestParam("quesContent") String quesContent,
			@RequestParam(name = "file", required = false) MultipartFile file, Principal principal) throws IOException {
		// 현재 로그인한 사용자 아이디
		String currentUserid = principal.getName();
		// QuesNo를 사용하여 글을 데이터베이스에서 가져오기
		QuesBoard existingQuesBoard = quesBoardService.findById(quesNo);
		
		if (existingQuesBoard == null) {
			// 글이 존재하지 않는 경우 에러 처리
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
		// 글 작성자의 아이디
		String authorId = existingQuesBoard.getUserid();
		
		// 현재 사용자와 글 작성자를 비교하여 권한 확인
		if (!currentUserid.equals(authorId)) {
			// 현재 사용자가 글 작성자가 아닌 경우 권한이 없으므로 에러 처리
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		
		// 글 수정 로직 구현
		existingQuesBoard.setQuesTitle(quesTitle);
		existingQuesBoard.setQuesContent(quesContent);
		
		QuesBoard updatedQuesBoard = quesBoardService.update(quesNo, quesTitle, quesContent, file);

		return ResponseEntity.ok().body(updatedQuesBoard);
	}
}
