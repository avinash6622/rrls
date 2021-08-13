package com.unify.rrls.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.unify.rrls.domain.PoliciesFileUpload;
import com.unify.rrls.repository.PoliciesFileUploadRepository;
import com.unify.rrls.security.SecurityUtils;
import com.unify.rrls.web.rest.util.HeaderUtil;
import com.unify.rrls.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
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

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.springframework.http.MediaType;
import org.springframework.core.io.FileSystemResource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import com.unify.rrls.config.ApplicationProperties;

@RestController
@RequestMapping("/api")
public class PoliciesFileUploadResource {
    private final Logger log = LoggerFactory.getLogger(PoliciesFileUploadResource.class);

    private static final String ENTITY_NAME = "policiesFileUpload";

    @Autowired
    private final PoliciesFileUploadRepository policiesFileUploadRepository;
    private final ApplicationProperties applicationProperties;

    public PoliciesFileUploadResource(PoliciesFileUploadRepository policiesFileUploadRepository, ApplicationProperties applicationProperties) {
        this.policiesFileUploadRepository = policiesFileUploadRepository;
        this.applicationProperties = applicationProperties;
    }

    private byte[] fileStream;
    private String fileName;
//    private String policyName;


    public byte[] getFileStream() {
        return fileStream;
    }

    public void setFileStream(byte[] fileStream) {
        this.fileStream = fileStream;
    }

    public String getFilename() {
        return fileName;
    }

    public void setFilename(String filename) {
        this.fileName = filename;
    }

//    public String getPolicyName() { return policyName;}
//
//    public void setPolicyName(String policyName) { this.policyName = policyName; }

    @PostMapping("/policies")
    @Timed
    public ResponseEntity<PoliciesFileUpload> cretaePoliciesFileUpload(@RequestParam MultipartFile fileUploads, String filetype, String uploadfileName, String policyName) throws URISyntaxException, IOException, MissingServletRequestParameterException {

        System.out.println("fileName" + fileName);

        String user = SecurityUtils.getCurrentUserLogin();
        String sFilesDirectory = applicationProperties.getDatafolder()+"/Policies/" + "/" + user + "-" + uploadfileName;


        File dirFiles = new File(sFilesDirectory);
        dirFiles.mkdirs();

        PoliciesFileUpload fileUploaded = new PoliciesFileUpload();
        PoliciesFileUpload result = new PoliciesFileUpload();


        String extension = "";
        String name = "";

        //  for (MultipartFile sFile : fileUploads) {
        setFilename(fileUploads.getOriginalFilename());

        fileStream = IOUtils.toByteArray(fileUploads.getInputStream());

        File sFiles = new File(dirFiles, fileName);
        writeFile(fileStream, sFiles);
        System.out.println("sFiles "+ sFiles);
        String filePath = sFiles.toString();
        String[] paths = filePath.split("fileUpload");
        System.out.println("path"+paths[1]);
        fileUploaded.setFileData(paths[1]);

        int idxOfDot = fileUploads.getOriginalFilename().lastIndexOf('.');
        extension = fileUploads.getOriginalFilename().substring(idxOfDot + 1).toLowerCase();
        name = fileUploads.getOriginalFilename().substring(0, idxOfDot);

        fileUploaded.setFileName(fileName);
        fileUploaded.setFiletype(filetype);
        fileUploaded.setPolicyName(policyName);
        result = policiesFileUploadRepository.save(fileUploaded);
        System.out.println(result.getFileData());
        System.out.println(result.getFileName());
        System.out.println(result.getFiletype());
        System.out.println(result.getCreatedBy());
        System.out.println(result.getCreatedDate());
        System.out.println(result.getLastModifiedBy());
        System.out.println(result.getLastModifiedDate());
        System.out.print(result.getPolicyName());

        // }

        return ResponseEntity.created(new URI("/api/policiesFileUpload/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
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

    @DeleteMapping("/delete-PoliciesFileUpload/{id}")
    public ResponseEntity<Void> deletePoliciesFileUpload(@PathVariable Integer id) {
        policiesFileUploadRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("A policy is deleted with identifier " + id, id.toString())).build();
    }

    @GetMapping("/policies")
    public ResponseEntity<List<PoliciesFileUpload>> getAllPolicies(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Policies");
        Page<PoliciesFileUpload> page = policiesFileUploadRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/policies");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
    @GetMapping("/policies/{id}")
    public ResponseEntity<PoliciesFileUpload> getPoliciesById(@PathVariable Integer id) {
        log.debug("REST request to get Policies: {}", id);
        PoliciesFileUpload policies3 = policiesFileUploadRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(policies3));
    }

    @GetMapping("/ur/policies/fileDownload/{id}")
    @Timed
    public ResponseEntity getFile(@PathVariable("id") String id) throws IOException {
        System.out.println("id"+id);
        PoliciesFileUpload policies3 = policiesFileUploadRepository.findById(Integer.valueOf(id));
        if (policies3 != null) {
            String path= applicationProperties.getDatafolder()+policies3.getFileData();
            File file = new File(path);
            System.out.println("file"+file.getName() + file.exists());
            System.out.println(file);
            if (file.exists()) {
                return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=" + path)
                    .contentLength(file.length())
                    .lastModified(file.lastModified())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(new FileSystemResource(file));
            } else {
                return ResponseEntity.ok().body("file not found");
            }
        }
        return null;
    }
}
