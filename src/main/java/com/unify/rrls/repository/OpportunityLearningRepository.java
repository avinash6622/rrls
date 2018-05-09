package com.unify.rrls.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unify.rrls.domain.OpportunityLearning;
import com.unify.rrls.domain.OpportunityMaster;

@SuppressWarnings("unused")
@Repository
public interface OpportunityLearningRepository extends JpaRepository<OpportunityLearning, Long>  {
	
	 List<OpportunityLearning> findByOpportunityMaster(OpportunityMaster opportunityMaster);
	 List<OpportunityLearning> findBySubject(String subject);

}
