package com.unify.rrls.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "answer_comment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AnswerComment extends AbstractAuditingEntity implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "question_id")
	private OpportunityQuestion opportunityQuestion;
	
	@Column(name="answer_text")
	private String answerText;
	
	@ManyToOne
	@JoinColumn(name = "parent_answer_id")
	private AnswerComment answerComment;
		
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public OpportunityQuestion getOpportunityQuestion() {
		return opportunityQuestion;
	}
	public void setOpportunityQuestion(OpportunityQuestion opportunityQuestion) {
		this.opportunityQuestion = opportunityQuestion;
	}
	public String getAnswerText() {
		return answerText;
	}
	public void setAnswerText(String answerText) {
		this.answerText = answerText;
	}
	public AnswerComment getAnswerComment() {
		return answerComment;
	}
	public void setAnswerComment(AnswerComment answerComment) {
		this.answerComment = answerComment;
	}
	
}