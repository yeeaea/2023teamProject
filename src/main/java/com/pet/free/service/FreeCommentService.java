package com.pet.free.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.pet.free.domain.FreeBoard;
import com.pet.free.domain.FreeComment;
import com.pet.free.dto.FreeCommentRequest;
import com.pet.free.dto.UpdateFreeCommentRequest;
import com.pet.free.repository.FreeBoardRepository;
import com.pet.free.repository.FreeCommentRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor

@Service
public class FreeCommentService {

	private final FreeCommentRepository freeCommentRepo;
	private final FreeBoardRepository freeBoardRepo;

	// 댓글 등록
	public FreeComment saveComment(FreeCommentRequest commentRequest, Long freeNo) {
		// freeNo에 해당하는 게시글에 댓글을 저장하는 로직 구현
		FreeBoard freeBoard = freeBoardRepo.findById(freeNo)
				.orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없음"));
		FreeComment comment = commentRequest.toEntity();
		comment.setFreeBoard(freeBoard);
		return freeCommentRepo.save(comment);
	}

	// 댓글 조회
	public List<FreeComment> findAll() {
		return freeCommentRepo.findAll();
	}

	// 댓글 가져오기
	public FreeComment findByFreeCmtNo(long freeCmtNo) {
		return freeCommentRepo.findById(freeCmtNo)
				.orElseThrow(() -> 
					new IllegalArgumentException("not found : " + freeCmtNo));
	}

	// 댓글 삭제
	public void delete(long freeCmtNo) {
		freeCommentRepo.deleteById(freeCmtNo);
	}

	// 해당 게시글에 댓글목록 보여지기
	public List<FreeComment> getCommentsByFreeNo(Long freeNo) {
		return freeCommentRepo.findByFreeBoard_FreeNo(freeNo);
	}

	// 댓글 수정
	@Transactional
	public FreeComment update(long freeNo, UpdateFreeCommentRequest request) {
		FreeComment freeComment = freeCommentRepo.findById(freeNo)
				.orElseThrow(() ->
					new IllegalArgumentException("수정할 댓글이 없습니다."));

		freeComment.setFreeCmtRdate(LocalDateTime.now());
		freeComment.update(request.getFreeCmtContent());

		return freeComment;
	}
}
