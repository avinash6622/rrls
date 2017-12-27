package com.unify.rrls.repository;

import com.unify.rrls.domain.AdditionalFileUpload;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the AdditionalFileUpload entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdditionalFileUploadRepository extends JpaRepository<AdditionalFileUpload, Long> {

}
