package com.unify.rrls.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.unify.rrls.domain.OpportunityMaster;
import com.unify.rrls.domain.StrategyMapping;
import com.unify.rrls.domain.StrategyMaster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.codahale.metrics.annotation.Timed;
import com.unify.rrls.domain.OpportunitySummaryData;
import com.unify.rrls.repository.OpportunitySummaryDataRepository;
import com.unify.rrls.web.rest.util.HeaderUtil;

@RestController
@RequestMapping("/api")
public class OpportunitySummaryDataResource {

	private final Logger log = LoggerFactory.getLogger(OpportunitySummaryDataResource.class);

	private static final String ENTITY_NAME = "opportunitySummaryData";

	private final OpportunitySummaryDataRepository opportunitySummaryDataRepository;

	public OpportunitySummaryDataResource(OpportunitySummaryDataRepository opportunitySummaryDataRepository) {
		this.opportunitySummaryDataRepository = opportunitySummaryDataRepository;
	}

	@PostMapping("/opportunity-summary")
	@Timed
	public ResponseEntity<OpportunitySummaryData> createOpportunitySummaryData(
			@Valid @RequestBody OpportunitySummaryData opportunitySummaryData) throws URISyntaxException {
		log.debug("REST request to save OpportunitySummaryData : {}", opportunitySummaryData);
		if (opportunitySummaryData.getId() != null) {
			return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists",
					"A new OpportunitySummaryData cannot already have an ID")).body(null);
		}
		OpportunitySummaryData result = opportunitySummaryDataRepository.save(opportunitySummaryData);
		return ResponseEntity.created(new URI("/api/opportunity-summary/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(result);
	}



    @GetMapping("/opportunity-summary/getdata")
    @Timed
    public ResponseEntity<List<OpportunitySummaryData>> getAllOpportunitySummaryData() {
        log.debug("REST request to get a page of OpportunityMasters");
        List<OpportunitySummaryData> page = opportunitySummaryDataRepository.findAll();

        System.out.println(page);
        //HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/opportunity-masters");
        HttpHeaders headers=new HttpHeaders();
        return new ResponseEntity<>(page, headers, HttpStatus.OK);
    }




}
