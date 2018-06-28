package com.unify.rrls.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.unify.rrls.domain.FileUpload;
import com.unify.rrls.domain.OpportunityMaster;
import com.unify.rrls.domain.OpportunityQuestion;

@SuppressWarnings("unused")
@Repository
public interface OpportunityQuestionRepository extends JpaRepository<OpportunityQuestion, Long>  {
	
	 List<OpportunityQuestion> findByOpportunityMaster(OpportunityMaster opportunityMaster);
	 
	@Query(value="SELECT id FROM question_opportunity where opp_mas_id in(?1) and created_by!=?2", nativeQuery = true)
	List<Integer> findByQuestions(String opportunities,String user);

}
