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
import com.unify.rrls.repository.StrategyMasterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.codahale.metrics.annotation.Timed;
import com.unify.rrls.repository.OpportunitySummaryDataRepository;
import com.unify.rrls.web.rest.util.HeaderUtil;

@RestController
@RequestMapping("/api")
public class OpportunitySummaryDataResource {

	private final Logger log = LoggerFactory.getLogger(OpportunitySummaryDataResource.class);

	private static final String ENTITY_NAME = "opportunitySummaryData";

	private final OpportunitySummaryDataRepository opportunitySummaryDataRepository;

	private final FinancialSummaryDataRepository financialSummaryDataRepository;

	private final NonFinancialSummaryDataRepository nonFinancialSummaryDataRepository;

	private final StrategyMasterRepository strategyMasterRepository;

	public OpportunitySummaryDataResource(OpportunitySummaryDataRepository opportunitySummaryDataRepository,FinancialSummaryDataRepository financialSummaryDataRepository,
                                          NonFinancialSummaryDataRepository nonFinancialSummaryDataRepository,StrategyMasterRepository strategyMasterRepository) {
		this.opportunitySummaryDataRepository = opportunitySummaryDataRepository;
		this.financialSummaryDataRepository = financialSummaryDataRepository;
		this.nonFinancialSummaryDataRepository = nonFinancialSummaryDataRepository;
		this.strategyMasterRepository = strategyMasterRepository;
	}

	@PostMapping("/opportunity-summary")
	@Timed
	public String updateOpportunitySummaryData(
        @Valid @RequestBody OpportunityMaster opportunityMaster) throws URISyntaxException {


		log.debug("REST request to save OpportunitySummaryData : {}", opportunityMaster.getFinancialSummaryData()+""+opportunityMaster.getNonFinancialSummaryData()+""+opportunityMaster.getOpportunitySummaryData());
      //  System.out.println("opportunityMaster---->"+opportunityMaster);

		 /*OpportunitySummaryData result = opportunitySummaryDataRepository.save(opportunityMaster.getOpportunitySummaryData());
		 FinancialSummaryData financialSummaryData =financialSummaryDataRepository.save(opportunityMaster.getFinancialSummaryData());
		 NonFinancialSummaryData nonFinancialSummaryData = nonFinancialSummaryDataRepository.save(opportunityMaster.getNonFinancialSummaryData());*/
		/*if(opportunitySummaryData.getOpportunityMasterid().getFinancialSummaryData() != null)
        {
            FinancialSummaryData summaryData = financialSummaryDataRepository.save(opportunitySummaryData.getOpportunityMasterid().getFinancialSummaryData());

        }
       if(opportunitySummaryData.getOpportunityMasterid().getNonFinancialSummaryData() != null){
           NonFinancialSummaryData nonFinancialSummaryData = nonFinancialSummaryDataRepository.save(opportunitySummaryData.getOpportunityMasterid().getNonFinancialSummaryData());

		}*/

		return null;
	}

    @PersistenceContext
    EntityManager em;


    @GetMapping("/opportunity-summary/getdata")
    @Timed
    public ResponseEntity<List<OpportunitySummaryData>> getAllOpportunitySummaryData() {
        log.debug("REST request to get a page of OpportunityMasters");

        Query q = em.createNativeQuery("select * from opportunity_summary_data group by opp_master",OpportunitySummaryData.class);



        List<OpportunitySummaryData> page =  q.getResultList();

        List<OpportunitySummaryData> summaryData = new ArrayList<OpportunitySummaryData>();

        for (OpportunitySummaryData opportunitySummaryData:page) {
            System.out.println("kjhdsfhisdj----->"+opportunitySummaryData);
            List <StrategyMaster> strategyMasterList=getStrategyList(opportunitySummaryData.getOpportunityMasterid().getId());
            opportunitySummaryData.setStrategyMasterList(strategyMasterList);
            summaryData.add(opportunitySummaryData);
        }

        System.out.println(page);
        //HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/opportunity-masters");
        HttpHeaders headers=new HttpHeaders();
        return new ResponseEntity<>(summaryData, headers, HttpStatus.OK);
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
