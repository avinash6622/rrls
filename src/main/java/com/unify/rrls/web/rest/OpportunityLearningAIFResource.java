package com.unify.rrls.web.rest;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.unify.rrls.domain.FixedLearningMapping;
import com.unify.rrls.domain.LearningAIF;
import com.unify.rrls.domain.LearningAIFMapping;
import com.unify.rrls.domain.OpportunityLearning;
import com.unify.rrls.domain.OpportunityLearningAIF;
import com.unify.rrls.repository.LearningAIFRepository;
import com.unify.rrls.repository.LearningsAIFMappingRepository;
import com.unify.rrls.repository.OpportunityLearningAIFRepository;
import com.unify.rrls.web.rest.util.HeaderUtil;
import com.unify.rrls.web.rest.util.PaginationUtil;

import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/api")
public class OpportunityLearningAIFResource {

	private final OpportunityLearningAIFRepository opportunityLearningAIFRepository;
	private final LearningAIFRepository learningAIFRepository;
	private final LearningsAIFMappingRepository learningsAIFMappingRepository;

	private final Logger log = LoggerFactory.getLogger(OpportunityQuestionResource.class);

	private static final String ENTITY_NAME = "opportunityLearning";

	 @Autowired
	  NotificationServiceResource notificationServiceResource;

	  @Autowired
	  UserResource userResource;


	public OpportunityLearningAIFResource(OpportunityLearningAIFRepository opportunityLearningAIFRepository,
			LearningAIFRepository learningAIFRepository,LearningsAIFMappingRepository learningsAIFMappingRepository) {
		this.opportunityLearningAIFRepository = opportunityLearningAIFRepository;
		this.learningAIFRepository=learningAIFRepository;
		this.learningsAIFMappingRepository=learningsAIFMappingRepository;
	}


    @PostMapping("/opportunity-learnings-aif")
    @Timed
    public ResponseEntity<OpportunityLearningAIF> createOpportunityLearningAIF(@RequestBody OpportunityLearningAIF opportunityLearningAIF)
        throws URISyntaxException, IOException, MissingServletRequestParameterException {
        log.debug("REST request to save OpportunityLearningAIF : {}", opportunityLearningAIF);


        if (opportunityLearningAIF.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists",
                "A new OpportunityLearningAIF cannot already have an ID")).body(null);
        }
        
        String sDescription=opportunityLearningAIF.getDescription().replaceAll("////", "\\");
        opportunityLearningAIF.setDescription(sDescription);
        opportunityLearningAIF.setOppName(opportunityLearningAIF.getOpportunityMaster().getMasterName().getOppName());

        OpportunityLearningAIF result = opportunityLearningAIFRepository.save(opportunityLearningAIF);
        
        /* To store fixed learning before tagging*/
        LearningAIF learningAIF=learningAIFRepository.findBySubject(result.getSubject());
        if(learningAIF!=null){
        LearningAIFMapping learningAIFMapping=new LearningAIFMapping();
        learningAIFMapping.setOpportunityMaster(opportunityLearningAIF.getOpportunityMaster());
        learningAIFMapping.setLearningAIF(learningAIF); 
        learningsAIFMappingRepository.save(learningAIFMapping);
        }
        
        String page="Opportunity";
        String subContent="Learning AIF:"+opportunityLearningAIF.getSubject();

        String name =String.valueOf(result.getOpportunityMaster().getMasterName().getOppName());
         Long id =  userResource.getUserId(result.getCreatedBy());

        notificationServiceResource.notificationHistorysave(name,result.getCreatedBy(),result.getLastModifiedBy(),result.getCreatedDate(),"added",page,subContent,id,result.getOpportunityMaster().getId(),Long.parseLong("0"),Long.parseLong("0"));



        return ResponseEntity.created(new URI("/api/opportunity-learnings-aif/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(result);
    }

    @PutMapping("/opportunity-learnings-aif")
    @Timed
    public ResponseEntity<OpportunityLearningAIF> updateOpportunityLearningAIF(@RequestBody OpportunityLearningAIF opportunityLearningAIF)
        throws URISyntaxException, IOException, MissingServletRequestParameterException {
        log.debug("REST request to update OpportunityLearning : {}", opportunityLearningAIF);

        String sDescription=opportunityLearningAIF.getDescription().replaceAll("////", "\\");
        opportunityLearningAIF.setDescription(sDescription);

        OpportunityLearningAIF result = opportunityLearningAIFRepository.save(opportunityLearningAIF);

        String page="Opportunity";
        String subContent="Learning AIF:"+opportunityLearningAIF.getSubject();

        String name =String.valueOf(result.getOpportunityMaster().getMasterName().getOppName());
         Long id =  userResource.getUserId(result.getCreatedBy());

        notificationServiceResource.notificationHistorysave(name,result.getCreatedBy(),result.getLastModifiedBy(),result.getCreatedDate(),"updated",page,subContent,id,result.getOpportunityMaster().getId(),Long.parseLong("0"),Long.parseLong("0"));



        return ResponseEntity.created(new URI("/api/opportunity-learnings-aif/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(result);
    }

    @GetMapping("/opportunity-learnings-aif/{id}")
	@Timed
	public ResponseEntity<OpportunityLearningAIF> getOpportunityLearningAIF(@PathVariable Long id) {
		log.debug("REST request to get OpportunityLearningAIF : {}", id);

		OpportunityLearningAIF opportunityLearningAIF = opportunityLearningAIFRepository.findOne(id);

		return ResponseUtil.wrapOrNotFound(Optional.ofNullable(opportunityLearningAIF));
	}

    @PostMapping("/opportunity-learnings-aif-subject")
   	@Timed
   	public ResponseEntity<List<OpportunityLearningAIF>> getLearningSubject(@RequestBody OpportunityLearningAIF subjectLearning) {
   		log.debug("REST request to get OpportunityLearningAIF : {}", subjectLearning.getSubject());

   		List<OpportunityLearningAIF> opportunityLearningAIF = opportunityLearningAIFRepository.findBySubject(subjectLearning.getSubject());

   		return ResponseUtil.wrapOrNotFound(Optional.ofNullable(opportunityLearningAIF));
   	}

    @GetMapping("/opportunity-learnings-aif")
	@Timed
	public ResponseEntity<List<OpportunityLearningAIF>> getAllOpportunityLearningAIF(@ApiParam Pageable pageable) {
		log.debug("REST request to get a page of OpportunityLearningsAIF");
		Page<OpportunityLearningAIF> page = null;

		page = opportunityLearningAIFRepository.findAll(pageable);

		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/opportunity-learning-consolidated");

		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}


    @GetMapping("/opportunity-learnings-aif/search")
    @Timed
    public ResponseEntity<List<OpportunityLearningAIF>> getAllOpportunityLearningsforauto() {
        log.debug("REST request to get a page of OpportunityLearningsAIF");
        List<OpportunityLearningAIF> page = null;

        page = opportunityLearningAIFRepository.findAllGroupby();

        HttpHeaders headers=new HttpHeaders();
        return new ResponseEntity<>(page, headers,HttpStatus.OK);
    }

    @PostMapping("/opportunity-learnings-aif/get-name")
    @Timed
    public ResponseEntity<List<OpportunityLearningAIF>> searchOpportunities(@RequestBody OpportunityLearningAIF opportunityLearningAIF,@ApiParam Pageable pageable) throws URISyntaxException {
        Page<OpportunityLearningAIF> page = opportunityLearningAIFRepository.findByOppName(opportunityLearningAIF.getOppName(),pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/opportunity-masters");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

 }
