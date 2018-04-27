package com.unify.rrls.domain;


import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "reply_comment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ReplyComment extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private CommentOpportunity commentOpportunity;

    @Column(name="reply_text")
    private String replyText;

    @ManyToOne
    @JoinColumn(name = "parent_reply_id")
    private ReplyComment replyComment;

    @Transient
    @JsonProperty
    private String commentStatuscol;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CommentOpportunity getCommentOpportunity() {
        return commentOpportunity;
    }

    public void setCommentOpportunity(CommentOpportunity commentOpportunity) {
        this.commentOpportunity = commentOpportunity;
    }

    public String getReplyText() {
        return replyText;
    }

    public void setReplyText(String replyText) {
        this.replyText = replyText;
    }

    public ReplyComment getReplyComment() {
        return replyComment;
    }

    public void setReplyComment(ReplyComment replyComment) {
        this.replyComment = replyComment;
    }

    public String getCommentStatuscol() {
        return commentStatuscol;
    }

    public void setCommentStatuscol(String commentStatuscol) {
        this.commentStatuscol = commentStatuscol;
    }
}
