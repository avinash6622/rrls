package com.unify.rrls.web.rest;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;

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

import com.codahale.metrics.annotation.Timed;
import com.unify.rrls.domain.ExternalRAFileUpload;
import com.unify.rrls.domain.ExternalResearchAnalyst;
import com.unify.rrls.domain.OpportunityMaster;
import com.unify.rrls.repository.ExternalRAFileUploadRepository;
import com.unify.rrls.repository.ExternalResearchAnalystRepository;
import com.unify.rrls.repository.OpportunityMasterRepository;
import com.unify.rrls.security.SecurityUtils;
import com.unify.rrls.web.rest.util.HeaderUtil;

@RestController
@RequestMapping("/api")
public class ExternalRAFileUploadResource {
	
	  private static final String ENTITY_NAME = "externalRAFileUpload";
	  
	  private final Logger log = LoggerFactory.getLogger(ExternalRAFileUploadResource.class);
	  
	  @Autowired
	  private final OpportunityMasterRepository opportunityMasterRepository;
	  private final ExternalRAFileUploadRepository externalRAFileUploadRepository;
	  private final ExternalResearchAnalystRepository externalResearchAnalystRepository;
	  
	  public ExternalRAFileUploadResource(OpportunityMasterRepository opportunityMasterRepository,
			  ExternalRAFileUploadRepository externalRAFileUploadRepository,ExternalResearchAnalystRepository externalResearchAnalystRepository) {
	        this.opportunityMasterRepository = opportunityMasterRepository;
	        this.externalRAFileUploadRepository=externalRAFileUploadRepository;
	        this.externalResearchAnalystRepository=externalResearchAnalystRepository;
	       
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
		
		 
	    @RequestMapping(value="/external-upload", method =RequestMethod.POST)	  
	    @Timed
	    public ResponseEntity<ExternalRAFileUpload> createExternalFileUpload(@RequestParam(required=false,value="oppId")Long oppId, @RequestParam(value="fileUploads")
	    		MultipartFile[] fileUploads,@RequestParam(value="filetype") String filetype,@RequestParam(value="uploadfileName") String uploadfileName,
	    		@RequestParam(value="externalId")Integer externalId) throws URISyntaxException, IOException, MissingServletRequestParameterException {
	      
	    	log.debug("REST request to save ExternalRAFileUpload : {}", fileUploads);
	        
	        OpportunityMaster opp=opportunityMasterRepository.findOne(oppId);
	        ExternalResearchAnalyst exRa=externalResearchAnalystRepository.findOne(externalId);
	    
	        String user= SecurityUtils.getCurrentUserLogin();
	        String  sFilesDirectory =  "C:/RRLS_Backup/RRLS/External/"+opp.getMasterName().getOppName()+"/"+user+"-"+uploadfileName;
	      
	      File dirFiles = new File(sFilesDirectory);
	      dirFiles.mkdirs();
	    
	      ExternalRAFileUpload fileUploaded=new ExternalRAFileUpload();
	      ExternalRAFileUpload result =new ExternalRAFileUpload();

	        String extension = "";
	        String name = "";

	      for (MultipartFile sFile : fileUploads) {

	    	 setFileName(sFile.getOriginalFilename());
	    	fileStream = IOUtils.toByteArray(sFile.getInputStream());

	                
	              File sFiles = new File(dirFiles, fileName);
	              writeFile(fileStream, sFiles);
	              fileUploaded.setFileData(sFiles.toString());
	   
	          int idxOfDot =sFile.getOriginalFilename().lastIndexOf('.');   //Get the last index of . to separate extension
	          extension = sFile.getOriginalFilename().substring(idxOfDot + 1).toLowerCase();
	          name = sFile.getOriginalFilename().substring(0, idxOfDot);

	    	
	    	fileUploaded.setFileName(uploadfileName);	    	
	    	fileUploaded.setOpportunityMasterId(opp);
	    	fileUploaded.setFiletype(filetype);
	    	fileUploaded.setExternalResearchAnalyst(exRa);
	    	result=externalRAFileUploadRepository.save(fileUploaded);

	      }
	       
	        return ResponseEntity.created(new URI("/api/external-upload/" + result.getId()))
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

}
