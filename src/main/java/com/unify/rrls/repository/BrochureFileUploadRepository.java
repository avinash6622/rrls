package com.unify.rrls.repository;

import com.unify.rrls.domain.BrochureFileUpload;
import com.unify.rrls.domain.PresentationFileUpload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface BrochureFileUploadRepository extends JpaRepository<BrochureFileUpload, Long> {
    BrochureFileUpload findById(Long id);
}

