package com.unify.rrls.repository;

import com.unify.rrls.domain.HistoryLogs;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface HistoryLogsRepository extends JpaRepository<HistoryLogs,Integer>{
	
	@Query(value="select questions_id FROM `history_logs` where opp_created_by =?1 and action='added' and (questions_id!=0)", nativeQuery = true)
	List<Integer> findByCreatedBy(String user);
	
	@Query(value="select questions_id FROM `history_logs` where opp_created_by !=?1 and action='added' and questions_id!=0 and opp_id in(?2)", nativeQuery = true)
	List<Integer> findByCreatedByAndOpportunity(String user,String opportunity);


}
