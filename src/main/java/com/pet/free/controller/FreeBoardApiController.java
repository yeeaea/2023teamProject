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

import com.pet.free.domain.FreeBoard;
import com.pet.free.dto.AddFreeBoardRequest;
import com.pet.free.dto.FreeBoardResponse;
import com.pet.free.dto.UpdateFreeBoardRequest;
import com.pet.free.service.FreeBoardService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class FreeBoardApiController {

	private final FreeBoardService freeBoardService;

	@PostMapping("/freeboard")
	public ResponseEntity<FreeBoard> addFreeBoard(
			@RequestBody AddFreeBoardRequest request) {
		FreeBoard savedFreeBoard = 				freeBoardService.save(request);

		return ResponseEntity.status(HttpStatus.CREATED).body(savedFreeBoard);
	}

	// 글 목록 가져오기
	@GetMapping("/freeboard")
	public ResponseEntity<List<FreeBoardResponse>> findAllFreeBoard() {
		List<FreeBoardResponse> freeboard = freeBoardService.findAll(null)
				.stream()
				.map(FreeBoardResponse::new)
				.toList();

		return ResponseEntity.ok().body(freeboard);
	}
	
	// 글 하나 조회하기
	@GetMapping("/freeboard/{freeNo}")
	public ResponseEntity<FreeBoardResponse> findFreeBoard(@PathVariable Long freeNo) {
		FreeBoard freeBoard = freeBoardService.findById(freeNo);

		return ResponseEntity.ok().body(new FreeBoardResponse(freeBoard));
	}
	
	// 글 하나 삭제하기
	@DeleteMapping("/freeboards/{freeNo}")
	public ResponseEntity<Void> deleteFreeBoard(@PathVariable Long freeNo) {
		freeBoardService.delete(freeNo);

		return ResponseEntity.ok().build();
	}
	
	// 글 수정하기
	@PutMapping("/freeboards/{freeNo}")
	public ResponseEntity<FreeBoard> updateFreeBoard(@PathVariable Long freeNo,
			@RequestBody UpdateFreeBoardRequest request) {
		FreeBoard updatedFreeBoard = freeBoardService.update(freeNo, request);

		return ResponseEntity.ok().body(updatedFreeBoard);
	}
}
