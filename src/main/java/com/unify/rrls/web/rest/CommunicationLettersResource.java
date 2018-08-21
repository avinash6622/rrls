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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.codahale.metrics.annotation.Timed;
import com.unify.rrls.domain.CommunicationLetters;
import com.unify.rrls.domain.OpportunityLearningAIF;
import com.unify.rrls.domain.OpportunityMaster;
import com.unify.rrls.repository.CommunicationLettersRepository;
import com.unify.rrls.repository.OpportunityMasterRepository;
import com.unify.rrls.security.SecurityUtils;
import com.unify.rrls.web.rest.util.HeaderUtil;
import com.unify.rrls.web.rest.util.PaginationUtil;

import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/api")
public class CommunicationLettersResource {
	
	 private final Logger log = LoggerFactory.getLogger(CommunicationLettersResource.class);

	    private static final String ENTITY_NAME = "communicationLetters";
	    
	    @Autowired
	    private final CommunicationLettersRepository communicationLettersRepository;
	    private final OpportunityMasterRepository opportunityMasterRepository;
	    
	    public CommunicationLettersResource(CommunicationLettersRepository communicationLettersRepository,
	    		OpportunityMasterRepository opportunityMasterRepository) {
	        this.communicationLettersRepository=communicationLettersRepository;
	        this.opportunityMasterRepository=opportunityMasterRepository;	       
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
	
	    @RequestMapping(value = "/communication-letter", method =RequestMethod.POST)
	    @Timed
	    public ResponseEntity<CommunicationLetters> createCommunicationLetters(@RequestParam(value="oppId")Long oppId,
	    		@RequestParam(value="fileUploads")MultipartFile[] fileUploads,@RequestParam(value="filetype") String filetype,
	    		@RequestParam(value="uploadfileName") String uploadfileName,@RequestParam(value="subject") String subject) throws URISyntaxException, IOException, MissingServletRequestParameterException {
	      
	      OpportunityMaster opp=opportunityMasterRepository.findOne(oppId);
	     
	        String user= SecurityUtils.getCurrentUserLogin();
	        String  sFilesDirectory =  "C:/RRLS_Backup/RRLS/Communication/"+opp.getMasterName().getOppName()+"/"+user+"-"+uploadfileName;
	    
	      File dirFiles = new File(sFilesDirectory);
	      dirFiles.mkdirs();
	    
	      CommunicationLetters fileUploaded=new CommunicationLetters();
	      CommunicationLetters result =new CommunicationLetters();

	        String extension = "";
	        String name = "";

	      for (MultipartFile sFile : fileUploads) {

	    	setFileName(sFile.getOriginalFilename());
	    	fileStream = IOUtils.toByteArray(sFile.getInputStream());

	          System.out.println("FILE NAME--->"+fileName);

	              File sFiles = new File(dirFiles, fileName);
	              writeFile(fileStream, sFiles);
	              fileUploaded.setFileData(sFiles.toString());
	    
	          int idxOfDot =sFile.getOriginalFilename().lastIndexOf('.');   //Get the last index of . to separate extension
	          extension = sFile.getOriginalFilename().substring(idxOfDot + 1).toLowerCase();
	          name = sFile.getOriginalFilename().substring(0, idxOfDot);

	    
	    	fileUploaded.setFileName(uploadfileName);	    	
	    	fileUploaded.setOpportunityMasterId(opp);
	    	fileUploaded.setFiletype(filetype);
	    	fileUploaded.setSubject(subject);
	    	result=communicationLettersRepository.save(fileUploaded);



	      }
	        
	        return ResponseEntity.created(new URI("/api/communication-letter/" + result.getId()))
	            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
	            .body(result);
	    }
	    
	    @GetMapping("/communication-letter")
		@Timed
		public ResponseEntity<List<CommunicationLetters>> getAllCommunicationLetters(@ApiParam Pageable pageable) {
			log.debug("REST request to get a page of CommunicationLetters");
			Page<CommunicationLetters> page = null;

			page = communicationLettersRepository.findAll(pageable);

			HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/communication-letter");

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

}
