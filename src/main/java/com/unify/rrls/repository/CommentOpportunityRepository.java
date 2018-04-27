package com.unify.rrls.repository;

import com.unify.rrls.domain.CommentOpportunity;
import com.unify.rrls.domain.OpportunityMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@SuppressWarnings("unused")
@Repository
public interface CommentOpportunityRepository  extends JpaRepository<CommentOpportunity,Long> {
    List<CommentOpportunity> findByOpportunityMaster(OpportunityMaster opportunityMaster);
}
