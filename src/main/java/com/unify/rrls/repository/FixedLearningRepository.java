package com.unify.rrls.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unify.rrls.domain.FixedLearning;

@SuppressWarnings("unused")
@Repository
public interface FixedLearningRepository extends JpaRepository<FixedLearning, Long>  {
	
	FixedLearning findBySubject(String name);
	
	

}
