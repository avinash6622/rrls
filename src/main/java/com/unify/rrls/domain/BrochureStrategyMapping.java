package com.unify.rrls.domain;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

@Entity
@Table(name = "brochure_strategy_mapping")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BrochureStrategyMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="brochure_id")
    private BrochureFileUpload brochureFileUpload;

    @ManyToOne
    @JoinColumn(name="strategy_id")
    private StrategyMaster strategyMaster;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BrochureFileUpload getBrochureFileUpload() {
        return brochureFileUpload;
    }

    public void setBrochureFileUpload(BrochureFileUpload brochureFileUpload) {
        this.brochureFileUpload = brochureFileUpload;
    }

    public StrategyMaster getStrategyMaster() {
        return strategyMaster;
    }

    public void setStrategyMaster(StrategyMaster strategyMaster) {
        this.strategyMaster = strategyMaster;
    }
}
