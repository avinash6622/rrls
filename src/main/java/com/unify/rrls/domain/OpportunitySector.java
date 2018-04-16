package com.unify.rrls.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "opportunity_sector")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OpportunitySector {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sector_name")
    private String sectorName;
    @Column(name = "sector_types")
    private String sectorTypes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSectorName() {
        return sectorName;
    }

    public void setSectorName(String sectorName) {
        this.sectorName = sectorName;
    }

    public String getSectorTypes() {
        return sectorTypes;
    }

    public void setSectorTypes(String sectorTypes) {
        this.sectorTypes = sectorTypes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OpportunitySector that = (OpportunitySector) o;
        return Objects.equals(id, that.id) &&
            Objects.equals(sectorName, that.sectorName) &&
            Objects.equals(sectorTypes, that.sectorTypes);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, sectorName, sectorTypes);
    }
}
