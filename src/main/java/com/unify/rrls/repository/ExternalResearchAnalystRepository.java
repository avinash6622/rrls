package com.unify.rrls.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unify.rrls.domain.ExternalResearchAnalyst;

@SuppressWarnings("unused")
@Repository
public interface ExternalResearchAnalystRepository extends JpaRepository<ExternalResearchAnalyst, Integer> {


}
