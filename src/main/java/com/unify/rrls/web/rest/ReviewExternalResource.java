package com.unify.rrls.web.rest;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.unify.rrls.domain.CommentOpportunity;
import com.unify.rrls.domain.ReviewExternal;
import com.unify.rrls.repository.ReviewExternalRepository;
import com.unify.rrls.web.rest.util.HeaderUtil;


@RestController
@RequestMapping("/api")
public class ReviewExternalResource {

    private final ReviewExternalRepository reviewExternalRepository;

    private final Logger log = LoggerFactory.getLogger(ReviewExternalResource.class);

    private static final String ENTITY_NAME = "reviewExternal";  

    @Autowired
    UserResource userResource;


    public ReviewExternalResource(ReviewExternalRepository reviewExternalRepository) {
        this.reviewExternalRepository = reviewExternalRepository;
    }


    @PostMapping("/external-review")
    @Timed
    public ResponseEntity<ReviewExternal> createReviewExternalt(@RequestBody ReviewExternal reviewExternal)
        throws URISyntaxException, IOException, MissingServletRequestParameterException {
        log.debug("REST request to save OpportunityMaster : {}", reviewExternal);


        if (reviewExternal.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists",
                "A new ReviewExternal cannot already have an ID")).body(null);
        }

        ReviewExternal result = reviewExternalRepository.save(reviewExternal);

  
        return ResponseEntity.created(new URI("/api/external-review/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(result);
    }
    
    
}
