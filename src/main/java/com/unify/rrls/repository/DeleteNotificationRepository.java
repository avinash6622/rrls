package com.unify.rrls.repository;

import com.unify.rrls.domain.DeleteNotification;
import com.unify.rrls.domain.HistoryLogs;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface DeleteNotificationRepository extends JpaRepository<DeleteNotification,Integer> {

    DeleteNotification findByUserIdAndNotiId(Integer userId,Integer notiId);
    DeleteNotification findByUserId(Integer userId);
   
    @Query(value="select * from delete_notification where status !='deleted' and user_id =?1 and history_log_id=?2", nativeQuery = true)
    DeleteNotification findByHistoryLog(Integer user,Integer historyId);

}
