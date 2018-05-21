package com.unify.rrls.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unify.rrls.domain.AnswerComment;
import com.unify.rrls.domain.OpportunityQuestion;

@SuppressWarnings("unused")
@Repository
public interface AnswerCommentRepository extends JpaRepository<AnswerComment, Long>  {
	
	 List<AnswerComment> findByOpportunityQuestion(OpportunityQuestion opportunityQuestion);
	 List<AnswerComment> findByAnswerComment(AnswerComment answerComment);

}
