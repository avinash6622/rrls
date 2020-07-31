package com.unify.rrls.web.rest;


import com.codahale.metrics.annotation.Timed;
import com.unify.rrls.domain.BrochureFileUpload;
import com.unify.rrls.domain.BrochureSupportingFiles;
import com.unify.rrls.domain.StrategyMaster;
import com.unify.rrls.repository.BorchureStrategyMappingRespository;
import com.unify.rrls.repository.BrochureFileUploadRepository;
import com.unify.rrls.repository.BrochureSupportedFileRepository;
import com.unify.rrls.repository.StrategyMasterRepository;
import com.unify.rrls.security.SecurityUtils;
import com.unify.rrls.web.rest.util.HeaderUtil;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;

@RestController
@RequestMapping("/api")
public class BrochureSupportedFileUploadResource {

    private final Logger log = LoggerFactory.getLogger(FileUploadResource.class);
    private static final String ENTITY_NAME = "brochureFileUpload";


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

    @Autowired
    private final BrochureSupportedFileRepository brochureSupportedFileRepository;
    private final BrochureFileUploadRepository brochureFileUploadRepository;
    private final NotificationServiceResource notificationServiceResource;

    private final BorchureStrategyMappingRespository borchureStrategyMappingRespository;
    private final StrategyMasterRepository strategyMasterRepository;

    public BrochureSupportedFileUploadResource(BrochureSupportedFileRepository brochureSupportedFileRepository, BrochureFileUploadRepository brochureFileUploadRepository,BorchureStrategyMappingRespository borchureStrategyMappingRespository,StrategyMasterRepository strategyMasterRepository,NotificationServiceResource notificationServiceResource)
    {
        this.brochureSupportedFileRepository=brochureSupportedFileRepository;
        this.borchureStrategyMappingRespository=borchureStrategyMappingRespository;
        this.strategyMasterRepository=strategyMasterRepository;
        this.brochureFileUploadRepository=brochureFileUploadRepository;
        this.notificationServiceResource=notificationServiceResource;
    }

    @RequestMapping(value = "/brochure/supportedFileUploads", method = RequestMethod.POST)
    @Timed
    public ResponseEntity<BrochureSupportingFiles> createBrochureSupportedFileUpload(@RequestParam MultipartFile fileUploads,
                                                                            @RequestParam(value="filetype") String filetype,
                                                                            @RequestParam(value="uploadfileName") String uploadfileName,
                                                                            @RequestParam(value="Strategy") Long strategyId,
                                                                            @RequestParam(value="fileDescription") String fileDescription,
                                                                            @RequestParam (value="brochure_id") Long borchureId) throws URISyntaxException, IOException, MissingServletRequestParameterException {
        log.debug("REST request to save FileUpload : {}");
        String page="Brochure Support files";
        System.out.println("id "+borchureId);
        BrochureFileUpload brochureFileUpload = brochureFileUploadRepository.findById(borchureId);

        String user;
        String sFilesDirectory;

        user = SecurityUtils.getCurrentUserLogin();
        sFilesDirectory = "src/main/webapp/content/fileUpload/BrochureSupportedFile/" +  user + "-" + uploadfileName;
        File dirFiles = new File(sFilesDirectory);
        dirFiles.mkdirs();

        BrochureSupportingFiles brochureSupportingFiles = new BrochureSupportingFiles();
        BrochureSupportingFiles brochureSupportingFilesResult = new BrochureSupportingFiles();
//        BrochureStrategyMapping brochureStrategyMapping= new BrochureStrategyMapping();

        setFileName(fileUploads.getOriginalFilename());
        fileStream = IOUtils.toByteArray(fileUploads.getInputStream());


        File sFiles = new File(dirFiles, fileName);
        writeFile(fileStream, sFiles);
        brochureSupportingFiles.setFilePath(sFiles.toString());

        brochureSupportingFiles.setFileName(uploadfileName);
        brochureSupportingFiles.setFileContentType(filetype);
        brochureSupportingFiles.setFileDesc(fileDescription);
        brochureSupportingFiles.setCreatedBy(user);
        brochureSupportingFiles.setCreatedDate(Instant.now());
        brochureSupportingFiles.setBrochureFileUpload(brochureFileUpload);

        brochureSupportingFilesResult =brochureSupportedFileRepository.save(brochureSupportingFiles);






        notificationServiceResource.notificationHistorysave(brochureSupportingFilesResult.getFileName(), brochureSupportingFilesResult.getCreatedBy(), brochureSupportingFilesResult.getLastmodifiedBy(), brochureSupportingFilesResult.getCreatedDate(), "created", page, "", strategyId, Long.parseLong("0"), Long.parseLong("0"), Long.parseLong("0"));


        return ResponseEntity.created(new URI("/api/brochure/mainFileUploads/" + brochureSupportingFilesResult.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, brochureSupportingFilesResult.getId().toString()))
            .body(brochureSupportingFilesResult);
    }


    // GetBrochureValueById

    @GetMapping("/brochureSupportedFileList/getById")
    public BrochureSupportingFiles getByBrochureId(@RequestParam(value ="id") Long id) {
        log.debug("REST request to get a page of Policies");

        BrochureSupportingFiles brochureSupportingFiles = brochureSupportedFileRepository.findById(id);
        return brochureSupportingFiles;

    }


    @PutMapping("/brochureSupportingFile/Update")
    @Timed
    public ResponseEntity<BrochureSupportingFiles> updateFileUpload(@Valid @RequestBody BrochureSupportingFiles brochureSupportingFiles) throws URISyntaxException {
        log.debug("REST request to update FileUpload : {}", brochureSupportingFiles);
        String page="Brochure Support files";
        if (brochureSupportingFiles.getId() == null) {
            //return createFileUpload(fileUpload);
        }
        String user = SecurityUtils.getCurrentUserLogin();

        brochureSupportingFiles.setLastModifiedDate(Instant.now());
        brochureSupportingFiles.setLastmodifiedBy(user);

        BrochureSupportingFiles result = brochureSupportedFileRepository.save(brochureSupportingFiles);
        notificationServiceResource.notificationHistorysave(result.getFileName(), result.getCreatedBy(), result.getLastmodifiedBy(), result.getCreatedDate(), "updated", page, "", result.getId(), Long.parseLong("0"), Long.parseLong("0"), Long.parseLong("0"));

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, brochureSupportingFiles.getId().toString()))
            .body(result);
    }


    @DeleteMapping("/brochureSupportingFile/delete")
    @Timed
    public ResponseEntity<Void> deleteBrochureSupportingFileUpload(@RequestParam (value ="borchureSupportingId") Long brochureSupporingId) {
        String page="Brochure Support files";

        BrochureSupportingFiles brochureSupportingFiles = brochureSupportedFileRepository.findById(brochureSupporingId);


        brochureSupportedFileRepository.delete(brochureSupporingId);

        notificationServiceResource.notificationHistorysave(brochureSupportingFiles.getFileName(), brochureSupportingFiles.getCreatedBy(), brochureSupportingFiles.getLastmodifiedBy(), brochureSupportingFiles.getCreatedDate(), "delegated", page, "", brochureSupportingFiles.getId(), Long.parseLong("0"), Long.parseLong("0"), Long.parseLong("0"));

        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, brochureSupporingId.toString())).build();
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
