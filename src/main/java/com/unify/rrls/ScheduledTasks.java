package com.unify.rrls;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.unify.rrls.domain.FinancialSummaryData;
import com.unify.rrls.domain.OpportunityAutomation;
import com.unify.rrls.domain.OpportunityMaster;
import com.unify.rrls.repository.FinancialSummaryDataRepository;
import com.unify.rrls.repository.NonFinancialSummaryDataRepository;
import com.unify.rrls.repository.OpportunityAutomationRepository;
import com.unify.rrls.repository.OpportunitySummaryDataRepository;

@Component
public class ScheduledTasks {
	
	 	@Autowired
	    OpportunityAutomationRepository opportunityAutomationRepository;
	 	FinancialSummaryDataRepository financialSummaryDataRepository;
	 	NonFinancialSummaryDataRepository nonFinancialSummaryDataRepository;
	 	OpportunitySummaryDataRepository OpportunitySummaryDataRepository;
	
	  private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);
	  private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");	   
	  
	 /* @Scheduled(cron = "0 * * * * ?")*/
	  public void scheduleTaskWithFixedRate() {
	      logger.info("Fixed Rate Task :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()) );
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
	  }

	private void nonFinancialCalculation(OpportunityAutomation calculation) {
	FinancialSummaryData financialSummaryData=financialSummaryDataRepository.findByOpportunityMasterId(calculation.getOpportunityMaster());
	}

	private void financialCalculation(OpportunityAutomation marketCap) {
		// TODO Auto-generated method stub
		
	}

	

}
