package com.unify.rrls.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unify.rrls.domain.ExternalRASector;

@SuppressWarnings("unused")
@Repository
public interface ExternalRASectorRepository extends JpaRepository<ExternalRASector, Long>   {

}
