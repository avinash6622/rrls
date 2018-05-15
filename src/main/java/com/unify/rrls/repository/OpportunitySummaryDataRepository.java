package com.unify.rrls.repository;

import com.unify.rrls.domain.OpportunityMaster;
import com.unify.rrls.domain.StrategyMaster;
import com.unify.rrls.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.unify.rrls.domain.OpportunitySummaryData;

import java.util.List;

@SuppressWarnings("unused")
@Repository
public interface OpportunitySummaryDataRepository extends JpaRepository<OpportunitySummaryData, Long> {
    List<OpportunitySummaryData>  findByOpportunityMasterid(OpportunityMaster opportunityMaster);
    Page<OpportunitySummaryData> findByStrategyMasterId(StrategyMaster strategyMaster,Pageable pageable);

    @Query(value="select * from opportunity_summary_data  where created_by = ?1 group by opp_master  ORDER BY ?#{#pageable}", nativeQuery = true)
    Page<OpportunitySummaryData> findAllGroupByOpportunityMasterid(String username,Pageable pageable);
}

