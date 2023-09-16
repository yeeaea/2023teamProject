package com.pet.free.controller;

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

import com.pet.free.domain.FreeComment;
import com.pet.free.dto.FreeCommentRequest;
import com.pet.free.dto.FreeCommentResponse;
import com.pet.free.dto.UpdateFreeCommentRequest;
import com.pet.free.service.FreeCommentService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class FreeCommentApiController {

	private final FreeCommentService freeCommentService;

	// 댓글 등록
	@PostMapping("/api/freecomments")
	public ResponseEntity<FreeCommentResponse> addFreeComment(@RequestBody FreeCommentRequest dto) {
		FreeComment savedFreeComment = 
				freeCommentService.saveComment(dto, dto.getFreeNo());
		
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(new FreeCommentResponse(savedFreeComment));
	}
	
	// 댓글 리스트
	@GetMapping("/api/freecomments")
	public ResponseEntity<List<FreeCommentResponse>> findAllFreeComments() {
		List<FreeCommentResponse> freecomments = freeCommentService.findAll().stream().map(FreeCommentResponse::new)
				.toList();

		return ResponseEntity.ok().body(freecomments);
	}
	
	// 댓글 가져오기
	@GetMapping("/api/freecomments/{freeCmtNo}")
	public ResponseEntity<FreeCommentResponse> FreeComment(@PathVariable Long freeCmtNo) {
		FreeComment freeComment = freeCommentService.findByFreeCmtNo(freeCmtNo);

		return ResponseEntity.ok().body(new FreeCommentResponse(freeComment));
	}
	
	// 댓글 삭제
	@DeleteMapping("/api/freecomments/{freeCmtNo}")
	public ResponseEntity<Void> deleteFreeComment(@PathVariable Long freeCmtNo) {
		freeCommentService.delete(freeCmtNo);

		return ResponseEntity.ok().build();
	}
	
	// 댓글 수정
	@PutMapping("/api/freecomments/{freeCmtNo}")
	public ResponseEntity<FreeComment> updateFreeComment(@PathVariable Long freeCmtNo,
			@RequestBody UpdateFreeCommentRequest dto) {
		FreeComment updatedFreeComment = freeCommentService.update(freeCmtNo, dto);

		return ResponseEntity.ok().body(updatedFreeComment);
	}

}