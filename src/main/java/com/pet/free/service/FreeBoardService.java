package com.pet.free.service;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.pet.free.domain.FreeBoard;
import com.pet.free.dto.FreeBoardRequest;
import com.pet.free.dto.UpdateFreeBoardRequest;
import com.pet.free.repository.FreeBoardRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FreeBoardService {

	private final FreeBoardRepository freeBoardRepository;
	
	public FreeBoard save(FreeBoardRequest request) {
		return freeBoardRepository.save(request.toEntity());
	}
	
	public Page<FreeBoard> findAll(Pageable pageable){
		Pageable descendingPageable = PageRequest.of(
				pageable.getPageNumber(),
				pageable.getPageSize(),
				Sort.by(Sort.Direction.DESC, "freeNo"));
		return freeBoardRepository.findAll(descendingPageable);
	}
	
	public FreeBoard findById(Long freeNo) {
		return freeBoardRepository.findById(freeNo)
				.orElseThrow(()-> new IllegalArgumentException("not found: " + freeNo));
	}
	
	public void delete(Long freeNo) {
		freeBoardRepository.deleteById(freeNo);
	}
	
	@Transactional
	public FreeBoard update(long freeNo, UpdateFreeBoardRequest request) {
		FreeBoard freeBoard = freeBoardRepository.findById(freeNo)
				.orElseThrow(()-> new IllegalArgumentException("not found : " + freeNo));
		
		freeBoard.setFreeRdate(LocalDateTime.now());
		freeBoard.update(request.getFreeTitle(), request.getFreeContent());
		return freeBoard;
	}
	
	public Page<FreeBoard> boardSearchList(String keyword, Pageable pageable){
		return freeBoardRepository.findByfreeTitleContaining(keyword, pageable);
	}
}
