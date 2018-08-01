package com.unify.rrls.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
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
import com.unify.rrls.domain.ExternalRASector;
import com.unify.rrls.domain.ExternalResearchAnalyst;
import com.unify.rrls.domain.OpportunityMaster;
import com.unify.rrls.domain.OpportunitySector;
import com.unify.rrls.domain.ReplyReview;
import com.unify.rrls.domain.ReviewExternal;
import com.unify.rrls.repository.ExternalRASectorRepository;
import com.unify.rrls.repository.ExternalResearchAnalystRepository;
import com.unify.rrls.repository.OpportunitySectorRepository;
import com.unify.rrls.repository.ReplyReviewRepository;
import com.unify.rrls.repository.ReviewExternalRepository;
import com.unify.rrls.security.SecurityUtils;
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
    private final ReviewExternalRepository reviewExternalRepository;
    private final ReplyReviewRepository replyReviewRepository;
    private final ExternalRASectorRepository externalRASectorRepository;
    private final OpportunitySectorRepository opportunitySectorRepository;
  

    public ExternalResearchAnalystResource(ExternalResearchAnalystRepository externalResearchAnalystRepository,
    		ReviewExternalRepository reviewExternalRepository,ReplyReviewRepository replyReviewRepository,
    		ExternalRASectorRepository externalRASectorRepository,OpportunitySectorRepository opportunitySectorRepository) {
        this.externalResearchAnalystRepository = externalResearchAnalystRepository;
        this.reviewExternalRepository=reviewExternalRepository;
        this.replyReviewRepository=replyReviewRepository;
        this.externalRASectorRepository=externalRASectorRepository;
        this.opportunitySectorRepository=opportunitySectorRepository;
       
    }

   
    @PostMapping("/external-research")
    @Timed
    public ResponseEntity<ExternalResearchAnalyst> createExternalResearchAnalyst( @RequestBody ExternalResearchAnalyst externalResearchAnalyst) throws URISyntaxException {
        log.debug("REST request to save ExternalResearchAnalyst : {}", externalResearchAnalyst.getOpportunitySector());
        if (externalResearchAnalyst.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new ExternalResearchAnalyst cannot already have an ID")).body(null);
        }
        
        ExternalResearchAnalyst result=externalResearchAnalystRepository.save(externalResearchAnalyst);
         for(OpportunitySector es:externalResearchAnalyst.getOpportunitySector())
         {
        	 ExternalRASector sectorSave = new ExternalRASector();
        	 sectorSave.setExternalResearchAnalyst(result);
        	 sectorSave.setSector(es);
        	 externalRASectorRepository.save(sectorSave);
         }
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
      
       List<ExternalRASector> exsa=externalRASectorRepository.findByExternalResearchAnalyst(result);
       for(ExternalRASector ex:exsa){
          	externalRASectorRepository.delete(ex);
          }
       for(OpportunitySector es:externalResearchAnalyst.getOpportunitySector())
       {
      	 ExternalRASector sectorSave = new ExternalRASector();
      	 sectorSave.setExternalResearchAnalyst(result);
      	 sectorSave.setSector(es);
      	 externalRASectorRepository.save(sectorSave);
       }
     
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
        List<ExternalRASector> exsa=externalRASectorRepository.findByExternalResearchAnalyst(externalResearchAnalyst);
        List<OpportunitySector> os=new ArrayList<OpportunitySector>();
        for(ExternalRASector ex:exsa){
        	os.add(ex.getSector());
        }

        externalResearchAnalyst.setOpportunitySector(os);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(externalResearchAnalyst));
    }

	@GetMapping("/external-research/get-reviews/{id}")
	@Timed
	public ResponseEntity<List<ReviewExternal>> getReviews(@PathVariable Integer id) {
		log.debug("REST request to get OpportunityMaster : {}", id);

		ExternalResearchAnalyst externalResearchAnalysts = externalResearchAnalystRepository.findOne(id);
		List<ReviewExternal> reviewExternal = reviewExternalRepository.findByExternalResearchAnalyst(externalResearchAnalysts);
		List<ReviewExternal> commentReply = new ArrayList<>();
		List<ReplyReview> replyreviews;

		for (ReviewExternal comment : reviewExternal) {
			replyreviews = replyReviewRepository.findByReviewExternal(comment);
			comment.setReviewExternalList(replyreviews);
			commentReply.add(comment);
		}

		return ResponseUtil.wrapOrNotFound(Optional.ofNullable(commentReply));
	}
	
	@GetMapping("/external-research-sector")
	@Timed
	public ResponseEntity<List<OpportunitySector>> getOpportunitySectorList() {
	
	    List<OpportunitySector> opportunitySector = opportunitySectorRepository.findAll();  
	
	    HttpHeaders headers=new HttpHeaders();
	    return new ResponseEntity<List<OpportunitySector>>(opportunitySector, headers, HttpStatus.OK);
	}
   
}
