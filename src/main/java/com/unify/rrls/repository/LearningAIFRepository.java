package com.unify.rrls.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unify.rrls.domain.FixedLearning;
import com.unify.rrls.domain.LearningAIF;

@SuppressWarnings("unused")
@Repository
public interface LearningAIFRepository extends JpaRepository<LearningAIF, Long>  {
	
	LearningAIF findBySubject(String name);
	
	

}
