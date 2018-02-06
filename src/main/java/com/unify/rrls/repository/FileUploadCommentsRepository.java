package com.unify.rrls.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unify.rrls.domain.FileUpload;
import com.unify.rrls.domain.FileUploadComments;
import com.unify.rrls.domain.OpportunityMaster;


/**
 * Spring Data JPA repository for the FileUploadComments entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FileUploadCommentsRepository extends JpaRepository<FileUploadComments, Long> {
	 List<FileUploadComments> findByOpportunityMaster(OpportunityMaster opportunityMaster);

}
