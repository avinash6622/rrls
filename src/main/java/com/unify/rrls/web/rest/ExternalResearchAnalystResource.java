package com.unify.rrls.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.unify.rrls.domain.ExternalResearchAnalyst;
import com.unify.rrls.domain.OpportunitySummaryData;
import com.unify.rrls.domain.StrategyMaster;
import com.unify.rrls.repository.ExternalResearchAnalystRepository;
import com.unify.rrls.web.rest.util.HeaderUtil;
import com.unify.rrls.web.rest.util.PaginationUtil;

import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;

/**
 * REST controller for managing StrategyMaster.
 */
@RestController
@RequestMapping("/api")
public class ExternalResearchAnalystResource {

  

    private final Logger log = LoggerFactory.getLogger(ExternalResearchAnalystResource.class);

    private static final String ENTITY_NAME = "externalResearch";
    @Autowired
    private final ExternalResearchAnalystRepository externalResearchAnalystRepository;
  

    public ExternalResearchAnalystResource(ExternalResearchAnalystRepository externalResearchAnalystRepository) {
        this.externalResearchAnalystRepository = externalResearchAnalystRepository;
       
    }

   
    @PostMapping("/external-research")
    @Timed
    public ResponseEntity<ExternalResearchAnalyst> createExternalResearchAnalyst(@Valid @RequestBody ExternalResearchAnalyst externalResearchAnalyst) throws URISyntaxException {
        log.debug("REST request to save ExternalResearchAnalyst : {}", externalResearchAnalyst);
        if (externalResearchAnalyst.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new ExternalResearchAnalyst cannot already have an ID")).body(null);
        }
        
        ExternalResearchAnalyst result=externalResearchAnalystRepository.save(externalResearchAnalyst);
       
       return ResponseEntity.created(new URI("/api/external-research/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /strategy-masters : Updates an existing strategyMaster.
     *
     * @param strategyMaster the strategyMaster to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated strategyMaster,
     * or with status 400 (Bad Request) if the strategyMaster is not valid,
     * or with status 500 (Internal Server Error) if the strategyMaster couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/external-research")
    @Timed
    public ResponseEntity<ExternalResearchAnalyst> updateExternalResearchAnalyst(@Valid @RequestBody ExternalResearchAnalyst externalResearchAnalyst) throws URISyntaxException {
        log.debug("REST request to update StrategyMaster : {}", externalResearchAnalyst);
        if (externalResearchAnalyst.getId() == null) {
            return createExternalResearchAnalyst(externalResearchAnalyst);
        }
       ExternalResearchAnalyst result = externalResearchAnalystRepository.save(externalResearchAnalyst);
       
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * GET  /strategy-masters : get all the strategyMasters.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of strategyMasters in body
     */
    @GetMapping("/external-research")
    @Timed
    public ResponseEntity<List<ExternalResearchAnalyst>> getAllExternalResearch(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of ExternalResearchAnalysts");
      List<ExternalResearchAnalyst> listAll = externalResearchAnalystRepository.findAll();
      System.out.println(listAll);
        Page<ExternalResearchAnalyst> page = externalResearchAnalystRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/external-research");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

  

    @GetMapping("/external-research/{id}")
    @Timed
    public ResponseEntity<ExternalResearchAnalyst> getExternalResearchAnalyst(@PathVariable Integer id) {
        log.debug("REST request to get ExternalResearchAnalyst : {}", id);
        ExternalResearchAnalyst externalResearchAnalyst = externalResearchAnalystRepository.findOne(id);

       
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(externalResearchAnalyst));
    }

  
   
}
