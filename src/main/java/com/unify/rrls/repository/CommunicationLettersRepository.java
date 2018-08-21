package com.unify.rrls.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unify.rrls.domain.CommunicationLetters;
import com.unify.rrls.domain.OpportunityMaster;

@SuppressWarnings("unused")
@Repository
public interface CommunicationLettersRepository extends JpaRepository<CommunicationLetters, Long> {
	List<CommunicationLetters> findByOpportunityMasterId(OpportunityMaster opportunityMaster);

}
