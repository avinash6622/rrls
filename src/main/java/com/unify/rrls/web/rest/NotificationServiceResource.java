package com.unify.rrls.web.rest;


import com.codahale.metrics.annotation.Timed;
import com.unify.rrls.domain.*;
import com.unify.rrls.repository.DeleteNotificationRepository;
import com.unify.rrls.repository.HistoryLogsRepository;
import com.unify.rrls.repository.OpportunityMasterRepository;
import com.unify.rrls.repository.OpportunityQuestionRepository;
import com.unify.rrls.security.SecurityUtils;
import com.unify.rrls.web.rest.util.HeaderUtil;
import com.unify.rrls.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@Transactional
public class NotificationServiceResource {

    private static final String ENTITY_NAME = "historyLogs";

    private final Logger log = LoggerFactory.getLogger(NotificationServiceResource.class);

    @Autowired
    HistoryLogsRepository historyLogsRepository;
    OpportunityMasterRepository opportunityMasterRepository;
    OpportunityQuestionRepository opportunityQuestionRepository;

    DeleteNotificationRepository deleteNotificationRepository;

    public NotificationServiceResource(HistoryLogsRepository historyLogsRepository,DeleteNotificationRepository deleteNotificationRepository,
    		OpportunityMasterRepository opportunityMasterRepository, OpportunityQuestionRepository opportunityQuestionRepository){
        this.historyLogsRepository=historyLogsRepository;
        this.deleteNotificationRepository=deleteNotificationRepository;
        this.opportunityMasterRepository=opportunityMasterRepository;
        this.opportunityQuestionRepository=opportunityQuestionRepository;
    }

    public String notificationHistorysave(String name,String createdBy, String modifiedBy, Instant createdDate, String action, String page,String fileName,Long userId,Long oppId,Long questionsId,Long commentsId){



        HistoryLogs historyLogs = new HistoryLogs();

        historyLogs.setOppname(name);

        historyLogs.setCreatedBy(createdBy);
        historyLogs.setLastModifiedBy(modifiedBy);
        historyLogs.setCreatedDate(createdDate);
        historyLogs.setAction(action);
        historyLogs.setPage(page);
        historyLogs.setFileNamecontent(fileName);
        historyLogs.setUserId(userId);
        historyLogs.setOppId(oppId);
        historyLogs.setQuestionsId(questionsId);
        historyLogs.setCommentsId(commentsId);
        historyLogsRepository.save(historyLogs);

        return null;


     }


    @PersistenceContext
    EntityManager em;


    @GetMapping("/history-logs/{userId}")
    @Timed
    public ResponseEntity<List<HistoryLogs>> getAllHistoryLogs(@PathVariable Integer userId) {
        log.debug("REST request to get a page of OpportunityMasters");
        String user = SecurityUtils.getCurrentUserLogin();
        String role=SecurityUtils.getCurrentRoleLogin();
        List<OpportunityMaster> oppMaster=opportunityMasterRepository.findAllByCreatedBy(user);
        List<Integer> questionLogs=historyLogsRepository.findByCreatedBy(user);
        List<Long> idList=new ArrayList<>();
        
        Query comm = em.createNativeQuery("select comments_id FROM `history_logs` where opp_created_by ='"+user+"' and action='added' and (comments_id!=0)");
        List<Integer> commentsLogs = comm.getResultList();
        
        if (commentsLogs.size()==0)
        	commentsLogs.add(0);
      
        System.out.println(questionLogs);
      
        if (questionLogs.size()==0)
        	questionLogs.add(0);		
        if(oppMaster.size()!=0){
        idList = oppMaster.stream().map(OpportunityMaster::getId).collect(Collectors.toList());
        System.out.println(StringUtils.join(idList,','));}
        else{
        	idList.add((long) 0);
        }
        
        
        Query questn = em.createNativeQuery("select questions_id FROM `history_logs` where opp_created_by !='"+user+"' and action='Replied' and questions_id!=0 and opp_id in("+StringUtils.join(idList,',')+")");
        List<Integer> questionId = questn.getResultList();
        
        System.out.println(questionId);
        
        if (questionId.size()==0)
        	questionId.add(0);
        
        Query comtn = em.createNativeQuery("select comments_id FROM `history_logs` where opp_created_by !='"+user+"' and action='Replied' and comments_id!=0 and opp_id in("+StringUtils.join(idList,',')+")");
        List<Integer> commentsId = comtn.getResultList();
        
        System.out.println(commentsId);
        
        if (commentsId.size()==0)
        	commentsId.add(0);
        
        Query questnNull = em.createNativeQuery("update `history_logs`  set questions_id=null where questions_id=0");
        questnNull.executeUpdate();
        Query commNull = em.createNativeQuery("update `history_logs`  set comments_id=null where comments_id=0");
        commNull.executeUpdate();
         
        List<HistoryLogs> list = null;
        List<HistoryLogs> historyLogs = new ArrayList<>();
        
        if(role.equals("Research")){
       // Query q = em.createNativeQuery("select * from history_logs where id not in(select history_log_id from delete_notification where user_id="+userId+" and status = 'deleted') order by id desc limit 20",HistoryLogs.class);
      Query q = em.createNativeQuery("select * from history_logs where(page='Opportunity' and action='Replied' and opp_id in("+StringUtils.join(idList,',')+") and opp_created_by!='"+user+"' and "
        		+ "(questions_id in("+StringUtils.join(questionId,',')+") or comments_id in("+StringUtils.join(commentsId,',')+")) or id"
        		+ " in(select id from history_logs where (page='Opportunity' and opp_id not in("+StringUtils.join(idList,',')+") and opp_created_by!='"+user+"' and "
        				+ "(questions_id in("+StringUtils.join(questionLogs,',')+")) or comments_id in("+StringUtils.join(commentsLogs,',')+"))) or "+
        	 "id in(select id from history_logs where opp_id in("+StringUtils.join(idList,',')+") and page='Opportunity' and (action not in('Answered','Replied','delegated','created','uploaded') and opp_created_by!='"+user+"') and id not "+
        	 "in(select history_log_id from delete_notification where user_id="+userId+" and status in('deleted'))))order by opp_created_date desc limit 20",HistoryLogs.class);
        	/* Query q = em.createNativeQuery("select * from history_logs where(page='Opportunity' and action='Replied' and opp_id in("+StringUtils.join(idList,',')+") and opp_created_by!='"+user+"' and questions_id in("+StringUtils.join(questionId,',')+")) or id"
        			         		+ " in(select id from history_logs where (page='Opportunity' and opp_id not in("+StringUtils.join(idList,',')+") and opp_created_by!='"+user+"' and questions_id in("+StringUtils.join(questionLogs,',')+")) or "+
        			        	 "id in(select id from history_logs where opp_id in("+StringUtils.join(idList,',')+") and page='Opportunity' and (action not in('Answered','Replied','delegated') and opp_created_by!='"+user+"') and id not "+
        			       	 "in(select history_log_id from delete_notification where user_id="+userId+" and status in('deleted'))))order by id desc limit 20",HistoryLogs.class);
        	*/list   = q.getResultList();
        
        
        for(HistoryLogs hl:list){
       
        	DeleteNotification readMessage=deleteNotificationRepository.findByHistoryLog(userId, hl.getId());
        	
              if(readMessage!= null )
                {
                    hl.setdStatus("Read");
                }
                else
                {
                    hl.setdStatus("UnRead");
                }

            

        }}
      
        	 if(role.equals("CIO")){
        	       // Query q = em.createNativeQuery("select * from history_logs where id not in(select history_log_id from delete_notification where user_id="+userId+" and status = 'deleted') order by id desc limit 20",HistoryLogs.class);
        	     Query q = em.createNativeQuery("select * from history_logs where (page='Opportunity' and action not in ('Added') and opp_created_by!='"+user+"' "
        	     		+ "and (questions_id in("+StringUtils.join(questionLogs,',')+") or comments_id in("+StringUtils.join(commentsLogs,',')+"))) and "
        	     				+ "id not in(select history_log_id from delete_notification where user_id="+userId+" and status in('deleted'))order by id desc limit 20",HistoryLogs.class); 	
        	       list   = q.getResultList();

        	        for(HistoryLogs hl:list){
        	       
        	        	DeleteNotification readMessage=deleteNotificationRepository.findByHistoryLog(userId, hl.getId());
        	        	
        	              if(readMessage!= null )
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
    
    @GetMapping("/clear_notification/{userId}")
    @Timed
    public ResponseEntity<DeleteNotification> clearNotification(@PathVariable Integer userId) {
        log.debug("REST request to clear notifications");
        List<HistoryLogs> list = null;
        
        Query q = em.createNativeQuery("select * from history_logs where id not in(select history_log_id from delete_notification where user_id="+userId+" and status in('deleted'))",HistoryLogs.class);

        list   = q.getResultList();
        
        for(HistoryLogs hl:list){
        	DeleteNotification result=new DeleteNotification();
        	result.setUserId(userId);
        	result.setNotiId(hl.getId());
        	result.setStatus("deleted");
        	deleteNotificationRepository.save(result);
        }       

        return new ResponseEntity<>(HttpStatus.OK);

    }





}
