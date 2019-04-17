package com.unify.rrls.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.unify.rrls.domain.OpportunityMaster;
import com.unify.rrls.domain.DueDiligence;


@SuppressWarnings("unused")
@Repository
public interface DueDiligenceRepository extends JpaRepository<DueDiligence, Long>{
    List<DueDiligence> findByOpportunityMasterId(OpportunityMaster opportunityMaster);
    Page<DueDiligence> findByOpportunityMasterId(OpportunityMaster opportunityName,Pageable pageable);
    Page<DueDiligence> findBySubject(String subject,Pageable pageable);
}

