package com.unify.rrls.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unify.rrls.domain.ExternalRAContacts;
import com.unify.rrls.domain.ExternalRAFileUpload;

@SuppressWarnings("unused")
@Repository
public interface ExternalRAFileUploadRepository extends JpaRepository<ExternalRAFileUpload, Long> {

}
