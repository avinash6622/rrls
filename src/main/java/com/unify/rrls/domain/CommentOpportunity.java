package com.unify.rrls.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "comment_opportunity")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CommentOpportunity extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "comment_text")
    private String commentText;

    @ManyToOne
    @JoinColumn(name = "opp_mas_id")
    private OpportunityMaster opportunityMaster;

    @OneToMany(mappedBy="commentOpportunity")
    private List<ReplyComment> commentList;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public OpportunityMaster getOpportunityMaster() {
        return opportunityMaster;
    }

    public void setOpportunityMaster(OpportunityMaster opportunityMaster) {
        this.opportunityMaster = opportunityMaster;
    }

    public List<ReplyComment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<ReplyComment> commentList) {
        this.commentList = commentList;
    }
}
