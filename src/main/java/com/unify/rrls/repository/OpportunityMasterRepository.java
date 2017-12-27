package com.unify.rrls.repository;

import com.unify.rrls.domain.OpportunityMaster;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the OpportunityMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OpportunityMasterRepository extends JpaRepository<OpportunityMaster, Long> {

}
