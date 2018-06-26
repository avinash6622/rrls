package com.unify.rrls.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.unify.rrls.domain.CommentOpportunity;
import com.unify.rrls.domain.OpportunityQuestion;
import com.unify.rrls.repository.CommentOpportunityRepository;
import com.unify.rrls.web.rest.util.HeaderUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;


@RestController
@RequestMapping("/api")
public class CommentOpportunityResource {

    private final CommentOpportunityRepository commentOpportunityRepository;

    private final Logger log = LoggerFactory.getLogger(CommentOpportunityResource.class);

    private static final String ENTITY_NAME = "commentOpportunity";

    @Autowired
    NotificationServiceResource notificationServiceResource;

    @Autowired
    UserResource userResource;


    public CommentOpportunityResource(CommentOpportunityRepository commentOpportunityRepository) {
        this.commentOpportunityRepository = commentOpportunityRepository;
    }


    @PostMapping("/opportunity-comment")
    @Timed
    public ResponseEntity<CommentOpportunity> createOpportunityComment(@RequestBody CommentOpportunity commentOpportunity)
        throws URISyntaxException, IOException, MissingServletRequestParameterException {
        log.debug("REST request to save OpportunityMaster : {}", commentOpportunity);


        if (commentOpportunity.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists",
                "A new opportunityName cannot already have an ID")).body(null);
        }

        CommentOpportunity result = commentOpportunityRepository.save(commentOpportunity);

        String page="Opportunity";
        String subContent="Comment:"+commentOpportunity.getCommentText();

        String name =String.valueOf(result.getOpportunityMaster().getMasterName().getOppName());
        Long id =  userResource.getUserId(result.getCreatedBy());

        notificationServiceResource.notificationHistorysave(name,result.getCreatedBy(),result.getLastModifiedBy(),result.getCreatedDate(),"added",page,subContent,id,result.getOpportunityMaster().getId(),Long.parseLong("0"));




        return ResponseEntity.created(new URI("/api/opportunity-comment/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(result);
    }
}
