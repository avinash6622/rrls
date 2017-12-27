package com.unify.rrls.repository;

import com.unify.rrls.domain.StrategyMaster;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the StrategyMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StrategyMasterRepository extends JpaRepository<StrategyMaster, Long> {

}
