package com.unify.rrls.repository;

import com.unify.rrls.domain.CommentOpportunity;
import com.unify.rrls.domain.ReplyComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@SuppressWarnings("unused")
@Repository
public interface ReplyCommentRepository  extends JpaRepository<ReplyComment,Long>{

    List<ReplyComment> findByCommentOpportunity(CommentOpportunity commentOpportunity);
}
