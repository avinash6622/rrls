package com.unify.rrls.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.unify.rrls.domain.OpportunityMaster;
import com.unify.rrls.domain.OpportunitySummaryData;
import com.unify.rrls.domain.StrategyMaster;

import com.unify.rrls.repository.OpportunitySummaryDataRepository;
import com.unify.rrls.repository.StrategyMasterRepository;
import com.unify.rrls.web.rest.util.HeaderUtil;
import com.unify.rrls.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing StrategyMaster.
 */
@RestController
@RequestMapping("/api")
public class StrategyMasterResource {

    @Autowired
    NotificationServiceResource notificationServiceResource;

    @Autowired
    UserResource userResource;

    private final Logger log = LoggerFactory.getLogger(StrategyMasterResource.class);

    private static final String ENTITY_NAME = "strategyMaster";

    private final StrategyMasterRepository strategyMasterRepository;
    private final OpportunitySummaryDataRepository opportunitySummaryDataRepository;

    public StrategyMasterResource(StrategyMasterRepository strategyMasterRepository,OpportunitySummaryDataRepository opportunitySummaryDataRepository) {
        this.strategyMasterRepository = strategyMasterRepository;
        this.opportunitySummaryDataRepository=opportunitySummaryDataRepository;
    }

    /**
     * POST  /strategy-masters : Create a new strategyMaster.
     *
     * @param strategyMaster the strategyMaster to create
     * @return the ResponseEntity with status 201 (Created) and with body the new strategyMaster, or with status 400 (Bad Request) if the strategyMaster has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/strategy-masters")
    @Timed
    public ResponseEntity<StrategyMaster> createStrategyMaster(@Valid @RequestBody StrategyMaster strategyMaster) throws URISyntaxException {
        log.debug("REST request to save StrategyMaster : {}", strategyMaster);
        if (strategyMaster.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new strategyMaster cannot already have an ID")).body(null);
        }
        StrategyMaster result = strategyMasterRepository.save(strategyMaster);

        String page="Strategy";

        String name   = result.getStrategyName();

        Long id =  userResource.getUserId(result.getCreatedBy());


        notificationServiceResource.notificationHistorysave(name,result.getCreatedBy(),result.getLastModifiedBy(),result.getCreatedDate(),"created",page,"",id,result.getId());


        return ResponseEntity.created(new URI("/api/strategy-masters/" + result.getId()))
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
    @PutMapping("/strategy-masters")
    @Timed
    public ResponseEntity<StrategyMaster> updateStrategyMaster(@Valid @RequestBody StrategyMaster strategyMaster) throws URISyntaxException {
        log.debug("REST request to update StrategyMaster : {}", strategyMaster);
        if (strategyMaster.getId() == null) {
            return createStrategyMaster(strategyMaster);
        }
        StrategyMaster result = strategyMasterRepository.save(strategyMaster);

        String name = "Strategy:"+result.getStrategyName()+","+"AUM :"+result.getAum()+","+"Total stocks:"+result.getTotalStocks();
        String page = "Strategy";
        Long id =  userResource.getUserId(result.getCreatedBy());

        notificationServiceResource.notificationHistorysave(name,result.getCreatedBy(),result.getLastModifiedBy(),result.getCreatedDate(),"updated",page,"",id,result.getId());


        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, strategyMaster.getId().toString()))
            .body(result);
    }

    /**
     * GET  /strategy-masters : get all the strategyMasters.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of strategyMasters in body
     */
    @GetMapping("/strategy-masters")
    @Timed
    public ResponseEntity<List<StrategyMaster>> getAllStrategyMasters(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of StrategyMasters");
        Page<StrategyMaster> page = strategyMasterRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/strategy-masters");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /strategy-masters/:id : get the "id" strategyMaster.
     *
     * @param id the id of the strategyMaster to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the strategyMaster, or with status 404 (Not Found)
     */
    @GetMapping("/strategy-masters/{id}")
    @Timed
    public ResponseEntity<StrategyMaster> getStrategyMaster(@PathVariable Long id) {
        log.debug("REST request to get StrategyMaster : {}", id);
        StrategyMaster strategyMaster = strategyMasterRepository.findOne(id);

        List<OpportunitySummaryData> opportunitySummaryData = opportunitySummaryDataRepository.findByStrategyMasterId(strategyMaster);
        strategyMaster.setOpportunitySummaryData(opportunitySummaryData);

        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(strategyMaster));
    }

    /**
     * DELETE  /strategy-masters/:id : delete the "id" strategyMaster.
     *
     * @param id the id of the strategyMaster to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/strategy-masters/{id}")
    @Timed
    public ResponseEntity<Void> deleteStrategyMaster(@PathVariable Long id) {
        log.debug("REST request to delete StrategyMaster : {}", id);
        strategyMasterRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
