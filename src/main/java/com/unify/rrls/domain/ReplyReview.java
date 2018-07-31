package com.unify.rrls.domain;


import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "reply_review")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ReplyReview extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "review_id")
    private ReviewExternal reviewExternal;

    @Column(name="reply_text")
    private String replyText;

    @ManyToOne
    @JoinColumn(name = "parent_reply_id")
    private ReplyReview replyReview;

     public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }   

    public String getReplyText() {
        return replyText;
    }

    public void setReplyText(String replyText) {
        this.replyText = replyText;
    }
    
    public ReviewExternal getReviewExternal() {
		return reviewExternal;
	}

	public void setReviewExternal(ReviewExternal reviewExternal) {
		this.reviewExternal = reviewExternal;
	}

	public ReplyReview getReplyReview() {
		return replyReview;
	}

	public void setReplyReview(ReplyReview replyReview) {
		this.replyReview = replyReview;
	}

}
