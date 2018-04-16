package com.unify.rrls.repository;


import com.unify.rrls.domain.OpportunityMaster;
import com.unify.rrls.domain.OpportunityMasterContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@SuppressWarnings("unused")
@Repository
public interface OpportunityMasterContactRepository extends JpaRepository<OpportunityMasterContact, Long> {
   List<OpportunityMasterContact> findByOpportunityMasterId(OpportunityMaster opportunityMaster);

}
