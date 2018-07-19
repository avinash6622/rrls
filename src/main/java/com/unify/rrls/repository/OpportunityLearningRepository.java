package com.unify.rrls.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.unify.rrls.domain.OpportunityLearning;
import com.unify.rrls.domain.OpportunityMaster;

@SuppressWarnings("unused")
@Repository
public interface OpportunityLearningRepository extends JpaRepository<OpportunityLearning, Long>  {

	 List<OpportunityLearning> findByOpportunityMaster(OpportunityMaster opportunityMaster);
	 OpportunityLearning findByOpportunityMasterAndSubject(OpportunityMaster opportunityMaster,String subject);
	 List<OpportunityLearning> findBySubject(String subject);

    @Query(value="select * from opportunity_learning group by opp_name", nativeQuery = true)
    List<OpportunityLearning> findAllGroupby();

    Page<OpportunityLearning> findByOppName(String oppname, Pageable pageable);



}
