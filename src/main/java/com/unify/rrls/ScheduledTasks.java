package com.unify.rrls;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.codahale.metrics.annotation.Timed;
import com.unify.rrls.domain.FinancialSummaryData;
import com.unify.rrls.domain.NonFinancialSummaryData;
import com.unify.rrls.domain.OpportunityAutomation;
import com.unify.rrls.domain.OpportunitySummaryData;
import com.unify.rrls.repository.FinancialSummaryDataRepository;
import com.unify.rrls.repository.NonFinancialSummaryDataRepository;
import com.unify.rrls.repository.OpportunityAutomationRepository;
import com.unify.rrls.repository.OpportunitySummaryDataRepository;

@Service
public class ScheduledTasks {
	
	 	@Autowired
	    OpportunityAutomationRepository opportunityAutomationRepository;
	 	@Autowired
	 	FinancialSummaryDataRepository financialSummaryDataRepository;
	 	@Autowired
	 	NonFinancialSummaryDataRepository nonFinancialSummaryDataRepository;
	 	@Autowired
	 	OpportunitySummaryDataRepository opportunitySummaryDataRepository;
	
	  private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);
	  private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");	   
	  
	  @Timed
	/*  @Scheduled(fixedDelay = 10000000, initialDelay = 5000)*/
	  @Scheduled(cron = "0 0 6-7 * * ?")
	  public void scheduleTaskWithFixedRate() {
	      logger.info("Fixed Rate Task :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()) );
	      
	      runSchedulerFunction();
	  	
	  }
	  
	  private void runSchedulerFunction() {
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
	      System.out.println("End");
	  }

	  private void nonFinancialCalculation(OpportunityAutomation calculation) {
		  System.out.println(calculation.getPrevClose()+"-----"+calculation.getOpportunityMaster().getId());
			NonFinancialSummaryData nonFinancialSummaryData=nonFinancialSummaryDataRepository.findByOpportunityMaster(calculation.getOpportunityMaster());
			System.out.println(nonFinancialSummaryData);
			List<OpportunitySummaryData> opportunitySummaryData=opportunitySummaryDataRepository.findByOpportunityMasterid(calculation.getOpportunityMaster());
			if(calculation.getPrevClose()!=null){
				System.out.println(calculation.getPrevClose()+"-----"+calculation.getOpportunityMaster().getId());
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
					nonFinancialSummaryData.setPeFour(calculation.getMarketCap()/nonFinancialSummaryData.getPatfour());
				}
				if(nonFinancialSummaryData.getPatFive()!=null && nonFinancialSummaryData.getPatFive()!=0){
					nonFinancialSummaryData.setPeFive(calculation.getMarketCap()/nonFinancialSummaryData.getPatFive());
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
					if((nonFinancialSummaryData.getPethree()!=null && nonFinancialSummaryData.getPatGrowthThree()!=null)&&
							(nonFinancialSummaryData.getPethree()!=0.0 && nonFinancialSummaryData.getPatGrowthThree()!=0.0))
					sa.setPegOj((nonFinancialSummaryData.getPethree() / nonFinancialSummaryData.getPatGrowthThree()));
					if (nonFinancialSummaryData.getWeight() != null && nonFinancialSummaryData.getWeight() != 0) {
						sa.setbWeight(nonFinancialSummaryData.getWeight());
						if(nonFinancialSummaryData.getPeOne()!=null && nonFinancialSummaryData.getPeOne()!=0.0)
						sa.setPortPeFirst(nonFinancialSummaryData.getWeight() * nonFinancialSummaryData.getPeOne());
						if(nonFinancialSummaryData.getPeTwo()!=null && nonFinancialSummaryData.getPeTwo()!=0.0)
						sa.setPortPeSecond(nonFinancialSummaryData.getWeight() * nonFinancialSummaryData.getPeTwo());
						if(nonFinancialSummaryData.getPethree()!=null && nonFinancialSummaryData.getPethree()!=0.0)
						sa.setPortPeThird(nonFinancialSummaryData.getWeight() * nonFinancialSummaryData.getPethree());
						if(nonFinancialSummaryData.getPeFour()!=null && nonFinancialSummaryData.getPeFour()!=0.0)
						sa.setPortPeFourth(nonFinancialSummaryData.getWeight() * nonFinancialSummaryData.getPeFour());
						if(nonFinancialSummaryData.getPeFive()!=null && nonFinancialSummaryData.getPeFive()!=0.0)
						sa.setPortPeFifth(nonFinancialSummaryData.getWeight() * nonFinancialSummaryData.getPeFive());
						if(nonFinancialSummaryData.getPatGrowthOne()!=null && nonFinancialSummaryData.getPatGrowthOne()!=0.0)
						sa.setEarningsFirst((nonFinancialSummaryData.getWeight() * nonFinancialSummaryData.getPatGrowthOne()) / 100.0);
						if(nonFinancialSummaryData.getPatGrowthTwo()!=null && nonFinancialSummaryData.getPatGrowthTwo()!=0.0)
						sa.setEarningsSecond((nonFinancialSummaryData.getWeight() * nonFinancialSummaryData.getPatGrowthTwo()) / 100.0);
						if(nonFinancialSummaryData.getPatGrowthThree()!=null && nonFinancialSummaryData.getPatGrowthThree()!=0.0)
						sa.setEarningsThird((nonFinancialSummaryData.getWeight() * nonFinancialSummaryData.getPatGrowthThree())/ 100.0);
						if(nonFinancialSummaryData.getPatGrowthFour()!=null && nonFinancialSummaryData.getPatGrowthFour()!=0.0)
						sa.setEarningsFourth((nonFinancialSummaryData.getWeight() * nonFinancialSummaryData.getPatGrowthFour()) / 100.0);
						if(nonFinancialSummaryData.getPatGrowthFive()!=null && nonFinancialSummaryData.getPatGrowthFive()!=0.0)
						sa.setEarningsFifth((nonFinancialSummaryData.getWeight() * nonFinancialSummaryData.getPatGrowthFive()) / 100.0);
						if(nonFinancialSummaryData.getMarketCapThree()!=null && nonFinancialSummaryData.getMarketCapThree()!=0.0)
						sa.setWtAvgCap((nonFinancialSummaryData.getWeight() * nonFinancialSummaryData.getMarketCapThree())/ 100.0);
						if(nonFinancialSummaryData.getRoeThree()!=null && nonFinancialSummaryData.getRoeThree()!=0.0)
						sa.setRoe((nonFinancialSummaryData.getWeight() * nonFinancialSummaryData.getRoeThree())/ 100.0);
						if((nonFinancialSummaryData.getPethree()!=null && nonFinancialSummaryData.getPatGrowthThree()!=null) &&
								nonFinancialSummaryData.getPethree()!=0.0 && nonFinancialSummaryData.getPatGrowthThree()!=0.0)
						sa.setPegYearPeg(nonFinancialSummaryData.getWeight()* (nonFinancialSummaryData.getPethree() / nonFinancialSummaryData.getPatGrowthThree()));
					}
					opportunitySummaryDataRepository.save(sa);
				}
				
			}
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
		financialSummaryData.setPeFour(calculation.getMarketCap()/financialSummaryData.getPatFour());
	}
	if(financialSummaryData.getPatFive()!=null && financialSummaryData.getPatFive()!=0){
		financialSummaryData.setPeFive(calculation.getMarketCap()/financialSummaryData.getPatFive());
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


}
