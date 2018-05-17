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
    private final OpportunityAutomationRepository opportunityAutomationRepository;
    private final NonFinancialSummaryDataRepository nonFinancialSummaryDataRepository;


    public UserDelegationResource(UserDelegationAuditRepository userDelegationAuditRepository,OpportunityMasterRepository opportunityMasterRepository,OpportunityNameRepository opportunityNameRepository,
                                  OpportunitySummaryDataRepository opportunitySummaryDataRepository,FinancialSummaryDataRepository financialSummaryDataRepository,
                                  OpportunityAutomationRepository opportunityAutomationRepository,NonFinancialSummaryDataRepository nonFinancialSummaryDataRepository){
        this.userDelegationAuditRepository = userDelegationAuditRepository;
        this.opportunityMasterRepository=opportunityMasterRepository;
        this.opportunityNameRepository=opportunityNameRepository;
        this.opportunitySummaryDataRepository=opportunitySummaryDataRepository;
        this.financialSummaryDataRepository=financialSummaryDataRepository;
        this.opportunityAutomationRepository=opportunityAutomationRepository;
        this.nonFinancialSummaryDataRepository=nonFinancialSummaryDataRepository;
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

        System.out.println(result.getCreatedBy());

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

        Long id = userResource.getUserId(result.getCreatedBy());


        notificationServiceResource.notificationHistorysave(result.getOppName(), result.getCreatedBy(), "",
            result.getCreatedDate(), "delegated", page, result.getDeleUserName(), id,master.getId());

        return ResponseEntity.created(new URI("/api/oppName_userName/" + result.getId()))
            .headers(HeaderUtil.createAlert(reponse, result.getId().toString())).body(result);
    }

    @PostMapping("/schedule-marketCap")
    @Timed
    public String checkAutomation()
  {
    List<OpportunityAutomation> automation=opportunityAutomationRepository.findAll();
    for(OpportunityAutomation oa:automation){
   	 if(oa.getOpportunityMaster().getMasterName().getSegment().equals("Finance"))
   	 {
   		 financialCalculation(oa);
   	 }
   	 else{
   		 nonFinancialCalculation(oa);
   	 }
    }
	return null;
 }

private void financialCalculation(OpportunityAutomation calculation) {
FinancialSummaryData financialSummaryData=financialSummaryDataRepository.findByOpportunityMasterId(calculation.getOpportunityMaster());
List<OpportunitySummaryData> opportunitySummaryData=opportunitySummaryDataRepository.findByOpportunityMasterid(calculation.getOpportunityMaster());
if(calculation.getPrevClose()!=null){
	financialSummaryData.setCmp(calculation.getPrevClose());
	financialSummaryDataRepository.save(financialSummaryData);
	for(OpportunitySummaryData sa:opportunitySummaryData){
		sa.setCmp(calculation.getPrevClose());
		opportunitySummaryDataRepository.save(sa);
	}
}
if(calculation.getMarketCap()!=null && calculation.getMarketCap()!=0){
	financialSummaryData.setMarCapThree(calculation.getMarketCap());
	if(financialSummaryData.getPatOne()!=null && financialSummaryData.getPatOne()!=0){
		financialSummaryData.setPeOne(calculation.getMarketCap()/financialSummaryData.getPatOne());
	}
	if(financialSummaryData.getPatTwo()!=null && financialSummaryData.getPatTwo()!=0){
		financialSummaryData.setPeTwo(calculation.getMarketCap()/financialSummaryData.getPatTwo());
	}
	if(financialSummaryData.getPatThree()!=null && financialSummaryData.getPatThree()!=0){
		financialSummaryData.setPeThree(calculation.getMarketCap()/financialSummaryData.getPatThree());
	}
	if(financialSummaryData.getPatFour()!=null && financialSummaryData.getPatFour()!=0){
		financialSummaryData.setPatFour(calculation.getMarketCap()/financialSummaryData.getPatFour());
	}
	if(financialSummaryData.getPatFive()!=null && financialSummaryData.getPatFive()!=0){
		financialSummaryData.setPatFive(calculation.getMarketCap()/financialSummaryData.getPatFive());
	}
	if(financialSummaryData.getNetworthOne()!=null && financialSummaryData.getNetworthOne()!=0){
		financialSummaryData.setPbvOne(calculation.getMarketCap()/financialSummaryData.getNetworthOne());
	}
	if(financialSummaryData.getNetworthTwo()!=null && financialSummaryData.getNetworthTwo()!=0){
		financialSummaryData.setPbvTwo(calculation.getMarketCap()/financialSummaryData.getNetworthTwo());
	}
	if(financialSummaryData.getNetworthThree()!=null && financialSummaryData.getNetworthThree()!=0){
		financialSummaryData.setPbvThree(calculation.getMarketCap()/financialSummaryData.getNetworthThree());
	}
	if(financialSummaryData.getNetworthFour()!=null && financialSummaryData.getNetworthFour()!=0){
		financialSummaryData.setPbvFour(calculation.getMarketCap()/financialSummaryData.getNetworthFour());
	}
	if(financialSummaryData.getNetworthfive()!=null && financialSummaryData.getNetworthfive()!=0){
		financialSummaryData.setPbvFive(calculation.getMarketCap()/financialSummaryData.getNetworthfive());
	}
	financialSummaryDataRepository.save(financialSummaryData);
	for(OpportunitySummaryData sa:opportunitySummaryData){
		sa.setPeFirstYear(financialSummaryData.getPeOne());
		sa.setPeSecondYear(financialSummaryData.getPeTwo());
		sa.setPeThirdYear(financialSummaryData.getPeThree());
		sa.setPeFourthYear(financialSummaryData.getPeFour());
		sa.setPeFifthYear(financialSummaryData.getPeFive());
		sa.setMarketCap(financialSummaryData.getMarCapThree());
		opportunitySummaryDataRepository.save(sa);
	}
}

}

private void nonFinancialCalculation(OpportunityAutomation calculation) {
	NonFinancialSummaryData nonFinancialSummaryData=nonFinancialSummaryDataRepository.findByOpportunityMaster(calculation.getOpportunityMaster());
	List<OpportunitySummaryData> opportunitySummaryData=opportunitySummaryDataRepository.findByOpportunityMasterid(calculation.getOpportunityMaster());
	if(calculation.getPrevClose()!=null){
		nonFinancialSummaryData.setCmp(calculation.getPrevClose());
		nonFinancialSummaryDataRepository.save(nonFinancialSummaryData);
		for(OpportunitySummaryData sa:opportunitySummaryData){
			sa.setCmp(calculation.getPrevClose());
			opportunitySummaryDataRepository.save(sa);
		}
	}
	if(calculation.getMarketCap()!=null && calculation.getMarketCap()!=0){
		nonFinancialSummaryData.setMarketCapThree(calculation.getMarketCap());
		if(nonFinancialSummaryData.getPatOne()!=null && nonFinancialSummaryData.getPatOne()!=0){
			nonFinancialSummaryData.setPeOne(calculation.getMarketCap()/nonFinancialSummaryData.getPatOne());
		}
		if(nonFinancialSummaryData.getPatTwo()!=null && nonFinancialSummaryData.getPatTwo()!=0){
			nonFinancialSummaryData.setPeTwo(calculation.getMarketCap()/nonFinancialSummaryData.getPatTwo());
		}
		if(nonFinancialSummaryData.getPatthree()!=null && nonFinancialSummaryData.getPatthree()!=0){
			nonFinancialSummaryData.setPethree(calculation.getMarketCap()/nonFinancialSummaryData.getPatthree());
		}
		if(nonFinancialSummaryData.getPatfour()!=null && nonFinancialSummaryData.getPatfour()!=0){
			nonFinancialSummaryData.setPatfour(calculation.getMarketCap()/nonFinancialSummaryData.getPatfour());
		}
		if(nonFinancialSummaryData.getPatFive()!=null && nonFinancialSummaryData.getPatFive()!=0){
			nonFinancialSummaryData.setPatFive(calculation.getMarketCap()/nonFinancialSummaryData.getPatFive());
		}
		if(nonFinancialSummaryData.getNetworthOne()!=null && nonFinancialSummaryData.getNetworthOne()!=0){
			nonFinancialSummaryData.setPbOne(calculation.getMarketCap()/nonFinancialSummaryData.getNetworthOne());
		}
		if(nonFinancialSummaryData.getNetworthTwo()!=null && nonFinancialSummaryData.getNetworthTwo()!=0){
			nonFinancialSummaryData.setPbTwo(calculation.getMarketCap()/nonFinancialSummaryData.getNetworthTwo());
		}
		if(nonFinancialSummaryData.getNetworthThree()!=null && nonFinancialSummaryData.getNetworthThree()!=0){
			nonFinancialSummaryData.setPbThree(calculation.getMarketCap()/nonFinancialSummaryData.getNetworthThree());
		}
		if(nonFinancialSummaryData.getNetworthFour()!=null && nonFinancialSummaryData.getNetworthFour()!=0){
			nonFinancialSummaryData.setPbFour(calculation.getMarketCap()/nonFinancialSummaryData.getNetworthFour());
		}
		if(nonFinancialSummaryData.getNetworthFive()!=null && nonFinancialSummaryData.getNetworthFive()!=0){
			nonFinancialSummaryData.setPbFive(calculation.getMarketCap()/nonFinancialSummaryData.getNetworthFive());
		}
		nonFinancialSummaryDataRepository.save(nonFinancialSummaryData);
		for(OpportunitySummaryData sa:opportunitySummaryData){
			sa.setPeFirstYear(nonFinancialSummaryData.getPeOne());
			sa.setPeSecondYear(nonFinancialSummaryData.getPeTwo());
			sa.setPeThirdYear(nonFinancialSummaryData.getPethree());
			sa.setPeFourthYear(nonFinancialSummaryData.getPeFour());
			sa.setPeFifthYear(nonFinancialSummaryData.getPeFive());
			sa.setMarketCap(nonFinancialSummaryData.getMarketCapThree());
			if (nonFinancialSummaryData.getWeight() != null && nonFinancialSummaryData.getWeight() != 0) {
				sa.setbWeight(nonFinancialSummaryData.getWeight());
				sa.setPegOj((nonFinancialSummaryData.getPethree() / nonFinancialSummaryData.getPatGrowthThree()));
				sa.setPortPeFirst(nonFinancialSummaryData.getWeight() * nonFinancialSummaryData.getPeOne());
				sa.setPortPeSecond(nonFinancialSummaryData.getWeight() * nonFinancialSummaryData.getPeTwo());
				sa.setPortPeThird(nonFinancialSummaryData.getWeight() * nonFinancialSummaryData.getPethree());
				sa.setPortPeFourth(nonFinancialSummaryData.getWeight() * nonFinancialSummaryData.getPeFour());
				sa.setPortPeFifth(nonFinancialSummaryData.getWeight() * nonFinancialSummaryData.getPeFive());
				sa.setEarningsSecond((nonFinancialSummaryData.getWeight() * nonFinancialSummaryData.getPatGrowthTwo()) / 100.0);
				sa.setEarningsSecond((nonFinancialSummaryData.getWeight() * nonFinancialSummaryData.getPatGrowthTwo()) / 100.0);
				sa.setEarningsThird((nonFinancialSummaryData.getWeight() * nonFinancialSummaryData.getPatGrowthThree())/ 100.0);
				sa.setEarningsFourth((nonFinancialSummaryData.getWeight() * nonFinancialSummaryData.getPatGrowthFour()) / 100.0);
				sa.setEarningsFifth((nonFinancialSummaryData.getWeight() * nonFinancialSummaryData.getPatGrowthFive()) / 100.0);
				sa.setWtAvgCap((nonFinancialSummaryData.getWeight() * nonFinancialSummaryData.getMarketCapThree())/ 100.0);
				sa.setRoe((nonFinancialSummaryData.getWeight() * nonFinancialSummaryData.getMarketCapThree())/ 100.0);
				sa.setPegYearPeg(nonFinancialSummaryData.getWeight()* (nonFinancialSummaryData.getPethree() / nonFinancialSummaryData.getPatGrowthThree()));
			}
			opportunitySummaryDataRepository.save(sa);
		}
		
	}
}


}
