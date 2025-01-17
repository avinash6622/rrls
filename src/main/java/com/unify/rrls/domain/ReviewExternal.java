package com.unify.rrls.domain;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "reviews_external")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ReviewExternal extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reviews_text")
    private String reviewText;

    @ManyToOne
    @JoinColumn(name = "external_ra_id")
    private ExternalResearchAnalyst externalResearchAnalyst;
 
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }    

    public String getReviewText() {
		return reviewText;
	}

	public void setReviewText(String reviewText) {
		this.reviewText = reviewText;
	}

	public ExternalResearchAnalyst getExternalResearchAnalyst() {
		return externalResearchAnalyst;
	}

	public void setExternalResearchAnalyst(ExternalResearchAnalyst externalResearchAnalyst) {
		this.externalResearchAnalyst = externalResearchAnalyst;
	}


}
