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
	
	@Query(value="SELECT questions_id FROM `history_logs` where opp_created_by =?1 and action='added' and (questions_id!=0 or questions_id is not null)", nativeQuery = true)
	List<Integer> findByCreatedBy(String user);

}
