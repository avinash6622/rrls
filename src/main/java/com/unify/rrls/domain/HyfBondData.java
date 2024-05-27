package com.unify.rrls.domain;

import javax.persistence.*;

@Entity
@Table(name = "hyf_term_sheet")
public class HyfBondData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "isin")
    private String isin;
    @Column(name = "company_name")
    private String companyName;
    @Column(name = "maturity_date")
    private String maturityDate;
    @Column(name = "file_name")
    private String termSheetFileName;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getIsin() {
        return isin;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getMaturityDate() {
        return maturityDate;
    }

    public void setMaturityDate(String maturityDate) {
        this.maturityDate = maturityDate;
    }

    public String getTermSheetFileName() {
        return termSheetFileName;
    }

    public void setTermSheetFileName(String termSheetFileName) {
        this.termSheetFileName = termSheetFileName;
    }

    @Override
    public String toString() {
        return "BondDataRequest{" +
            "isin='" + isin + '\'' +
            ", companyName='" + companyName + '\'' +
            ", maturityDate='" + maturityDate + '\'' +
            ", termSheetFileName='" + termSheetFileName + '\'' +
            '}';
    }
}
