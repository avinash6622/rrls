package com.unify.rrls.web.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unify.rrls.repository.LearningsAIFMappingRepository;

@RestController
@RequestMapping("/api")
public class LearningAIFMappingResource {
	
private final LearningsAIFMappingRepository learningsAIFMappingRepository;
	
	private static final String ENTITY_NAME = "fixedMappingLearning";
	
	public LearningAIFMappingResource(LearningsAIFMappingRepository learningsAIFMappingRepository) {
		this.learningsAIFMappingRepository = learningsAIFMappingRepository;
	}

}
