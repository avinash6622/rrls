package com.unify.rrls.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unify.rrls.domain.AdditionalFileUpload;
import com.unify.rrls.domain.FixedLearning;
import com.unify.rrls.domain.FixedLearningMapping;
import com.unify.rrls.domain.OpportunityMaster;

@SuppressWarnings("unused")
@Repository
public interface FixedLearningMappingRepository extends JpaRepository<FixedLearningMapping, Long>  {

	List<OpportunityMaster> findByFixedLearning(FixedLearning fixedLearning);
}
