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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pet.free.domain.FreeBoard;
import com.pet.free.dto.FreeBoardRequest;
import com.pet.free.dto.FreeBoardResponse;
import com.pet.free.service.FreeBoardService;
import com.pet.security.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class FreeBoardApiController {

	private final MemberService memberService;
	private final FreeBoardService freeBoardService;

	// 글 업로드
	@PostMapping("/api/freeboards")
	public ResponseEntity<FreeBoard> addFreeBoard(@RequestParam("freeTitle") String freeTitle,
			@RequestParam("freeContent") String freeContent,
			@RequestParam(value = "file", required = false) MultipartFile file, Principal principal) throws Exception {
		if (principal != null) {
			// 현재 로그인한 사용자의 아이디
			String userid = principal.getName();
			// 현재 로그인한 사용자의 닉네임
			String nickname = memberService.getNickname(userid);

			FreeBoardRequest dto = new FreeBoardRequest();
			dto.setFreeTitle(freeTitle);
			dto.setFreeContent(freeContent);
			dto.setUserid(userid);
			dto.setNickname(nickname);

			FreeBoard savedFreeBoard = freeBoardService.save(dto, file);

			// 요청한 자원이 성공적으로 생성되었으며 저장된 블로그 글 정보를 응답 객체에 담아 전송
			return ResponseEntity.status(HttpStatus.CREATED).body(savedFreeBoard);
		
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
	}

	// 글 목록 조회
	@GetMapping("/api/freeboards")
	public ResponseEntity<List<FreeBoardResponse>> findAllQuestions() {
		List<FreeBoardResponse> freeboard = freeBoardService.findAll(null).stream().map(FreeBoardResponse::new)
				.toList();
		return ResponseEntity.ok().body(freeboard);
	}

	// 글 조회
	@GetMapping("/api/freeboards/{freeNo}")
	public ResponseEntity<FreeBoardResponse> findFreeBoard(@PathVariable Long freeNo) {
		FreeBoard freeBoard = freeBoardService.findById(freeNo);

		return ResponseEntity.ok().body(new FreeBoardResponse(freeBoard));
	}

	// 글 삭제
	@DeleteMapping("/api/freeboards/{freeNo}")
	public ResponseEntity<Void> deleteFreeBoard(@PathVariable Long freeNo) {
		freeBoardService.delete(freeNo);

		return ResponseEntity.ok().build();
	}

	// 글 수정
	@PutMapping("/api/freeboards/{freeNo}")
	public ResponseEntity<FreeBoard> updatefreeBoard(@PathVariable Long freeNo,
			@RequestParam("freeTitle") String freeTitle, @RequestParam("freeContent") String freeContent,
			@RequestParam(name = "file", required = false) MultipartFile file, Principal principal) throws IOException {
		// 현재 로그인한 사용자 id
		String currentUserid = principal.getName();
		// FreeNo를 사용하여 글을 데이터베이스에서 가져옴
		FreeBoard existingFreeBoard = freeBoardService.findById(freeNo);

		if (existingFreeBoard == null) {
			// 글이 존재하지 않는 경우 에러 처리
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		// 글 작성자의 id
		String authorId = existingFreeBoard.getUserid();

		// 현재 사용자와 글 작성자를 비교하여 권한 확인
		if (!currentUserid.equals(authorId)) {
			// 현재 사용자가 글 작성자가 아닌 경우 권한이 없음으로 간주, 에러 처리
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

		// 글 수정 로직 구현
		existingFreeBoard.setFreeTitle(freeTitle);
		existingFreeBoard.setFreeContent(freeContent);

		FreeBoard updatedFreeBoard = freeBoardService.update(freeNo, freeTitle, freeContent, file);

		return ResponseEntity.ok().body(updatedFreeBoard);
	}
}
