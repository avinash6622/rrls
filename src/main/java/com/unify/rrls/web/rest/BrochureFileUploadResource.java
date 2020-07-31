package com.unify.rrls.web.rest;


import com.codahale.metrics.annotation.Timed;
import com.unify.rrls.domain.*;
import com.unify.rrls.repository.*;
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
import java.util.List;

@RestController
@RequestMapping("/api")
public class BrochureFileUploadResource {

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
    private final BrochureFileUploadRepository brochureFileUploadRepository;
    private final BorchureStrategyMappingRespository borchureStrategyMappingRespository;
    private final StrategyMasterRepository strategyMasterRepository;
    private final NotificationServiceResource notificationServiceResource;


    public BrochureFileUploadResource(BrochureFileUploadRepository brochureFileUploadRepository,BorchureStrategyMappingRespository borchureStrategyMappingRespository,StrategyMasterRepository strategyMasterRepository,NotificationServiceResource notificationServiceResource)
    {
        this.brochureFileUploadRepository=brochureFileUploadRepository;
        this.borchureStrategyMappingRespository=borchureStrategyMappingRespository;
        this.strategyMasterRepository=strategyMasterRepository;
        this.notificationServiceResource=notificationServiceResource;
    }

    @RequestMapping(value = "/brochure/mainFileUploads", method = RequestMethod.POST)
    @Timed
    public ResponseEntity<BrochureFileUpload> createBrochureFileUpload(@RequestParam MultipartFile fileUploads,
                                                                       @RequestParam(value="filetype") String filetype,
                                                                       @RequestParam(value="uploadfileName") String uploadfileName,
                                                                       @RequestParam(value="Strategy") Long strategyId,
                                                                       @RequestParam(value="fileDescription") String fileDescription) throws URISyntaxException, IOException, MissingServletRequestParameterException {
        log.debug("REST request to save FileUpload : {}");
        String page="Brochure";
        String user;
        String sFilesDirectory;

        user = SecurityUtils.getCurrentUserLogin();
        sFilesDirectory = "src/main/webapp/content/fileUpload/BrochureMainFile/" +  user + "-" + uploadfileName;
        File dirFiles = new File(sFilesDirectory);
        dirFiles.mkdirs();

        BrochureFileUpload brochureFileUpload = new BrochureFileUpload();
        BrochureFileUpload brochureFileUploadResult = new BrochureFileUpload();
        BrochureStrategyMapping brochureStrategyMapping= new BrochureStrategyMapping();

        setFileName(fileUploads.getOriginalFilename());
        fileStream = IOUtils.toByteArray(fileUploads.getInputStream());


        File sFiles = new File(dirFiles, fileName);
        writeFile(fileStream, sFiles);
        brochureFileUpload.setFilePath(sFiles.toString());

        brochureFileUpload.setFileName(uploadfileName);
        brochureFileUpload.setFileContentType(filetype);
        brochureFileUpload.setFileDesc(fileDescription);
        brochureFileUpload.setCreatedBy(user);
        brochureFileUpload.setCreatedDate(Instant.now());
        brochureFileUploadResult = brochureFileUploadRepository.save(brochureFileUpload);


        StrategyMaster strategyMaster = strategyMasterRepository.findById(strategyId);

        int totalBrochure = strategyMaster.getTotalBrochure();
        strategyMaster.setTotalBrochure(totalBrochure + 1);
        StrategyMaster strategyMaster1 = strategyMasterRepository.save(strategyMaster);


        brochureStrategyMapping.setBrochureFileUpload(brochureFileUploadResult);
        brochureStrategyMapping.setStrategyMaster(strategyMaster);

        borchureStrategyMappingRespository.save(brochureStrategyMapping);


        notificationServiceResource.notificationHistorysave(brochureFileUploadResult.getFileName(), brochureFileUploadResult.getCreatedBy(), brochureFileUploadResult.getLastmodifiedBy(), brochureFileUploadResult.getCreatedDate(), "created", page, "", strategyId, Long.parseLong("0"), Long.parseLong("0"), Long.parseLong("0"));


        return ResponseEntity.created(new URI("/api/brochure/mainFileUploads/" + brochureFileUploadResult.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, brochureFileUploadResult.getId().toString()))
            .body(brochureFileUploadResult);
    }

    // GetBrochure List By strategy

    @GetMapping("/brochureMainFileList/viewByStrategy")
    public ResponseEntity<List<BrochureStrategyMapping>> getBrochureListByStrategyId(@RequestParam Long strategyId , @ApiParam Pageable pageable) {
        log.debug("REST request to get a page of brochureMainFile");
        StrategyMaster strategyMaster = strategyMasterRepository.findById(strategyId);

        Page<BrochureStrategyMapping> page = borchureStrategyMappingRespository.findByStrategyMaster(strategyMaster,pageable);
        System.out.println("page"+page.getTotalElements());
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/brochureMainFileList/viewByStrategy");

        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    // GetBrochureValueById

    @GetMapping("/brochureMainFileList/getById")
    public BrochureFileUpload getByBrochureId(@RequestParam(value ="id") Long id) {
        log.debug("REST request to get a page of brochureMainFile");

        BrochureFileUpload brochureFileUpload = brochureFileUploadRepository.findById(id);
        return brochureFileUpload;

    }
    // GetBrochureCount by strategyID

    @GetMapping("/brochureMainFile/Count/getByStrategyId")
    public Integer getCountByStrategyId(@RequestParam(value ="id") Long id) {
        log.debug("REST request to get a page of brochureMainFile");

        Integer brochureValue = borchureStrategyMappingRespository.findCountOfBrochure(id);
        return brochureValue;

   }

    @PutMapping("/brochureMainFile/Update")
    @Timed
    public ResponseEntity<BrochureFileUpload> updateFileUpload(@Valid @RequestBody BrochureFileUpload brochureFileUpload) throws URISyntaxException {
        log.debug("REST request to update FileUpload : {}", brochureFileUpload);
        String page="Brochure";
        if (brochureFileUpload.getId() == null) {
            //return createFileUpload(fileUpload);
        }
        String user = SecurityUtils.getCurrentUserLogin();

        brochureFileUpload.setLastModifiedDate(Instant.now());
        brochureFileUpload.setLastmodifiedBy(user);

        BrochureFileUpload result = brochureFileUploadRepository.save(brochureFileUpload);
        notificationServiceResource.notificationHistorysave(result.getFileName(), result.getCreatedBy(), result.getLastmodifiedBy(), result.getCreatedDate(), "updated", page, "", result.getId(), Long.parseLong("0"), Long.parseLong("0"), Long.parseLong("0"));

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, brochureFileUpload.getId().toString()))
            .body(result);
    }

    @DeleteMapping("/brochureMainFile/delete")
    @Timed
    public ResponseEntity<Void> deleteBrochureMainFileUpload(@RequestParam(value ="strategyId") Long strategyId, @RequestParam (value ="borchureId") Long brochureId) {
        log.debug("REST request to delete FileUpload : {}", brochureId,strategyId);
        String page="Brochure";

        StrategyMaster strategyMaster = strategyMasterRepository.findById(strategyId);
        if (strategyMaster.getTotalBrochure() != 0) {
            strategyMaster.setTotalBrochure(strategyMaster.getTotalBrochure() - 1);

        } else {
            strategyMaster.setTotalBrochure(0);

        }
        BrochureFileUpload brochureFileUpload = brochureFileUploadRepository.findById(brochureId);

        strategyMasterRepository.save(strategyMaster);
        borchureStrategyMappingRespository.deleteByBrochureIdAndStrategyId(brochureId,strategyId);
        brochureFileUploadRepository.delete(brochureId);

        notificationServiceResource.notificationHistorysave(brochureFileUpload.getFileName(), brochureFileUpload.getCreatedBy(), brochureFileUpload.getLastmodifiedBy(), brochureFileUpload.getCreatedDate(), "delegated", page, "", brochureFileUpload.getId(), Long.parseLong("0"), Long.parseLong("0"), Long.parseLong("0"));

        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, brochureId.toString())).build();
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
