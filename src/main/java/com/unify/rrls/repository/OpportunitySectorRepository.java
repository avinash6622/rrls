package com.unify.rrls.repository;


import com.unify.rrls.domain.OpportunityName;
import com.unify.rrls.domain.OpportunitySector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface OpportunitySectorRepository extends JpaRepository<OpportunitySector, Long> {
}
