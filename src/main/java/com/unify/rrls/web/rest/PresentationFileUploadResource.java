package com.unify.rrls.web.rest;
import com.codahale.metrics.annotation.Timed;
import com.unify.rrls.domain.*;
import com.unify.rrls.repository.PresentationFileUploadRepository;
import com.unify.rrls.repository.PresentationStrategyRepository;
import com.unify.rrls.repository.StrategyMasterRepository;
import com.unify.rrls.security.SecurityUtils;
import com.unify.rrls.web.rest.util.HeaderUtil;
import com.unify.rrls.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PresentationFileUploadResource {
    private final Logger log = LoggerFactory.getLogger(FileUploadResource.class);
    private static final String ENTITY_NAME = "presentationFileUpload";

    @Autowired
    private final PresentationFileUploadRepository presentationFileUploadRepository;
    private final PresentationStrategyRepository presentationStrategyRepository;
    private final StrategyMasterRepository strategyMasterRepository;

    public PresentationFileUploadResource(PresentationFileUploadRepository presentationFileUploadRepository, PresentationStrategyRepository presentationStrategyRepository, StrategyMasterRepository strategyMasterRepository) {
        this.presentationFileUploadRepository = presentationFileUploadRepository;
        this.presentationStrategyRepository = presentationStrategyRepository;
        this.strategyMasterRepository = strategyMasterRepository;
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


    @RequestMapping(value = "/presentation-file-uploads", method = RequestMethod.POST)
    @Timed
    public ResponseEntity<PresentationFileUpload> createFileUpload(@RequestParam MultipartFile fileUploads,
                                                                   @RequestParam(value = "filetype") String filetype,
                                                                   @RequestParam(value = "uploadfileName") String uploadfileName,
                                                                   @RequestParam(value = "Strategy") Long strategyId,
                                                                   @RequestParam(value = "fileDescription") String fileDescription) throws URISyntaxException, IOException, MissingServletRequestParameterException {
        log.debug("REST request to save FileUpload : {}");
        System.out.println("id " + strategyId);
        String user;
        String sFilesDirectory;

        user = SecurityUtils.getCurrentUserLogin();
        System.out.println("fileUploads " + fileUploads);
        sFilesDirectory = "src/main/webapp/content/fileUpload/Presentation/" + user + "-" + uploadfileName;
        File dirFiles = new File(sFilesDirectory);
        dirFiles.mkdirs();

        PresentationFileUpload presentationFileUpload = new PresentationFileUpload();
        PresentationFileUpload presentationFileUploadResult = new PresentationFileUpload();
        PresentationStrategyMapping presentationStrategyMapping = new PresentationStrategyMapping();

        setFileName(fileUploads.getOriginalFilename());
        fileStream = IOUtils.toByteArray(fileUploads.getInputStream());

        System.out.println("FILE NAME--->" + fileName);

        File sFiles = new File(dirFiles, fileName);
        writeFile(fileStream, sFiles);
        presentationFileUpload.setFilePath(sFiles.toString());

        presentationFileUpload.setFileName(uploadfileName);
        presentationFileUpload.setFileContentType(filetype);
        presentationFileUpload.setFileDesc(fileDescription);
        presentationFileUpload.setCreatedBy(user);
        presentationFileUpload.setCreatedDate(Instant.now());
        presentationFileUploadResult = presentationFileUploadRepository.save(presentationFileUpload);
        System.out.println("ssdsd" + presentationFileUploadResult.getId());
        System.out.println("dsdsdsd" + presentationFileUploadResult.getCreatedBy());

        StrategyMaster strategyMaster = strategyMasterRepository.findById(strategyId);
        int totalPres = strategyMaster.getTotalPresentation();
        strategyMaster.setTotalPresentation(totalPres + 1);
        StrategyMaster strategyMaster1 = strategyMasterRepository.save(strategyMaster);

        System.out.println("dsdsdsd" + presentationFileUploadResult.getCreatedBy());

        presentationStrategyMapping.setPresentationFileUpload(presentationFileUploadResult);
        presentationStrategyMapping.setStrategyMaster(strategyMaster);

        presentationStrategyRepository.save(presentationStrategyMapping);


        System.out.println("preserntation" + presentationFileUpload.getFileDesc());


        return ResponseEntity.created(new URI("/api/presentation-file-uploads/" + presentationFileUploadResult.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, presentationFileUploadResult.getId().toString()))
            .body(presentationFileUploadResult);
    }

    @RequestMapping(value = "/presentation/update/fileUploads", method = RequestMethod.PUT)
    @Timed
    public ResponseEntity<PresentationFileUpload> updatePresentationFile(@RequestParam MultipartFile fileUploads,
                                                                         @RequestParam(value = "filetype") String filetype,
                                                                         @RequestParam(value = "uploadfileName") String uploadfileName,
                                                                         @RequestParam(value = "Strategy") Long strategyId,
                                                                         @RequestParam(value = "fileDescription") String fileDescription,
                                                                         @RequestParam(value = "id") Long id) throws URISyntaxException, IOException, MissingServletRequestParameterException {
        log.debug("REST request to save FileUpload : {}");
        System.out.println("id " + strategyId);
        String user;
        String sFilesDirectory;

        user = SecurityUtils.getCurrentUserLogin();
        System.out.println("fileUploads " + fileUploads);
        sFilesDirectory = "src/main/webapp/content/fileUpload/Presentation/" + user + "-" + uploadfileName;
        File dirFiles = new File(sFilesDirectory);
        dirFiles.mkdirs();

        PresentationFileUpload presentationFileUpload = new PresentationFileUpload();
        PresentationFileUpload presentationFileUploadResult = new PresentationFileUpload();
        PresentationStrategyMapping presentationStrategyMapping = new PresentationStrategyMapping();

        setFileName(fileUploads.getOriginalFilename());
        fileStream = IOUtils.toByteArray(fileUploads.getInputStream());

        System.out.println("FILE NAME--->" + fileName);

        File sFiles = new File(dirFiles, fileName);
        writeFile(fileStream, sFiles);
        presentationFileUpload.setFilePath(sFiles.toString());

        presentationFileUpload.setFileName(uploadfileName);
        presentationFileUpload.setFileContentType(filetype);
        presentationFileUpload.setFileDesc(fileDescription);
        presentationFileUpload.setLastmodifiedBy(user);
        presentationFileUpload.setLastModifiedDate(Instant.now());
        presentationFileUpload.setId(id);

        presentationFileUploadResult = presentationFileUploadRepository.save(presentationFileUpload);


        return ResponseEntity.created(new URI("/api/presentation-file-uploads/" + presentationFileUploadResult.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, presentationFileUploadResult.getId().toString()))
            .body(presentationFileUploadResult);
    }


    @GetMapping("/presentationList/viewByStrategy")
    public ResponseEntity<List<PresentationStrategyMapping>> getPresentationByStrategyId(@RequestParam Long strategyId, @ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Policies");
        StrategyMaster strategyMaster = strategyMasterRepository.findById(strategyId);

        Page<PresentationStrategyMapping> page = presentationStrategyRepository.findByStrategyMaster(strategyMaster, pageable);

//        Page<PresentationFileUpload> page = presentationFileUploadRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/policies");

        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/presentationList/getById")
    public PresentationFileUpload getByPresentationId(@RequestParam(value = "id") Long id) {
        log.debug("REST request to get a page of Policies");

        PresentationFileUpload presentaionValue = presentationFileUploadRepository.findById(id);
        return presentaionValue;

//        Page<PresentationFileUpload> page = presentationFileUploadRepository.findAll(pageable);
//        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(presentaionValue, "/api/policies");

//        return new ResponseEntity<>(presentaionValue.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/presentaitonAndBorchure/count/allStrategy")
    public List<StrategyMaster> getCountByStrategyId() {
        log.debug("REST request to get a page of Policies");

        List<StrategyMaster> strategyMaster = strategyMasterRepository.findAll();
        return strategyMaster;

//        Page<PresentationFileUpload> page = presentationFileUploadRepository.findAll(pageable);
//        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(presentaionValue, "/api/policies");

//        return new ResponseEntity<>(presentaionValue.getContent(), headers, HttpStatus.OK);
    }

    @PutMapping("/presentation/Update")
    @Timed
    public ResponseEntity<PresentationFileUpload> updateFileUpload(@Valid @RequestBody PresentationFileUpload presentationFileUpload) throws URISyntaxException {
        log.debug("REST request to update FileUpload : {}", presentationFileUpload);
        if (presentationFileUpload.getId() == null) {
            //return createFileUpload(fileUpload);
        }
        String user = SecurityUtils.getCurrentUserLogin();

        presentationFileUpload.setLastModifiedDate(Instant.now());
        presentationFileUpload.setLastmodifiedBy(user);

        PresentationFileUpload result = presentationFileUploadRepository.save(presentationFileUpload);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, presentationFileUpload.getId().toString()))
            .body(result);
    }

    @DeleteMapping("/presentationFile/delete")
    @Timed
    public ResponseEntity<Void> deletePresentationFileUpload(@RequestParam(value = "strategyId") Long strategyId, @RequestParam(value = "presentationId") Long presentationId) {
        log.debug("REST request to delete FileUpload : {}", presentationId, strategyId);
        System.out.println("strategyId Presentaiotid" + strategyId + presentationId);
        StrategyMaster strategyMaster = strategyMasterRepository.findById(strategyId);
        if (strategyMaster.getTotalPresentation() != 0) {
            strategyMaster.setTotalPresentation(strategyMaster.getTotalPresentation() - 1);

        } else {
            strategyMaster.setTotalPresentation(0);

        }
        strategyMasterRepository.save(strategyMaster);
        presentationStrategyRepository.deleteByPresentationIdAndStrategyId(presentationId, strategyId);
        presentationFileUploadRepository.delete(presentationId);

        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, presentationId.toString())).build();
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


}
