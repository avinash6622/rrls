package com.unify.rrls.repository;

import com.unify.rrls.domain.OpportunityMaster;
import com.unify.rrls.domain.OpportunityName;
import com.unify.rrls.domain.OpportunitySummaryData;

import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the OpportunityMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OpportunityMasterRepository extends JpaRepository<OpportunityMaster, Long> {

	Page<OpportunityMaster> findByCreatedBy(String Role,Pageable pageable);
	List<OpportunityMaster>findByCreatedBy(String name);
	OpportunityMaster findByMasterName(OpportunityName opportunityName);
	List<OpportunityMaster> findAllByCreatedBy(String name);
	Page<OpportunityMaster> findById(Long id,Pageable pageable);
	
}
