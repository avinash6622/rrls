package com.unify.rrls.repository;

import com.unify.rrls.domain.OpportunityMaster;
import com.unify.rrls.domain.StrategyMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unify.rrls.domain.OpportunitySummaryData;

import java.util.List;

@SuppressWarnings("unused")
@Repository
public interface OpportunitySummaryDataRepository extends JpaRepository<OpportunitySummaryData, Long> {
    List<OpportunitySummaryData>  findByOpportunityMasterid(OpportunityMaster opportunityMaster);
    List<OpportunitySummaryData> findByStrategyMasterId(StrategyMaster strategyMaster);


/*    List<OpportunitySummaryData> findAllGroupByOpportunityMasterid();*/
}

