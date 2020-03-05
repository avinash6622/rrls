package com.unify.rrls.repository;

import com.unify.rrls.domain.PresentationStrategyMapping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PresentationStrategyRepository extends JpaRepository<PresentationStrategyMapping, Long>  {
}
