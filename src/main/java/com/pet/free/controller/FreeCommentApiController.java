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

	@PostMapping("/api/freecomments")
	public ResponseEntity<FreeCommentResponse> addFreeComment(@RequestBody FreeCommentRequest dto) {
		// freeNo를 이용하여 댓글이 속한 질문을 찾아서 저장
		FreeComment savedFreeComment = freeCommentService.saveComment(dto, dto.getFreeNo());

		// 새로운 댓글을 저장하고, 그 댓글의 응답을 반환
		return ResponseEntity.status(HttpStatus.CREATED).body(new FreeCommentResponse(savedFreeComment));
	}

	@GetMapping("/api/freecomments")
	public ResponseEntity<List<FreeCommentResponse>> findAllFreeComments() {
		List<FreeCommentResponse> freecomments = freeCommentService.findAll().stream().map(FreeCommentResponse::new)
				.toList();

		return ResponseEntity.ok().body(freecomments);
	}

	@GetMapping("/api/freecomments/{freeCmtNo}")
	public ResponseEntity<FreeCommentResponse> FreeComment(@PathVariable long freeCmtNo) {
		FreeComment freeComment = freeCommentService.findByFreeCmtNo(freeCmtNo);

		return ResponseEntity.ok().body(new FreeCommentResponse(freeComment));
	}

	@DeleteMapping("/api/freecomments/{freeCmtNo}")
	public ResponseEntity<Void> deleteFreeComment(@PathVariable long freeCmtNo) {
		freeCommentService.delete(freeCmtNo);

		return ResponseEntity.ok().build();
	}
	
	 @PutMapping("/api/freecomments/{freeCmtNo}")
     public ResponseEntity<FreeComment> updateFreeComment(@PathVariable long freeCmtNo,
                                                  @RequestBody UpdateFreeCommentRequest request) {
         FreeComment updatedFreeComment = freeCommentService.update(freeCmtNo, request);

         return ResponseEntity.ok()
                 .body(updatedFreeComment);
     }

}