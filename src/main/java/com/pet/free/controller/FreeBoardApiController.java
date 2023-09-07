package com.pet.free.controller;

import java.io.IOException;
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
import org.springframework.web.multipart.MultipartFile;

import com.pet.free.domain.FreeBoard;
import com.pet.free.dto.FreeBoardRequest;
import com.pet.free.dto.FreeBoardResponse;
import com.pet.free.dto.UpdateFreeBoardRequest;
import com.pet.free.service.FreeBoardService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class FreeBoardApiController {

	private final FreeBoardService freeBoardService;

	@PostMapping("/api/freeboard")
	public ResponseEntity<FreeBoard> addFreeBoard(
		    @RequestParam("freeTitle") String freeTitle,
		    @RequestParam("freeContent") String freeContent,
		    @RequestParam(value="file", required = false) MultipartFile file) throws Exception {
		    FreeBoardRequest request = new FreeBoardRequest();
		    request.setFreeTitle(freeTitle);
		    request.setFreeContent(freeContent);

		    FreeBoard savedFreeBoard = freeBoardService.save(request, file);
		    
		    // 요청한 자원이 성공적으로 생성되었으며 저장된 블로그 글 정보를 응답 객체에 담아 전송
		    return ResponseEntity.status(HttpStatus.CREATED)
		            .body(savedFreeBoard);
		}
	// 글 목록 가져오기
	@GetMapping("/api/freeboard")
	public ResponseEntity<List<FreeBoardResponse>> findAllQuestions(){
		List<FreeBoardResponse> freeboard = freeBoardService.findAll(null)
				.stream()
				.map(FreeBoardResponse::new)
				.toList();  // 최종 리턴값을 List로 바꿔라
		return ResponseEntity.ok().body(freeboard);
	}
	
	
	
	// 글 하나 조회하기
	@GetMapping("/api/freeboard/{freeNo}")
	public ResponseEntity<FreeBoardResponse> findFreeBoard(@PathVariable Long freeNo) {
		FreeBoard freeBoard = freeBoardService.findById(freeNo);

		return ResponseEntity.ok().body(new FreeBoardResponse(freeBoard));
	}
	
	// 글 하나 삭제하기
	@DeleteMapping("/api/freeboards/{freeNo}")
	public ResponseEntity<Void> deleteFreeBoard(@PathVariable Long freeNo) {
		freeBoardService.delete(freeNo);

		return ResponseEntity.ok().build();
	}
	
	// 글 수정하기
	@PutMapping("/api/freeboards/{freeNo}")
	public ResponseEntity<FreeBoard> updateFreeBoard(@PathVariable long freeNo,
	        @RequestBody UpdateFreeBoardRequest request,
	        @RequestParam(name = "file", required = false) MultipartFile file) throws IOException {
	    FreeBoard updatedFreeBoard = freeBoardService.update(freeNo, request, file);

	    return ResponseEntity.ok().body(updatedFreeBoard);
	}
}
