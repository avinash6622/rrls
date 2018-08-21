package com.unify.rrls.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unify.rrls.domain.CommunicationLetters;
import com.unify.rrls.domain.OpportunityMaster;

@SuppressWarnings("unused")
@Repository
public interface CommunicationLettersRepository extends JpaRepository<CommunicationLetters, Long> {
	List<CommunicationLetters> findByOpportunityMasterId(OpportunityMaster opportunityMaster);
	Page<CommunicationLetters> findByOpportunityMasterId(OpportunityMaster opportunityName,Pageable pageable);
	Page<CommunicationLetters> findBySubject(String subject,Pageable pageable);

}
