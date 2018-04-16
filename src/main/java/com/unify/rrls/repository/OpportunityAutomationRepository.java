package com.unify.rrls.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unify.rrls.domain.NonFinancialSummaryData;
import com.unify.rrls.domain.OpportunityAutomation;
import com.unify.rrls.domain.OpportunityMaster;

@SuppressWarnings("unused")
@Repository
public interface OpportunityAutomationRepository extends JpaRepository<OpportunityAutomation, Long> {
	
	OpportunityAutomation findByOpportunityMaster(OpportunityMaster opportunityMaster);

}
