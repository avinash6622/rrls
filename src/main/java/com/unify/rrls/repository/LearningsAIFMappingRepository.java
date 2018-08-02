package com.unify.rrls.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unify.rrls.domain.LearningAIF;
import com.unify.rrls.domain.LearningAIFMapping;
import com.unify.rrls.domain.OpportunityMaster;

@SuppressWarnings("unused")
@Repository
public interface LearningsAIFMappingRepository extends JpaRepository<LearningAIFMapping, Long>  {

	List<LearningAIFMapping> findByLearningAIF(LearningAIF learningAIF);
	
	LearningAIFMapping findByOpportunityMasterAndLearningAIF(OpportunityMaster opportunityMaster,LearningAIF learningAIF);
}
