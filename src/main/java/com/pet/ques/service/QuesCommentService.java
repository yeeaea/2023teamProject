package com.pet.ques.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pet.free.domain.FreeComment;
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

	@Autowired
	private final QuesCommentRepository quesCommentRepo;
	private final QuesBoardRepository quesBoardRepo;

	// 댓글 등록
	public QuesComment saveComment(QuesCommentRequest commentRequest, Long quesNo) {
		// quesNo에 해당하는 게시글에 댓글을 저장하는 로직 구현
		QuesBoard quesBoard = quesBoardRepo.findById(quesNo).orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없음"));
		QuesComment comment = commentRequest.toEntity();
		comment.setQuesBoard(quesBoard);
		return quesCommentRepo.save(comment);
	}

	// 댓글 조회
	public List<QuesComment> findAll() {
		return quesCommentRepo.findAll();
	}

	// 댓글 가져오기
	public QuesComment findByQuesCmtNo(Long quesCmtNo) {
		return quesCommentRepo.findById(quesCmtNo)
				.orElseThrow(() -> new IllegalArgumentException("not found : " + quesCmtNo));
	}

	// 댓글 삭제
	public void delete(Long quesCmtNo) {
		quesCommentRepo.deleteById(quesCmtNo);
	}

	// 해당 게시글에 댓글목록 보여지기
	public List<QuesComment> getCommentsByQuesNo(Long quesNo) {
		return quesCommentRepo.findByQuesBoard_QuesNo(quesNo);
	}

	// 댓글 수정
	@Transactional
	public QuesComment update(Long quesCmtNo, UpdateQuesCommentRequest dto) {
		QuesComment quesComment = quesCommentRepo.findById(quesCmtNo)
				.orElseThrow(() -> new IllegalArgumentException("수정할 댓글이 없습니다."));

		quesComment.setQuesCmtRdate(LocalDateTime.now());
		quesComment.update(dto.getQuesCmtContent());

		return quesCommentRepo.save(quesComment);
	}

	// 댓글 작성자의 닉네임을 가져오는 메소드
	public String getNickname(Long quesCmtNo) {
		QuesComment quesComment = quesCommentRepo.findById(quesCmtNo).orElse(null);

		if (quesComment != null) {
			return quesComment.getCmtNickname();
		}
		return null;
	}
}