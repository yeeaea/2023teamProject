package com.pet.map.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pet.map.domain.Place;

public interface PlaceRepository extends JpaRepository<Place, Long>{
	
	List<Place> findByTypeAndSidoAndGugun(String type, String sido, String gugun);

	@Query("SELECT DISTINCT p.type FROM Place p")
	List<String> findDistinctTypes();

    @Query("SELECT DISTINCT p.sido FROM Place p WHERE p.type = :type")
    List<String> findDistinctSidosByType(@Param("type") String type);

    @Query("SELECT DISTINCT p.gugun FROM Place p WHERE p.type = :type AND p.sido = :sido")
    List<String> findDistinctGugunsByTypeAndSido(@Param("type") String type, @Param("sido") String sido);
}
