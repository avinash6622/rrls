package com.unify.rrls.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unify.rrls.domain.FinancialSummaryData;
import com.unify.rrls.domain.OpportunitySummaryData;

@SuppressWarnings("unused")
@Repository
public interface FinancialSummaryDataRepository extends JpaRepository<FinancialSummaryData, Long>  {

}
