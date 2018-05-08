package com.unify.rrls.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.unify.rrls.domain.*;
import com.unify.rrls.repository.*;
import com.unify.rrls.security.SecurityUtils;
import com.unify.rrls.web.rest.util.HeaderUtil;
import com.unify.rrls.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hibernate.id.IdentifierGenerator.ENTITY_NAME;

@RestController
@RequestMapping("/api")
public class UserDelegationResource {

    @Autowired
    NotificationServiceResource notificationServiceResource;


    @Autowired
    UserResource userResource;

    private static final String ENTITY_NAME = "userDelegationAudit";


    private final Logger log = LoggerFactory.getLogger(UserDelegationResource.class);
    private final UserDelegationAuditRepository userDelegationAuditRepository;
    private final OpportunityMasterRepository opportunityMasterRepository;
    private final OpportunityNameRepository opportunityNameRepository;
    private final OpportunitySummaryDataRepository opportunitySummaryDataRepository;
    private final FinancialSummaryDataRepository financialSummaryDataRepository;


    public UserDelegationResource(UserDelegationAuditRepository userDelegationAuditRepository,OpportunityMasterRepository opportunityMasterRepository,OpportunityNameRepository opportunityNameRepository,
                                  OpportunitySummaryDataRepository opportunitySummaryDataRepository,FinancialSummaryDataRepository financialSummaryDataRepository){
        this.userDelegationAuditRepository = userDelegationAuditRepository;
        this.opportunityMasterRepository=opportunityMasterRepository;
        this.opportunityNameRepository=opportunityNameRepository;
        this.opportunitySummaryDataRepository=opportunitySummaryDataRepository;
        this.financialSummaryDataRepository=financialSummaryDataRepository;
    }


    @GetMapping("/opportunity-masters/createdby")
    @Timed
    public ResponseEntity<List<OpportunityMaster>> getAllOpportunityMasters() {
        log.debug("REST request to get a page of OpportunityMasters");
        List<OpportunityMaster> page = null;
        String role = SecurityUtils.getCurrentRoleLogin();
        String username = SecurityUtils.getCurrentUserLogin();


        page = opportunityMasterRepository.findAllByCreatedBy(username);
        System.out.println(page);

        HttpHeaders headers=new HttpHeaders();
        return new ResponseEntity<>(page, headers,HttpStatus.OK);

    }






    @PostMapping("/oppName_userName")
    @Timed
    public ResponseEntity<UserDelegationAudit> userDelegation(@RequestBody UserDelegationAudit userDelegationAudit)
        throws URISyntaxException, IOException {
        // log.debug("REST request to update OpportunityMaster : {}",
        // opportunityMaster);

        String username = SecurityUtils.getCurrentUserLogin();

        UserDelegationAudit result =userDelegationAuditRepository.save(userDelegationAudit);

        System.out.println(result.getCretedBy());

        OpportunityName opportunityName = opportunityNameRepository.findByOppName(result.getOppName());

        OpportunityMaster master = opportunityMasterRepository.findByMasterName(opportunityName);

        master.setCreatedBy(result.getDeleUserName());
        opportunityMasterRepository.save(master);
        List<OpportunitySummaryData> opportunitySummaryData = opportunitySummaryDataRepository.findByOpportunityMasterid(master);

        for(OpportunitySummaryData opportunitySummaryData1:opportunitySummaryData)
        {
            opportunitySummaryData1.setCreatedBy(result.getDeleUserName());
            opportunitySummaryDataRepository.save(opportunitySummaryData1);
        }

        String reponse = username+" "+"delegated"+" "+result.getOppName()+" "+"to "+result.getDeleUserName();

        String page = "Opportunity";

        Long id = userResource.getUserId(result.getCretedBy());


        notificationServiceResource.notificationHistorysave(result.getOppName(), result.getCretedBy(), "",
            result.getCreatedDate(), "delegated", page, result.getDeleUserName(), id,master.getId());

        return ResponseEntity.created(new URI("/api/oppName_userName/" + result.getId()))
            .headers(HeaderUtil.createAlert(reponse, result.getId().toString())).body(result);
    }



}