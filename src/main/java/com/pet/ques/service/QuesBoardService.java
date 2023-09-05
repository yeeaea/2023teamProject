package com.pet.ques.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.pet.ques.domain.QuesBoard;
import com.pet.ques.dto.QuesBoardRequest;
import com.pet.ques.dto.UpdateQuesBoardRequest;
import com.pet.ques.repository.QuesBoardRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

// final이 붙거나 @NotNull이 붙은 필드의 생성자 추가
@RequiredArgsConstructor
// 빈으로 등록
@Service
public class QuesBoardService {

	private final QuesBoardRepository quesBoardRepo;
	
	// 블로그 글 추가 메서드
	public QuesBoard save(QuesBoardRequest dto) {
		return quesBoardRepo.save(dto.toEntity());
	}
	
	// JPA의 지원메서드인 findAll()을 호출해 QuesBoard 테이블에 저장되어 있는 모든 데이터를 조회합니다.
	public Page<QuesBoard> findAll(Pageable pageable){
	    Pageable descendingPageable = PageRequest.of(
	        pageable.getPageNumber(),
	        pageable.getPageSize(),
	        Sort.by(Sort.Direction.DESC, "quesNo"));
	    return quesBoardRepo.findAll(descendingPageable);
	}
	
	// 데이터베이스에 저장되어 있는 글의 No를 이용해 글을 하나 조회하기
	public QuesBoard findById(long quesNo) {
		return quesBoardRepo.findById(quesNo)
				.orElseThrow(() -> new IllegalArgumentException(quesNo + "번 글이 존재하지 않습니다."));
	}
	
	
	// 글의 no을 받은 뒤 JPA에서 제공하는 deleteById() 메서드를 이용해 데이터 삭제
	public void delete(long quesNo) {
		quesBoardRepo.deleteById(quesNo);
	}
	
	
	// 글 수정 메서드
	@Transactional  // 매칭한 메서드를 하나의 트랜잭션으로 묶는 역할을 합니다.
	public QuesBoard update(long quesNo, UpdateQuesBoardRequest dto) {
		QuesBoard quesBoard = quesBoardRepo.findById(quesNo)
				.orElseThrow(()->new IllegalArgumentException(quesNo + "번 글이 존재하지 않습니다."));
		quesBoard.setQuesRdate(LocalDateTime.now());
		quesBoard.update(dto.getQuesTitle(), dto.getQuesContent());
		
		return quesBoard;
	}
	
	
	public Page<QuesBoard> boardSearchList(String keyword, Pageable pageable){
	    return quesBoardRepo.findByquesTitleContaining(keyword, pageable);
	}
	
//	@Transactional
//    public List<QuesBoard> findAllDesc(List<QuesBoard> articles) {
//        return quesBoardRepo.findAllDesc();
//
//
//    }
}
