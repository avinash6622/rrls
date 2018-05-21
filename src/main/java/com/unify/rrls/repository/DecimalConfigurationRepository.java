package com.unify.rrls.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unify.rrls.domain.DecimalConfiguration;
import com.unify.rrls.domain.User;

@SuppressWarnings("unused")
@Repository
public interface DecimalConfigurationRepository extends JpaRepository<DecimalConfiguration, Long> {
	
	DecimalConfiguration findByUser(User user);

}
