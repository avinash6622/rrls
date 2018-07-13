package com.unify.rrls.web.rest;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.net.URI;

import com.unify.rrls.domain.OpportunitySector;
import com.unify.rrls.repository.OpportunitySectorRepository;
import com.unify.rrls.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import com.codahale.metrics.annotation.Timed;
import com.unify.rrls.domain.OpportunityName;
import com.unify.rrls.repository.OpportunityNameRepository;
import com.unify.rrls.web.rest.util.PaginationUtil;

import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/api")
public class OpportunityNameResource {

    @Autowired
    NotificationServiceResource notificationServiceResource;

    @Autowired
    UserResource userResource;

	private final Logger log = LoggerFactory.getLogger(OpportunityNameResource.class);

	private static final String ENTITY_NAME = "opportunityName";

	private final OpportunityNameRepository opportunityNameRepository;

	private final OpportunitySectorRepository opportunitySectorRepository;

	public OpportunityNameResource(OpportunityNameRepository opportunityNameRepository,OpportunitySectorRepository opportunitySectorRepository) {
		this.opportunityNameRepository = opportunityNameRepository;
		this.opportunitySectorRepository=opportunitySectorRepository;
	}

	@GetMapping("/opportunity-names")
	@Timed
	public ResponseEntity<List<OpportunityName>> getAllOpportunityNames(@ApiParam Pageable pageable) {
		log.debug("REST request to get a page of OpportunityNames");
		List<OpportunityName> page = opportunityNameRepository.findAll();
        System.out.println("page---->"+page);
		/*HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/opportunity-names");
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);*/
        HttpHeaders headers=new HttpHeaders();
        return new ResponseEntity<>(page, headers,HttpStatus.OK);
	}

    @PostMapping("/opportunity-names")
    @Timed
    public ResponseEntity<OpportunityName> createOpportunityName(@RequestBody OpportunityName opportunityName)
        throws URISyntaxException, IOException, MissingServletRequestParameterException {
        log.debug("REST request to save OpportunityMaster : {}", opportunityName);


        if (opportunityName.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists",
                "A new opportunityName cannot already have an ID")).body(null);
        }

        OpportunityName result = opportunityNameRepository.save(opportunityName);
        System.out.println("sdfsdfsd--->"+result);


        String page = "Opportunity Name";
        Long id =  userResource.getUserId(result.getCreatedBy());

        notificationServiceResource.notificationHistorysave(result.getOppName(),result.getCreatedBy(),result.getLastModifiedBy(),result.getCreatedDate(),"created",page,"",id,Long.parseLong("0"),Long.parseLong("0"),Long.parseLong("0"));


        return ResponseEntity.created(new URI("/api/opportunity-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(result);
    }

    @GetMapping("/opportunity-sector")
    @Timed
    public ResponseEntity<List<OpportunitySector>> getAllSectorNames(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of OpportunityNames");
        List<OpportunitySector> page = opportunitySectorRepository.findAll();
        System.out.println("page---->"+page);
		/*HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/opportunity-names");
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);*/
        HttpHeaders headers=new HttpHeaders();
        return new ResponseEntity<>(page, headers,HttpStatus.OK);
    }


}
