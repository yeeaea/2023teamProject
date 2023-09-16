package com.pet.free.controller;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pet.free.domain.FreeComment;
import com.pet.free.dto.FreeCommentRequest;
import com.pet.free.dto.FreeCommentResponse;
import com.pet.free.dto.UpdateFreeCommentRequest;
import com.pet.free.service.FreeCommentService;
import com.pet.security.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class FreeCommentApiController {

	private final MemberService memberService;
	private final FreeCommentService freeCommentService;

	// 댓글 등록
	@PostMapping("/api/freecomments")
	public ResponseEntity<FreeComment> addFreeComment(@RequestParam("freeCmtContent") String freeCmtContent,
			@RequestParam("freeNo") Long freeNo, Principal principal) throws Exception {
		if (principal != null) {
			// 현재 로그인한 사용자의 아이디
			String userid = principal.getName();
			// 현재 로그인한 사용자의 닉네임
			String nickname = memberService.getNickname(userid);

			FreeCommentRequest dto = new FreeCommentRequest();
			dto.setFreeCmtContent(freeCmtContent);
			dto.setUserid(userid);
			dto.setNickname(nickname);

			FreeComment savedFreeComment = freeCommentService.saveComment(dto, freeNo);

			return ResponseEntity.status(HttpStatus.CREATED).body(savedFreeComment);
		
		} else {
			// 새로운 댓글을 저장하고, 그 댓글의 응답을 반환
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
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
	public ResponseEntity<FreeComment> updateFreeComment(
			@PathVariable Long freeCmtNo,
			@RequestParam("freeCmtContent") String freeCmtContent,
			Principal principal) throws IOException {
		// 현재 로그인한 사용자 아이디
		String currentUserid = principal.getName();
		// CmtNo을 사용하여 댓글을 데이터베이스에서 가져오기
		FreeComment existingFreeCmt = freeCommentService.findByFreeCmtNo(freeCmtNo);
		
		if (existingFreeCmt == null) {
			// 댓글이 존재하지 않는 경우 에러 처리
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
		// 댓글 작성자의 아이디
		String authorId = existingFreeCmt.getUserid();
		
		// 현재 사용자와 댓글 작성자를 비교하여 권한 확인
		if (!currentUserid.equals(authorId)) {
			// 현재 사용자가 댓글 작성자가 아닌 경우 에러 처리
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		
		// 댓글 수정 로직 구현
		existingFreeCmt.setFreeCmtContent(freeCmtContent);
		
		FreeComment updatedFreeComment = freeCommentService.update(freeCmtNo, freeCmtContent);

		return ResponseEntity.ok().body(updatedFreeComment);
	}

}