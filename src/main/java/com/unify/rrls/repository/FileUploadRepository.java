package com.unify.rrls.repository;

import com.unify.rrls.domain.FileUpload;
import com.unify.rrls.domain.OpportunityMaster;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the FileUpload entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FileUploadRepository extends JpaRepository<FileUpload, Long> {
    List<FileUpload> findByOpportunityMasterId(OpportunityMaster opportunityMaster);
    FileUpload findById(Long id);
}
