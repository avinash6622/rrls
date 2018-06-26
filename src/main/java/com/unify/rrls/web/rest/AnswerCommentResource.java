package com.unify.rrls.web.rest;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.unify.rrls.domain.AnswerComment;
import com.unify.rrls.domain.OpportunityQuestion;
import com.unify.rrls.repository.AnswerCommentRepository;
import com.unify.rrls.web.rest.util.HeaderUtil;

import io.github.jhipster.web.util.ResponseUtil;

@RestController
@RequestMapping("/api")
public class AnswerCommentResource {

private final AnswerCommentRepository answerCommentRepository;

	private final Logger log = LoggerFactory.getLogger(AnswerCommentResource.class);

	private static final String ENTITY_NAME = "answerComment";

	  @Autowired
	  NotificationServiceResource notificationServiceResource;

	  @Autowired
	  UserResource userResource;

	public AnswerCommentResource(AnswerCommentRepository answerCommentRepository) {
		this.answerCommentRepository = answerCommentRepository;
	}


    @PostMapping("/answer-comment")
    @Timed
    public ResponseEntity<AnswerComment> createReplyComment(@RequestBody AnswerComment answerComment)
        throws URISyntaxException, IOException, MissingServletRequestParameterException {
        log.debug("REST request to save OpportunityMaster : {}", answerComment);


        if (answerComment.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists",
                "A new CommentReply cannot already have an ID")).body(null);
        }

        AnswerComment result = answerCommentRepository.save(answerComment);
        String status=answerComment.getCommentStatus();
        String page="Opportunity";
        String subContent=answerComment.getAnswerText();

        String name = String.valueOf(result.getOpportunityQuestion().getOpportunityMaster().getMasterName().getOppName());
         Long id =  userResource.getUserId(result.getCreatedBy());
         Long question;
         question=(answerComment.getCommentStatus().equals("Answered")) ? answerComment.getOpportunityQuestion().getId() : answerComment.getAnswerComment().getId();
      
        notificationServiceResource.notificationHistorysave(name,result.getCreatedBy(),result.getLastModifiedBy(),result.getCreatedDate(),status,page,subContent,id,result.getOpportunityQuestion().getOpportunityMaster().getId(),question);


        return ResponseEntity.created(new URI("/api/answer-comment/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(result);
    }

    @GetMapping("/answer-comment/{id}")
	@Timed
	public ResponseEntity<AnswerComment> getAnswerComment(@PathVariable Long id) {
		log.debug("REST request to get CommentReply : {}", id);

		AnswerComment answerComment = answerCommentRepository.findOne(id);

		return ResponseUtil.wrapOrNotFound(Optional.ofNullable(answerComment));
	}

}
