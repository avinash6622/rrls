package com.unify.rrls.repository;


import com.unify.rrls.domain.UserDelegationAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface UserDelegationAuditRepository extends JpaRepository<UserDelegationAudit,Long> {
}
