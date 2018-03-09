package com.unify.rrls.web.rest;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;


import com.unify.rrls.security.SecurityUtils;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.codahale.metrics.annotation.Timed;
import com.unify.rrls.domain.FileUpload;
import com.unify.rrls.domain.OpportunityMaster;
import com.unify.rrls.repository.FileUploadRepository;
import com.unify.rrls.repository.OpportunityMasterRepository;
import com.unify.rrls.web.rest.util.HeaderUtil;
import com.unify.rrls.web.rest.util.PaginationUtil;

import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;

/**
 * REST controller for managing FileUpload.
 */
@RestController
@RequestMapping("/api")
public class FileUploadResource {

    private final Logger log = LoggerFactory.getLogger(FileUploadResource.class);

    private static final String ENTITY_NAME = "fileUpload";

    @Autowired
    private final FileUploadRepository fileUploadRepository;
    private final OpportunityMasterRepository opportunityMasterRepository;

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


    public FileUploadResource(FileUploadRepository fileUploadRepository, OpportunityMasterRepository opportunityMasterRepository) {
        this.fileUploadRepository = fileUploadRepository;
        this.opportunityMasterRepository = opportunityMasterRepository;
    }

    /**
     * POST  /file-uploads : Create a new fileUpload.
     *
     * @param fileUpload the fileUpload to create
     * @return the ResponseEntity with status 201 (Created) and with body the new fileUpload, or with status 400 (Bad Request) if the fileUpload has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     * @throws IOException
     */
   // @PostMapping("/file-uploads")
    @RequestMapping(value = "/file-uploads", method =RequestMethod.POST)
    @Timed
    public ResponseEntity<FileUpload> createFileUpload(@RequestParam(value="oppId")Long oppId,
    		@RequestParam(value="fileUploads")
    		MultipartFile[] fileUploads,@RequestParam(value="filetype") String filetype,@RequestParam(value="uploadfileName") String uploadfileName) throws URISyntaxException, IOException, MissingServletRequestParameterException {
        log.debug("REST request to save FileUpload : {}", fileUploads);
        System.out.println("FILETYPE--->"+filetype);
        System.out.println("UPLOADFILE"+uploadfileName);
        OpportunityMaster opp=opportunityMasterRepository.findOne(oppId);
      /*  if (fileUpload.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new fileUpload cannot already have an ID")).body(null);
        }*/
        String user= SecurityUtils.getCurrentUserLogin();
        String  sFilesDirectory =  "src/main/resources/"+opp.getMasterName().getOppName()+"/"+user;
       // String  sFilesDirectoryimg =  "src/main/resources/"+opp.getMasterName().getOppName()+"/"+user+"/image/";
      File dirFiles = new File(sFilesDirectory);
      dirFiles.mkdirs();
     /* File dirFiles2 = new File(sFilesDirectoryimg);
        dirFiles2.mkdirs();*/
      FileUpload fileUploaded=new FileUpload();
      FileUpload result =new FileUpload();


        String extension = "";
        String name = "";



      for (MultipartFile sFile : fileUploads) {

    	 setFileName(sFile.getOriginalFilename());
    	fileStream = IOUtils.toByteArray(sFile.getInputStream());

          System.out.println("FILE NAME--->"+fileName);

          /*if(fileName.contains("xls") || fileName.contains("xlsx"))
          {*/
              File sFiles = new File(dirFiles, fileName);
              writeFile(fileStream, sFiles);
              fileUploaded.setFileData(sFiles.toString());
      //    }

    /*  else{
              File sFiles1 = new File(dirFiles2,fileName);
              writeFile(fileStream,sFiles1);
              fileUploaded.setFileData(sFiles1.toString());
          }

*/
    	// System.out.println("sfiletype---->"+sFiles1);



          int idxOfDot =sFile.getOriginalFilename().lastIndexOf('.');   //Get the last index of . to separate extension
          extension = sFile.getOriginalFilename().substring(idxOfDot + 1).toLowerCase();
          name = sFile.getOriginalFilename().substring(0, idxOfDot);

    	//System.out.println("Filename---->"+sFile.getOriginalFilename());

    	fileUploaded.setFileName(uploadfileName);
    	//fileUploaded.setFileDataContentType(extension);
    	fileUploaded.setOpportunityMasterId(opp);
    	fileUploaded.setFiletype(filetype);
    	result=fileUploadRepository.save(fileUploaded);
      }
        //fileUploadRepository.save(fileUpload);
        return ResponseEntity.created(new URI("/api/file-uploads/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /file-uploads : Updates an existing fileUpload.
     *
     * @param fileUpload the fileUpload to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated fileUpload,
     * or with status 400 (Bad Request) if the fileUpload is not valid,
     * or with status 500 (Internal Server Error) if the fileUpload couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/file-uploads")
    @Timed
    public ResponseEntity<FileUpload> updateFileUpload(@Valid @RequestBody FileUpload fileUpload) throws URISyntaxException {
        log.debug("REST request to update FileUpload : {}", fileUpload);
        if (fileUpload.getId() == null) {
            //return createFileUpload(fileUpload);
        }
        FileUpload result = fileUploadRepository.save(fileUpload);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, fileUpload.getId().toString()))
            .body(result);
    }

    /**
     * GET  /file-uploads : get all the fileUploads.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of fileUploads in body
     */
    @GetMapping("/file-uploads")
    @Timed
    public ResponseEntity<List<FileUpload>> getAllFileUploads(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of FileUploads");
        Page<FileUpload> page = fileUploadRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/file-uploads");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /file-uploads/:id : get the "id" fileUpload.
     *
     * @param id the id of the fileUpload to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the fileUpload, or with status 404 (Not Found)
     */
    @GetMapping("/file-uploads/{id}")
    @Timed
    public ResponseEntity<FileUpload> getFileUpload(@PathVariable Long id) {
        log.debug("REST request to get FileUpload : {}", id);
        FileUpload fileUpload = fileUploadRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(fileUpload));
    }

    /**
     * DELETE  /file-uploads/:id : delete the "id" fileUpload.
     *
     * @param id the id of the fileUpload to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/file-uploads/{id}")
    @Timed
    public ResponseEntity<Void> deleteFileUpload(@PathVariable Long id) {
        log.debug("REST request to delete FileUpload : {}", id);
        fileUploadRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
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
