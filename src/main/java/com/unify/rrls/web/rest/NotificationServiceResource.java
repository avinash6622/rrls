package com.unify.rrls.web.rest;


import com.codahale.metrics.annotation.Timed;
import com.unify.rrls.domain.HistoryLogs;
import com.unify.rrls.domain.OpportunityMaster;
import com.unify.rrls.domain.StrategyMapping;
import com.unify.rrls.domain.StrategyMaster;
import com.unify.rrls.repository.HistoryLogsRepository;
import com.unify.rrls.security.SecurityUtils;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class NotificationServiceResource {

    private static final String ENTITY_NAME = "historyLogs";

    private final Logger log = LoggerFactory.getLogger(NotificationServiceResource.class);

    @Autowired
    private final HistoryLogsRepository historyLogsRepository;

    public NotificationServiceResource(HistoryLogsRepository historyLogsRepository){
        this.historyLogsRepository=historyLogsRepository;
    }

    @GetMapping("/history-logs")
    @Timed
    public ResponseEntity<List<HistoryLogs>> getAllHistoryLogs() {
        log.debug("REST request to get a page of OpportunityMasters");
        List<HistoryLogs> list = null;

        list = historyLogsRepository.findAll();


        System.out.println(list);
        //HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/opportunity-masters");

        return new ResponseEntity<>(list,HttpStatus.OK);
    }



}
