package com.unify.rrls.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unify.rrls.domain.ExternalRASector;
import com.unify.rrls.domain.ExternalResearchAnalyst;

@SuppressWarnings("unused")
@Repository
public interface ExternalRASectorRepository extends JpaRepository<ExternalRASector, Long>   {
	List<ExternalRASector> findByExternalResearchAnalyst(ExternalResearchAnalyst externalResearchAnalyst);

}