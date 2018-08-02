package com.unify.rrls.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
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
import com.unify.rrls.domain.FixedLearningMapping;
import com.unify.rrls.domain.LearningAIF;
import com.unify.rrls.domain.LearningAIFMapping;
import com.unify.rrls.domain.OpportunityLearning;
import com.unify.rrls.domain.OpportunityLearningAIF;
import com.unify.rrls.domain.OpportunityMaster;
import com.unify.rrls.repository.LearningAIFRepository;
import com.unify.rrls.repository.LearningsAIFMappingRepository;
import com.unify.rrls.repository.OpportunityLearningAIFRepository;
import com.unify.rrls.web.rest.util.HeaderUtil;
import com.unify.rrls.web.rest.util.PaginationUtil;

import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/api")
public class LearningAIFResource {
	
	@Autowired
	private final LearningAIFRepository learningAIFRepository;
	private final LearningsAIFMappingRepository learningsAIFMappingRepository;
	private final OpportunityLearningAIFRepository opportunityLearningAIFRepository;
	
	private final Logger log = LoggerFactory.getLogger(FixedLearningResource.class);
	
	
	private static final String ENTITY_NAME = "fixedLearning";
	
	public LearningAIFResource(LearningAIFRepository learningAIFRepository,LearningsAIFMappingRepository learningsAIFMappingRepository,
			OpportunityLearningAIFRepository opportunityLearningAIFRepository) {
		this.learningAIFRepository = learningAIFRepository;
		this.learningsAIFMappingRepository=learningsAIFMappingRepository;
		this.opportunityLearningAIFRepository=opportunityLearningAIFRepository;
	}
	
	  @GetMapping("/learning-aif")
	  @Timed
		public ResponseEntity<List<LearningAIF>> getLearningAIF(@ApiParam Pageable pageable) {
			log.debug("REST request to get LearningAIF : {}");
			List<OpportunityMaster> oppMap;
			Page<LearningAIF> pageFixed = null;
			pageFixed = learningAIFRepository.findAll(pageable);
			
			for(LearningAIF fl: pageFixed){
				oppMap=new ArrayList<OpportunityMaster>();
				List<LearningAIFMapping> learningAIFMapping=learningsAIFMappingRepository.findByLearningAIF(fl);
				for(LearningAIFMapping fslm : learningAIFMapping){
					OpportunityMaster om=fslm.getOpportunityMaster();
					om.setOppName(om.getMasterName().getOppName());
				oppMap.add(om);}
				fl.setOpportunityMaster(oppMap);
			}
			
			HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(pageFixed, "/api/fixed-learning");

			return new ResponseEntity<>(pageFixed.getContent(), headers, HttpStatus.OK);
		}
	  
	  @GetMapping("/learning-aif_all")
	  @Timed
		public ResponseEntity<List<LearningAIF>> getAllLearningAIF() {
			log.debug("REST request to get LearningAIF : {}");
		
			List<LearningAIF> pageFixed = null;
			pageFixed = learningAIFRepository.findAll();			
			
			HttpHeaders headers = new HttpHeaders();
			return new ResponseEntity<>(pageFixed, headers, HttpStatus.OK);
		}
	  
	  
	  
	  @PostMapping("/learning-aif")
	  @Timed
		public ResponseEntity<LearningAIF> updateLearningAIF(@RequestBody LearningAIF learningAIF) {
			log.debug("REST request  to update LearningAIF : {}",learningAIF);
			LearningAIFMapping  learningsAIFMapping;
			
			System.out.println("Remove"+learningAIF.getRemoveOpportunityMaster());
			
			LearningAIF learningAIFUpdate=learningAIFRepository.findOne(learningAIF.getId());
			
			List<LearningAIFMapping> checkLearning= learningsAIFMappingRepository.findByLearningAIF(learningAIFUpdate);
			for(LearningAIFMapping flm: checkLearning){				
				learningsAIFMappingRepository.delete(flm);
			}
			LearningAIF result=learningAIFRepository.save(learningAIF);
			for(OpportunityMaster fm:learningAIF.getOpportunityMaster())
			{
				learningsAIFMapping=new LearningAIFMapping();
				learningsAIFMapping.setLearningAIF(result);
				learningsAIFMapping.setOpportunityMaster(fm);
				learningsAIFMappingRepository.save(learningsAIFMapping);
				OpportunityLearningAIF opportunityLearningAIF=opportunityLearningAIFRepository.findByOpportunityMasterAndSubject(fm, result.getSubject());
				System.out.println(opportunityLearningAIF);
				if(opportunityLearningAIF==null){
					opportunityLearningAIF=new OpportunityLearningAIF();
					opportunityLearningAIF.setSubject(result.getSubject());
					opportunityLearningAIF.setOpportunityMaster(fm);
					opportunityLearningAIF.setCreatedBy(fm.getCreatedBy());
					opportunityLearningAIF.setCreatedDate(Instant.now());
					opportunityLearningAIF.setCreatedBy(fm.getLastModifiedBy());
					opportunityLearningAIF.setLastModifiedDate(Instant.now());					
					opportunityLearningAIF.setOppName(fm.getMasterName().getOppName());
					opportunityLearningAIFRepository.save(opportunityLearningAIF);
				}
			}
			
			return new ResponseEntity<>(HttpStatus.OK);
		}

	  @PostMapping("/learning-aif-create")
	  @Timed
		public ResponseEntity<LearningAIF> createLearningAIF(@RequestBody LearningAIF learningAIF) throws URISyntaxException {
			log.debug("REST request  to c LearningAIF : {}",learningAIF);			
				
			LearningAIF result=learningAIFRepository.save(learningAIF);	
			
			 return ResponseEntity.created(new URI("/api/learning-aif" + result.getId()))
					 .headers(HeaderUtil.createAlert( "A Learning is created with identifier " + result.getId().toString(), result.getSubject()))			          
			         .body(result);
		}
	
}
