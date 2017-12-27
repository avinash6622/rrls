package com.unify.rrls.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unify.rrls.domain.FileUpload;
import com.unify.rrls.domain.OpportunityMaster;
import com.unify.rrls.domain.StrategyMapping;

@SuppressWarnings("unused")
@Repository
public interface StrategyMappingRepository extends JpaRepository<StrategyMapping, Long>  {
	
	List<StrategyMapping> findByOpportunityMaster(OpportunityMaster opportunityMaster);

}
