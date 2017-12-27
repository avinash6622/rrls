package com.unify.rrls.repository;

import com.unify.rrls.domain.RoleMaster;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the RoleMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RoleMasterRepository extends JpaRepository<RoleMaster, Long> {

}
