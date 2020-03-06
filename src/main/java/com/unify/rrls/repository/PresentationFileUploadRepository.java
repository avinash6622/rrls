package com.unify.rrls.repository;


import com.unify.rrls.domain.PresentationFileUpload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PresentationFileUploadRepository extends JpaRepository<PresentationFileUpload, Long> {

    PresentationFileUpload findById(Long id);



}

