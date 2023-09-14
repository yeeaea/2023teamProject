package com.pet.ques.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.pet.ques.domain.QuesBoard;
import com.pet.ques.domain.QuesComment;
import com.pet.ques.dto.QuesCommentRequest;
import com.pet.ques.dto.UpdateQuesCommentRequest;
import com.pet.ques.repository.QuesBoardRepository;
import com.pet.ques.repository.QuesCommentRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor

@Service
public class QuesCommentService {

	private final QuesCommentRepository quesCommentRepo;
	private final QuesBoardRepository quesBoardRepo;

	public QuesComment saveComment(QuesCommentRequest commentRequest, Long quesNo) {
		// quesNo에 해당하는 게시글에 댓글을 저장하는 로직 구현
		QuesBoard quesBoard = quesBoardRepo.findById(quesNo)
				.orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없음"));
		QuesComment comment = commentRequest.toEntity();
		
		comment.setQuesBoard(quesBoard);
		
		return quesCommentRepo.save(comment);
	}

	public List<QuesComment> findAll() {
		return quesCommentRepo.findAll();
	}

	public QuesComment findByQuesCmtNo(Long quesCmtNo) {
		return quesCommentRepo.findById(quesCmtNo)
				.orElseThrow(() ->
					new IllegalArgumentException("not found : " + quesCmtNo));
	}

	// 댓글삭제
	public void delete(Long quesCmtNo) {
		quesCommentRepo.deleteById(quesCmtNo);
	}

	// 해당 게시글에 댓글목록 보여지기
	public List<QuesComment> getCommentsByQuesNo(Long quesNo) {
		return quesCommentRepo.findByQuesBoard_QuesNo(quesNo);
	}

	@Transactional
	public QuesComment update(Long quesCmtNo, UpdateQuesCommentRequest dto) {
		QuesComment quesComment = quesCommentRepo.findById(quesCmtNo)
				.orElseThrow(() ->
					new IllegalArgumentException("수정할 댓글이 없습니다."));
		
		quesComment.setQuesCmtRdate(LocalDateTime.now());
		quesComment.update(dto.getQuesCmtContent());

		return quesComment;
	}
}