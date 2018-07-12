package com.unify.rrls.web.rest;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.unify.rrls.domain.FixedLearning;
import com.unify.rrls.domain.FixedLearningMapping;
import com.unify.rrls.domain.OpportunityMaster;
import com.unify.rrls.repository.FixedLearningMappingRepository;
import com.unify.rrls.repository.FixedLearningRepository;
import com.unify.rrls.web.rest.util.PaginationUtil;

import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/api")
public class FixedLearningResource {
	
	@Autowired
	private final FixedLearningRepository fixedLearningRepository;
	private final FixedLearningMappingRepository fixedLearningMappingRepository;
	
	private final Logger log = LoggerFactory.getLogger(FixedLearningResource.class);
	
	private static final String ENTITY_NAME = "fixedLearning";
	
	public FixedLearningResource(FixedLearningRepository fixedLearningRepository,FixedLearningMappingRepository fixedLearningMappingRepository) {
		this.fixedLearningRepository = fixedLearningRepository;
		this.fixedLearningMappingRepository=fixedLearningMappingRepository;
	}
	
	  @GetMapping("/fixed-learning")
	  @Timed
		public ResponseEntity<List<FixedLearning>> getFixedLearning(@ApiParam Pageable pageable) {
			log.debug("REST request to get FixedLearning : {}");
			List<OpportunityMaster> oppMap;
			Page<FixedLearning> pageFixed = null;
			pageFixed = fixedLearningRepository.findAll(pageable);
			
			for(FixedLearning fl: pageFixed){
				oppMap=new ArrayList<OpportunityMaster>();
				List<FixedLearningMapping> fixedLearningMapping=fixedLearningMappingRepository.findByFixedLearning(fl);
				for(FixedLearningMapping fslm:fixedLearningMapping){
					OpportunityMaster om=fslm.getOpportunityMaster();
					om.setOppName(om.getMasterName().getOppName());
				oppMap.add(om);}
				fl.setOpportunityMaster(oppMap);
			}
			
			HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(pageFixed, "/api/fixed-learning");

			return new ResponseEntity<>(pageFixed.getContent(), headers, HttpStatus.OK);
		}
	  
	  @PostMapping("/fixed-learning")
	  @Timed
		public ResponseEntity<FixedLearning> updateFixedLearning(@RequestBody FixedLearning fixedLearning) {
			log.debug("REST request  to update FixedLearning : {}",fixedLearning);
			FixedLearningMapping  fixedLearningMapping;
				
			FixedLearning fixedLearningUpdate=fixedLearningRepository.findOne(fixedLearning.getId());
			
			List<FixedLearningMapping> checkLearning= fixedLearningMappingRepository.findByFixedLearning(fixedLearningUpdate);
			for(FixedLearningMapping flm: checkLearning){
				fixedLearningMappingRepository.delete(flm);
			}
			FixedLearning result=fixedLearningRepository.save(fixedLearning);
			for(OpportunityMaster fm:fixedLearning.getOpportunityMaster())
			{
				fixedLearningMapping=new FixedLearningMapping();
				fixedLearningMapping.setFixedLearning(result);
				fixedLearningMapping.setOpportunityMaster(fm);
				fixedLearningMappingRepository.save(fixedLearningMapping);
			}

			return new ResponseEntity<>(HttpStatus.OK);
		}

	
	
}
