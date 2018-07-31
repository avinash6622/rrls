package com.unify.rrls.web.rest;


import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.unify.rrls.domain.ReplyComment;
import com.unify.rrls.domain.ReplyReview;
import com.unify.rrls.repository.ReplyReviewRepository;
import com.unify.rrls.web.rest.util.HeaderUtil;

import io.github.jhipster.web.util.ResponseUtil;

@RestController
@RequestMapping("/api")
public class ReplyReviewResource {

    private final ReplyReviewRepository replyReviewRepository;

    private final Logger log = LoggerFactory.getLogger(ReplyReviewResource.class);

    private static final String ENTITY_NAME = "replyReview";


    public ReplyReviewResource(ReplyReviewRepository replyReviewRepository) {
        this.replyReviewRepository = replyReviewRepository;
    }



    @PostMapping("/reply-review")
    @Timed
    public ResponseEntity<ReplyReview> createReplyReview(@RequestBody ReplyReview replyReview)
        throws URISyntaxException, IOException, MissingServletRequestParameterException {
        log.debug("REST request to save replyReview : {}", replyReview);


        if (replyReview.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists",
                "A new replyReview cannot already have an ID")).body(null);
        }

        ReplyReview result =replyReviewRepository.save(replyReview);

        return ResponseEntity.created(new URI("/api/reply-review/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(result);
    }

    @GetMapping("/reply-review/{id}")
    @Timed
    public ResponseEntity<ReplyReview> getAnswerReview(@PathVariable Long id) {
        log.debug("REST request to get CommentReply : {}", id);

        ReplyReview answerReply = replyReviewRepository.findOne(id);

        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(answerReply));
    }

}
