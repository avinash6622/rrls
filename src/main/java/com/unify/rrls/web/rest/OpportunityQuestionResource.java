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
import com.unify.rrls.domain.OpportunityQuestion;
import com.unify.rrls.repository.OpportunityQuestionRepository;
import com.unify.rrls.web.rest.util.HeaderUtil;

import io.github.jhipster.web.util.ResponseUtil;

@RestController
@RequestMapping("/api")
public class OpportunityQuestionResource {

	private final OpportunityQuestionRepository opportunityQuestionRepository;

	private final Logger log = LoggerFactory.getLogger(OpportunityQuestionResource.class);

	private static final String ENTITY_NAME = "opportunityQuestion";
	 @Autowired
	  NotificationServiceResource notificationServiceResource;

	  @Autowired
	  UserResource userResource;

	public OpportunityQuestionResource(OpportunityQuestionRepository opportunityQuestionRepository) {
		this.opportunityQuestionRepository = opportunityQuestionRepository;
	}


    @PostMapping("/opportunity-questions")
    @Timed
    public ResponseEntity<OpportunityQuestion> createOpportunityQuestion(@RequestBody OpportunityQuestion opportunityQuestion)
        throws URISyntaxException, IOException, MissingServletRequestParameterException {
        log.debug("REST request to save OpportunityMaster : {}", opportunityQuestion);


        if (opportunityQuestion.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists",
                "A new opportunityName cannot already have an ID")).body(null);
        }

        OpportunityQuestion result = opportunityQuestionRepository.save(opportunityQuestion);
        String page="Opportunity";
        String subContent="Question:"+opportunityQuestion.getQuestionText();

        String name =String.valueOf(result.getOpportunityMaster().getMasterName().getOppName());
         Long id =  userResource.getUserId(result.getCreatedBy());

        notificationServiceResource.notificationHistorysave(name,result.getCreatedBy(),result.getLastModifiedBy(),result.getCreatedDate(),"added",page,subContent,id,result.getOpportunityMaster().getId(),result.getId(),Long.parseLong("0"));



        return ResponseEntity.created(new URI("/api/opportunity-questions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(result);
    }

    @GetMapping("/opportunity-question/{id}")
	@Timed
	public ResponseEntity<OpportunityQuestion> getOpportunityQuestion(@PathVariable Long id) {
		log.debug("REST request to get OpportunityQuestion : {}", id);

		OpportunityQuestion opportunityQuestion = opportunityQuestionRepository.findOne(id);

		return ResponseUtil.wrapOrNotFound(Optional.ofNullable(opportunityQuestion));
	}
}
