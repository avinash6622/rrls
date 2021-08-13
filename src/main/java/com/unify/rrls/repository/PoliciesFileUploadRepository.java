package com.unify.rrls.repository;

import com.unify.rrls.domain.PoliciesFileUpload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PoliciesFileUploadRepository extends JpaRepository<PoliciesFileUpload, Integer> {
//    List<PoliciesFileUpload> findByPolicies(Policies policies);
    PoliciesFileUpload findById(Integer id);
}
