package com.pet.ques.service;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.pet.ques.domain.QuesBoard;
import com.pet.ques.domain.QuesComment;
import com.pet.ques.dto.QuesCommentRequest;
import com.pet.ques.dto.QuesCommentResponse;
import com.pet.ques.repository.QuesCommentRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor

@Service
public class QuesCommentService {
	
	private final QuesCommentRepository quesCommentRepo;
	
	public QuesComment save(QuesCommentRequest dto) {
	    QuesComment quesComment = dto.toEntity();
	    quesComment.setQuesNo(dto.getQuesNo());

	    return quesCommentRepo.save(quesComment);
	}



}
