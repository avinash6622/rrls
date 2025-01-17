package com.unify.rrls.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unify.rrls.domain.ExternalRAFileUpload;
import com.unify.rrls.domain.ExternalResearchAnalyst;
import com.unify.rrls.domain.OpportunityMaster;

@SuppressWarnings("unused")
@Repository
public interface ExternalRAFileUploadRepository extends JpaRepository<ExternalRAFileUpload, Long> {
	List<ExternalRAFileUpload> findByExternalResearchAnalyst(ExternalResearchAnalyst externalResearchAnalyst);
	List<ExternalRAFileUpload> findByOpportunityMasterId(OpportunityMaster opportunityMaster);
    ExternalRAFileUpload findById(Long id);

}
