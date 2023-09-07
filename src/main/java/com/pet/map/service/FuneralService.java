package com.pet.map.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pet.map.domain.FuneralMap;
import com.pet.map.repository.FuneralRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FuneralService {
	private final FuneralRepository funeralRepository;
	
	public List<FuneralMap> findAll(){
		return funeralRepository.findAll();
	}
}
