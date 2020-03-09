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


    public BrochureFileUploadResource(BrochureFileUploadRepository brochureFileUploadRepository,BorchureStrategyMappingRespository borchureStrategyMappingRespository,StrategyMasterRepository strategyMasterRepository)
    {
        this.brochureFileUploadRepository=brochureFileUploadRepository;
        this.borchureStrategyMappingRespository=borchureStrategyMappingRespository;
        this.strategyMasterRepository=strategyMasterRepository;
    }

    @RequestMapping(value = "/brochure/mainFileUploads", method = RequestMethod.POST)
    @Timed
    public ResponseEntity<BrochureFileUpload> createBrochureFileUpload(@RequestParam MultipartFile fileUploads,
                                                                       @RequestParam(value="filetype") String filetype,
                                                                       @RequestParam(value="uploadfileName") String uploadfileName,
                                                                       @RequestParam(value="Strategy") Long strategyId,
                                                                       @RequestParam(value="fileDescription") String fileDescription) throws URISyntaxException, IOException, MissingServletRequestParameterException {
        log.debug("REST request to save FileUpload : {}");
        System.out.println("id "+strategyId);
        String user;
        String sFilesDirectory;

        user = SecurityUtils.getCurrentUserLogin();
        System.out.println("fileUploads "+fileUploads);
        sFilesDirectory = "src/main/webapp/content/fileUpload/BrochureMainFile/" +  user + "-" + uploadfileName;
        File dirFiles = new File(sFilesDirectory);
        dirFiles.mkdirs();

        BrochureFileUpload brochureFileUpload = new BrochureFileUpload();
        BrochureFileUpload brochureFileUploadResult = new BrochureFileUpload();
        BrochureStrategyMapping brochureStrategyMapping= new BrochureStrategyMapping();

        setFileName(fileUploads.getOriginalFilename());
        fileStream = IOUtils.toByteArray(fileUploads.getInputStream());

        System.out.println("FILE NAME--->" + fileName);

        File sFiles = new File(dirFiles, fileName);
        writeFile(fileStream, sFiles);
        brochureFileUpload.setFilePath(sFiles.toString());

        brochureFileUpload.setFileName(uploadfileName);
        brochureFileUpload.setFileContentType(filetype);
        brochureFileUpload.setFileDesc(fileDescription);
        brochureFileUpload.setCreatedBy(user);
        brochureFileUpload.setCreatedDate(Instant.now());
        brochureFileUploadResult = brochureFileUploadRepository.save(brochureFileUpload);
        System.out.println("ssdsd"+brochureFileUploadResult.getId());
        System.out.println("dsdsdsd"+brochureFileUploadResult.getCreatedBy());

        StrategyMaster strategyMaster = strategyMasterRepository.findById(strategyId);

        System.out.println("dsdsdsd"+brochureFileUploadResult.getCreatedBy());

        brochureStrategyMapping.setBrochureFileUpload(brochureFileUploadResult);
        brochureStrategyMapping.setStrategyMaster(strategyMaster);

        borchureStrategyMappingRespository.save(brochureStrategyMapping);




        return ResponseEntity.created(new URI("/api/brochure/mainFileUploads/" + brochureFileUploadResult.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, brochureFileUploadResult.getId().toString()))
            .body(brochureFileUploadResult);
    }

    // GetBrochure List By strategy

    @GetMapping("/brochureMainFileList/viewByStrategy")
    public ResponseEntity<List<BrochureStrategyMapping>> getBrochureListByStrategyId(@RequestParam Long strategyId , @ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Policies");
        StrategyMaster strategyMaster = strategyMasterRepository.findById(strategyId);
        System.out.println("strategyMaster value"+strategyMaster.getStrategyName());

        Page<BrochureStrategyMapping> page = borchureStrategyMappingRespository.findByStrategyMaster(strategyMaster,pageable);
        System.out.println("page"+page.getTotalElements());
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/brochureMainFileList/viewByStrategy");

        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    // GetBrochureValueById

    @GetMapping("/brochureMainFileList/getById")
    public BrochureFileUpload getByBrochureId(@RequestParam(value ="id") Long id) {
        log.debug("REST request to get a page of Policies");

        BrochureFileUpload brochureFileUpload = brochureFileUploadRepository.findById(id);
        return brochureFileUpload;

    }
    // GetBrochureCount by strategyID

    @GetMapping("/brochureMainFile/Count/getByStrategyId")
    public Integer getCountByStrategyId(@RequestParam(value ="id") Long id) {
        log.debug("REST request to get a page of Policies");

        Integer brochureValue = borchureStrategyMappingRespository.findCountOfBrochure(id);
        return brochureValue;

   }

    @PutMapping("/brochureMainFile/Update")
    @Timed
    public ResponseEntity<BrochureFileUpload> updateFileUpload(@Valid @RequestBody BrochureFileUpload brochureFileUpload) throws URISyntaxException {
        log.debug("REST request to update FileUpload : {}", brochureFileUpload);
        if (brochureFileUpload.getId() == null) {
            //return createFileUpload(fileUpload);
        }
        String user = SecurityUtils.getCurrentUserLogin();

        brochureFileUpload.setLastModifiedDate(Instant.now());
        brochureFileUpload.setLastmodifiedBy(user);

        BrochureFileUpload result = brochureFileUploadRepository.save(brochureFileUpload);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, brochureFileUpload.getId().toString()))
            .body(result);
    }

    @DeleteMapping("/brochureMainFile/delete")
    @Timed
    public ResponseEntity<Void> deleteBrochureMainFileUpload(@RequestParam(value ="strategyId") Long strategyId, @RequestParam (value ="borchureId") Long brochureId) {
        log.debug("REST request to delete FileUpload : {}", brochureId,strategyId);
        System.out.println("strategyId Presentaiotid"+strategyId +brochureId);
        borchureStrategyMappingRespository.deleteByBrochureIdAndStrategyId(brochureId,strategyId);
        brochureFileUploadRepository.delete(brochureId);

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
