package com.unify.rrls.web.rest;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.codahale.metrics.annotation.Timed;
import com.unify.rrls.domain.FileUpload;
import com.unify.rrls.domain.FinancialSummaryData;
import com.unify.rrls.domain.NonFinancialSummaryData;
import com.unify.rrls.domain.OpportunityMaster;
import com.unify.rrls.domain.OpportunitySummaryData;
import com.unify.rrls.domain.StrategyMapping;
import com.unify.rrls.repository.FileUploadRepository;
import com.unify.rrls.repository.FinancialSummaryDataRepository;
import com.unify.rrls.repository.NonFinancialSummaryDataRepository;
import com.unify.rrls.repository.OpportunityMasterRepository;
import com.unify.rrls.repository.OpportunitySummaryDataRepository;
import com.unify.rrls.repository.StrategyMappingRepository;
import com.unify.rrls.security.SecurityUtils;
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
    private final FinancialSummaryDataRepository financialSummaryDataRepository;
    private final NonFinancialSummaryDataRepository nonFinancialSummaryDataRepository;
    private final OpportunitySummaryDataRepository opportunitySummaryDataRepository;
    private final StrategyMappingRepository strategyMappingRepository;

    @Autowired
    NotificationServiceResource notificationServiceResource;

    @Autowired
    UserResource userResource;


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


	  public FileUploadResource(FileUploadRepository fileUploadRepository, OpportunityMasterRepository opportunityMasterRepository,
	    		FinancialSummaryDataRepository financialSummaryDataRepository,NonFinancialSummaryDataRepository nonFinancialSummaryDataRepository,
	    		OpportunitySummaryDataRepository opportunitySummaryDataRepository,StrategyMappingRepository strategyMappingRepository) {
	        this.fileUploadRepository = fileUploadRepository;
	        this.opportunityMasterRepository = opportunityMasterRepository;
	        this.financialSummaryDataRepository=financialSummaryDataRepository;
	        this.nonFinancialSummaryDataRepository=nonFinancialSummaryDataRepository;
	        this.opportunitySummaryDataRepository=opportunitySummaryDataRepository;
	        this.strategyMappingRepository=strategyMappingRepository;
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
        String  sFilesDirectory =  "C:/RRLS_Backup/RRLS/"+opp.getMasterName().getOppName()+"/"+user;
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



         String oppName = result.getOpportunityMasterId().getMasterName().getOppName();

         String page ="Opportunity";


          Long id =  userResource.getUserId(result.getCreatedBy());

      notificationServiceResource.notificationHistorysave(oppName,result.getCreatedBy(),result.getLastModifiedBy(),result.getCreatedDate(),"uploaded",page,result.getFileName(),id,result.getOpportunityMasterId().getId());




      }
        //fileUploadRepository.save(fileUpload);
        return ResponseEntity.created(new URI("/api/file-uploads/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @SuppressWarnings("deprecation")
	@RequestMapping(value = "/file-upload-data", method =RequestMethod.POST)
    @Timed
    public ResponseEntity<FileUpload> uploadSummaryData(@RequestParam(value="oppId")Long oppId,
    		@RequestParam(value="fileUploads")
    		MultipartFile[] fileUploads) throws URISyntaxException, IOException, MissingServletRequestParameterException {
        log.debug("REST request to save FileUpload : {}", fileUploads);
        OpportunityMaster opportunityMaster=opportunityMasterRepository.findOne(oppId);
        List<StrategyMapping> strategyMap=strategyMappingRepository.findByOpportunityMaster(opportunityMaster);

        int iCurrRowNum=0;

        String user= SecurityUtils.getCurrentUserLogin();
        String  sFilesDirectory =  "src/main/resources/"+opportunityMaster.getMasterName().getOppName()+"/summary/"+user;

      File dirFiles = new File(sFilesDirectory);
      dirFiles.mkdirs();

      FileUpload fileUploaded=new FileUpload();

      for (MultipartFile sFile : fileUploads) {

    	 setFileName(sFile.getOriginalFilename());
    	fileStream = IOUtils.toByteArray(sFile.getInputStream());

          System.out.println("FILE NAME--->"+fileName);

              File sFiles = new File(dirFiles, fileName);
              writeFile(fileStream, sFiles);
              fileUploaded.setFileData(sFiles.toString());
              if(opportunityMaster.getMasterName().getSegment().equals("Finance")){
                  try {
                  	FinancialSummaryData finance=financialSummaryDataRepository.findByOpportunityMasterId(opportunityMaster);
                  	//String file="C:\\Users\\girija noah\\Desktop\\Financial template.xlsx";

                      FileInputStream fis = new FileInputStream(sFiles);
                      //FileInputStream fis = new FileInputStream(new File("C:\\Users\\Noah\\AppData\\Roaming\\Skype\\My Skype Received Files\\2604 nov.xls"));

                      XSSFWorkbook workbook = new XSSFWorkbook(fis);

                      XSSFSheet sheet = workbook.getSheetAt(0);
                      int iPutNxtDetailsToDB = 0;

                      Iterator<Row> rowIterator = sheet.iterator();
                      int iTotRowInserted = 0;
                      int iPhysNumOfCells;
                      String dDate = "";
                      Integer iSheetCheck=0;

                      while (rowIterator.hasNext()) {
                          Row row = rowIterator.next();
                          iCurrRowNum = row.getRowNum() + 1;

                          // Row row2 = row;
                          iTotRowInserted++;
                          Iterator<Cell> cellIterator = row.cellIterator();
                          int iPos = 0;
                          int iTotConToMeet = 1;
                          int iTotConMet = 0;
                          int iConCheckNow = 0;
                          String sConCheck = "";
                          iPhysNumOfCells = row.getPhysicalNumberOfCells();

                          while (cellIterator.hasNext() && iPutNxtDetailsToDB == 0) {
                              Cell cell = cellIterator.next();

                              if (iPutNxtDetailsToDB == 0) {
                                  if (iPos == 0)
                                      sConCheck = "INR Cr";

                                  if (cell.getStringCellValue().trim().equals(sConCheck))
                                      iTotConMet++;

                                  iPos++;
                                  if (iTotConToMeet == iTotConMet) {
                                      iPutNxtDetailsToDB = 1;
                                      iConCheckNow = 1;
                                  }
                              }
                          }

                          if (iPutNxtDetailsToDB == 1 && iConCheckNow == 0) {

                              if (iPhysNumOfCells != 1) {

                                  while (cellIterator.hasNext()) {
                                      Cell cell = cellIterator.next();
                                      cell.setCellType(CellType.STRING);}
                                     
                                  String finColumn = row.getCell(0).getStringCellValue();
                                
                                  if (finColumn.contains("Net Interest Income")) {
                                	if(row.getCell(1).getStringCellValue()!="")
                                  	finance.setNetIntOne(Double.parseDouble(row.getCell(1).getStringCellValue()));                                	
                                	if(row.getCell(2).getStringCellValue()!="")
                                  	finance.setNetIntTwo(Double.parseDouble(row.getCell(2).getStringCellValue()));                                	
                                	if(row.getCell(3).getStringCellValue()!="")
                                  	finance.setNetIntThree(Double.parseDouble(row.getCell(3).getStringCellValue()));
                                	if(row.getCell(4).getStringCellValue()!="")
                                  	finance.setNetIntFour(Double.parseDouble(row.getCell(4).getStringCellValue()));
                                	if(row.getCell(5).getStringCellValue()!="")
                                  	finance.setNetIntFive(Double.parseDouble(row.getCell(5).getStringCellValue()));}
                                 if (finColumn.contains("Non Interest Income")){
                                	 if(row.getCell(1).getStringCellValue()!="")
                              	   finance.setNonIntOne(Double.parseDouble(row.getCell(1).getStringCellValue()));
                                	 if(row.getCell(2).getStringCellValue()!="")
                             		   finance.setNonIntTwo(Double.parseDouble(row.getCell(2).getStringCellValue()));
                                	 if(row.getCell(3).getStringCellValue()!="")
                             		   finance.setNonIntThree(Double.parseDouble(row.getCell(3).getStringCellValue()));
                                	 if(row.getCell(4).getStringCellValue()!="")
                             		   finance.setNonIntFour(Double.parseDouble(row.getCell(4).getStringCellValue()));
                                	 if(row.getCell(5).getStringCellValue()!="")
                             		   finance.setNonIntFive(Double.parseDouble(row.getCell(5).getStringCellValue()));}
                                  if (finColumn.contains("Total Income"))
                                  {   if(row.getCell(1).getStringCellValue()!="")
                                	  finance.setTotIncOne(Double.parseDouble(row.getCell(1).getStringCellValue()));
                                  if(row.getCell(2).getStringCellValue()!="")
                                 		   finance.setTotIncTwo(Double.parseDouble(row.getCell(2).getStringCellValue()));
                                  if(row.getCell(3).getStringCellValue()!="")
                                 		   finance.setTotIncThree(Double.parseDouble(row.getCell(3).getStringCellValue()));
                                  if(row.getCell(4).getStringCellValue()!="")
                                 		   finance.setTotIncFour(Double.parseDouble(row.getCell(4).getStringCellValue()));
                                  if(row.getCell(5).getStringCellValue()!="")
                                 		   finance.setTotIncFive(Double.parseDouble(row.getCell(5).getStringCellValue()));}
                                  if (finColumn.contains("Operating Expenses"))
                                  { if(row.getCell(1).getStringCellValue()!="")
                                	  finance.setOpExpOne(Double.parseDouble(row.getCell(1).getStringCellValue()));
                                  if(row.getCell(2).getStringCellValue()!="")
                             		   finance.setOpExpTwo(Double.parseDouble(row.getCell(2).getStringCellValue()));
                                  if(row.getCell(3).getStringCellValue()!="")
                             		   finance.setOpExpThree(Double.parseDouble(row.getCell(3).getStringCellValue()));
                                  if(row.getCell(4).getStringCellValue()!="")
                             		   finance.setOpExpFour(Double.parseDouble(row.getCell(4).getStringCellValue()));
                                  if(row.getCell(5).getStringCellValue()!="")
                             		   finance.setOpExpFive(Double.parseDouble(row.getCell(5).getStringCellValue()));}
                                  if (finColumn.contains("Operating Profit"))
                                  {  if(row.getCell(1).getStringCellValue()!="")
                                	  finance.setOpProOne(Double.parseDouble(row.getCell(1).getStringCellValue()));
                                  if(row.getCell(2).getStringCellValue()!="")
                             		   finance.setOpProTwo(Double.parseDouble(row.getCell(2).getStringCellValue()));
                                  if(row.getCell(3).getStringCellValue()!="")
                             		   finance.setOpProThree(Double.parseDouble(row.getCell(3).getStringCellValue()));
                                  if(row.getCell(4).getStringCellValue()!="")
                             		   finance.setOpProFour(Double.parseDouble(row.getCell(4).getStringCellValue()));
                                  if(row.getCell(5).getStringCellValue()!="")
                             		   finance.setOpProFive(Double.parseDouble(row.getCell(5).getStringCellValue()));}
                                  if (finColumn.contains("Provisions"))
                                  {  if(row.getCell(1).getStringCellValue()!="")
                                	  finance.setProvisionsOne(Double.parseDouble(row.getCell(1).getStringCellValue()));
                                  if(row.getCell(2).getStringCellValue()!="")
                             		   finance.setProvisionsTwo(Double.parseDouble(row.getCell(2).getStringCellValue()));
                                  if(row.getCell(3).getStringCellValue()!="")
                             		   finance.setProvisionsThree(Double.parseDouble(row.getCell(3).getStringCellValue()));
                                  if(row.getCell(4).getStringCellValue()!="")
                             		   finance.setProvisionsFour(Double.parseDouble(row.getCell(4).getStringCellValue()));
                                  if(row.getCell(5).getStringCellValue()!="")
                             		   finance.setProvisionsFive(Double.parseDouble(row.getCell(5).getStringCellValue()));}
                                  if (finColumn.contains("PAT"))
                                  {
                                	  if(row.getCell(1).getStringCellValue()!="")
                                	  finance.setPatOne(Double.parseDouble(row.getCell(1).getStringCellValue()));
                                	  if(row.getCell(2).getStringCellValue()!="")
                             		   finance.setPatTwo(Double.parseDouble(row.getCell(2).getStringCellValue()));
                                	  if(row.getCell(3).getStringCellValue()!="")
                             		   finance.setPatThree(Double.parseDouble(row.getCell(3).getStringCellValue()));
                                	  if(row.getCell(4).getStringCellValue()!="")
                             		   finance.setPatFour(Double.parseDouble(row.getCell(4).getStringCellValue()));
                                	  if(row.getCell(5).getStringCellValue()!="")
                             		   finance.setPatFive(Double.parseDouble(row.getCell(5).getStringCellValue()));}
                                  if (finColumn.contains("EPS"))
                                  { 
                                	  if(row.getCell(1).getStringCellValue()!="")
                                	  finance.setEpsOne(Double.parseDouble(row.getCell(1).getStringCellValue()));
                                	  if(row.getCell(2).getStringCellValue()!="")
                             		   finance.setEpsTwo(Double.parseDouble(row.getCell(2).getStringCellValue()));
                                	  if(row.getCell(3).getStringCellValue()!="")
                             		   finance.setEpsThree(Double.parseDouble(row.getCell(3).getStringCellValue()));
                                	  if(row.getCell(4).getStringCellValue()!="")
                             		   finance.setEpsFour(Double.parseDouble(row.getCell(4).getStringCellValue()));
                                	  if(row.getCell(5).getStringCellValue()!="")
                             		   finance.setEpsFive(Double.parseDouble(row.getCell(5).getStringCellValue()));}
                                  if (finColumn.contains("Market Cap"))
                                  { /* finance.setMarCapOne(row.getCell(1).getNumericCellValue());
                             		   finance.setMarCapTwo(row.getCell(2).getNumericCellValue());*/
                                	  if(row.getCell(3).getStringCellValue()!="")
                             		   finance.setMarCapThree(Double.parseDouble(row.getCell(3).getStringCellValue()));
                             		  /* finance.setMarCapFour(row.getCell(4).getNumericCellValue());
                             		   finance.setMarCapFive(row.getCell(5).getNumericCellValue());*/}
                                  if (finColumn.contains("AUM"))
                                  { 
                                	  if(row.getCell(1).getStringCellValue()!="")
                                	  finance.setAumOne(Double.parseDouble(row.getCell(1).getStringCellValue()));
                                	  if(row.getCell(2).getStringCellValue()!="")
                             		   finance.setAumTwo(Double.parseDouble(row.getCell(2).getStringCellValue()));
                                	  if(row.getCell(3).getStringCellValue()!="")
                             		   finance.setAumThree(Double.parseDouble(row.getCell(3).getStringCellValue()));
                                	  if(row.getCell(4).getStringCellValue()!="")
                             		   finance.setAumFour(Double.parseDouble(row.getCell(4).getStringCellValue()));
                                	  if(row.getCell(5).getStringCellValue()!="")
                             		   finance.setAumFive(Double.parseDouble(row.getCell(5).getStringCellValue()));}
                                  if (finColumn.contains("Networth"))
                                  {  
                                	  if(row.getCell(1).getStringCellValue()!="")
                                	  finance.setNetworthOne(Double.parseDouble(row.getCell(1).getStringCellValue()));
                                	  if(row.getCell(2).getStringCellValue()!="")
                             		   finance.setNetworthTwo(Double.parseDouble(row.getCell(2).getStringCellValue()));
                                	  if(row.getCell(3).getStringCellValue()!="")
                             		   finance.setNetworthThree(Double.parseDouble(row.getCell(3).getStringCellValue()));
                                	  if(row.getCell(4).getStringCellValue()!="")
                             		   finance.setNetworthFour(Double.parseDouble(row.getCell(4).getStringCellValue()));
                                	  if(row.getCell(5).getStringCellValue()!="")
                             		   finance.setNetworthfive(Double.parseDouble(row.getCell(5).getStringCellValue()));}
                                  if (finColumn.contains("ROE"))
                                  { 
                                	  if(row.getCell(1).getStringCellValue()!="")
                                	  finance.setRoeOne((Double.parseDouble(row.getCell(1).getStringCellValue()))*100);
                                	  if(row.getCell(2).getStringCellValue()!="")
                             		   finance.setRoeTwo((Double.parseDouble(row.getCell(2).getStringCellValue()))*100);
                                	  if(row.getCell(3).getStringCellValue()!="")
                             		   finance.setRoeThree((Double.parseDouble(row.getCell(3).getStringCellValue()))*100);
                                	  if(row.getCell(4).getStringCellValue()!="")
                             		   finance.setRoeFour((Double.parseDouble(row.getCell(4).getStringCellValue()))*100);
                                	  if(row.getCell(5).getStringCellValue()!="")
                             		   finance.setRoeFive((Double.parseDouble(row.getCell(5).getStringCellValue()))*100);}
                                  if (finColumn.contains("PBV"))
                                  {  
                                	  if(row.getCell(1).getStringCellValue()!="")
                                	  finance.setPbvOne(Double.parseDouble(row.getCell(1).getStringCellValue()));
                                	  if(row.getCell(2).getStringCellValue()!="")
                             		   finance.setPbvTwo(Double.parseDouble(row.getCell(2).getStringCellValue()));
                                	  if(row.getCell(3).getStringCellValue()!="")
                             		   finance.setPbvThree(Double.parseDouble(row.getCell(3).getStringCellValue()));
                                	  if(row.getCell(4).getStringCellValue()!="")
                             		   finance.setPbvFour(Double.parseDouble(row.getCell(4).getStringCellValue()));
                                	  if(row.getCell(5).getStringCellValue()!="")
                             		   finance.setPbvFive(Double.parseDouble(row.getCell(5).getStringCellValue()));}
                                  if (finColumn.contains("PE"))
                                  { 
                                	  if(row.getCell(1).getStringCellValue()!="")
                                	  finance.setPeOne(Double.parseDouble(row.getCell(1).getStringCellValue()));
                                	  if(row.getCell(2).getStringCellValue()!="")
                             		   finance.setPeTwo(Double.parseDouble(row.getCell(2).getStringCellValue()));
                                	  if(row.getCell(3).getStringCellValue()!="")
                             		   finance.setPeThree(Double.parseDouble(row.getCell(3).getStringCellValue()));
                                	  if(row.getCell(4).getStringCellValue()!="")
                             		   finance.setPeFour(Double.parseDouble(row.getCell(4).getStringCellValue()));
                                	  if(row.getCell(5).getStringCellValue()!="")
                             		   finance.setPeFive(Double.parseDouble(row.getCell(5).getStringCellValue()));}
                              
                          }

                          }
                      }

                      financialSummaryDataRepository.save(finance);
                      List<OpportunitySummaryData> opportunitySummaryDataList = opportunitySummaryDataRepository.findByOpportunityMasterid(opportunityMaster);

                      for (OpportunitySummaryData sm : opportunitySummaryDataList) {

                    	  sm.setPatFirstYear(finance.getPatOne());
                    	  sm.setPatSecondYear(finance.getPatTwo());
                    	  sm.setPatThirdYear(finance.getPatThree());
                    	  sm.setPatFourthYear(finance.getPatFour());
                    	  sm.setPatFifthYear(finance.getPatFive());
                    	  sm.setMarketCap(finance.getMarCapThree());
                    	  sm.setRoeFirstYear(finance.getRoeOne());
                    	  sm.setRoeSecondYear(finance.getRoeTwo());
                    	  sm.setRoeThirdYear(finance.getRoeThree());
                    	  sm.setRoeFourthYear(finance.getRoeFour());
                    	  sm.setRoeFifthYear(finance.getRoeFive());
                    	  sm.setPeFirstYear(finance.getPeOne());
                    	  sm.setPeSecondYear(finance.getPeTwo());
                    	  sm.setPeThirdYear(finance.getPeThree());
                    	  sm.setPeFourthYear(finance.getPeFour());
                    	  sm.setPeFifthYear(finance.getPeFive());
                      opportunitySummaryDataRepository.save(opportunitySummaryDataList);

                       }
                      log.info("In financial uploadFinancial" + iTotRowInserted + " inserted");

                  } catch (FileNotFoundException e) {
                      log.error("Exception at financial uploadFinancial() method " + e);
                  } catch (IOException e) {
                      log.error("Exception at financial uploadFinancial() method " + e);
                  } catch (Exception e) {
                      log.error("Exception at financial uploadFinancial() method at row "+iCurrRowNum + e);
                  }

          }if(!opportunityMaster.getMasterName().getSegment().equals("Finance")){
                  try {
                  	NonFinancialSummaryData nonFinance=nonFinancialSummaryDataRepository.findByOpportunityMaster(opportunityMaster);
                  	//String file="C:\\Users\\girija noah\\Desktop\\Non-Financial.xlsx";

                      FileInputStream fis = new FileInputStream(sFiles);
                      //FileInputStream fis = new FileInputStream(new File("C:\\Users\\Noah\\AppData\\Roaming\\Skype\\My Skype Received Files\\2604 nov.xls"));

                      XSSFWorkbook workbook = new XSSFWorkbook(fis);

                      XSSFSheet sheet = workbook.getSheetAt(0);
                      int iPutNxtDetailsToDB = 0;

                      Iterator<Row> rowIterator = sheet.iterator();
                      int iTotRowInserted = 0;
                      int iPhysNumOfCells;
                      String dDate = "";
                      Integer iSheetCheck=0;

                      while (rowIterator.hasNext()) {
                          Row row = rowIterator.next();
                          iCurrRowNum = row.getRowNum() + 1;

                          // Row row2 = row;
                          iTotRowInserted++;
                          Iterator<Cell> cellIterator = row.cellIterator();
                          int iPos = 0;
                          int iTotConToMeet = 1;
                          int iTotConMet = 0;
                          int iConCheckNow = 0;
                          String sConCheck = "";
                          iPhysNumOfCells = row.getPhysicalNumberOfCells();

                          while (cellIterator.hasNext() && iPutNxtDetailsToDB == 0) {
                              Cell cell = cellIterator.next();

                              if (iPutNxtDetailsToDB == 0) {
                                  if (iPos == 0)
                                      sConCheck = "INR Cr";

                                  if (cell.getStringCellValue().trim().equals(sConCheck))
                                      iTotConMet++;

                                  iPos++;
                                  if (iTotConToMeet == iTotConMet) {
                                      iPutNxtDetailsToDB = 1;
                                      iConCheckNow = 1;
                                  }
                              }
                          }

                          if (iPutNxtDetailsToDB == 1 && iConCheckNow == 0) {

                              if (iPhysNumOfCells != 1 && iPhysNumOfCells != 0) {

                                  while (cellIterator.hasNext()) {
                                	  Cell cell = cellIterator.next();         							
                                	  cell.setCellType(CellType.STRING);}
                                  String finColumn = row.getCell(0).getStringCellValue();
                                
                                  if (finColumn.equals("Revenues")) {
                                	if(row.getCell(1).getStringCellValue()!="")
                                  	nonFinance.setRevenueOne(Double.parseDouble(row.getCell(1).getStringCellValue()));
                                	else
                                		nonFinance.setRevenueOne(0.0);	
                                	if(row.getCell(2).getStringCellValue()!="")
                                  	nonFinance.setRevenueTwo(Double.parseDouble(row.getCell(2).getStringCellValue()));
                                	else
                                		nonFinance.setRevenueTwo(0.0);
                                	if(row.getCell(3).getStringCellValue()!="")
                                  	nonFinance.setRevenueThree(Double.parseDouble(row.getCell(3).getStringCellValue()));
                                	else
                                		nonFinance.setRevenueThree(0.0);
                                	if(row.getCell(4).getStringCellValue()!="")
                                  	nonFinance.setRevenueFour(Double.parseDouble(row.getCell(4).getStringCellValue()));
                                	else
                                		nonFinance.setRevenueFour(0.0);
                                  	if(row.getCell(5).getStringCellValue()!="")
                                  	nonFinance.setRevenueFive(Double.parseDouble(row.getCell(5).getStringCellValue()));
                                  	else
                                  		nonFinance.setRevenueFive(0.0);}
                                 if (finColumn.equals("Rev.Growth(%)")){
                              	  /* nonFinance.setRevGrowthOne(row.getCell(1).getNumericCellValue());*/
                                	 if(row.getCell(2).getStringCellValue()!="")
                              	   nonFinance.setRevGrowthTwo((Double.parseDouble(row.getCell(2).getStringCellValue()))*100);
                                	 else
                                		 nonFinance.setRevGrowthTwo(0.0);
                                	 if(row.getCell(3).getStringCellValue()!="")
                              	   nonFinance.setRevGrowthThree((Double.parseDouble(row.getCell(3).getStringCellValue())*100));
                                	 else
                                		 nonFinance.setRevGrowthThree(0.0);
                                	 if(row.getCell(4).getStringCellValue()!="")
                              	   nonFinance.setRevGrowthFour((Double.parseDouble(row.getCell(4).getStringCellValue()))*100);
                                	 else
                                		 nonFinance.setRevGrowthFour(0.0);
                                	 if(row.getCell(5).getStringCellValue()!="")
                              	   nonFinance.setRevGrowthFive((Double.parseDouble(row.getCell(5).getStringCellValue()))*100);
                                	 else
                                		 nonFinance.setRevGrowthFive(0.0);}
                                  if (finColumn.equals("EBITDA"))
                                  {
                                	  if(row.getCell(1).getStringCellValue()!="")
                                  nonFinance.setEbitdaOne(Double.parseDouble(row.getCell(1).getStringCellValue()));
                                	  else
                                		  nonFinance.setEbitdaOne(0.0);  
                                	  if(row.getCell(2).getStringCellValue()!="")
                                  nonFinance.setEbitdaTwo(Double.parseDouble(row.getCell(2).getStringCellValue()));
                                	  else
                                		  nonFinance.setEbitdaTwo(0.0);
                                	  if(row.getCell(3).getStringCellValue()!="")
                                  nonFinance.setEbitdaThree(Double.parseDouble(row.getCell(3).getStringCellValue()));
                                	  else
                                		  nonFinance.setEbitdaThree(0.0);
                                	  if(row.getCell(4).getStringCellValue()!="")
                                  nonFinance.setEbitdaFour(Double.parseDouble(row.getCell(4).getStringCellValue()));
                                	  else
                                		  nonFinance.setEbitdaFour(0.0);
                                	  if(row.getCell(5).getStringCellValue()!="")
                                  nonFinance.setEbitdaFive(Double.parseDouble(row.getCell(5).getStringCellValue()));
                                	  else
                                		  nonFinance.setEbitdaFive(0.0);}
                                  if (finColumn.equals("Margin(%)"))
                                  { if(row.getCell(1).getStringCellValue()!="")
                                	  nonFinance.setMarginOne((Double.parseDouble(row.getCell(1).getStringCellValue()))*100);
                                  else
                                	  nonFinance.setMarginOne(0.0);
                                  if(row.getCell(2).getStringCellValue()!="")
                                  nonFinance.setMarginTwo((Double.parseDouble(row.getCell(2).getStringCellValue()))*100);
                                  else
                                	  nonFinance.setMarginTwo(0.0);
                                  if(row.getCell(3).getStringCellValue()!="")
                                  nonFinance.setMarginThree((Double.parseDouble(row.getCell(3).getStringCellValue()))*100);
                                  else
                                	  nonFinance.setMarginThree(0.0);  
                                  if(row.getCell(4).getStringCellValue()!="")
                                  nonFinance.setMarginFour((Double.parseDouble(row.getCell(4).getStringCellValue()))*100);
                                  else
                                	  nonFinance.setMarginFour(0.0);
                                  if(row.getCell(5).getStringCellValue()!="")
                                  nonFinance.setMarginFive((Double.parseDouble(row.getCell(5).getStringCellValue()))*100);
                                  else
                                	  nonFinance.setMarginFive(0.0);}
                                  if (finColumn.equals("EBI.Growth(%)"))
                                  { /* nonFinance.setEbitdaGrowthOne(row.getCell(1).getNumericCellValue());*/
                                	  if(row.getCell(2).getStringCellValue()!="")
                                  nonFinance.setEbitdaGrowthTwo((Double.parseDouble(row.getCell(2).getStringCellValue()))*100);
                                	  else
                                		  nonFinance.setEbitdaGrowthTwo(0.0);
                                	  if(row.getCell(3).getStringCellValue()!="")
                                  nonFinance.setEbitdaGrowthThree((Double.parseDouble(row.getCell(3).getStringCellValue()))*100);
                                	  else
                                		  nonFinance.setEbitdaGrowthThree(0.0);
                                	  if(row.getCell(4).getStringCellValue()!="")
                                  nonFinance.setEbitdaGrowthFour((Double.parseDouble(row.getCell(4).getStringCellValue()))*100);
                                	  else
                                		  nonFinance.setEbitdaGrowthFour(0.0);
                                	  if(row.getCell(5).getStringCellValue()!="")
                                  nonFinance.setEbitdaGrowthFive((Double.parseDouble(row.getCell(5).getStringCellValue()))*100);
                                	  else
                                		  nonFinance.setEbitdaGrowthFive(0.0);}
                                  if (finColumn.equals("Other Income"))
                                  {
                                	  if(row.getCell(1).getStringCellValue()!="")
                                	  nonFinance.setOtherIncOne(Double.parseDouble(row.getCell(1).getStringCellValue()));
                                	  else
                                		  nonFinance.setOtherIncOne(0.0);
                                	  if(row.getCell(2).getStringCellValue()!="")
                                  nonFinance.setOtherIncTwo(Double.parseDouble(row.getCell(2).getStringCellValue()));
                                	  else
                                		  nonFinance.setOtherIncTwo(0.0);
                                	  if(row.getCell(3).getStringCellValue()!="")
                                  nonFinance.setOtherIncThree(Double.parseDouble(row.getCell(3).getStringCellValue()));
                                	  else
                                		  nonFinance.setOtherIncThree(0.0);
                                	  if(row.getCell(4).getStringCellValue()!="")
                                  nonFinance.setOtherIncFour(Double.parseDouble(row.getCell(4).getStringCellValue()));
                                  else
                                	  nonFinance.setOtherIncFour(0.0);
                                	
                                	  if(row.getCell(5).getStringCellValue()!="")
                                  nonFinance.setOtherIncFive(Double.parseDouble(row.getCell(5).getStringCellValue()));
                                	  else
                                		  nonFinance.setOtherIncFive(0.0);}
                                  if (finColumn.equals("Interest Exp"))
                                  { 
                                	  if(row.getCell(1).getStringCellValue()!="")
                                	  nonFinance.setIntExpOne(Double.parseDouble(row.getCell(1).getStringCellValue()));
                                	  else
                                		  nonFinance.setIntExpOne(0.0);
                                	  if(row.getCell(2).getStringCellValue()!="")
                                  nonFinance.setIntExpTwo(Double.parseDouble(row.getCell(2).getStringCellValue()));
                                	  else
                                		  nonFinance.setIntExpTwo(0.0);
                                	  if(row.getCell(3).getStringCellValue()!="")
                                  nonFinance.setIntExpThree(Double.parseDouble(row.getCell(3).getStringCellValue()));
                                	  else
                                		  nonFinance.setIntExpThree(0.0);
                                	  if(row.getCell(4).getStringCellValue()!="")
                                  nonFinance.setIntExpFour(Double.parseDouble(row.getCell(4).getStringCellValue()));
                                	  else
                                		  nonFinance.setIntExpFour(0.0);
                                	  if(row.getCell(5).getStringCellValue()!="")
                                  nonFinance.setIntExpFive(Double.parseDouble(row.getCell(5).getStringCellValue()));
                                	  nonFinance.setIntExpFive(0.0);}
                                  if (finColumn.equals("Depriciation"))
                                  {if(row.getCell(1).getStringCellValue()!="")
                                	  nonFinance.setDepOne(Double.parseDouble(row.getCell(1).getStringCellValue()));
                                  else
                                	  nonFinance.setDepOne(0.0);
                                  if(row.getCell(2).getStringCellValue()!="")
                                  nonFinance.setDepTwo(Double.parseDouble(row.getCell(2).getStringCellValue()));
                                  else
                                  nonFinance.setDepTwo(0.0);
                                  if(row.getCell(3).getStringCellValue()!="")
                                  nonFinance.setDepThree(Double.parseDouble(row.getCell(3).getStringCellValue()));
                                  else
                                	  nonFinance.setDepThree(0.0);  
                                  if(row.getCell(4).getStringCellValue()!="")
                                  nonFinance.setDepFour(Double.parseDouble(row.getCell(4).getStringCellValue()));
                                  else
                                	  nonFinance.setDepFour(0.0);
                                  if(row.getCell(5).getStringCellValue()!="")
                                  nonFinance.setDepFive(Double.parseDouble(row.getCell(5).getStringCellValue()));
                                  else
                                	  nonFinance.setDepFive(0.0);}
                                  if (finColumn.equals("PBT"))
                                  { if(row.getCell(1).getStringCellValue()!="")
                                	  nonFinance.setPbtOne(Double.parseDouble(row.getCell(1).getStringCellValue()));
                                  else
                                	  nonFinance.setPbtOne(0.0);
                                  if(row.getCell(2).getStringCellValue()!="")
                                  nonFinance.setPbtTwo(Double.parseDouble(row.getCell(2).getStringCellValue()));
                                  else
                                	  nonFinance.setPbtTwo(0.0);
                                  if(row.getCell(3).getStringCellValue()!="")
                                  nonFinance.setPbtThree(Double.parseDouble(row.getCell(3).getStringCellValue()));
                                  else
                                	  nonFinance.setPbtThree(0.0);
                                  if(row.getCell(4).getStringCellValue()!="")
                                  nonFinance.setPbtFour(Double.parseDouble(row.getCell(4).getStringCellValue()));
                                  else
                                	  nonFinance.setPbtFour(0.0);
                                  if(row.getCell(5).getStringCellValue()!="")
                                  nonFinance.setPbtFive(Double.parseDouble(row.getCell(5).getStringCellValue()));
                                  else
                                	  nonFinance.setPbtFive(0.0);}
                                  if (finColumn.equals("Tax"))
                                  { 
                                	  if(row.getCell(1).getStringCellValue()!="")
                                	  nonFinance.setTaxOne(Double.parseDouble(row.getCell(1).getStringCellValue()));
                                	  else
                                		  nonFinance.setTaxOne(0.0);
                                	  if(row.getCell(2).getStringCellValue()!="")
                                  nonFinance.setTaxTwo(Double.parseDouble(row.getCell(2).getStringCellValue()));
                                	  else
                                		  nonFinance.setTaxTwo(0.0);
                                	  if(row.getCell(3).getStringCellValue()!="")
                                  nonFinance.setTaxThree(Double.parseDouble(row.getCell(3).getStringCellValue()));
                                	  else
                                		  nonFinance.setTaxThree(0.0);
                                	  if(row.getCell(4).getStringCellValue()!="")
                                  nonFinance.setTaxFour(Double.parseDouble(row.getCell(4).getStringCellValue()));
                                	  else
                                		  nonFinance.setTaxFour(0.0);
                                	  if(row.getCell(5).getStringCellValue()!="")
                                  nonFinance.setTaxFive(Double.parseDouble(row.getCell(5).getStringCellValue()));
                                	  else
                                		  nonFinance.setTaxFive(0.0);}
                                  if (finColumn.equals("PAT"))
                                  {
                                	  if(row.getCell(1).getStringCellValue()!="")
                                	  nonFinance.setPatOne(Double.parseDouble(row.getCell(1).getStringCellValue()));
                                	  else
                                		  nonFinance.setPatOne(0.0);
                                	  if(row.getCell(2).getStringCellValue()!="")
                                  nonFinance.setPatTwo(Double.parseDouble(row.getCell(2).getStringCellValue()));
                                	  else
                                		  nonFinance.setPatTwo(0.0);
                                	  if(row.getCell(3).getStringCellValue()!="")
                                  nonFinance.setPatthree(Double.parseDouble(row.getCell(3).getStringCellValue()));
                                	  else
                                		  nonFinance.setPatthree(0.0);
                                	  if(row.getCell(4).getStringCellValue()!="")
                                  nonFinance.setPatfour(Double.parseDouble(row.getCell(4).getStringCellValue()));
                                	  else
                                		  nonFinance.setPatfour(0.0);
                                	  if(row.getCell(5).getStringCellValue()!="")
                                  nonFinance.setPatFive(Double.parseDouble(row.getCell(5).getStringCellValue()));
                                	  else
                                		  nonFinance.setPatFive(0.0);}
                                  if (finColumn.equals("PAT.Growth(%)"))
                                  { /* nonFinance.setPatGrowthOne(row.getCell(1).getNumericCellValue());*/
                                	  if(row.getCell(2).getStringCellValue()!="")
                                  nonFinance.setPatGrowthTwo((Double.parseDouble(row.getCell(2).getStringCellValue()))*100);
                                	  else
                                		  nonFinance.setPatGrowthTwo(0.0);
                                	  if(row.getCell(3).getStringCellValue()!="")
                                  nonFinance.setPatGrowthThree((Double.parseDouble(row.getCell(3).getStringCellValue()))*100);
                                	  else
                                		  nonFinance.setPatGrowthThree(0.0);
                                	  if(row.getCell(4).getStringCellValue()!="")
                                  nonFinance.setPatGrowthFour((Double.parseDouble(row.getCell(4).getStringCellValue()))*100);
                                	  else
                                		  nonFinance.setPatGrowthFour(0.0);
                                	  if(row.getCell(5).getStringCellValue()!="")
                                  nonFinance.setPatGrowthFive((Double.parseDouble(row.getCell(5).getStringCellValue()))*100);
                                	  else
                                		  nonFinance.setPatGrowthFive(0.0);}
                                  if (finColumn.equals("Market Cap"))
                                  {  /*nonFinance.setMarketCapOne(row.getCell(1).getNumericCellValue());
                                  nonFinance.setMarketCapTwo(row.getCell(2).getNumericCellValue());*/
                                	  if(row.getCell(3).getStringCellValue()!="")
                                  nonFinance.setMarketCapThree(Double.parseDouble(row.getCell(3).getStringCellValue()));
                                	  else
                                		  nonFinance.setMarketCapThree(0.0);
                                 /* nonFinance.setMarketCapFour(row.getCell(4).getNumericCellValue());
                                  nonFinance.setMarketCapFive(row.getCell(5).getNumericCellValue()); */}
                               /*   if (finColumn.equals("PE"))
                                  {  nonFinance.setPeOne(row.getCell(1).getNumericCellValue());
                                  nonFinance.setPeTwo(row.getCell(2).getNumericCellValue());
                                  nonFinance.setPethree(row.getCell(3).getNumericCellValue());
                                  nonFinance.setPeFour(row.getCell(4).getNumericCellValue());
                                  nonFinance.setPeFive(row.getCell(5).getNumericCellValue());}*/
                                  if (finColumn.equals("Networth"))
                                  {
                                	  if(row.getCell(1).getStringCellValue()!="")
                                	  nonFinance.setNetworthOne(Double.parseDouble(row.getCell(1).getStringCellValue()));
                                	  else
                                		  nonFinance.setNetworthOne(0.0);
                                	  if(row.getCell(2).getStringCellValue()!="")
                                  nonFinance.setNetworthTwo(Double.parseDouble(row.getCell(2).getStringCellValue()));
                                	  else
                                		  nonFinance.setNetworthTwo(0.0);
                                	  if(row.getCell(3).getStringCellValue()!="")
                                  nonFinance.setNetworthThree(Double.parseDouble(row.getCell(3).getStringCellValue()));
                                	  else
                                		  nonFinance.setNetworthThree(0.0);
                                	  if(row.getCell(4).getStringCellValue()!="")
                                  nonFinance.setNetworthFour(Double.parseDouble(row.getCell(4).getStringCellValue()));
                                	  else
                                		  nonFinance.setNetworthFour(0.0);
                                	  if(row.getCell(5).getStringCellValue()!="")
                                  nonFinance.setNetworthFive(Double.parseDouble(row.getCell(5).getStringCellValue()));
                                	  else
                                		  nonFinance.setNetworthFive(0.0);}
                               /*   if (finColumn.equals("PB"))
                                  {  nonFinance.setPbOne(row.getCell(1).getNumericCellValue());
                                  nonFinance.setPbTwo(row.getCell(2).getNumericCellValue());
                                  nonFinance.setPbThree(row.getCell(3).getNumericCellValue());
                                  nonFinance.setPbFour(row.getCell(4).getNumericCellValue());
                                  nonFinance.setPbFive(row.getCell(5).getNumericCellValue());}*/
                                  if (finColumn.equals("ROE(%)"))
                                  {   if(row.getCell(1).getStringCellValue()!="")
                                	  nonFinance.setRoeOne((Double.parseDouble(row.getCell(1).getStringCellValue()))*100);
                                  else
                                	  nonFinance.setRoeOne(0.0);
                                  if(row.getCell(2).getStringCellValue()!="")
                                  nonFinance.setRoeTwo((Double.parseDouble(row.getCell(2).getStringCellValue()))*100);
                                  else
                                	  nonFinance.setRoeTwo(0.0);
                                  if(row.getCell(3).getStringCellValue()!="")
                                  nonFinance.setRoeThree((Double.parseDouble(row.getCell(3).getStringCellValue()))*100);
                                  nonFinance.setRoeThree(0.0);
                                  if(row.getCell(4).getStringCellValue()!="")
                                  nonFinance.setRoeFour((Double.parseDouble(row.getCell(4).getStringCellValue()))*100);
                                  nonFinance.setRoeFour(0.0);
                                  if(row.getCell(5).getStringCellValue()!="")
                                  nonFinance.setRoefive((Double.parseDouble(row.getCell(5).getStringCellValue()))*100);
                                  nonFinance.setRoefive(0.0);}
                                  if (finColumn.equals("Total Debt"))
                                  {
                                	  if(row.getCell(1).getStringCellValue()!="")
                                	  nonFinance.setTotDebOne(Double.parseDouble(row.getCell(1).getStringCellValue()));
                                	  else
                                		  nonFinance.setTotDebOne(0.0);
                                	  if(row.getCell(2).getStringCellValue()!="")
                                  nonFinance.setTotDebTwo(Double.parseDouble(row.getCell(2).getStringCellValue()));
                                	  else
                                		  nonFinance.setTotDebTwo(0.0);
                                	  if(row.getCell(3).getStringCellValue()!="")
                                  nonFinance.setTotDebThree(Double.parseDouble(row.getCell(3).getStringCellValue()));
                                	  else
                                		  nonFinance.setTotDebThree(0.0);
                                	  if(row.getCell(4).getStringCellValue()!="")
                                  nonFinance.setTotDebFour(Double.parseDouble(row.getCell(4).getStringCellValue()));
                                	  else
                                		  nonFinance.setTotDebFour(0.0);
                                	  if(row.getCell(5).getStringCellValue()!="")
                                  nonFinance.setTotDebFive(Double.parseDouble(row.getCell(5).getStringCellValue()));
                                	  else
                                		  nonFinance.setTotDebFive(0.0);}
                                  if (finColumn.equals("DE"))
                                  {
                                	  if(row.getCell(1).getStringCellValue()!="")
                                	  nonFinance.setDeOne(Double.parseDouble(row.getCell(1).getStringCellValue()));
                                	  else
                                		  nonFinance.setDeOne(0.0);
                                	  if(row.getCell(2).getStringCellValue()!="")
                                  nonFinance.setDeTwo(Double.parseDouble(row.getCell(2).getStringCellValue()));
                                	  else
                                		  nonFinance.setDeTwo(0.0);
                                	  if(row.getCell(3).getStringCellValue()!="")
                                  nonFinance.setDeThree(Double.parseDouble(row.getCell(3).getStringCellValue()));
                                	  else
                                		  nonFinance.setDeThree(0.0);
                                	  if(row.getCell(4).getStringCellValue()!="")
                                  nonFinance.setDeFour(Double.parseDouble(row.getCell(4).getStringCellValue()));
                                	  else
                                		  nonFinance.setDeFour(0.0);
                                	  if(row.getCell(5).getStringCellValue()!="")
                                  nonFinance.setDeFive(Double.parseDouble(row.getCell(5).getStringCellValue()));
                                	  else
                                		  nonFinance.setDeFive(0.0);}
                                /*  if (finColumn.equals("Tax Rate"))
                                  {  nonFinance.setTaxRateOne(row.getCell(1).getNumericCellValue());
                                  nonFinance.setTaxRateTwo(row.getCell(2).getNumericCellValue());
                                  nonFinance.setTaxRateThree(row.getCell(3).getNumericCellValue());
                                  nonFinance.setTaxRateFour(row.getCell(4).getNumericCellValue());
                                  nonFinance.setTaxRateFive(row.getCell(5).getNumericCellValue());}
                                  if (finColumn.equals("Interest Rate"))
                                  {  nonFinance.setIntRateOne(row.getCell(1).getNumericCellValue());
                                  nonFinance.setIntRateTwo(row.getCell(2).getNumericCellValue());
                                  nonFinance.setIntRateThree(row.getCell(3).getNumericCellValue());
                                  nonFinance.setIntRateFour(row.getCell(4).getNumericCellValue());
                                  nonFinance.setIntRateFive(row.getCell(5).getNumericCellValue());}*/
                                  if (finColumn.equals("Deprciation Rate"))
                                  {if(row.getCell(1).getStringCellValue()!="")
                                	  nonFinance.setDepRateOne(Double.parseDouble(row.getCell(1).getStringCellValue()));
                                  else
                                	  nonFinance.setDepRateOne(0.0);
                                  if(row.getCell(2).getStringCellValue()!="")
                                  nonFinance.setDepRateTwo(Double.parseDouble(row.getCell(2).getStringCellValue()));
                                  else
                                	  nonFinance.setDepRateTwo(0.0);
                                  if(row.getCell(3).getStringCellValue()!="")
                                  nonFinance.setDepRateThree(Double.parseDouble(row.getCell(3).getStringCellValue()));
                                  else
                                	  nonFinance.setDepRateThree(0.0);
                                  if(row.getCell(4).getStringCellValue()!="")
                                  nonFinance.setDepRateFour(Double.parseDouble(row.getCell(4).getStringCellValue()));
                                  else
                                	  nonFinance.setDepRateFour(0.0);
                                  if(row.getCell(5).getStringCellValue()!="")
                                  nonFinance.setDepRateFive(Double.parseDouble(row.getCell(5).getStringCellValue()));
                                  else
                                	  nonFinance.setDepRateFive(0.0);}
                                  if (finColumn.equals("Weight")){
                                	  if(row.getCell(3).getStringCellValue()!="")
                                      nonFinance.setWeight(Double.parseDouble(row.getCell(3).getStringCellValue()));
                                	  else
                                		  nonFinance.setWeight(0.0);

                                  }
                          }                             

                          }
                      }

                      if((nonFinance.getMarketCapThree()!=0.0 && nonFinance.getPatOne()!=0.0))
                      nonFinance.setPeOne(nonFinance.getMarketCapThree()/nonFinance.getPatOne());
                      else
                    	  nonFinance.setPeOne(0.00);
                      if(nonFinance.getMarketCapThree()!=0.0 && nonFinance.getPatTwo()!=0.0)
                      nonFinance.setPeTwo(nonFinance.getMarketCapThree()/nonFinance.getPatTwo());
                      else
                    	  nonFinance.setPeTwo(0.00);  
                      if(nonFinance.getMarketCapThree()!=0.0 && nonFinance.getPatthree()!=0.0)
                      nonFinance.setPethree(nonFinance.getMarketCapThree()/nonFinance.getPatthree());
                      else
                    	  nonFinance.setPethree(0.00);
                      if(nonFinance.getMarketCapThree()!=0.0 && nonFinance.getPatfour()!=0.0)
                      nonFinance.setPeFour(nonFinance.getMarketCapThree()/nonFinance.getPatfour());
                      else
                    	  nonFinance.setPeFour(0.00);	
                      if(nonFinance.getMarketCapThree()!=0.0 && nonFinance.getPatFive()!=0.0)
                      nonFinance.setPeFive(nonFinance.getMarketCapThree()/nonFinance.getPatFive());
                      else
                    	  nonFinance.setPeFive(0.00);
                      if(nonFinance.getMarketCapThree()!=0.0 && nonFinance.getNetworthOne()!=0.0)
                      nonFinance.setPbOne(nonFinance.getMarketCapThree()/nonFinance.getNetworthOne());
                      else
                    	  nonFinance.setPbOne(0.00);
                      if(nonFinance.getMarketCapThree()!=0.0 && nonFinance.getNetworthTwo()!=0.0)
                      nonFinance.setPbTwo(nonFinance.getMarketCapThree()/nonFinance.getNetworthTwo());
                      else
                    	  nonFinance.setPbTwo(0.00);
                      if(nonFinance.getMarketCapThree()!=0.0 && nonFinance.getNetworthThree()!=0.0)
                      nonFinance.setPbThree(nonFinance.getMarketCapThree()/nonFinance.getNetworthThree());
                      else
                    	  nonFinance.setPbThree(0.00);  
                      if(nonFinance.getMarketCapThree()!=0.0 && nonFinance.getNetworthFour()!=0.0)
                      nonFinance.setPbFour(nonFinance.getMarketCapThree()/nonFinance.getNetworthFour());
                      else
                    	  nonFinance.setPbFour(0.00); 
                      if(nonFinance.getMarketCapThree()!=0.0 && nonFinance.getNetworthFive()!=0.0)
                      nonFinance.setPbFive(nonFinance.getMarketCapThree()/nonFinance.getNetworthFive());
                      else
                    	  nonFinance.setPbFive(0.00);  
                      if(nonFinance.getTaxOne()!=0.0 && nonFinance.getPbtOne()!=0.0)
                      nonFinance.setTaxRateOne((nonFinance.getTaxOne()/nonFinance.getPbtOne())*100);
                      else
                      nonFinance.setTaxRateOne(0.00);
                      if(nonFinance.getTaxTwo()!=0.0 && nonFinance.getPbtTwo()!=0.0)
                      nonFinance.setTaxRateTwo((nonFinance.getTaxTwo()/nonFinance.getPbtTwo())*100);
                      else
                    	  nonFinance.setTaxRateTwo(0.00);
                      if(nonFinance.getTaxThree()!=0.0 && nonFinance.getPbtThree()!=0.0)
                      nonFinance.setTaxRateThree((nonFinance.getTaxThree()/nonFinance.getPbtThree())*100);
                      else
                    	  nonFinance.setTaxRateThree(0.00);
                      if(nonFinance.getTaxFour()!=0.0 && nonFinance.getPbtFour()!=0.0)
                      nonFinance.setTaxRateFour((nonFinance.getTaxFour()/nonFinance.getPbtFour())*100);
                      else
                    	  nonFinance.setTaxRateFour(0.00);  
                      if(nonFinance.getTaxFive()!=0.0 && nonFinance.getPbtFive()!=0.0)
                      nonFinance.setTaxRateFive((nonFinance.getTaxFive()/nonFinance.getPbtFive())*100);
                      else
                    	  nonFinance.setTaxRateFive(0.00);	 
                      if(nonFinance.getIntExpOne()!=0.0 && nonFinance.getTotDebOne()!=0.0)
                      nonFinance.setIntRateOne((nonFinance.getIntExpOne()/nonFinance.getTotDebOne())*100);
                      else
                    	  nonFinance.setIntRateOne(0.00); 
                      if(nonFinance.getIntExpTwo()!=0.0 && nonFinance.getTotDebTwo()!=0.0)
                      nonFinance.setIntRateTwo((nonFinance.getIntExpTwo()/nonFinance.getTotDebTwo())*100);
                      else
                    	  nonFinance.setIntRateTwo(0.00);
                      if(nonFinance.getIntExpThree()!=0.0 && nonFinance.getTotDebThree()!=0.0)
                      nonFinance.setIntRateThree((nonFinance.getIntExpThree()/nonFinance.getTotDebThree())*100);
                      else
                    	  nonFinance.setIntRateThree(0.00); 
                      if(nonFinance.getIntExpFour()!=0.0 && nonFinance.getTotDebFour()!=0.0)
                      nonFinance.setIntRateFour((nonFinance.getIntExpFour()/nonFinance.getTotDebFour())*100);
                      else
                    	  nonFinance.setIntRateFour(0.00);  
                      if(nonFinance.getIntExpFive()!=0.0 && nonFinance.getTotDebFive()!=0.0)
                      nonFinance.setIntRateFive((nonFinance.getIntExpFive()/nonFinance.getTotDebFive())*100);
                      else{
                    	  nonFinance.setIntRateFive(0.00);  }
                      nonFinancialSummaryDataRepository.save(nonFinance);
                      List<OpportunitySummaryData> opportunitySummaryDataList = opportunitySummaryDataRepository.findByOpportunityMasterid(opportunityMaster);
                      System.out.println(opportunitySummaryDataList);


                      for (OpportunitySummaryData sm : opportunitySummaryDataList) {

                    	  sm.setMarketCap(nonFinance.getMarketCapThree());
                    	  sm.setPatFirstYear(nonFinance.getPatOne());
                    	  sm.setPatSecondYear(nonFinance.getPatTwo());
                    	  sm.setPatThirdYear(nonFinance.getPatthree());
                    	  sm.setPatFourthYear(nonFinance.getPatfour());
                    	  sm.setPatFifthYear(nonFinance.getPatFive());
                    	  sm.setPeFirstYear(nonFinance.getPeOne());
                    	  sm.setPeSecondYear(nonFinance.getPeTwo());
                    	  sm.setPeThirdYear(nonFinance.getPethree());
                    	  sm.setPeFourthYear(nonFinance.getPeFour());
                    	  sm.setPeFifthYear(nonFinance.getPeFive());
                    	  sm.setRoeFirstYear(nonFinance.getRoeOne());
                    	  sm.setRoeSecondYear(nonFinance.getRoeTwo());
                    	  sm.setRoeThirdYear(nonFinance.getRoeThree());
                    	  sm.setRoeFourthYear(nonFinance.getRoeFour());
                    	  sm.setRoeFifthYear(nonFinance.getRoefive());
                    	  sm.setDeFirstYear(nonFinance.getDeOne());
                    	  sm.setDeSecondYear(nonFinance.getDeTwo());
                    	  sm.setDeThirdColour(nonFinance.getDeThree());
                    	  sm.setDeFourthYear(nonFinance.getDeFour());
                    	  sm.setDeFifthYear(nonFinance.getDeFive());
                    	  sm.setPatGrowthFirst(nonFinance.getPatGrowthOne());
                    	  sm.setPatGrowthSecond(nonFinance.getPatGrowthTwo());
                    	  sm.setPatGrowthThird(nonFinance.getPatGrowthThree());
                    	  sm.setPatGrowthFourth(nonFinance.getPatGrowthFour());
                    	  sm.setPatGrowthFifth(nonFinance.getPatGrowthFive());
                    	  sm.setbWeight(nonFinance.getWeight());
                    	  if(nonFinance.getPethree()!=0.0 && nonFinance.getPatGrowthThree()!=0.0)
                    	  sm.setPegOj((nonFinance.getPethree()/nonFinance.getPatGrowthThree()));
                    	  if(nonFinance.getWeight()!=0.0){
                    		  if(nonFinance.getWeight()!=0.0 && nonFinance.getPeOne()!=0.0)
                    	  sm.setPortPeFirst(nonFinance.getWeight()*nonFinance.getPeOne());
                    		  if(nonFinance.getWeight()!=0.0 && nonFinance.getPeTwo()!=0.0)
                    	  sm.setPortPeSecond(nonFinance.getWeight()*nonFinance.getPeTwo());
                    		  if(nonFinance.getWeight()!=0.0 && nonFinance.getPethree()!=0.0)
                    	  sm.setPortPeThird(nonFinance.getWeight()*nonFinance.getPethree());
                    		  if(nonFinance.getWeight()!=0.0 && nonFinance.getPeFour()!=0.0)
                    	  sm.setPortPeFourth(nonFinance.getWeight()*nonFinance.getPeFour());
                    		  if(nonFinance.getWeight()!=0.0 && nonFinance.getPeFive()!=0.0)
                    	  sm.setPortPeFifth(nonFinance.getWeight()*nonFinance.getPeFive());
                    	  //sm.setEarningsFirst((nonFinance.getWeight()*nonFinance.getPatGrowthOne())/100.0);
                    		  if(nonFinance.getWeight()!=0.0 && nonFinance.getPatGrowthTwo()!=0.0)
                    	  sm.setEarningsSecond((nonFinance.getWeight()*nonFinance.getPatGrowthTwo())/100.0);
                    		  if(nonFinance.getWeight()!=0.0 && nonFinance.getPatGrowthThree()!=0.0)
                    	  sm.setEarningsThird((nonFinance.getWeight()*nonFinance.getPatGrowthThree())/100.0);
                    		  if(nonFinance.getWeight()!=0.0 && nonFinance.getPatGrowthFour()!=0.0)
                    	  sm.setEarningsFourth((nonFinance.getWeight()*nonFinance.getPatGrowthFour())/100.0);
                    		  if(nonFinance.getWeight()!=0.0 && nonFinance.getPatGrowthFive()!=0.0)
                    	  sm.setEarningsFifth((nonFinance.getWeight()*nonFinance.getPatGrowthFive())/100.0);
                    		  if(nonFinance.getWeight()!=0.0 && nonFinance.getMarketCapThree()!=0.0)
                    	  sm.setWtAvgCap((nonFinance.getWeight()*nonFinance.getMarketCapThree())/100.0);
                    		  if(nonFinance.getWeight()!=0.0 && nonFinance.getMarketCapThree()!=0.0)
                    	  sm.setRoe((nonFinance.getWeight()*nonFinance.getMarketCapThree())/100.0);
                    		  if(nonFinance.getWeight()!=0.0 && nonFinance.getPethree()!=0.0 && nonFinance.getPatGrowthThree()!=0.0)
                    	  sm.setPegYearPeg(nonFinance.getWeight()*(nonFinance.getPethree()/nonFinance.getPatGrowthThree()));
                    	  }

                      opportunitySummaryDataRepository.save(sm);
                      }
                      log.info("In financial uploadFinancial" + iTotRowInserted + " inserted");

                  } catch (FileNotFoundException e) {
                      log.error("Exception at financial uploadFinancial() method " + e);
                  } catch (IOException e) {
                      log.error("Exception at financial uploadFinancial() method " + e);
                  } catch (Exception e) {
                      log.error("Exception at financial uploadFinancial() method at row "+iCurrRowNum + e);
                  }
          }
              }




        //fileUploadRepository.save(fileUpload);
        return null;
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

