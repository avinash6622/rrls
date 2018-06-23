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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.unify.rrls.domain.OpportunityLearning;
import com.unify.rrls.repository.OpportunityLearningRepository;
import com.unify.rrls.web.rest.util.HeaderUtil;
import com.unify.rrls.web.rest.util.PaginationUtil;

import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/api")
public class OpportunityLearningResource {

	private final OpportunityLearningRepository opportunityLearningRepository;

	private final Logger log = LoggerFactory.getLogger(OpportunityQuestionResource.class);

	private static final String ENTITY_NAME = "opportunityLearning";

	 @Autowired
	  NotificationServiceResource notificationServiceResource;

	  @Autowired
	  UserResource userResource;


	public OpportunityLearningResource(OpportunityLearningRepository opportunityLearningRepository) {
		this.opportunityLearningRepository = opportunityLearningRepository;
	}


    @PostMapping("/opportunity-learnings")
    @Timed
    public ResponseEntity<OpportunityLearning> createOpportunityLearning(@RequestBody OpportunityLearning opportunityLearning)
        throws URISyntaxException, IOException, MissingServletRequestParameterException {
        log.debug("REST request to save OpportunityLearning : {}", opportunityLearning);


        if (opportunityLearning.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists",
                "A new OpportunityLearning cannot already have an ID")).body(null);
        }

        String sDescription=opportunityLearning.getDescription().replaceAll("////", "\\");
        opportunityLearning.setDescription(sDescription);
        opportunityLearning.setOppName(opportunityLearning.getOpportunityMaster().getMasterName().getOppName());

        OpportunityLearning result = opportunityLearningRepository.save(opportunityLearning);

        String page="Opportunity";
        String subContent="Learning:"+opportunityLearning.getSubject();

        String name =String.valueOf(result.getOpportunityMaster().getMasterName().getOppName());
         Long id =  userResource.getUserId(result.getCreatedBy());

        notificationServiceResource.notificationHistorysave(name,result.getCreatedBy(),result.getLastModifiedBy(),result.getCreatedDate(),"added",page,subContent,id,result.getOpportunityMaster().getId(),Long.parseLong("0"),Long.parseLong("0"));



        return ResponseEntity.created(new URI("/api/opportunity-learnings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(result);
    }

    @PutMapping("/opportunity-learnings")
    @Timed
    public ResponseEntity<OpportunityLearning> updateOpportunityLearning(@RequestBody OpportunityLearning opportunityLearning)
        throws URISyntaxException, IOException, MissingServletRequestParameterException {
        log.debug("REST request to update OpportunityLearning : {}", opportunityLearning);

        String sDescription=opportunityLearning.getDescription().replaceAll("////", "\\");
        opportunityLearning.setDescription(sDescription);

        OpportunityLearning result = opportunityLearningRepository.save(opportunityLearning);

        String page="Opportunity";
        String subContent="Learning:"+opportunityLearning.getSubject();

        String name =String.valueOf(result.getOpportunityMaster().getMasterName().getOppName());
         Long id =  userResource.getUserId(result.getCreatedBy());

        notificationServiceResource.notificationHistorysave(name,result.getCreatedBy(),result.getLastModifiedBy(),result.getCreatedDate(),"updated",page,subContent,id,result.getOpportunityMaster().getId(),Long.parseLong("0"),Long.parseLong("0"));



        return ResponseEntity.created(new URI("/api/opportunity-learnings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(result);
    }

    @GetMapping("/opportunity-learnings/{id}")
	@Timed
	public ResponseEntity<OpportunityLearning> getOpportunityLearning(@PathVariable Long id) {
		log.debug("REST request to get OpportunityLearning : {}", id);

		OpportunityLearning opportunityLearning = opportunityLearningRepository.findOne(id);

		return ResponseUtil.wrapOrNotFound(Optional.ofNullable(opportunityLearning));
	}

    @PostMapping("/opportunity-learnings-subject")
   	@Timed
   	public ResponseEntity<List<OpportunityLearning>> getLearningSubject(@RequestBody OpportunityLearning subjectLearning) {
   		log.debug("REST request to get OpportunityLearning : {}", subjectLearning.getSubject());

   		List<OpportunityLearning> opportunityLearning = opportunityLearningRepository.findBySubject(subjectLearning.getSubject());

   		return ResponseUtil.wrapOrNotFound(Optional.ofNullable(opportunityLearning));
   	}

    @GetMapping("/opportunity-learnings")
	@Timed
	public ResponseEntity<List<OpportunityLearning>> getAllOpportunityLearnings(@ApiParam Pageable pageable) {
		log.debug("REST request to get a page of OpportunityLearningss");
		Page<OpportunityLearning> page = null;

		page = opportunityLearningRepository.findAll(pageable);

		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/opportunity-learning-consolidated");

		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}


    @GetMapping("/opportunity-learnings/search")
    @Timed
    public ResponseEntity<List<OpportunityLearning>> getAllOpportunityLearningsforauto() {
        log.debug("REST request to get a page of OpportunityLearningss");
        List<OpportunityLearning> page = null;

        page = opportunityLearningRepository.findAllGroupby();

        HttpHeaders headers=new HttpHeaders();
        return new ResponseEntity<>(page, headers,HttpStatus.OK);
    }

    @PostMapping("/opportunity-learning/get-name")
    @Timed
    public ResponseEntity<List<OpportunityLearning>> searchOpportunities(@RequestBody OpportunityLearning opportunityLearning,@ApiParam Pageable pageable) throws URISyntaxException {
        Page<OpportunityLearning> page = opportunityLearningRepository.findByOppName(opportunityLearning.getOppName(),pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/opportunity-masters");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
