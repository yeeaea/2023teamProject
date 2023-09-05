package com.pet.ques.controller;

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

import com.pet.ques.domain.QuesBoard;
import com.pet.ques.dto.QuesBoardRequestDto;
import com.pet.ques.dto.QuesBoardResponseDto;
import com.pet.ques.dto.UpdateQuesBoardRequestDto;
import com.pet.ques.service.QuesBoardService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor  // final이 붙거나 @NotNull이 붙은 필드의 생성자 추가
@RestController  // HTTP Response Body에 객체 데이터를 JSON 형식으로 반환하는 컨트롤러
public class QuesBoardController {
	
	private final QuesBoardService quesBoardService;

	@PostMapping("/api/questions")
	// @ResponseBody 애너테이션은 HTTP를 요청할 때 응답에 해당하는 값을 
	// @RequestBody 애너테이션이 붙은 대상 객체에 매핑한다.
	public ResponseEntity<QuesBoard> addQuesBoard(@RequestBody QuesBoardRequestDto dto){
		QuesBoard savedQuesBoard = quesBoardService.save(dto);
		
		// 요청한 자원이 성공적으로 생성되었으며 저장된 블로그 글 정보를 응답 객체에 담아 전송
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(savedQuesBoard);
	}
	
	// GET 요청이 오면 글 전체를 조회하는 findAll() 메서드를 호출한 다음
	// 응답용 객체인 ArticleResponse로 파싱해 body에 담아 클라이언트로 전송
	@GetMapping("/api/questions")
	public ResponseEntity<List<QuesBoardResponseDto>> findAllQuestions(){
		List<QuesBoardResponseDto> questions = quesBoardService.findAll(null)
				.stream()
				.map(QuesBoardResponseDto::new)
				.toList();  // 최종 리턴값을 List로 바꿔라
		return ResponseEntity.ok().body(questions);
	}
	
	
	// 블로그 글 하나 조회하는 api
	@GetMapping("/api/questions/{quesNo}")
	// URL 경로에서 값 추출
	public ResponseEntity<QuesBoardResponseDto> findQuestion(@PathVariable long quesNo){
		QuesBoard quesBoard = quesBoardService.findById(quesNo);
		
		return ResponseEntity.ok()
				.body(new QuesBoardResponseDto(quesBoard));
	}
	
	
	// DELETE 요청이 오면 글을 삭제하기 위한 findQuestions() 메서드
	@DeleteMapping("/api/questions/{quesNo}")
	public ResponseEntity<Void> deleteQuestion(@PathVariable long quesNo){
		quesBoardService.delete(quesNo);
		
		return ResponseEntity.ok()
				.build();
	}
	
	
	// 수정
	@PutMapping("/api/questions/{quesNo}")
	public ResponseEntity<QuesBoard> updateQuesBoard(@PathVariable long quesNo,
			@RequestBody UpdateQuesBoardRequestDto dto){
		QuesBoard updatedQuesBoard = quesBoardService.update(quesNo, dto);
		
		return ResponseEntity.ok()
				.body(updatedQuesBoard);
	}
}

