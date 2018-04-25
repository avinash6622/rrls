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

      notificationServiceResource.notificationHistorysave(oppName,result.getCreatedBy(),result.getLastModifiedBy(),result.getCreatedDate(),"uploaded",page,result.getFileName(),id);




      }
        //fileUploadRepository.save(fileUpload);
        return ResponseEntity.created(new URI("/api/file-uploads/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

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
                                  }
                                  String finColumn = row.getCell(0).getStringCellValue();

                                  if (finColumn.contains("Net Interest Income")) {
                                  	finance.setNetIntOne(row.getCell(1).getNumericCellValue());
                                  	finance.setNetIntTwo(row.getCell(2).getNumericCellValue());
                                  	finance.setNetIntThree(row.getCell(3).getNumericCellValue());
                                  	finance.setNetIntFour(row.getCell(4).getNumericCellValue());
                                  	finance.setNetIntFive(row.getCell(5).getNumericCellValue());}
                                 if (finColumn.contains("Non Interest Income")){
                              	   finance.setNonIntOne(row.getCell(1).getNumericCellValue());
                             		   finance.setNonIntTwo(row.getCell(2).getNumericCellValue());
                             		   finance.setNonIntThree(row.getCell(3).getNumericCellValue());
                             		   finance.setNonIntFour(row.getCell(4).getNumericCellValue());
                             		   finance.setNonIntFive(row.getCell(5).getNumericCellValue());}
                                  if (finColumn.contains("Total Income"))
                                  {    	finance.setTotIncOne(row.getCell(1).getNumericCellValue());
                                 		   finance.setTotIncTwo(row.getCell(2).getNumericCellValue());
                                 		   finance.setTotIncThree(row.getCell(3).getNumericCellValue());
                                 		   finance.setTotIncFour(row.getCell(4).getNumericCellValue());
                                 		   finance.setTotIncFive(row.getCell(5).getNumericCellValue());}
                                  if (finColumn.contains("Operating Expenses"))
                                  {  finance.setOpExpOne(row.getCell(1).getNumericCellValue());
                             		   finance.setOpExpTwo(row.getCell(2).getNumericCellValue());
                             		   finance.setOpExpThree(row.getCell(3).getNumericCellValue());
                             		   finance.setOpExpFour(row.getCell(4).getNumericCellValue());
                             		   finance.setOpExpFive(row.getCell(5).getNumericCellValue());}
                                  if (finColumn.contains("Operating Profit"))
                                  {  finance.setOpProOne(row.getCell(1).getNumericCellValue());
                             		   finance.setOpProTwo(row.getCell(2).getNumericCellValue());
                             		   finance.setOpProThree(row.getCell(3).getNumericCellValue());
                             		   finance.setOpProFour(row.getCell(4).getNumericCellValue());
                             		   finance.setOpProFive(row.getCell(5).getNumericCellValue());}
                                  if (finColumn.contains("Provisions"))
                                  {  finance.setProvisionsOne(row.getCell(1).getNumericCellValue());
                             		   finance.setProvisionsTwo(row.getCell(2).getNumericCellValue());
                             		   finance.setProvisionsThree(row.getCell(3).getNumericCellValue());
                             		   finance.setProvisionsFour(row.getCell(4).getNumericCellValue());
                             		   finance.setProvisionsFive(row.getCell(5).getNumericCellValue());}
                                  if (finColumn.contains("PAT"))
                                  {  finance.setPatOne(row.getCell(1).getNumericCellValue());
                             		   finance.setPatTwo(row.getCell(2).getNumericCellValue());
                             		   finance.setPatThree(row.getCell(3).getNumericCellValue());
                             		   finance.setPatFour(row.getCell(4).getNumericCellValue());
                             		   finance.setPatFive(row.getCell(5).getNumericCellValue());}
                                  if (finColumn.contains("EPS"))
                                  {  finance.setEpsOne(row.getCell(1).getNumericCellValue());
                             		   finance.setEpsTwo(row.getCell(2).getNumericCellValue());
                             		   finance.setEpsThree(row.getCell(3).getNumericCellValue());
                             		   finance.setEpsFour(row.getCell(4).getNumericCellValue());
                             		   finance.setEpsFive(row.getCell(5).getNumericCellValue());}
                                  if (finColumn.contains("Market Cap"))
                                  { /* finance.setMarCapOne(row.getCell(1).getNumericCellValue());
                             		   finance.setMarCapTwo(row.getCell(2).getNumericCellValue());*/
                             		   finance.setMarCapThree(row.getCell(3).getNumericCellValue());
                             		  /* finance.setMarCapFour(row.getCell(4).getNumericCellValue());
                             		   finance.setMarCapFive(row.getCell(5).getNumericCellValue());*/}
                                  if (finColumn.contains("AUM"))
                                  {  finance.setAumOne(row.getCell(1).getNumericCellValue());
                             		   finance.setAumTwo(row.getCell(2).getNumericCellValue());
                             		   finance.setAumThree(row.getCell(3).getNumericCellValue());
                             		   finance.setAumFour(row.getCell(4).getNumericCellValue());
                             		   finance.setAumFive(row.getCell(5).getNumericCellValue());}
                                  if (finColumn.contains("Networth"))
                                  {  finance.setNetworthOne(row.getCell(1).getNumericCellValue());
                             		   finance.setNetworthTwo(row.getCell(2).getNumericCellValue());
                             		   finance.setNetworthThree(row.getCell(3).getNumericCellValue());
                             		   finance.setNetworthFour(row.getCell(4).getNumericCellValue());
                             		   finance.setNetworthfive(row.getCell(5).getNumericCellValue());}
                                  if (finColumn.contains("ROE"))
                                  {  finance.setRoeOne(row.getCell(1).getNumericCellValue()*100);
                             		   finance.setRoeTwo(row.getCell(2).getNumericCellValue()*100);
                             		   finance.setRoeThree(row.getCell(3).getNumericCellValue()*100);
                             		   finance.setRoeFour(row.getCell(4).getNumericCellValue()*100);
                             		   finance.setRoeFive(row.getCell(5).getNumericCellValue()*100);}
                                  if (finColumn.contains("PBV"))
                                  {  finance.setPbvOne(row.getCell(1).getNumericCellValue());
                             		   finance.setPbvTwo(row.getCell(2).getNumericCellValue());
                             		   finance.setPbvThree(row.getCell(3).getNumericCellValue());
                             		   finance.setPbvFour(row.getCell(4).getNumericCellValue());
                             		   finance.setPbvFive(row.getCell(5).getNumericCellValue());}
                                  if (finColumn.contains("PE"))
                                  {  finance.setPeOne(row.getCell(1).getNumericCellValue());
                             		   finance.setPeTwo(row.getCell(2).getNumericCellValue());
                             		   finance.setPeThree(row.getCell(3).getNumericCellValue());
                             		   finance.setPeFour(row.getCell(4).getNumericCellValue());
                             		   finance.setPeFive(row.getCell(5).getNumericCellValue());}

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

                      XSSFSheet sheet = workbook.getSheetAt(1);
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
                                  }
                                  String finColumn = row.getCell(0).getStringCellValue();

                                  if (finColumn.equals("Revenues")) {
                                  	nonFinance.setRevenueOne(row.getCell(1).getNumericCellValue());
                                  	nonFinance.setRevenueTwo(row.getCell(2).getNumericCellValue());
                                  	nonFinance.setRevenueThree(row.getCell(3).getNumericCellValue());
                                  	nonFinance.setRevenueFour(row.getCell(4).getNumericCellValue());
                                  	nonFinance.setRevenueFive(row.getCell(5).getNumericCellValue());}
                                 if (finColumn.equals("Rev.Growth(%)")){
                              	  /* nonFinance.setRevGrowthOne(row.getCell(1).getNumericCellValue());*/
                              	   nonFinance.setRevGrowthTwo(row.getCell(2).getNumericCellValue());
                              	   nonFinance.setRevGrowthThree(row.getCell(3).getNumericCellValue());
                              	   nonFinance.setRevGrowthFour(row.getCell(4).getNumericCellValue());
                              	   nonFinance.setRevGrowthFive(row.getCell(5).getNumericCellValue());}
                                  if (finColumn.equals("EBITDA"))
                                  {
                                  nonFinance.setEbitdaOne(row.getCell(1).getNumericCellValue());
                                  nonFinance.setEbitdaTwo(row.getCell(2).getNumericCellValue());
                                  nonFinance.setEbitdaThree(row.getCell(3).getNumericCellValue());
                                  nonFinance.setEbitdaFour(row.getCell(4).getNumericCellValue());
                                  nonFinance.setEbitdaFive(row.getCell(5).getNumericCellValue());}
                                  if (finColumn.equals("Margin(%)"))
                                  {  nonFinance.setMarginOne(row.getCell(1).getNumericCellValue());
                                  nonFinance.setMarginTwo(row.getCell(2).getNumericCellValue());
                                  nonFinance.setMarginThree(row.getCell(3).getNumericCellValue());
                                  nonFinance.setMarginFour(row.getCell(4).getNumericCellValue());
                                  nonFinance.setMarginFive(row.getCell(5).getNumericCellValue());}
                                  if (finColumn.equals("EBI.Growth(%)"))
                                  { /* nonFinance.setEbitdaGrowthOne(row.getCell(1).getNumericCellValue());*/
                                  nonFinance.setEbitdaGrowthTwo(row.getCell(2).getNumericCellValue());
                                  nonFinance.setEbitdaGrowthThree(row.getCell(3).getNumericCellValue());
                                  nonFinance.setEbitdaGrowthFour(row.getCell(4).getNumericCellValue());
                                  nonFinance.setEbitdaGrowthFive(row.getCell(5).getNumericCellValue());}
                                  if (finColumn.equals("Other Income"))
                                  {  nonFinance.setOtherIncOne(row.getCell(1).getNumericCellValue());
                                  nonFinance.setOtherIncTwo(row.getCell(2).getNumericCellValue());
                                  nonFinance.setOtherIncThree(row.getCell(3).getNumericCellValue());
                                  nonFinance.setOtherIncFour(row.getCell(4).getNumericCellValue());
                                  nonFinance.setOtherIncFive(row.getCell(5).getNumericCellValue());}
                                  if (finColumn.equals("Interest Exp"))
                                  {  nonFinance.setIntExpOne(row.getCell(1).getNumericCellValue());
                                  nonFinance.setIntExpTwo(row.getCell(2).getNumericCellValue());
                                  nonFinance.setIntExpThree(row.getCell(3).getNumericCellValue());
                                  nonFinance.setIntExpFour(row.getCell(4).getNumericCellValue());
                                  nonFinance.setIntExpFive(row.getCell(5).getNumericCellValue());}
                                  if (finColumn.equals("Depriciation"))
                                  {  nonFinance.setDepOne(row.getCell(1).getNumericCellValue());
                                  nonFinance.setDepTwo(row.getCell(2).getNumericCellValue());
                                  nonFinance.setDepThree(row.getCell(3).getNumericCellValue());
                                  nonFinance.setDepFour(row.getCell(4).getNumericCellValue());
                                  nonFinance.setDepFive(row.getCell(5).getNumericCellValue());}
                                  if (finColumn.equals("PBT"))
                                  {  nonFinance.setPbtOne(row.getCell(1).getNumericCellValue());
                                  nonFinance.setPbtTwo(row.getCell(2).getNumericCellValue());
                                  nonFinance.setPbtThree(row.getCell(3).getNumericCellValue());
                                  nonFinance.setPbtFour(row.getCell(4).getNumericCellValue());
                                  nonFinance.setPbtFive(row.getCell(5).getNumericCellValue());}
                                  if (finColumn.equals("Tax"))
                                  {  nonFinance.setTaxOne(row.getCell(1).getNumericCellValue());
                                  nonFinance.setTaxTwo(row.getCell(2).getNumericCellValue());
                                  nonFinance.setTaxThree(row.getCell(3).getNumericCellValue());
                                  nonFinance.setTaxFour(row.getCell(4).getNumericCellValue());
                                  nonFinance.setTaxFive(row.getCell(5).getNumericCellValue());}
                                  if (finColumn.equals("PAT"))
                                  {  nonFinance.setPatOne(row.getCell(1).getNumericCellValue());
                                  nonFinance.setPatTwo(row.getCell(2).getNumericCellValue());
                                  nonFinance.setPatthree(row.getCell(3).getNumericCellValue());
                                  nonFinance.setPatfour(row.getCell(4).getNumericCellValue());
                                  nonFinance.setPatFive(row.getCell(5).getNumericCellValue());}
                                  if (finColumn.equals("PAT.Growth(%)"))
                                  { /* nonFinance.setPatGrowthOne(row.getCell(1).getNumericCellValue());*/
                                  nonFinance.setPatGrowthTwo(row.getCell(2).getNumericCellValue());
                                  nonFinance.setPatGrowthThree(row.getCell(3).getNumericCellValue());
                                  nonFinance.setPatGrowthFour(row.getCell(4).getNumericCellValue());
                                  nonFinance.setPatGrowthFive(row.getCell(5).getNumericCellValue());}
                                  if (finColumn.equals("Market Cap"))
                                  {  /*nonFinance.setMarketCapOne(row.getCell(1).getNumericCellValue());
                                  nonFinance.setMarketCapTwo(row.getCell(2).getNumericCellValue());*/
                                  nonFinance.setMarketCapThree(row.getCell(3).getNumericCellValue());
                                 /* nonFinance.setMarketCapFour(row.getCell(4).getNumericCellValue());
                                  nonFinance.setMarketCapFive(row.getCell(5).getNumericCellValue()); */}
                                  if (finColumn.equals("PE"))
                                  {  nonFinance.setPeOne(row.getCell(1).getNumericCellValue());
                                  nonFinance.setPeTwo(row.getCell(2).getNumericCellValue());
                                  nonFinance.setPethree(row.getCell(3).getNumericCellValue());
                                  nonFinance.setPeFour(row.getCell(4).getNumericCellValue());
                                  nonFinance.setPeFive(row.getCell(5).getNumericCellValue());}
                                  if (finColumn.equals("Networth"))
                                  {  nonFinance.setNetworthOne(row.getCell(1).getNumericCellValue());
                                  nonFinance.setNetworthTwo(row.getCell(2).getNumericCellValue());
                                  nonFinance.setNetworthThree(row.getCell(3).getNumericCellValue());
                                  nonFinance.setNetworthFour(row.getCell(4).getNumericCellValue());
                                  nonFinance.setNetworthFive(row.getCell(5).getNumericCellValue());}
                                  if (finColumn.equals("PB"))
                                  {  nonFinance.setPbOne(row.getCell(1).getNumericCellValue());
                                  nonFinance.setPbTwo(row.getCell(2).getNumericCellValue());
                                  nonFinance.setPbThree(row.getCell(3).getNumericCellValue());
                                  nonFinance.setPbFour(row.getCell(4).getNumericCellValue());
                                  nonFinance.setPbFive(row.getCell(5).getNumericCellValue());}
                                  if (finColumn.equals("ROE(%)"))
                                  {  nonFinance.setRoeOne(row.getCell(1).getNumericCellValue());
                                  nonFinance.setRoeTwo(row.getCell(2).getNumericCellValue());
                                  nonFinance.setRoeThree(row.getCell(3).getNumericCellValue());
                                  nonFinance.setRoeFour(row.getCell(4).getNumericCellValue());
                                  nonFinance.setRoefive(row.getCell(5).getNumericCellValue());}
                                  if (finColumn.equals("Total Debt"))
                                  {  nonFinance.setTotDebOne(row.getCell(1).getNumericCellValue());
                                  nonFinance.setTotDebTwo(row.getCell(2).getNumericCellValue());
                                  nonFinance.setTotDebThree(row.getCell(3).getNumericCellValue());
                                  nonFinance.setTotDebFour(row.getCell(4).getNumericCellValue());
                                  nonFinance.setTotDebFive(row.getCell(5).getNumericCellValue());}
                                  if (finColumn.equals("DE"))
                                  {  nonFinance.setDeOne(row.getCell(1).getNumericCellValue());
                                  nonFinance.setDeTwo(row.getCell(2).getNumericCellValue());
                                  nonFinance.setDeThree(row.getCell(3).getNumericCellValue());
                                  nonFinance.setDeFour(row.getCell(4).getNumericCellValue());
                                  nonFinance.setDeFive(row.getCell(5).getNumericCellValue());}
                                  if (finColumn.equals("Tax Rate"))
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
                                  nonFinance.setIntRateFive(row.getCell(5).getNumericCellValue());}
                                  if (finColumn.equals("Deprciation Rate"))
                                  {  nonFinance.setDepRateOne(row.getCell(1).getNumericCellValue());
                                  nonFinance.setDepRateTwo(row.getCell(2).getNumericCellValue());
                                  nonFinance.setDepRateThree(row.getCell(3).getNumericCellValue());
                                  nonFinance.setDepRateFour(row.getCell(4).getNumericCellValue());
                                  nonFinance.setDepRateFive(row.getCell(5).getNumericCellValue());}
                                  if (finColumn.equals("Weight")){

                                      nonFinance.setWeight(row.getCell(3).getNumericCellValue());


                                  }

                          }

                          }
                      }

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
                    	  sm.setPegOj((nonFinance.getPethree()/nonFinance.getPatGrowthThree()));
                    	  if(nonFinance.getWeight()!=null){
                    	  sm.setPortPeFirst(nonFinance.getWeight()*nonFinance.getPeOne());
                    	  sm.setPortPeSecond(nonFinance.getWeight()*nonFinance.getPeTwo());
                    	  sm.setPortPeThird(nonFinance.getWeight()*nonFinance.getPethree());
                    	  sm.setPortPeFourth(nonFinance.getWeight()*nonFinance.getPeFour());
                    	  sm.setPortPeFifth(nonFinance.getWeight()*nonFinance.getPeFive());
                    	  //sm.setEarningsFirst((nonFinance.getWeight()*nonFinance.getPatGrowthOne())/100.0);
                    	  sm.setEarningsSecond((nonFinance.getWeight()*nonFinance.getPatGrowthTwo())/100.0);
                    	  sm.setEarningsThird((nonFinance.getWeight()*nonFinance.getPatGrowthThree())/100.0);
                    	  sm.setEarningsFourth((nonFinance.getWeight()*nonFinance.getPatGrowthFour())/100.0);
                    	  sm.setEarningsFifth((nonFinance.getWeight()*nonFinance.getPatGrowthFive())/100.0);
                    	  sm.setWtAvgCap((nonFinance.getWeight()*nonFinance.getMarketCapThree())/100.0);
                    	  sm.setRoe((nonFinance.getWeight()*nonFinance.getMarketCapThree())/100.0);
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

