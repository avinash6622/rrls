package com.unify.rrls.web.rest;


import com.codahale.metrics.annotation.Timed;
import com.unify.rrls.domain.ConfidenctialLetters;
import com.unify.rrls.domain.PresentationFileUpload;
import com.unify.rrls.domain.PresentationStrategyMapping;
import com.unify.rrls.domain.StrategyMaster;
import com.unify.rrls.repository.FileUploadRepository;
import com.unify.rrls.repository.PresentationFileUploadRepository;
import com.unify.rrls.repository.PresentationStrategyRepository;
import com.unify.rrls.repository.StrategyMasterRepository;
import com.unify.rrls.security.SecurityUtils;
import com.unify.rrls.web.rest.util.HeaderUtil;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
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

    public PresentationFileUploadResource(PresentationFileUploadRepository presentationFileUploadRepository,PresentationStrategyRepository presentationStrategyRepository,StrategyMasterRepository strategyMasterRepository)
    {
        this.presentationFileUploadRepository=presentationFileUploadRepository;
        this.presentationStrategyRepository =presentationStrategyRepository;
        this.strategyMasterRepository=strategyMasterRepository;
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


    @RequestMapping(value = "/presentation-file-uploads", method =RequestMethod.POST)
    @Timed
    public ResponseEntity<PresentationFileUpload> createFileUpload(@RequestParam MultipartFile fileUploads,
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
        sFilesDirectory = "src/main/webapp/content/fileUpload/Presentation/" +  user + "-" + uploadfileName;
        File dirFiles = new File(sFilesDirectory);
        dirFiles.mkdirs();

        PresentationFileUpload presentationFileUpload = new PresentationFileUpload();
        PresentationFileUpload presentationFileUploadResult = new PresentationFileUpload();
        PresentationStrategyMapping presentationStrategyMapping= new PresentationStrategyMapping();

            setFileName(fileUploads.getOriginalFilename());
            fileStream = IOUtils.toByteArray(fileUploads.getInputStream());

            System.out.println("FILE NAME--->" + fileName);

            File sFiles = new File(dirFiles, fileName);
            writeFile(fileStream, sFiles);
            presentationFileUpload.setFilePath(sFiles.toString());

            presentationFileUpload.setFileName(uploadfileName);
            presentationFileUpload.setFileContentType(filetype);
            presentationFileUpload.setCreatedBy(user);
            presentationFileUploadResult = presentationFileUploadRepository.save(presentationFileUpload);
            System.out.println("ssdsd"+presentationFileUploadResult.getId());
            System.out.println("dsdsdsd"+presentationFileUploadResult.getCreatedBy());

        StrategyMaster strategyMaster = strategyMasterRepository.findById(strategyId);

        System.out.println("dsdsdsd"+presentationFileUploadResult.getCreatedBy());

        presentationStrategyMapping.setPresentationFileUpload(presentationFileUploadResult);
        presentationStrategyMapping.setStrategyMaster(strategyMaster);

        presentationStrategyRepository.save(presentationStrategyMapping);


        System.out.println("preserntation" +presentationFileUpload.getFileDesc());


        return ResponseEntity.created(new URI("/api/presentation-file-uploads/" + presentationFileUploadResult.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, presentationFileUploadResult.getId().toString()))
            .body(presentationFileUploadResult);
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
