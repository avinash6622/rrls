package com.unify.rrls.web.rest;


import com.codahale.metrics.annotation.Timed;
import com.unify.rrls.domain.*;
import com.unify.rrls.repository.DeleteNotificationRepository;
import com.unify.rrls.repository.HistoryLogsRepository;
import com.unify.rrls.security.SecurityUtils;
import com.unify.rrls.web.rest.util.HeaderUtil;
import com.unify.rrls.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class NotificationServiceResource {

    private static final String ENTITY_NAME = "historyLogs";

    private final Logger log = LoggerFactory.getLogger(NotificationServiceResource.class);

    @Autowired
   HistoryLogsRepository historyLogsRepository;

    DeleteNotificationRepository deleteNotificationRepository;

    public NotificationServiceResource(HistoryLogsRepository historyLogsRepository,DeleteNotificationRepository deleteNotificationRepository){
        this.historyLogsRepository=historyLogsRepository;
        this.deleteNotificationRepository=deleteNotificationRepository;
    }

    public String notificationHistorysave(String name,String createdBy, String modifiedBy, Instant createdDate, String action, String page,String fileName,Long userId){



        HistoryLogs historyLogs = new HistoryLogs();

        historyLogs.setOppname(name);

        historyLogs.setCreatedBy(createdBy);
        historyLogs.setLastModifiedBy(modifiedBy);
        historyLogs.setCreatedDate(createdDate);
        historyLogs.setAction(action);
        historyLogs.setPage(page);
        historyLogs.setFileNamecontent(fileName);
        historyLogs.setUserId(userId);
        historyLogsRepository.save(historyLogs);

        return null;


     }


    @PersistenceContext
    EntityManager em;


    @GetMapping("/history-logs/{userId}")
    @Timed
    public ResponseEntity<List<HistoryLogs>> getAllHistoryLogs(@PathVariable Integer userId) {
        log.debug("REST request to get a page of OpportunityMasters");
        List<HistoryLogs> list = null;
        List<HistoryLogs> historyLogs = new ArrayList<>();

        System.out.println("id--->"+userId);

        Query q = em.createNativeQuery("select * from history_logs where id not in(select history_log_id from delete_notification where user_id="+userId+" and status = 'deleted') order by id desc limit 20",HistoryLogs.class);

        list   = q.getResultList();

        for(HistoryLogs hl:list){

           // System.out.println(hl.getdStatus());

            if(hl.getdStatus() != null){

                Query q2 = em.createNativeQuery("select history_log_id from delete_notification where status !='deleted' and user_id ="+userId+"");

                Integer id= Integer.parseInt(q2.getSingleResult().toString());

                if(hl.getId() == id )
                {
                    hl.setdStatus("Read");
                }
                else
                {
                    hl.setdStatus("UnRead");
                }

            }

        }








        return new ResponseEntity<>(list,HttpStatus.OK);
    }


    @PostMapping("/store_noti_user_id")
    @Timed
    public ResponseEntity<DeleteNotification> storeNotiUserId(@RequestBody DeleteNotification deleteNotification) {
        log.debug("REST request to get a page of OpportunityMasters");
        List<DeleteNotification> list = null;


        DeleteNotification result1 = null;


        DeleteNotification result = deleteNotificationRepository.findByUserIdAndNotiId(deleteNotification.getUserId(),deleteNotification.getNotiId());

        if(result == null)
        {
          result1 = deleteNotificationRepository.save(deleteNotification);
        }
        else{


            result.setStatus(deleteNotification.getStatus());

            result1 = deleteNotificationRepository.save(result);
        }

        return new ResponseEntity<>(result1,HttpStatus.OK);

    }





}
