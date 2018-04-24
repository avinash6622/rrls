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

        Query q = em.createNativeQuery("select * from history_logs where id not in(select history_log_id from delete_notification where user_id="+userId+" and status = 'deleted') order by id desc limit 5",HistoryLogs.class);




       /* Query q = em.createNativeQuery("\n" +
            "select id,name,opp_created_by,opp_last_modified_by,opp_created_date,action,page,sub_content,user_id,'Read' from history_logs where id not in(select noti_id from delete_notification where user_id="+userId+" and status='deleted') order by id desc limit 5");



        rows = q.getResultList();
        for(Object[] row : rows){
            HistoryLogs emp = new HistoryLogs();
            emp.setId(Integer.parseInt(row[0].toString()));
            emp.setOppname(row[1].toString());
            emp.setCreatedBy(row[2].toString());
            emp.setLastModifiedBy(row[3].toString());
            emp.setCreatedDate(row[4]);
            emp.setAction(row[5].toString());
            emp.setPage(row[6].toString());
            emp.setFileNamecontent(row[7].toString());
            emp.setUserId(Long.valueOf(row[8].toString()));
            emp.setdStatus(row[9].toString());
            historyLogs.add(emp);


        }*/




        list   = q.getResultList();

        for(HistoryLogs hl:list){

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



        System.out.println(list);


        // System.out.println("jhdsfjsjkdh"+statusList);

        return new ResponseEntity<>(list,HttpStatus.OK);
    }


    @PostMapping("/store_noti_user_id")
    @Timed
    public ResponseEntity<DeleteNotification> storeNotiUserId(@RequestBody DeleteNotification deleteNotification) {
        log.debug("REST request to get a page of OpportunityMasters");
        List<DeleteNotification> list = null;
        System.out.println(deleteNotification.getNotiId());
        System.out.println(deleteNotification.getUserId());
        System.out.println(deleteNotification.getStatus());

        DeleteNotification result1 = null;


        DeleteNotification result = deleteNotificationRepository.findByUserIdAndNotiId(deleteNotification.getUserId(),deleteNotification.getNotiId());
        System.out.println("hjksahdkj--->"+result);
        if(result == null)
        {
          result1 = deleteNotificationRepository.save(deleteNotification);
        }
        else{
           // System.out.println("entered else");

            result.setStatus(deleteNotification.getStatus());

            result1 = deleteNotificationRepository.save(result);
        }

        return new ResponseEntity<>(result1,HttpStatus.OK);

    }





}
