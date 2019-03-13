package com.unify.rrls.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.unify.rrls.domain.ConfidenctialLetters;
import com.unify.rrls.domain.OpportunityMaster;

    @SuppressWarnings("unused")
	@Repository
	public interface ConfidenctialLettersRepository extends JpaRepository<ConfidenctialLetters, Long> {
		List<ConfidenctialLetters> findByOpportunityMasterId(OpportunityMaster opportunityMaster);
		Page<ConfidenctialLetters> findByOpportunityMasterId(OpportunityMaster opportunityName,Pageable pageable);
	    Page<ConfidenctialLetters> findBySubject(String subject,Pageable pageable);
	}
	
