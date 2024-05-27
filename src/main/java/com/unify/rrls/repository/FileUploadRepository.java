package com.unify.rrls.repository;

import com.unify.rrls.domain.FileUpload;
import com.unify.rrls.domain.OpportunityMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data JPA repository for the FileUpload entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FileUploadRepository extends JpaRepository<FileUpload, Long> {
    List<FileUpload> findByOpportunityMasterId(OpportunityMaster opportunityMaster);
    FileUpload findById(Long id);
    FileUpload findByFileName(String name);
    @Query(value="select * from ra_file_upload where file_name = :fileName",nativeQuery = true)
    FileUpload findByFileNames(@Param("fileName") String fileName);
}
