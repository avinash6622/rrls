package com.unify.rrls.repository;

import com.unify.rrls.domain.OpportunityMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unify.rrls.domain.OpportunitySummaryData;

@SuppressWarnings("unused")
@Repository
public interface OpportunitySummaryDataRepository extends JpaRepository<OpportunitySummaryData, Long> {
    OpportunitySummaryData  findByOpportunityMasterid(OpportunityMaster opportunityMaster);

}
