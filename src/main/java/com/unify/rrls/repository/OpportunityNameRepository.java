package com.unify.rrls.repository;

import com.unify.rrls.domain.OpportunityName;
import com.unify.rrls.domain.StrategyMaster;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the StrategyMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OpportunityNameRepository extends JpaRepository<OpportunityName, Long> {
    OpportunityName findByOppName(String oppname);
}
