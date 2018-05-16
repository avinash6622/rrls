package com.unify.rrls.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.Valid;

import com.unify.rrls.domain.*;
import com.unify.rrls.repository.FinancialSummaryDataRepository;
import com.unify.rrls.repository.NonFinancialSummaryDataRepository;
import com.unify.rrls.repository.OpportunityAutomationRepository;
import com.unify.rrls.repository.OpportunityMasterRepository;
import com.unify.rrls.repository.StrategyMasterRepository;
import com.unify.rrls.security.SecurityUtils;

import com.unify.rrls.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.codahale.metrics.annotation.Timed;
import com.unify.rrls.repository.OpportunitySummaryDataRepository;
import com.unify.rrls.web.rest.util.HeaderUtil;

import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/api")
public class OpportunitySummaryDataResource {

	private final Logger log = LoggerFactory.getLogger(OpportunitySummaryDataResource.class);

	private static final String ENTITY_NAME = "opportunitySummaryData";

	private final OpportunitySummaryDataRepository opportunitySummaryDataRepository;

	private final FinancialSummaryDataRepository financialSummaryDataRepository;

	private final NonFinancialSummaryDataRepository nonFinancialSummaryDataRepository;

	private final StrategyMasterRepository strategyMasterRepository;

	private final OpportunityAutomationRepository opportunityAutomationRepository;

	private final OpportunityMasterRepository opportunityMasterRepository;

	public OpportunitySummaryDataResource(OpportunitySummaryDataRepository opportunitySummaryDataRepository,FinancialSummaryDataRepository financialSummaryDataRepository,
                                          NonFinancialSummaryDataRepository nonFinancialSummaryDataRepository,StrategyMasterRepository strategyMasterRepository,
                                          OpportunityAutomationRepository opportunityAutomationRepository,OpportunityMasterRepository opportunityMasterRepository) {
		this.opportunitySummaryDataRepository = opportunitySummaryDataRepository;
		this.financialSummaryDataRepository = financialSummaryDataRepository;
		this.nonFinancialSummaryDataRepository = nonFinancialSummaryDataRepository;
		this.strategyMasterRepository = strategyMasterRepository;
		this.opportunityAutomationRepository=opportunityAutomationRepository;
		this.opportunityMasterRepository=opportunityMasterRepository;
	}

	@PostMapping("/opportunity-summary")
	@Timed
	public String updateOpportunitySummaryData(
        @Valid @RequestBody OpportunityMaster opportunityMaster) throws URISyntaxException {

		OpportunityAutomation opportunityAutomation=new OpportunityAutomation();
		opportunityAutomation=opportunityAutomationRepository.findByOpportunityMaster(opportunityMaster);
		log.debug("REST request to save OpportunitySummaryData : {}", opportunityMaster.getOpportunitySummaryData());
      //  System.out.println("opportunityMaster---->"+opportunityMaster);

		// OpportunitySummaryData result = opportunitySummaryDataRepository.save(opportunityMaster.getOpportunitySummaryData());
		/* FinancialSummaryData financialSummaryData =financialSummaryDataRepository.save(opportunityMaster.getFinancialSummaryData());
		 NonFinancialSummaryData nonFinancialSummaryData = nonFinancialSummaryDataRepository.save(opportunityMaster.getNonFinancialSummaryData());*/
		if(opportunityMaster.getFinancialSummaryData() != null)
        {
        financialSummaryDataRepository.save(opportunityMaster.getFinancialSummaryData());
        List<OpportunitySummaryData> opportunitySummaryDataList = opportunitySummaryDataRepository.findByOpportunityMasterid(opportunityMaster);


        for (OpportunitySummaryData sm : opportunitySummaryDataList) {
           // OpportunitySummaryData opportunitySummaryData = new OpportunitySummaryData();
            sm.setPatFirstYear(opportunityMaster.getFinancialSummaryData().getPatOne());
            sm.setPatSecondYear(opportunityMaster.getFinancialSummaryData().getPatTwo());
            sm.setPatThirdYear(opportunityMaster.getFinancialSummaryData().getPatThree());
            sm.setPatFourthYear(opportunityMaster.getFinancialSummaryData().getPatFour());
            sm.setPatFifthYear(opportunityMaster.getFinancialSummaryData().getPatFive());
            sm.setMarketCap(opportunityMaster.getFinancialSummaryData().getMarCapThree());
            sm.setRoeFirstYear(opportunityMaster.getFinancialSummaryData().getRoeOne());
            sm.setRoeSecondYear(opportunityMaster.getFinancialSummaryData().getRoeTwo());
            sm.setRoeThirdYear(opportunityMaster.getFinancialSummaryData().getRoeThree());
            sm.setRoeFourthYear(opportunityMaster.getFinancialSummaryData().getRoeFour());
            sm.setRoeFifthYear(opportunityMaster.getFinancialSummaryData().getRoeFive());
            sm.setPeFirstYear(opportunityMaster.getFinancialSummaryData().getPeOne());
            sm.setPeSecondYear(opportunityMaster.getFinancialSummaryData().getPeTwo());
            sm.setPeThirdYear(opportunityMaster.getFinancialSummaryData().getPeThree());
            sm.setPeFourthYear(opportunityMaster.getFinancialSummaryData().getPeFour());
            sm.setPeFifthYear(opportunityMaster.getFinancialSummaryData().getPeFive());
            sm.setbWeight(opportunityMaster.getFinancialSummaryData().getWeight());
            if((opportunityAutomation!=null) && (opportunityAutomation.getPrevClose()!=null))
            {
            	sm.setCmp(opportunityAutomation.getPrevClose());
            }
            opportunitySummaryDataRepository.save(sm);

        }

        }
		else if(opportunityMaster.getNonFinancialSummaryData() != null){
        nonFinancialSummaryDataRepository.save(opportunityMaster.getNonFinancialSummaryData());

        List<OpportunitySummaryData> opportunitySummaryDataList = opportunitySummaryDataRepository.findByOpportunityMasterid(opportunityMaster);

        for (OpportunitySummaryData sm : opportunitySummaryDataList) {

            sm.setMarketCap(opportunityMaster.getNonFinancialSummaryData().getMarketCapThree());
            sm.setPatFirstYear(opportunityMaster.getNonFinancialSummaryData().getPatOne());
            sm.setPatSecondYear(opportunityMaster.getNonFinancialSummaryData().getPatTwo());
            sm.setPatThirdYear(opportunityMaster.getNonFinancialSummaryData().getPatthree());
            sm.setPatFourthYear(opportunityMaster.getNonFinancialSummaryData().getPatfour());
            sm.setPatFifthYear(opportunityMaster.getNonFinancialSummaryData().getPatFive());
            sm.setPeFirstYear(opportunityMaster.getNonFinancialSummaryData().getPeOne());
            sm.setPeSecondYear(opportunityMaster.getNonFinancialSummaryData().getPeTwo());
            sm.setPeThirdYear(opportunityMaster.getNonFinancialSummaryData().getPethree());
            sm.setPeFourthYear(opportunityMaster.getNonFinancialSummaryData().getPeFour());
            sm.setPeFifthYear(opportunityMaster.getNonFinancialSummaryData().getPeFive());
            sm.setRoeFirstYear(opportunityMaster.getNonFinancialSummaryData().getRoeOne());
            sm.setRoeSecondYear(opportunityMaster.getNonFinancialSummaryData().getRoeTwo());
            sm.setRoeThirdYear(opportunityMaster.getNonFinancialSummaryData().getRoeThree());
            sm.setRoeFourthYear(opportunityMaster.getNonFinancialSummaryData().getRoeFour());
            sm.setRoeFifthYear(opportunityMaster.getNonFinancialSummaryData().getRoefive());
            sm.setDeFirstYear(opportunityMaster.getNonFinancialSummaryData().getDeOne());
            sm.setDeSecondYear(opportunityMaster.getNonFinancialSummaryData().getDeTwo());
            sm.setDeThirdColour(opportunityMaster.getNonFinancialSummaryData().getDeThree());
            sm.setDeFourthYear(opportunityMaster.getNonFinancialSummaryData().getDeFour());
            sm.setDeFifthYear(opportunityMaster.getNonFinancialSummaryData().getDeFive());
            sm.setPatGrowthFirst(opportunityMaster.getNonFinancialSummaryData().getPatGrowthOne());
            sm.setPatGrowthSecond(opportunityMaster.getNonFinancialSummaryData().getPatGrowthTwo());
            sm.setPatGrowthThird(opportunityMaster.getNonFinancialSummaryData().getPatGrowthThree());
            sm.setPatGrowthFourth(opportunityMaster.getNonFinancialSummaryData().getPatGrowthFour());
            sm.setPatGrowthFifth(opportunityMaster.getNonFinancialSummaryData().getPatGrowthFive());
            sm.setbWeight(opportunityMaster.getNonFinancialSummaryData().getWeight());
            if(opportunityMaster.getNonFinancialSummaryData().getPethree()!=null || opportunityMaster.getNonFinancialSummaryData().getPatGrowthThree()!=null){
            sm.setPegOj(opportunityMaster.getNonFinancialSummaryData().getPethree()/opportunityMaster.getNonFinancialSummaryData().getPatGrowthThree());}
          //  System.out.println("OJ value"+opportunityMaster.getNonFinancialSummaryData().getPethree()/opportunityMaster.getNonFinancialSummaryData().getPatGrowthThree());
            if(opportunityMaster.getNonFinancialSummaryData().getWeight()!=null && opportunityMaster.getNonFinancialSummaryData().getWeight()!=0.0){
            sm.setPortPeFirst(opportunityMaster.getNonFinancialSummaryData().getWeight()*opportunityMaster.getNonFinancialSummaryData().getPeOne());
            sm.setPortPeSecond(opportunityMaster.getNonFinancialSummaryData().getWeight()*opportunityMaster.getNonFinancialSummaryData().getPeTwo());
            sm.setPortPeThird(opportunityMaster.getNonFinancialSummaryData().getWeight()*opportunityMaster.getNonFinancialSummaryData().getPethree());
            sm.setPortPeFourth(opportunityMaster.getNonFinancialSummaryData().getWeight()*opportunityMaster.getNonFinancialSummaryData().getPeFour());
            sm.setPortPeFifth(opportunityMaster.getNonFinancialSummaryData().getWeight()*opportunityMaster.getNonFinancialSummaryData().getPeFive());
           // sm.setEarningsFirst((opportunityMaster.getNonFinancialSummaryData().getWeight()*opportunityMaster.getNonFinancialSummaryData().getPatGrowthOne())/100.0);
            sm.setEarningsSecond((opportunityMaster.getNonFinancialSummaryData().getWeight()*opportunityMaster.getNonFinancialSummaryData().getPatGrowthTwo())/100.0);
            sm.setEarningsThird((opportunityMaster.getNonFinancialSummaryData().getWeight()*opportunityMaster.getNonFinancialSummaryData().getPatGrowthThree())/100.0);
            sm.setEarningsFourth((opportunityMaster.getNonFinancialSummaryData().getWeight()*opportunityMaster.getNonFinancialSummaryData().getPatGrowthFour())/100.0);
            sm.setEarningsFifth((opportunityMaster.getNonFinancialSummaryData().getWeight()*opportunityMaster.getNonFinancialSummaryData().getPatGrowthFive())/100.0);
            sm.setWtAvgCap((opportunityMaster.getNonFinancialSummaryData().getWeight()*opportunityMaster.getNonFinancialSummaryData().getMarketCapThree())/100.0);
            sm.setRoe((opportunityMaster.getNonFinancialSummaryData().getWeight()*opportunityMaster.getNonFinancialSummaryData().getMarketCapThree())/100.0);
            sm.setPegYearPeg(opportunityMaster.getNonFinancialSummaryData().getWeight()*(opportunityMaster.getNonFinancialSummaryData().getPethree()/opportunityMaster.getNonFinancialSummaryData().getPatGrowthThree()));
            }
            if((opportunityAutomation!=null) && (opportunityAutomation.getPrevClose()!=null))
            {
            	sm.setCmp(opportunityAutomation.getPrevClose());
            }
System.out.println(sm);
            opportunitySummaryDataRepository.save(sm);
        }

		}
		else{

		}
		return null;
	}

    @PersistenceContext
    EntityManager em;


    @GetMapping("/opportunity-summary/getdata")
    @Timed
    public ResponseEntity<List<OpportunitySummaryData>> getAllOpportunitySummaryData(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of OpportunityMasters");

        String userName=SecurityUtils.getCurrentUserLogin();
        String role = SecurityUtils.getCurrentRoleLogin();
        /*Query q = em.createNativeQuery("select * from opportunity_summary_data group by opp_master",OpportunitySummaryData.class);

        List<OpportunitySummaryData> page =  q.getResultList();*/
        Page<OpportunitySummaryData> page = null;

        System.out.println("ROLE---->"+role);

        if(role.equals("Master"))
        {
             page = opportunitySummaryDataRepository.findAllGroupby(pageable);
        }
        else{
              page = opportunitySummaryDataRepository.findAllGroupByOpportunityMasterid(userName,pageable);
        }




        List<OpportunitySummaryData> summaryData = new ArrayList<OpportunitySummaryData>();

        for (OpportunitySummaryData opportunitySummaryData:page) {
           // if(opportunitySummaryData.getOpportunityMasterid().getOppStatus() != null) {
              //  if (opportunitySummaryData.getOpportunityMasterid().getCreatedBy().equals(createdBy)) {
                	System.out.println(opportunitySummaryData.getOpportunityMasterid());
                	//if(opportunitySummaryData.getCreatedBy().equals(userName)){
                    List<StrategyMaster> strategyMasterList = getStrategyList(opportunitySummaryData.getOpportunityMasterid().getId());
                    opportunitySummaryData.setStrategyMasterList(strategyMasterList);
                    summaryData.add(opportunitySummaryData);
                	//}

                //}
            //}
        }

      //  System.out.println("LIST---->"+page);
     /*   //HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/opportunity-masters");
        HttpHeaders headers=new HttpHeaders();
       // HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", Long.toString(summaryData.size()));
        return new ResponseEntity<>(summaryData, headers, HttpStatus.OK);*/

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/opportunity-masters");

        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
    @GetMapping("/opportunity-summary/getdata/{createdBy}")
    @Timed
    public ResponseEntity<List<OpportunitySummaryData>> getParticularSummaryData(@PathVariable String createdBy,@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of OpportunityMasters");
/*
        Query q = em.createNativeQuery("select * from opportunity_summary_data group by opp_master",OpportunitySummaryData.class);

        List<OpportunitySummaryData> page =  q.getResultList();*/
        String userName=SecurityUtils.getCurrentUserLogin();
        Page<OpportunitySummaryData> page = opportunitySummaryDataRepository.findAllGroupByOpportunityMasterid(createdBy,pageable);


        List<OpportunitySummaryData> summaryData = new ArrayList<OpportunitySummaryData>();

        for (OpportunitySummaryData opportunitySummaryData:page) {
           // if(opportunitySummaryData.getOpportunityMasterid().getOppStatus() != null) {
                if (opportunitySummaryData.getOpportunityMasterid().getCreatedBy().equals(createdBy)) {
                	System.out.println(opportunitySummaryData.getOpportunityMasterid());
                	//if(opportunitySummaryData.getCreatedBy().equals(userName)){
                    List<StrategyMaster> strategyMasterList = getStrategyList(opportunitySummaryData.getOpportunityMasterid().getId());
                    opportunitySummaryData.setStrategyMasterList(strategyMasterList);
                    summaryData.add(opportunitySummaryData);}

               // }
            //}
        }

        System.out.println(page);
        //HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/opportunity-masters");
      //  HttpHeaders headers=new HttpHeaders();
       // HttpHeaders headers = new HttpHeaders();
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/opportunity-masters");

        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    private List<StrategyMaster> getStrategyList(Long id) {
        System.out.println("vjahsdjhsajj"+id);
        StrategyMaster strategyMaster = new StrategyMaster();

        List<Integer> strategyids =new ArrayList<>();
        List<StrategyMaster> strategyMasters =new ArrayList<>();
        StrategyMaster object;
        Query q = em.createNativeQuery("select strategy_mas_id from opportunity_summary_data where opp_master = "+id+"");
        strategyids=q.getResultList();
        for(Integer sm:strategyids)
        {
        	object=strategyMasterRepository.findOne(sm.longValue());
        	strategyMasters.add(object);
        }

        System.out.println("LIST VAL---->"+strategyMasters);



       /* List<OpportunitySummaryData> results = q.getResultList();

        System.out.println("LIST VAL----->"+results);
        Query q1 =null;

        for (int i = 0; i < results.size(); i++) {


            q1 = em.createNativeQuery("select strategy_name from strategy_master where id = "+results.get(i)+"");
            strategyMasters.addAll(q1.getResultList());


            }
*/

        return strategyMasters;

        }
}
