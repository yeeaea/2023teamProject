package com.pet.map.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pet.free.domain.FreeBoard;
import com.pet.map.domain.FuneralMap;

public interface FuneralRepository extends JpaRepository<FuneralMap, Long>{

}
