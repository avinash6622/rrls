package com.unify.rrls.web.rest;


import com.codahale.metrics.annotation.Timed;
import com.unify.rrls.domain.AnswerComment;
import com.unify.rrls.domain.ReplyComment;
import com.unify.rrls.repository.ReplyCommentRepository;
import com.unify.rrls.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ReplyCommentResource {

    @Autowired
    NotificationServiceResource notificationServiceResource;

    @Autowired
    UserResource userResource;

    private final ReplyCommentRepository replyCommentRepository;

    private final Logger log = LoggerFactory.getLogger(ReplyCommentResource.class);

    private static final String ENTITY_NAME = "replyComment";


    public ReplyCommentResource(ReplyCommentRepository replyCommentRepository) {
        this.replyCommentRepository = replyCommentRepository;
    }



    @PostMapping("/reply-comment")
    @Timed
    public ResponseEntity<ReplyComment> createReplyComments(@RequestBody ReplyComment replyComment)
        throws URISyntaxException, IOException, MissingServletRequestParameterException {
        log.debug("REST request to save OpportunityMaster : {}", replyComment);


        if (replyComment.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists",
                "A new CommentReply cannot already have an ID")).body(null);
        }

        ReplyComment result = replyCommentRepository.save(replyComment);

        String status=replyComment.getCommentStatuscol();
        String page="Opportunity";
        String subContent=replyComment.getReplyText();

        String name = String.valueOf(result.getCommentOpportunity().getOpportunityMaster().getMasterName().getOppName());
        Long id =  userResource.getUserId(result.getCreatedBy());

        notificationServiceResource.notificationHistorysave(name,result.getCreatedBy(),result.getLastModifiedBy(),result.getCreatedDate(),status,page,subContent,id,result.getCommentOpportunity().getOpportunityMaster().getId(),Long.parseLong("0"),replyComment.getCommentOpportunity().getId());


        return ResponseEntity.created(new URI("/api/answer-comment/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(result);
    }

    @GetMapping("/reply-comment/{id}")
    @Timed
    public ResponseEntity<ReplyComment> getAnswerComments(@PathVariable Long id) {
        log.debug("REST request to get CommentReply : {}", id);

        ReplyComment answerComment = replyCommentRepository.findOne(id);

        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(answerComment));
    }

}
