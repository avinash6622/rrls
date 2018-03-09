package com.unify.rrls.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.unify.rrls.domain.*;
import com.unify.rrls.repository.FinancialSummaryDataRepository;
import com.unify.rrls.repository.NonFinancialSummaryDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.codahale.metrics.annotation.Timed;
import com.unify.rrls.repository.OpportunitySummaryDataRepository;
import com.unify.rrls.web.rest.util.HeaderUtil;

@RestController
@RequestMapping("/api")
public class OpportunitySummaryDataResource {

	private final Logger log = LoggerFactory.getLogger(OpportunitySummaryDataResource.class);

	private static final String ENTITY_NAME = "opportunitySummaryData";

	private final OpportunitySummaryDataRepository opportunitySummaryDataRepository;

	private final FinancialSummaryDataRepository financialSummaryDataRepository;

	private final NonFinancialSummaryDataRepository nonFinancialSummaryDataRepository;

	public OpportunitySummaryDataResource(OpportunitySummaryDataRepository opportunitySummaryDataRepository,FinancialSummaryDataRepository financialSummaryDataRepository,
                                          NonFinancialSummaryDataRepository nonFinancialSummaryDataRepository) {
		this.opportunitySummaryDataRepository = opportunitySummaryDataRepository;
		this.financialSummaryDataRepository = financialSummaryDataRepository;
		this.nonFinancialSummaryDataRepository = nonFinancialSummaryDataRepository;
	}

	@PostMapping("/opportunity-summary")
	@Timed
	public String updateOpportunitySummaryData(
        @Valid @RequestBody OpportunityMaster opportunityMaster) throws URISyntaxException {


		log.debug("REST request to save OpportunitySummaryData : {}", opportunityMaster.getFinancialSummaryData()+""+opportunityMaster.getNonFinancialSummaryData()+""+opportunityMaster.getOpportunitySummaryData());
      //  System.out.println("opportunityMaster---->"+opportunityMaster);

		/* OpportunitySummaryData result = opportunitySummaryDataRepository.save(opportunityMaster.getOpportunitySummaryData());
		 FinancialSummaryData financialSummaryData =financialSummaryDataRepository.save(opportunityMaster.getFinancialSummaryData());
		 NonFinancialSummaryData nonFinancialSummaryData = nonFinancialSummaryDataRepository.save(opportunityMaster.getNonFinancialSummaryData());*/
		/*if(opportunitySummaryData.getOpportunityMasterid().getFinancialSummaryData() != null)
        {
            FinancialSummaryData summaryData = financialSummaryDataRepository.save(opportunitySummaryData.getOpportunityMasterid().getFinancialSummaryData());

        }
       if(opportunitySummaryData.getOpportunityMasterid().getNonFinancialSummaryData() != null){
           NonFinancialSummaryData nonFinancialSummaryData = nonFinancialSummaryDataRepository.save(opportunitySummaryData.getOpportunityMasterid().getNonFinancialSummaryData());

		}*/

		return null;
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
