package com.unify.rrls.web.rest;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import io.swagger.annotations.ApiParam;

import com.codahale.metrics.annotation.Timed;
import com.unify.rrls.domain.ConfidenctialLetters;
import com.unify.rrls.domain.OpportunityMaster;
import com.unify.rrls.domain.OpportunityName;
import com.unify.rrls.repository.ConfidenctialLettersRepository;
import com.unify.rrls.repository.OpportunityMasterRepository;
import com.unify.rrls.security.SecurityUtils;
import com.unify.rrls.web.rest.util.HeaderUtil;
import com.unify.rrls.web.rest.util.PaginationUtil;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api")

public class ConfidenctialLettersResource {
    private final Logger log = LoggerFactory.getLogger(ConfidenctialLettersResource.class);

    private static final String ENTITY_NAME = "ConfidenctialLetters";

    @Autowired
    private final ConfidenctialLettersRepository confidenctialLettersRepository;
    private final OpportunityMasterRepository opportunityMasterRepository;

    public ConfidenctialLettersResource(ConfidenctialLettersRepository confidenctialLettersRepository,
                                        OpportunityMasterRepository opportunityMasterRepository) {
        this.confidenctialLettersRepository = confidenctialLettersRepository;
        this.opportunityMasterRepository = opportunityMasterRepository;
    }

    private byte[] fileStream;
    private String fileName;

    public byte[] getFileStream() {
        return fileStream;
    }

    public void setFileStream(byte[] fileStream) {
        this.fileStream = fileStream;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @PersistenceContext
    EntityManager em;

    @RequestMapping(value = "/confidenctial-letters", method = RequestMethod.POST)
    @Timed
    public ResponseEntity<ConfidenctialLetters> createConfidenctialLetters(@RequestParam(value = "oppId") Long oppId,
                                                                           @RequestParam(value = "fileUploads") MultipartFile[] fileUploads, @RequestParam(value = "filetype") String filetype,
                                                                           @RequestParam(value = "uploadfileName") String uploadfileName, @RequestParam(value = "subject") String subject) throws URISyntaxException, IOException, MissingServletRequestParameterException {

        OpportunityMaster opp = opportunityMasterRepository.findOne(oppId);

        String user = SecurityUtils.getCurrentUserLogin();
        String sFilesDirectory = "src/main/webapp/content/fileUpload/" + opp.getMasterName().getOppName() + "/" + user + "-" + uploadfileName;

        File dirFiles = new File(sFilesDirectory);
        dirFiles.mkdirs();

        ConfidenctialLetters fileUploaded = new ConfidenctialLetters();
        ConfidenctialLetters result = new ConfidenctialLetters();

        String extension = "";
        String name = "";

        for (MultipartFile sFile : fileUploads) {

            setFileName(sFile.getOriginalFilename());
            fileStream = IOUtils.toByteArray(sFile.getInputStream());

            System.out.println("FILE NAME--->" + fileName);

            File sFiles = new File(dirFiles, fileName);
            writeFile(fileStream, sFiles);
            fileUploaded.setFileData(sFiles.toString());

            int idxOfDot = sFile.getOriginalFilename().lastIndexOf('.');   //Get the last index of . to separate extension
            extension = sFile.getOriginalFilename().substring(idxOfDot + 1).toLowerCase();
            name = sFile.getOriginalFilename().substring(0, idxOfDot);


            fileUploaded.setFileName(uploadfileName);
            fileUploaded.setOpportunityMasterId(opp);
            fileUploaded.setFiletype(filetype);
            fileUploaded.setSubject(subject);
            result = confidenctialLettersRepository.save(fileUploaded);

        }

        return ResponseEntity.created(new URI("/api/confidenctial-letters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @GetMapping("/confidenctial-letters")
    @Timed
    public ResponseEntity<List<ConfidenctialLetters>> getAllConfidenctialLetters(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of ConfidenctialLetters");
        Page<ConfidenctialLetters> page = null;

        page = confidenctialLettersRepository.findAll(pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/confidenctial-letters");

        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/confidenctial-letters/opportunity")
    @Timed
    public ResponseEntity<List<OpportunityName>> getAllOpportunityMastersforauto() {
        log.debug("REST request to get a page of OpportunityMasters");
        List<OpportunityName> list = new ArrayList<>();
        Query q = em.createNativeQuery("select * from opportunity_name where id  in(select master_name from opportunity_master where id in (select distinct(opportunity_master_id) from confidenctial_letters))", OpportunityName.class);

        list = q.getResultList();
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(list, headers, HttpStatus.OK);

    }

    @PostMapping("/confidenctial-letters/search-opp")
    @Timed
    public ResponseEntity<List<ConfidenctialLetters>> searchOpportunities(@RequestBody OpportunityName opportunityName, Pageable pageable) throws URISyntaxException {
        OpportunityMaster opportunityMaster = opportunityMasterRepository.findByMasterName(opportunityName);
        Page<ConfidenctialLetters> page = confidenctialLettersRepository.findByOpportunityMasterId(opportunityMaster, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/confidenctial-letters");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/confidenctial-letters/subject")
    @Timed
    public ResponseEntity<List<ConfidenctialLetters>> getAllSubjectforauto() {
        log.debug("REST request to get a page of OpportunityMasters");
        List<ConfidenctialLetters> list = new ArrayList<>();
        Query q = em.createNativeQuery("select * from confidenctial_letters", ConfidenctialLetters.class);

        list = q.getResultList();
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(list, headers, HttpStatus.OK);

    }

    @PostMapping("/confidenctial-letter/search-sub")
    @Timed
    public ResponseEntity<List<ConfidenctialLetters>> searchSubject(@RequestBody ConfidenctialLetters confidenctialLetters, Pageable pageable) throws URISyntaxException {

        Page<ConfidenctialLetters> page = confidenctialLettersRepository.findBySubject(confidenctialLetters.getSubject(), pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/confidenctial-letters");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    public void writeFile(byte[] fileStream, File file) throws IOException {
        InputStream in;

        System.out.println(file);
        in = new ByteArrayInputStream(fileStream);
        OutputStream out = new FileOutputStream(file);
        byte buf[] = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.flush();
        out.close();
        System.out.println("File Uploading is Completed");
    }


    @DeleteMapping("/confidential-letter/{id}")
    @Timed
    public ResponseEntity<Void> deleteConfidentialLetter(@PathVariable Long id) {
        log.debug("REST request to delete confidential letter: {}", id);
        ConfidenctialLetters confidentialLetter = confidenctialLettersRepository.findOne(id);
        System.out.println("After find");
        System.out.println(confidentialLetter);
        confidenctialLettersRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("A confidential letter is deleted with identifier " + id, id.toString())).build();
    }

}
