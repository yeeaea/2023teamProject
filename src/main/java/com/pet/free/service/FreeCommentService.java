package com.pet.free.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.pet.free.domain.FreeBoard;
import com.pet.free.domain.FreeComment;
import com.pet.free.dto.FreeCommentListViewResponse;
import com.pet.free.dto.FreeCommentRequest;
import com.pet.free.dto.UpdateFreeCommentRequest;
import com.pet.free.repository.FreeBoardRepository;
import com.pet.free.repository.FreeCommentRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FreeCommentService {
	
	@Autowired
	private final FreeCommentRepository freeCommentRepo;
	private final FreeBoardRepository freeBoardRepo;

	// 댓글 등록
	public FreeComment saveComment(FreeCommentRequest commentRequest, Long freeNo) {
		// freeNo에 해당하는 게시글에 댓글을 저장하는 로직 구현
		FreeBoard freeBoard = freeBoardRepo.findById(freeNo)
				.orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
		FreeComment comment = commentRequest.toEntity();
		comment.setFreeBoard(freeBoard);
		return freeCommentRepo.save(comment);
	}

	// 댓글 조회
	public List<FreeComment> findAll() {
		return freeCommentRepo.findAll();
	}

	// 댓글 가져오기
	public FreeComment findByFreeCmtNo(Long freeCmtNo) {
		return freeCommentRepo.findById(freeCmtNo)
				.orElseThrow(() -> 
					new IllegalArgumentException(freeCmtNo + "번 댓글이 존재하지 않습니다."));
	}

	// 댓글 삭제
	public void delete(Long freeCmtNo) {
		freeCommentRepo.deleteById(freeCmtNo);
	}

	// 해당 게시글에 댓글목록 보여지기
	public List<FreeComment> getCommentsByFreeNo(Long freeNo) {
		return freeCommentRepo.findByFreeBoard_FreeNo(freeNo);
	}

	// 댓글 수정
	@Transactional
	public FreeComment update(Long freeCmtNo, UpdateFreeCommentRequest dto) {
		FreeComment freeComment = freeCommentRepo.findById(freeCmtNo)
				.orElseThrow(() ->
					new IllegalArgumentException("수정할 댓글이 없습니다."));

		freeComment.setFreeCmtRdate(LocalDateTime.now());
		freeComment.update(dto.getFreeCmtContent());

		return freeCommentRepo.save(freeComment);
	}
	
	// 댓글 작성자의 닉네임을 가져오는 메소드
	public String getNickname(Long freeCmtNo) {
		FreeComment freeComment = freeCommentRepo.findById(freeCmtNo).orElse(null);
		
		if (freeComment != null) {
			return freeComment.getCmtNickname();
		}
		return null;
	}
}
