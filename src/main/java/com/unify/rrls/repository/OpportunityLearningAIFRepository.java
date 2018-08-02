package com.unify.rrls.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.unify.rrls.domain.OpportunityLearning;
import com.unify.rrls.domain.OpportunityLearningAIF;
import com.unify.rrls.domain.OpportunityMaster;

@SuppressWarnings("unused")
@Repository
public interface OpportunityLearningAIFRepository extends JpaRepository<OpportunityLearningAIF, Long>  {

	 List<OpportunityLearningAIF> findByOpportunityMaster(OpportunityMaster opportunityMaster);
	 OpportunityLearningAIF findByOpportunityMasterAndSubject(OpportunityMaster opportunityMaster,String subject);
	 List<OpportunityLearningAIF> findBySubject(String subject);

    @Query(value="select * from opportunity_learning_aif group by opp_name", nativeQuery = true)
    List<OpportunityLearningAIF> findAllGroupby();

    Page<OpportunityLearningAIF> findByOppName(String oppname, Pageable pageable);



}
