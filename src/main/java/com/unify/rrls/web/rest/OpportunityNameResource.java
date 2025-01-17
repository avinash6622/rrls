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
import com.unify.rrls.domain.OpportunityMaster;
import com.unify.rrls.repository.OpportunityNameRepository;
import com.unify.rrls.repository.OpportunityMasterRepository;
import com.unify.rrls.web.rest.util.PaginationUtil;
import com.unify.rrls.security.SecurityUtils;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.ArrayList;


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

    private final OpportunityMasterRepository opportunityMasterRepository;


    private final OpportunitySectorRepository opportunitySectorRepository;

    public OpportunityNameResource(OpportunityNameRepository opportunityNameRepository, OpportunityMasterRepository opportunityMasterRepository, OpportunitySectorRepository opportunitySectorRepository) {
        this.opportunityNameRepository = opportunityNameRepository;
        this.opportunityMasterRepository = opportunityMasterRepository;
        this.opportunitySectorRepository = opportunitySectorRepository;
    }

    @GetMapping("/opportunity-names")
    @Timed
    public ResponseEntity<List<OpportunityName>> getAllOpportunityNames(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of OpportunityNames");
        Page<OpportunityName> page = null;
        String role = SecurityUtils.getCurrentRoleLogin();
        String username = SecurityUtils.getCurrentUserLogin();
        page = opportunityNameRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/opportunity-names");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @PostMapping("/opportunity-names")
    @Timed
    public ResponseEntity<OpportunityName> createOpportunityName(@RequestBody OpportunityName opportunityName)
        throws URISyntaxException, IOException, MissingServletRequestParameterException {
        log.debug("REST request to save OpportunityMaster : {}", opportunityName);
        System.out.println("opportunityName.getId()");
        System.out.println(opportunityName.getId());


        if (opportunityName.getId() != null) {

            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists",
                "A new opportunityName already have an ID")).body(null);
        }

        OpportunityName result = opportunityNameRepository.save(opportunityName);
//        System.out.println("sdfsdfsd--->"+result);
        String page = "Opportunity Name";
        Long id = userResource.getUserId(result.getCreatedBy());
        notificationServiceResource.notificationHistorysave(result.getOppName(), result.getCreatedBy(), result.getLastModifiedBy(), result.getCreatedDate(), "created", page, "", id, Long.parseLong("0"), Long.parseLong("0"), Long.parseLong("0"));
        return ResponseEntity.created(new URI("/api/opportunity-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(result);
    }

    @GetMapping("/opportunity-sector")
    @Timed
    public ResponseEntity<List<OpportunitySector>> getAllSectorNames(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of OpportunityNames");
        List<OpportunitySector> page = opportunitySectorRepository.findAll();
//        System.out.println("page---->"+page);
		/*HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/opportunity-names");
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);*/
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(page, headers, HttpStatus.OK);
    }


//    @DeleteMapping("/opportunity-name/{id}")
//    @Timed
//    public ResponseEntity<Void> deleteOpportunityName(@PathVariable Long id) {
//        log.debug("REST request to delete OpportunityName : {}", id);
//        System.out.println(id);
////        opportunityMasterRepository.delete(id);
//        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
//
//    }

    @DeleteMapping("/opportunity-name/{id}")
    @Timed
    public ResponseEntity<Void> deleteOpportunityName(@PathVariable Long id) {
        log.debug("REST request to delete OpportunityName: {}", id);
        OpportunityName opportunityName = opportunityNameRepository.findOne(id);
        System.out.println("After find");
        System.out.println(opportunityName);
        OpportunityMaster opportunityMaster = opportunityMasterRepository.findByMasterName(opportunityName);
        System.out.println("In oppo master");
        System.out.println(opportunityMaster);
        if (opportunityMaster == null) {
            opportunityNameRepository.delete(id);
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("A Opportunity Name is deleted with identifier " + id, id.toString())).build();

        } else {
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "Opportunity Name", "Opportunity Name already in use"))
                .body(null);
        }
    }

    @GetMapping("/opportunity-name-all")
    @Timed
    public ResponseEntity<List<OpportunityName>> opportunityNameList() {
        List<OpportunityName> listName = new ArrayList<OpportunityName>();
        List<OpportunityName> listOpportunity = opportunityNameRepository.findAll();
        for (OpportunityName om : listOpportunity) {
//            System.out.println("om");
//            System.out.println(om);
            om.setOppName(om.getOppName());
            listName.add(om);
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<List<OpportunityName>>(listName, headers, HttpStatus.OK);
    }

//    @GetMapping("/opp-name")
//    @Timed
//    public ResponseEntity<List<OpportunityName>> impotedList(@ApiParam Pageable pageable){
//        log.debug("REST request to get a page of OpportunityNames with categories - Imported");
//        Page<OpportunityName> page = null;
//        String role = SecurityUtils.getCurrentRoleLogin();
//        String username = SecurityUtils.getCurrentUserLogin();
//        page = opportunityNameRepository.findAllByImportedOppoName(pageable);
//        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/opp-name");
//        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
//    }
//    @GetMapping("/oppo-name")
//    @Timed
//    public ResponseEntity<List<OpportunityName>> manualList(@ApiParam Pageable pageable){
//        log.debug("REST request to get a page of OpportunityNames with categories - Manual");
//        Page<OpportunityName> page = null;
//        String role = SecurityUtils.getCurrentRoleLogin();
//        String username = SecurityUtils.getCurrentUserLogin();
//        page = opportunityNameRepository.findAllByManualOppoName(pageable);
//        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/oppo-name");
//        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
//    }

    @GetMapping("/opportunity-names-createdbyisnotnull")
    @Timed
    public ResponseEntity<List<OpportunityName>> createdByIsNotNull(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of OpportunityNames with categories - Manual");
        Page<OpportunityName> page = null;
        String role = SecurityUtils.getCurrentRoleLogin();
        String username = SecurityUtils.getCurrentUserLogin();
        page = opportunityNameRepository.findByCreatedByIsNotNull(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/opportunity-names-createdbyisnotnull");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/opportunity-names-createdbyisnull")
    @Timed
    public ResponseEntity<List<OpportunityName>> createdByIsNull(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of OpportunityNames with categories - Import");
        Page<OpportunityName> page = null;
        String role = SecurityUtils.getCurrentRoleLogin();
        String username = SecurityUtils.getCurrentUserLogin();
        page = opportunityNameRepository.findByCreatedByIsNull(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/opportunity-names-createdbyisnull");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
}
