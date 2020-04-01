package com.unify.rrls.domain;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;



@Entity
@Table(name = "presentation_strategy_mapping")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PresentationStrategyMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="presentation_id")
    private PresentationFileUpload presentationFileUpload;

    @ManyToOne
    @JoinColumn(name="strategy_id")
    private StrategyMaster strategyMaster;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PresentationFileUpload getPresentationFileUpload() {
        return presentationFileUpload;
    }

    public void setPresentationFileUpload(PresentationFileUpload presentationFileUpload) {
        this.presentationFileUpload = presentationFileUpload;
    }

    public StrategyMaster getStrategyMaster() {
        return strategyMaster;
    }

    public void setStrategyMaster(StrategyMaster strategyMaster) {
        this.strategyMaster = strategyMaster;
    }
}
