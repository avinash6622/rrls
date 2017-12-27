package com.unify.rrls.web.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.unify.rrls.domain.OpportunityName;
import com.unify.rrls.repository.OpportunityNameRepository;
import com.unify.rrls.web.rest.util.PaginationUtil;

import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/api")
public class OpportunityNameResource {

	private final Logger log = LoggerFactory.getLogger(OpportunityNameResource.class);

	private static final String ENTITY_NAME = "opportunityName";

	private final OpportunityNameRepository opportunityNameRepository;

	public OpportunityNameResource(OpportunityNameRepository opportunityNameRepository) {
		this.opportunityNameRepository = opportunityNameRepository;
	}

	@GetMapping("/opportunity-names")
	@Timed
	public ResponseEntity<List<OpportunityName>> getAllOpportunityNames(@ApiParam Pageable pageable) {
		log.debug("REST request to get a page of OpportunityNames");
		Page<OpportunityName> page = opportunityNameRepository.findAll(pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/opportunity-names");
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}

}
