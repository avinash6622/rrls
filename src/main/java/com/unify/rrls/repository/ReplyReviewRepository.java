package com.unify.rrls.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unify.rrls.domain.ReplyReview;
import com.unify.rrls.domain.ReviewExternal;

@SuppressWarnings("unused")
@Repository
public interface ReplyReviewRepository extends JpaRepository<ReplyReview,Long>  {
	
	List<ReplyReview> findByReviewExternal(ReviewExternal reviewExternal);

}
