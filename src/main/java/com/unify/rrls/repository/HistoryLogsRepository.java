package com.unify.rrls.repository;

import com.unify.rrls.domain.HistoryLogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface HistoryLogsRepository extends JpaRepository<HistoryLogs,Integer>{

}