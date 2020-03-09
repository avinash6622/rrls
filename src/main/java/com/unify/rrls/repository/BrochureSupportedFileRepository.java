package com.unify.rrls.repository;
import com.unify.rrls.domain.BrochureSupportingFiles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrochureSupportedFileRepository extends JpaRepository<BrochureSupportingFiles, Long> {

    BrochureSupportingFiles findById(Long id);

}

