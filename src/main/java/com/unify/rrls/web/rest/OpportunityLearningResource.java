package com.unify.rrls.web.rest;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.unify.rrls.domain.OpportunityLearning;
import com.unify.rrls.domain.OpportunityQuestion;
import com.unify.rrls.repository.OpportunityLearningRepository;
import com.unify.rrls.repository.OpportunityQuestionRepository;
import com.unify.rrls.web.rest.util.HeaderUtil;

import io.github.jhipster.web.util.ResponseUtil;

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
        
        OpportunityLearning result = opportunityLearningRepository.save(opportunityLearning);
        
        String page="Opportunity";
        String subContent="Learning:"+opportunityLearning.getSubject();

        String name =String.valueOf(result.getOpportunityMaster().getMasterName().getOppName());
         Long id =  userResource.getUserId(result.getCreatedBy());

        notificationServiceResource.notificationHistorysave(name,result.getCreatedBy(),result.getLastModifiedBy(),result.getCreatedDate(),"added",page,subContent,id);

       

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
}