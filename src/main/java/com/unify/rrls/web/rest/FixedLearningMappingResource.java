package com.unify.rrls.web.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unify.rrls.repository.FixedLearningMappingRepository;

@RestController
@RequestMapping("/api")
public class FixedLearningMappingResource {
	
private final FixedLearningMappingRepository fixedLearningMappingRepository;
	
	private static final String ENTITY_NAME = "fixedMappingLearning";
	
	public FixedLearningMappingResource(FixedLearningMappingRepository fixedLearningMappingRepository) {
		this.fixedLearningMappingRepository = fixedLearningMappingRepository;
	}

}
