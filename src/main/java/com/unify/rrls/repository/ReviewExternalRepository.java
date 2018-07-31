package com.unify.rrls.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unify.rrls.domain.ExternalResearchAnalyst;
import com.unify.rrls.domain.ReviewExternal;

@SuppressWarnings("unused")
@Repository
public interface ReviewExternalRepository extends JpaRepository<ReviewExternal,Long> {
	
	List<ReviewExternal> findByExternalResearchAnalyst(ExternalResearchAnalyst externalResearchAnalyst);
}
