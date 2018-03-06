package com.unify.rrls.repository;

import com.unify.rrls.domain.OpportunityMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unify.rrls.domain.FinancialSummaryData;
import com.unify.rrls.domain.NonFinancialSummaryData;

@SuppressWarnings("unused")
@Repository
public interface NonFinancialSummaryDataRepository extends JpaRepository<NonFinancialSummaryData, Long>  {
    NonFinancialSummaryData findByOpportunityMaster(OpportunityMaster opportunityMaster);

}