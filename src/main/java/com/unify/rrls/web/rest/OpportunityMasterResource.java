package com.unify.rrls.web.rest;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import com.unify.rrls.domain.*;
import com.unify.rrls.repository.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.docx4j.Docx4J;
import org.docx4j.Docx4jProperties;
import org.docx4j.XmlUtils;
import org.docx4j.convert.in.xhtml.XHTMLImporterImpl;
import org.docx4j.convert.out.HTMLSettings;
import org.docx4j.convert.out.html.AbstractHTMLExporter3;
import org.docx4j.convert.out.html.HTMLExporterXslt;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.MissingServletRequestParameterException;

import org.springframework.web.bind.annotation.*;

//import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;
import com.codahale.metrics.annotation.Timed;
import com.unify.rrls.security.SecurityUtils;
import com.unify.rrls.web.rest.util.HeaderUtil;
import com.unify.rrls.web.rest.util.PaginationUtil;

import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

/**
 * REST controller for managing OpportunityMaster.
 */
@RestController
@RequestMapping("/api")
public class OpportunityMasterResource {

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

	//StrategyMapping strategyMapping;
    //OpportunityMasterContact opportunityMasterContact= new OpportunityMasterContact();

	final static Pattern PATTERN = Pattern.compile("(.*?)(?:\\((\\d+)\\))?(\\.[^.]*)?");

	private final Logger log = LoggerFactory.getLogger(OpportunityMasterResource.class);

	private static final String ENTITY_NAME = "opportunityMaster";
	@Autowired
	private final OpportunityMasterRepository opportunityMasterRepository;
	private final FileUploadRepository fileUploadRepository;
	private final FileUploadCommentsRepository fileUploadCommentsRepository;
	private final StrategyMappingRepository strategyMappingRepository;
	private final StrategyMasterRepository strategyMasterRepository;
	private final AdditionalFileUploadRepository additionalFileUploadRepository;
	private final OpportunityMasterContactRepository opportunityMasterContactRepository;
	private final FinancialSummaryDataRepository financialSummaryDataRepository;
	private final NonFinancialSummaryDataRepository nonFinancialSummaryDataRepository;
	private final OpportunitySummaryDataRepository opportunitySummaryDataRepository;


	public OpportunityMasterResource(OpportunityMasterRepository opportunityMasterRepository,
			FileUploadRepository fileUploadRepository, FileUploadCommentsRepository fileUploadCommentsRepository,
			StrategyMappingRepository strategyMappingRepository,StrategyMasterRepository strategyMasterRepository,
			AdditionalFileUploadRepository additionalFileUploadRepository,OpportunityMasterContactRepository opportunityMasterContactRepository,FinancialSummaryDataRepository financialSummaryDataRepository
    ,NonFinancialSummaryDataRepository nonFinancialSummaryDataRepository, OpportunitySummaryDataRepository opportunitySummaryDataRepository) {
		this.opportunityMasterRepository = opportunityMasterRepository;
		this.fileUploadRepository = fileUploadRepository;
		this.fileUploadCommentsRepository = fileUploadCommentsRepository;
		this.strategyMappingRepository=strategyMappingRepository;
		this.strategyMasterRepository=strategyMasterRepository;
		this.additionalFileUploadRepository=additionalFileUploadRepository;
		this.opportunityMasterContactRepository=opportunityMasterContactRepository;
		this.financialSummaryDataRepository=financialSummaryDataRepository;
		this.nonFinancialSummaryDataRepository=nonFinancialSummaryDataRepository;
		this.opportunitySummaryDataRepository=opportunitySummaryDataRepository;
	}

	/**
	 * POST /opportunity-masters : Create a new opportunityMaster.
	 *
	 * @param opportunityMaster
	 *            the opportunityMaster to create
	 * @return the ResponseEntity with status 201 (Created) and with body the
	 *         new opportunityMaster, or with status 400 (Bad Request) if the
	 *         opportunityMaster has already an ID
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 * @throws IOException
	 */
	/*
	 * // @PostMapping("/opportunity-masters")
	 *
	 * @RequestMapping(value = "/opportunity-masters", method =
	 * RequestMethod.POST)
	 *
	 * @Timed public ResponseEntity<OpportunityMaster>
	 * createOpportunityMaster(@RequestParam(value = "oppCode") String oppCode,
	 *
	 * @RequestParam(value = "oppName") String oppName,
	 *
	 * @RequestParam(value = "oppDescription", required = false) String
	 * oppDescription,
	 *
	 * @RequestParam(value = "strategyMasterId", required = false)
	 * StrategyMaster strategyMasterId,
	 *
	 * @RequestParam(value = "htmlContent") String htmlContent,
	 *
	 * @RequestParam(value = "fileUpload", required = false) MultipartFile[]
	 * fileUpload) throws URISyntaxException, IOException,
	 * MissingServletRequestParameterException { // log.debug(
	 * "REST request to save OpportunityMaster : {}", // opportunityMaster);
	 *
	 *
	 * if (opportunityMaster.getId() != null) { return
	 * ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(
	 * ENTITY_NAME, "idexists",
	 * "A new opportunityMaster cannot already have an ID")).body(null); }
	 *
	 * OpportunityMaster opportunityMasters = new OpportunityMaster();
	 * opportunityMasters.setOppCode(oppCode);
	 * opportunityMasters.setOppName(oppName);
	 * opportunityMasters.setOppDescription(oppDescription);
	 * opportunityMasters.setStrategyMasterId(strategyMasterId); String
	 * sFilesDirectory = "src/main/resources/fileUpload"; File dirFiles = new
	 * File(sFilesDirectory); dirFiles.mkdirs(); FileUpload fileUploaded = new
	 * FileUpload();
	 *
	 * // System.out.println(sFile.getOriginalFilename());
	 *
	 *
	 * opportunityMasters.setFileName(sFile.getOriginalFilename());
	 * opportunityMasters.setData(sFile.getBytes());
	 *
	 * // }
	 *
	 * System.out.println("----------------------\n" + htmlContent +
	 * "---------------------------------------\n");
	 * convertHTMLToDoc(htmlContent, "", "test2.docx");
	 * opportunityMasters.setHtmlContent(htmlContent);
	 *
	 * OpportunityMaster result =
	 * opportunityMasterRepository.save(opportunityMasters); for (MultipartFile
	 * sFile : fileUpload) { setFileName(sFile.getOriginalFilename());
	 * fileStream = IOUtils.toByteArray(sFile.getInputStream()); File sFiles =
	 * new File(dirFiles, fileName); writeFile(fileStream, sFiles);
	 * fileUploaded.setFileData(sFiles.toString());
	 * fileUploaded.setFileName(sFile.getOriginalFilename());
	 * fileUploaded.setOpportunityMasterId(result);
	 * fileUploadRepository.save(fileUploaded);
	 *
	 * }
	 *
	 * return ResponseEntity.created(new URI("/api/opportunity-masters/" +
	 * result.getId()))
	 * .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME,
	 * result.getId().toString())).body(result); }
	 */

	@PostMapping("/opportunity-masters")
	@Timed
	public ResponseEntity<OpportunityMaster> createOpportunityMaster(@RequestBody OpportunityMaster opportunityMaster)
			throws URISyntaxException, IOException, MissingServletRequestParameterException {
		log.debug("REST request to save OpportunityMaster : {}", opportunityMaster);
		//System.out.println(opportunityMaster.getStrategyMasterId());

		if (opportunityMaster.getId() != null) {
			return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists",
					"A new opportunityMaster cannot already have an ID")).body(null);
		}

		OpportunityMaster result = opportunityMasterRepository.save(opportunityMaster);
        System.out.println("sdfsdfsd--->"+result);


            /*for(OpportunityMasterContact opportunityMasterContact:opportunityMaster.getOpportunityMasterContact())*/

      for(OpportunityMasterContact oC:opportunityMaster.getSelectedoppContanct())
             {
                 oC.setOpportunityMasterId(result);
                 System.out.println(oC);
                  opportunityMasterContactRepository.save(oC);

             }
      if(opportunityMaster.getMasterName().getSegment().equals("Finance")){

      	FinancialSummaryData summaryData = opportunityMaster.getFinancialSummaryData();
         summaryData.setOpportunityMasterId(result);
         financialSummaryDataRepository.save(summaryData);
          for(StrategyMaster sm:opportunityMaster.getSelectedStrategyMaster())
          {
            OpportunitySummaryData opportunitySummaryData=new OpportunitySummaryData();
            opportunitySummaryData.setPatFirstYear(summaryData.getPatOne());
            opportunitySummaryData.setPatSecondYear(summaryData.getPatTwo());
            opportunitySummaryData.setPatThirdYear(summaryData.getPatThree());
            opportunitySummaryData.setPatFourthYear(summaryData.getPatFour());
            opportunitySummaryData.setPatFifthYear(summaryData.getPatFive());
            opportunitySummaryData.setMarketCap(summaryData.getMarCapThree());
            opportunitySummaryData.setRoeFirstYear(summaryData.getRoeOne());
            opportunitySummaryData.setRoeSecondYear(summaryData.getRoeTwo());
            opportunitySummaryData.setRoeThirdYear(summaryData.getRoeThree());
            opportunitySummaryData.setRoeFourthYear(summaryData.getRoeFour());
            opportunitySummaryData.setRoeFifthYear(summaryData.getRoeFive());
            opportunitySummaryData.setPeFirstYear(summaryData.getPeOne());
            opportunitySummaryData.setPeSecondYear(summaryData.getPeTwo());
            opportunitySummaryData.setPeThirdYear(summaryData.getPeThree());
            opportunitySummaryData.setPeFourthYear(summaryData.getPeFour());
            opportunitySummaryData.setPeFifthYear(summaryData.getPeFive());
            opportunitySummaryData.setOpportunityMasterid(result);
            opportunitySummaryData.setStrategyMasterId(sm);
            opportunitySummaryDataRepository.save(opportunitySummaryData);

            }

      }
       else{
         NonFinancialSummaryData nonFinancialSummaryData=opportunityMaster.getNonFinancialSummaryData();
         nonFinancialSummaryData.setOpportunityMaster(result);
         nonFinancialSummaryDataRepository.save(nonFinancialSummaryData);
          for(StrategyMaster sm:opportunityMaster.getSelectedStrategyMaster())
          {
          OpportunitySummaryData opportunitySummaryData = new OpportunitySummaryData();
          opportunitySummaryData.setMarketCap(nonFinancialSummaryData.getMarketCapThree());
          opportunitySummaryData.setPatFirstYear(nonFinancialSummaryData.getPatOne());
          opportunitySummaryData.setPatSecondYear(nonFinancialSummaryData.getPatTwo());
          opportunitySummaryData.setPatThirdYear(nonFinancialSummaryData.getPatthree());
          opportunitySummaryData.setPatFourthYear(nonFinancialSummaryData.getPatfour());
          opportunitySummaryData.setPatFifthYear(nonFinancialSummaryData.getPatFive());
          opportunitySummaryData.setPeFirstYear(nonFinancialSummaryData.getPeOne());
          opportunitySummaryData.setPeSecondYear(nonFinancialSummaryData.getPeTwo());
          opportunitySummaryData.setPeThirdYear(nonFinancialSummaryData.getPethree());
          opportunitySummaryData.setPeFourthYear(nonFinancialSummaryData.getPeFour());
          opportunitySummaryData.setPeFifthYear(nonFinancialSummaryData.getPeFive());
          opportunitySummaryData.setRoeFirstYear(nonFinancialSummaryData.getRoeOne());
          opportunitySummaryData.setRoeSecondYear(nonFinancialSummaryData.getRoeTwo());
          opportunitySummaryData.setRoeThirdYear(nonFinancialSummaryData.getRoeThree());
          opportunitySummaryData.setRoeFourthYear(nonFinancialSummaryData.getRoeFour());
          opportunitySummaryData.setRoeFifthYear(nonFinancialSummaryData.getRoefive());
          opportunitySummaryData.setDeFirstYear(nonFinancialSummaryData.getDeOne());
          opportunitySummaryData.setDeSecondYear(nonFinancialSummaryData.getDeTwo());
          opportunitySummaryData.setDeThirdColour(nonFinancialSummaryData.getDeThree());
          opportunitySummaryData.setDeFourthYear(nonFinancialSummaryData.getDeFour());
          opportunitySummaryData.setDeFifthYear(nonFinancialSummaryData.getDeFive());
          opportunitySummaryData.setPatGrowthFirst(nonFinancialSummaryData.getPatGrowthOne());
          opportunitySummaryData.setPatGrowthSecond(nonFinancialSummaryData.getPatGrowthTwo());
          opportunitySummaryData.setPatGrowthThird(nonFinancialSummaryData.getPatGrowthThree());
          opportunitySummaryData.setPatGrowthFourth(nonFinancialSummaryData.getPatGrowthFour());
          opportunitySummaryData.setPatGrowthFifth(nonFinancialSummaryData.getPatGrowthFive());
          opportunitySummaryData.setOpportunityMasterid(result);
          opportunitySummaryData.setStrategyMasterId(sm);
          opportunitySummaryDataRepository.save(opportunitySummaryData);
          }
      }





		for(StrategyMaster sm:opportunityMaster.getSelectedStrategyMaster())
		{
			StrategyMapping strategyMapping=new StrategyMapping();
			strategyMapping.setOpportunityMaster(result);
			strategyMapping.setStrategyMaster(sm);
			strategyMappingRepository.save(strategyMapping);

		}

		return ResponseEntity.created(new URI("/api/opportunity-masters/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(result);
	}



    /**
	 * PUT /opportunity-masters : Updates an existing opportunityMaster.
	 *
	 * @param opportunityMaster
	 *            the opportunityMaster to update
	 * @return the ResponseEntity with status 200 (OK) and with body the updated
	 *         opportunityMaster, or with status 400 (Bad Request) if the
	 *         opportunityMaster is not valid, or with status 500 (Internal
	 *         Server Error) if the opportunityMaster couldn't be updated
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 * @throws IOException
	 * @throws MissingServletRequestParameterException
	 */

	@PutMapping("/opportunity-masters")
	@Timed
	public ResponseEntity<OpportunityMaster> updateOpportunityMaster(@RequestBody OpportunityMaster opportunityMasters)
			throws URISyntaxException, IOException, MissingServletRequestParameterException {
		log.debug("REST request to update OpportunityMaster : {}", opportunityMasters);
		List<StrategyMapping> strategyMappings=new ArrayList<>();
		strategyMappings = strategyMappingRepository.findByOpportunityMaster(opportunityMasters);
		System.out.println(opportunityMasters.getSelectedStrategyMaster());
		if(!opportunityMasters.getSelectedStrategyMaster().isEmpty()){
		strategyMappingRepository.delete(strategyMappings);}
		//opportunityMasters.setStrategyMappings()
		if(!opportunityMasters.getSelectedStrategyMaster().isEmpty()){

		for(StrategyMaster sm:opportunityMasters.getSelectedStrategyMaster())
		{
			StrategyMapping strategyMap=new StrategyMapping();
			strategyMap.setOpportunityMaster(opportunityMasters);
			strategyMap.setStrategyMaster(sm);
			strategyMappingRepository.save(strategyMap);

		}}
		opportunityMasterContactRepository.save(opportunityMasters.getSelectedoppContanct());


		OpportunityMaster result = opportunityMasterRepository.save(opportunityMasters);
        if(opportunityMasters.getMasterName().getSegment().equals("Finance")) {
            financialSummaryDataRepository.save(opportunityMasters.getFinancialSummaryData());

            List<OpportunitySummaryData> opportunitySummaryDataList = opportunitySummaryDataRepository.findByOpportunityMasterid(result);


            for (OpportunitySummaryData sm : opportunitySummaryDataList) {
               // OpportunitySummaryData opportunitySummaryData = new OpportunitySummaryData();
                sm.setPatFirstYear(opportunityMasters.getFinancialSummaryData().getPatOne());
                sm.setPatSecondYear(opportunityMasters.getFinancialSummaryData().getPatTwo());
                sm.setPatThirdYear(opportunityMasters.getFinancialSummaryData().getPatThree());
                sm.setPatFourthYear(opportunityMasters.getFinancialSummaryData().getPatFour());
                sm.setPatFifthYear(opportunityMasters.getFinancialSummaryData().getPatFive());
                sm.setMarketCap(opportunityMasters.getFinancialSummaryData().getMarCapThree());
                sm.setRoeFirstYear(opportunityMasters.getFinancialSummaryData().getRoeOne());
                sm.setRoeSecondYear(opportunityMasters.getFinancialSummaryData().getRoeTwo());
                sm.setRoeThirdYear(opportunityMasters.getFinancialSummaryData().getRoeThree());
                sm.setRoeFourthYear(opportunityMasters.getFinancialSummaryData().getRoeFour());
                sm.setRoeFifthYear(opportunityMasters.getFinancialSummaryData().getRoeFive());
                sm.setPeFirstYear(opportunityMasters.getFinancialSummaryData().getPeOne());
                sm.setPeSecondYear(opportunityMasters.getFinancialSummaryData().getPeTwo());
                sm.setPeThirdYear(opportunityMasters.getFinancialSummaryData().getPeThree());
                sm.setPeFourthYear(opportunityMasters.getFinancialSummaryData().getPeFour());
                sm.setPeFifthYear(opportunityMasters.getFinancialSummaryData().getPeFive());
                opportunitySummaryDataRepository.save(sm);

            }

        }
        else {

            nonFinancialSummaryDataRepository.save(opportunityMasters.getNonFinancialSummaryData());
            List<OpportunitySummaryData> opportunitySummaryDataList = opportunitySummaryDataRepository.findByOpportunityMasterid(result);

            for (OpportunitySummaryData sm : opportunitySummaryDataList) {

                sm.setMarketCap(opportunityMasters.getNonFinancialSummaryData().getMarketCapThree());
                sm.setPatFirstYear(opportunityMasters.getNonFinancialSummaryData().getPatOne());
                sm.setPatSecondYear(opportunityMasters.getNonFinancialSummaryData().getPatTwo());
                sm.setPatThirdYear(opportunityMasters.getNonFinancialSummaryData().getPatthree());
                sm.setPatFourthYear(opportunityMasters.getNonFinancialSummaryData().getPatfour());
                sm.setPatFifthYear(opportunityMasters.getNonFinancialSummaryData().getPatFive());
                sm.setPeFirstYear(opportunityMasters.getNonFinancialSummaryData().getPeOne());
                sm.setPeSecondYear(opportunityMasters.getNonFinancialSummaryData().getPeTwo());
                sm.setPeThirdYear(opportunityMasters.getNonFinancialSummaryData().getPethree());
                sm.setPeFourthYear(opportunityMasters.getNonFinancialSummaryData().getPeFour());
                sm.setPeFifthYear(opportunityMasters.getNonFinancialSummaryData().getPeFive());
                sm.setRoeFirstYear(opportunityMasters.getNonFinancialSummaryData().getRoeOne());
                sm.setRoeSecondYear(opportunityMasters.getNonFinancialSummaryData().getRoeTwo());
                sm.setRoeThirdYear(opportunityMasters.getNonFinancialSummaryData().getRoeThree());
                sm.setRoeFourthYear(opportunityMasters.getNonFinancialSummaryData().getRoeFour());
                sm.setRoeFifthYear(opportunityMasters.getNonFinancialSummaryData().getRoefive());
                sm.setDeFirstYear(opportunityMasters.getNonFinancialSummaryData().getDeOne());
                sm.setDeSecondYear(opportunityMasters.getNonFinancialSummaryData().getDeTwo());
                sm.setDeThirdColour(opportunityMasters.getNonFinancialSummaryData().getDeThree());
                sm.setDeFourthYear(opportunityMasters.getNonFinancialSummaryData().getDeFour());
                sm.setDeFifthYear(opportunityMasters.getNonFinancialSummaryData().getDeFive());
                sm.setPatGrowthFirst(opportunityMasters.getNonFinancialSummaryData().getPatGrowthOne());
                sm.setPatGrowthSecond(opportunityMasters.getNonFinancialSummaryData().getPatGrowthTwo());
                sm.setPatGrowthThird(opportunityMasters.getNonFinancialSummaryData().getPatGrowthThree());
                sm.setPatGrowthFourth(opportunityMasters.getNonFinancialSummaryData().getPatGrowthFour());
                sm.setPatGrowthFifth(opportunityMasters.getNonFinancialSummaryData().getPatGrowthFive());

                opportunitySummaryDataRepository.save(sm);
            }


        }

		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	@PostMapping("/opportunity-masters-upload/{id}")
	@Timed
	public ResponseEntity<OpportunityMaster> createFileOpportunityMaster(@PathVariable long id,
			@RequestParam MultipartFile fileUpload) throws URISyntaxException, IOException {
		// log.debug("REST request to update OpportunityMaster : {}",
		// opportunityMaster);
		System.out.println(fileUpload.getOriginalFilename());
		OpportunityMaster opportunityMaster = opportunityMasterRepository.findOne(id);

		/*
		 * opportunityMaster.setFileName(fileUpload.getOriginalFilename());
		 * opportunityMaster.setData(fileUpload.getBytes());
		 */
		OpportunityMaster result = opportunityMasterRepository.save(opportunityMaster);
		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, opportunityMaster.getId().toString()))
				.body(result);
	}

    @PutMapping("/opportunity-masters/description")
    @Timed
    public ResponseEntity<OpportunityMaster> updateDescription(@RequestBody OpportunityMaster opportunityMaster) throws URISyntaxException, IOException {
        // log.debug("REST request to update OpportunityMaster : {}",
        // opportunityMaster);

         OpportunityMaster opportunityMasters = opportunityMasterRepository.findOne(opportunityMaster.getId());
        opportunityMasters.setStatusDes(opportunityMaster.getStatusDes());
        opportunityMasters.setOppStatus(opportunityMaster.getOppStatus());
        System.out.println("des"+opportunityMaster.getStatusDes()+"Status---"+opportunityMaster.getOppStatus()+"Id---"+opportunityMaster.getId());



        OpportunityMaster result = opportunityMasterRepository.save(opportunityMasters);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, opportunityMaster.getId().toString()))
            .body(result);
    }

 /*   @Autowired
    private ResourceLoader resourceLoader;

	@Autowired

    Context context;
*/
  /*  @GetMapping("/opportunity-masters/download-file")
    @Timed
	public ResponseEntity<Object> downloadFile() throws IOException  {
	
		try {
	
		
		String filename = "src/main/resources/BANNARI AMMAN SUGARS LTD./baidik/image/Wheat_generic_UI.PNG";
	
		File file = new File(filename);
		
		InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		headers.add("Pragma", "no-cache");
		headers.add("Expires", "0");
		
		ResponseEntity<Object> responseEntity = ResponseEntity.ok().headers(headers).contentLength(file.length()).contentType(MediaType.parseMediaType("image/png")).body(resource);
		return responseEntity;
		} catch (Exception e ) {
			return new ResponseEntity<>("error occurred", HttpStatus.INTERNAL_SERVER_ERROR);	
		} 
	}*/


    @GetMapping("/opportunity-masters/download-file")
    @Timed
    public  void downloader(HttpServletRequest request, HttpServletResponse response){

       // System.out.println("Entering"+fileUpload.getFileName()+"path:"+fileUpload.getFileData());

    	ServletContext context=request.getServletContext();
    	String fileToDownload="src/main/resources/BANNARI AMMAN SUGARS LTD./baidik/image/Wheat_generic_UI.PNG";
    	File file=new File(fileToDownload);
        try
        (InputStream fileInputStream = new FileInputStream(file);
                OutputStream output = response.getOutputStream();) {
            response.reset();
       
        response.setContentType(MimetypesFileTypeMap.getDefaultFileTypeMap().getContentType(file));
        response.setHeader("Content-Disposition","attachment; filename=\""+file.getName()+"\"");
        response.setContentLength((int)(file.length()));

        IOUtils.copyLarge(fileInputStream, output);

        output.flush();
       

        }catch(FileNotFoundException ex){
        	System.out.println("File not found");
        }
        catch(Exception e)
        {
        	System.out.println("file"+e);
        }

                 }
    
  
    @GetMapping("/file")
    @Timed
    public void getFile( HttpServletResponse response) throws URISyntaxException, IOException {
    	String fileToDownload="src/main/resources/BANNARI AMMAN SUGARS LTD./baidik/image/Wheat_generic_UI.PNG";
    	File file=new File(fileToDownload);
        FileInputStream stream =  new FileInputStream(file);
        response.setContentType("image/png");
        response.setHeader("Content-disposition", "attachment; filename=Wheat_generic_UI.PNG");
        IOUtils.copy(stream,response.getOutputStream());
        stream.close();
    }

  /*  @Autowired
    ServletContext context;
     @GetMapping("/opportunity-masters/download-file")
     public void downloader(HttpServletRequest request, HttpServletResponse response) {
     try {
         System.out.println("entering");
         String fileName ="Formattingsah.xlsx";
         String downloadFolder = context.getRealPath("/resources/BOMBAY DYEING &amp; MFG.CO.LTD/girija");
         File file = new File(downloadFolder + File.separator + fileName);
         System.out.println("Filename---->"+file);
         System.out.println(file.exists());




         if (file.exists()) {
             System.out.println(file.getName());
             //String mimeType = context.getMimeType(file.getPath());
            String mimeType ="application/vnd.ms-excel";
             System.out.println(mimeType);

             if (mimeType == null) {
                 mimeType = "application/octet-stream";
             }

             response.setContentType(mimeType);
             response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
             response.setContentLength((int) file.length());

             OutputStream os = response.getOutputStream();
             FileInputStream fis = new FileInputStream(file);
             byte[] buffer = new byte[4096];
             int b = -1;

             while ((b = fis.read(buffer)) != -1) {
                 os.write(buffer, 0, b);
             }

             fis.close();
             os.close();


         } else {
             System.out.println("Requested " + fileName + " file not found!!");
         }
     } catch (IOException e) {
         System.out.println("Error:- " + e.getMessage());
     }



 }*/






	@PostMapping("/opportunity-masters/create-file")
	@Timed
	public ResponseEntity<FileUpload> createWordOpportunityMaster(@RequestBody DocumentCreationBean documentCreationBean)
			throws URISyntaxException, IOException{
		String sFilesDirectory="";
		String user=SecurityUtils.getCurrentUserLogin();
		sFilesDirectory = "src/main/resources/"+documentCreationBean.getOppName()+"/"+user+"/docx/";
		String sFile=documentCreationBean.getFileName()+".docx";
		String extension = "";
		String name = "";

		int idxOfDot =sFile.lastIndexOf('.');   //Get the last index of . to separate extension
		extension = sFile.substring(idxOfDot + 1);
		name = sFile.substring(0, idxOfDot);
		File fPath=new File(sFilesDirectory+sFile);
		Path path = Paths.get(sFile);

		int counter = 1;
		while(fPath.exists()){
			sFile = name+"("+counter+")."+extension;
		    path = Paths.get(sFile);
		    fPath=new File(sFilesDirectory+sFile);
		    counter++;
		}
		sFile=sFilesDirectory+sFile;



		File dirFiles = new File(sFilesDirectory);
		dirFiles.mkdirs();

		FileUpload result = new FileUpload();
		convertHTMLToDoc(documentCreationBean.getFileContent(),sFilesDirectory, sFile);
		OpportunityMaster opportunityMaster = opportunityMasterRepository.findOne(documentCreationBean.getOppId());
      //  System.out.println("Filename"+fPath.getName());
		result.setFileName(name);
		result.setFileDataContentType(extension);
		result.setFileData(sFile);
		result.setOpportunityMasterId(opportunityMaster);
		result=fileUploadRepository.save(result);

		return ResponseEntity.created(new URI("/api/opportunity-masters"))
				.headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(result);/*ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));*/
	}



	/**
	 * GET /opportunity-masters : get all the opportunityMasters.
	 *
	 * @param pageable
	 *            the pagination information
	 * @return the ResponseEntity with status 200 (OK) and the list of
	 *         opportunityMasters in bodyR
	 */
	@GetMapping("/opportunity-masters")
	@Timed
	public ResponseEntity<List<OpportunityMaster>> getAllOpportunityMasters() {
		log.debug("REST request to get a page of OpportunityMasters");
		List<OpportunityMaster> page = opportunityMasterRepository.findAll();
		List<StrategyMaster> strategyMapMaster;

		for(OpportunityMaster opportunityMaster:page)
		{
			strategyMapMaster=new ArrayList<StrategyMaster>();
			List<StrategyMapping> strategyMappings = strategyMappingRepository.findByOpportunityMaster(opportunityMaster);
			for(StrategyMapping sm:strategyMappings)
			{
				strategyMapMaster.add(sm.getStrategyMaster());
			}
			opportunityMaster.setSelectedStrategyMaster(strategyMapMaster);

		}
		System.out.println(page);
		//HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/opportunity-masters");
		HttpHeaders headers=new HttpHeaders();
		return new ResponseEntity<>(page, headers,HttpStatus.OK);
	}



	/**
	 * GET /opportunity-masters/:id : get the "id" opportunityMaster.
	 *
	 * @param id
	 *            the id of the opportunityMaster to retrieve
	 * @return the ResponseEntity with status 200 (OK) and with body the
	 *         opportunityMaster, or with status 404 (Not Found)
	 */
	@GetMapping("/opportunity-masters/{id}")
	@Timed
	public ResponseEntity<OpportunityMaster> getOpportunityMaster(@PathVariable Long id) {
		log.debug("REST request to get OpportunityMaster : {}", id);
		OpportunityMaster opportunityMaster = opportunityMasterRepository.findOne(id);
		List<FileUpload> fileUploads = fileUploadRepository.findByOpportunityMasterId(opportunityMaster);
		List<StrategyMapping> strategyMappings = strategyMappingRepository.findByOpportunityMaster(opportunityMaster);

		List<StrategyMaster> strategyMasters =new ArrayList<StrategyMaster>();
		List<OpportunityMasterContact> opportunityMasterContacts =  opportunityMasterContactRepository.findByOpportunityMasterId(opportunityMaster);
        System.out.println("CONTACT---->"+opportunityMasterContacts);
        opportunityMaster.setSelectedoppContanct(opportunityMasterContacts);
        System.out.println("MAPPING----->"+strategyMappings);
        for(StrategyMapping sms:strategyMappings){
            strategyMasters.add(sms.getStrategyMaster());
           opportunityMaster.setSelectedStrategyMaster(strategyMasters);
        }
		//opportunityMaster.setStrategyMapping(strategyMappings);
		//OpportunityMasterContact opportunityMasterContact = opportunityMasterContactRepository.findByOpportunityMasterId(opportunityMaster);
		//opportunityMaster.setOpportunityMasterContact(opportunityMasterContact);

		FinancialSummaryData summaryData = financialSummaryDataRepository.findByOpportunityMasterId(opportunityMaster);
        System.out.println("MAPPINGvf----->"+summaryData);
		opportunityMaster.setFinancialSummaryData(summaryData);
		NonFinancialSummaryData nonFinancialSummaryData=nonFinancialSummaryDataRepository.findByOpportunityMaster(opportunityMaster);
		opportunityMaster.setNonFinancialSummaryData(nonFinancialSummaryData);
        System.out.println("MAPPINGvfn----->"+nonFinancialSummaryData);
		List<FileUploadComments> fileComments = fileUploadCommentsRepository.findByOpportunityMaster(opportunityMaster);
		opportunityMaster.setFileUploadCommentList(fileComments);
		if (!fileUploads.isEmpty()) {
			try {
				for(FileUpload fm:fileUploads)
				{
				opportunityMaster.setFileUploads(fileUploads);
				String inputfilepath = fm.getFileData();

				//opportunityMaster.setHtmlContent(getHTMLContent(inputfilepath));
				}

			} catch (Exception e) {
				System.out.println("File not reading.....\n-------------------------\n" + e.getMessage());
			}
		}
		return ResponseUtil.wrapOrNotFound(Optional.ofNullable(opportunityMaster));
	}
	/**
	 * GET /opportunity-masters/get-file/:id : get the "id" opportunityMaster.
	 *
	 * @param id
	 *            the id of the File Upload word and comments to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the
     *         opportunityMaster, or with status 404 (Not Found)
	 */
	@GetMapping("/opportunity-masters/get-file/{id}")
	@Timed
	public ResponseEntity<FileUpload> getOpportunityFile(@PathVariable Long id) {
		log.debug("REST request to get OpportunityMaster : {}", id);

		FileUpload fileUploads =fileUploadRepository.findOne(id);
		//OpportunityMaster opportunityMaster=opportunityMasterRepository.findOne(fileUploads.getOpportunityMasterId().getId());
			try {

				//opportunityMaster.setFileUploads((List<FileUpload>) fileUploads);
				String inputfilepath = fileUploads.getFileData();

				fileUploads.setHtmlContent(getHTMLContent(inputfilepath));

			} catch (Exception e) {
				System.out.println("File not reading.....\n-------------------------\n" + e.getMessage());
			}

		return ResponseUtil.wrapOrNotFound(Optional.ofNullable(fileUploads));
	}

	/**
	 * DELETE /opportunity-masters/:id : delete the " id" opportunityMaster.
	 *
	 * @param id
	 *            the id of the opportunityMaster to delete
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@DeleteMapping("/opportunity-masters/{id}")
	@Timed
	public ResponseEntity<Void> deleteOpportunityMaster(@PathVariable Long id) {
		log.debug("REST request to delete OpportunityMaster : {}", id);
		opportunityMasterRepository.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
	}



	public String convertHTMLToDoc(String xhtml, String destinationPath, String fileName) {
		log.info("HTML to DOC conversion\n--------------------------------------\nstarted....\n" + xhtml);
		try {

			WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.createPackage();

			XHTMLImporterImpl XHTMLImporter = new XHTMLImporterImpl(wordMLPackage);

			xhtml=xhtml
			//.replace("&amp;", "&")
			.replace("&lt;", "<")
			.replace("&gt;", ">")
			.replace("&#39;", "'")
			.replace("&nbsp;", " ")
			.replace("&#43;", "+")
			.replace("\"", "\'");

			wordMLPackage.getMainDocumentPart().getContent().addAll(XHTMLImporter.convert(xhtml, null));

			System.out.println(
					XmlUtils.marshaltoString(wordMLPackage.getMainDocumentPart().getJaxbElement(), true, true));

			wordMLPackage.save(new java.io.File(fileName));
		} catch (Docx4JException e) {
			e.printStackTrace();
		}
		/*
		 * File dir = new File (destinationPath); File actualFile = new File
		 * (fileName);
		 *
		 * WordprocessingMLPackage wordMLPackage = null; try { wordMLPackage =
		 * WordprocessingMLPackage.createPackage(); } catch
		 * (InvalidFormatException e) { e.printStackTrace(); }
		 *
		 *
		 * XHTMLImporterImpl XHTMLImporter = new
		 * XHTMLImporterImpl(wordMLPackage);
		 *
		 * OutputStream fos = null; try { fos = new ByteArrayOutputStream();
		 *
		 * System.out.println(XmlUtils.marshaltoString(wordMLPackage
		 * .getMainDocumentPart().getJaxbElement(), true, true));
		 *
		 * HTMLSettings htmlSettings = Docx4J.createHTMLSettings();
		 * htmlSettings.setWmlPackage(wordMLPackage);
		 * Docx4jProperties.setProperty(
		 * "docx4j.Convert.Out.HTML.OutputMethodXML", true);
		 * Docx4J.toHTML(htmlSettings, fos, Docx4J.FLAG_EXPORT_PREFER_XSL);
		 * wordMLPackage.save(actualFile); } catch (Docx4JException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); } finally{ try {
		 * fos.close(); return actualFile.getPath(); } catch (IOException e) {
		 * e.printStackTrace(); } }
		 */
		return null;
	}

	public String getHTMLContent(String inputfilepath) throws Exception {
		// String inputfilepath = "test2.docx";
		Docx4jProperties.setProperty("docx4j.Convert.Out.HTML.OutputMethodXML", true);
		String stringFromFile = "";
		try {
			// System.out.println(inputfilepath);
			File file = new File(inputfilepath);
			WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(file);

			// XHTML export
			AbstractHTMLExporter3 exporter = new HTMLExporterXslt();
			HTMLSettings htmlSettings = new HTMLSettings();

			htmlSettings.setWmlPackage(wordMLPackage);

			String htmlFilePath = "DocxToXhtmlAndBack.html";
			OutputStream os = new java.io.FileOutputStream(htmlFilePath);

			Docx4J.toHTML(htmlSettings, os, Docx4J.FLAG_NONE);
			log.info("DOC Reading ends.....\n-----------------------------------");
			stringFromFile = FileUtils.readFileToString(new File(htmlFilePath), "UTF-8");
			log.info("DOCUMENT Content........\n----------------\n");
			Document doc = Jsoup.parse(stringFromFile, "", Parser.xmlParser());
			System.out.println(doc);
			return stringFromFile;
		} catch (Exception e) {
			System.out.println("Failed reading....\n-----------------" + e.getMessage());
		}
		return null;

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

    @PostMapping(value = "/file-upload-data")
    @Timed
    public  void uploadSummaryData(@RequestBody OpportunityMaster opportunityMaster,@RequestParam MultipartFile[] fileUpload) throws IOException {
        int iCurrRowNum=0;
        
        String  sFilesDirectory =  "src/main/resources/"+opportunityMaster.getMasterName().getOppName()+"/";
      
       File dirFiles = new File(sFilesDirectory);
       dirFiles.mkdirs();
         
       for (MultipartFile sFile : fileUpload) {
    	   String nFile="";
    	   nFile=sFile.getOriginalFilename();
     	fileStream = IOUtils.toByteArray(sFile.getInputStream());

           System.out.println("FILE NAME--->"+nFile);

          
               File sFiles = new File(dirFiles, nFile);
               writeFile(fileStream, sFiles);
              }
if(opportunityMaster.getMasterName().getSegment()=="Finance"){
        try {
        	FinancialSummaryData finance=new FinancialSummaryData();
        	String file="C:\\Users\\girija noah\\Desktop\\Financial template.xlsx";
        	
            FileInputStream fis = new FileInputStream(new File(file));
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
                        {  finance.setMarCapOne(row.getCell(1).getNumericCellValue());
                   		   finance.setMarCapTwo(row.getCell(2).getNumericCellValue());
                   		   finance.setMarCapThree(row.getCell(3).getNumericCellValue());
                   		   finance.setMarCapFour(row.getCell(4).getNumericCellValue());
                   		   finance.setMarCapFive(row.getCell(5).getNumericCellValue());}
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
                        if (finColumn.contains("P/BV"))
                        {  finance.setPbvOne(row.getCell(1).getNumericCellValue());
                   		   finance.setPbvTwo(row.getCell(2).getNumericCellValue());
                   		   finance.setPbvThree(row.getCell(3).getNumericCellValue());
                   		   finance.setPbvFour(row.getCell(4).getNumericCellValue());
                   		   finance.setPbvFive(row.getCell(5).getNumericCellValue());}                      
                        if (finColumn.contains("P/E"))
                        {  finance.setPeOne(row.getCell(1).getNumericCellValue());
                   		   finance.setPeTwo(row.getCell(2).getNumericCellValue());
                   		   finance.setPeThree(row.getCell(3).getNumericCellValue());
                   		   finance.setPeFour(row.getCell(4).getNumericCellValue());
                   		   finance.setPeFive(row.getCell(5).getNumericCellValue());}                      

                }

                }
            }
            financialSummaryDataRepository.save(finance);
            log.info("In financial uploadFinancial" + iTotRowInserted + " inserted");

        } catch (FileNotFoundException e) {
            log.error("Exception at financial uploadFinancial() method " + e);
        } catch (IOException e) {
            log.error("Exception at financial uploadFinancial() method " + e);
        } catch (Exception e) {
            log.error("Exception at financial uploadFinancial() method at row "+iCurrRowNum + e);
        }

}if(opportunityMaster.getMasterName().getSegment()!="Finance"){
        try {
        	NonFinancialSummaryData nonFinance=new NonFinancialSummaryData();
        	String file="C:\\Users\\girija noah\\Desktop\\Non-Financial.xlsx";
        	
            FileInputStream fis = new FileInputStream(new File(file));
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
                        }
                        String finColumn = row.getCell(0).getStringCellValue();
                     
                        if (finColumn.equals("Revenues")) {                       	 
                        	nonFinance.setRevenueOne(row.getCell(1).getNumericCellValue());
                        	nonFinance.setRevenueTwo(row.getCell(2).getNumericCellValue());
                        	nonFinance.setRevenueThree(row.getCell(3).getNumericCellValue());
                        	nonFinance.setRevenueFour(row.getCell(4).getNumericCellValue());
                        	nonFinance.setRevenueFive(row.getCell(5).getNumericCellValue());}
                       if (finColumn.equals("Rev.Growth(%)")){
                    	   nonFinance.setRevGrowthOne(row.getCell(1).getNumericCellValue());
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
                        {  nonFinance.setEbitdaGrowthOne(row.getCell(1).getNumericCellValue());
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
                        {  nonFinance.setPatGrowthOne(row.getCell(1).getNumericCellValue());
                        nonFinance.setPatGrowthTwo(row.getCell(2).getNumericCellValue());
                        nonFinance.setPatGrowthThree(row.getCell(3).getNumericCellValue());
                        nonFinance.setPatGrowthFour(row.getCell(4).getNumericCellValue());
                        nonFinance.setPatGrowthFive(row.getCell(5).getNumericCellValue());}
                        if (finColumn.equals("Market Cap"))
                        {  nonFinance.setMarketCapOne(row.getCell(1).getNumericCellValue());
                        nonFinance.setMarketCapTwo(row.getCell(2).getNumericCellValue());
                        nonFinance.setMarketCapThree(row.getCell(3).getNumericCellValue());
                        nonFinance.setMarketCapFour(row.getCell(4).getNumericCellValue());
                        nonFinance.setMarketCapFive(row.getCell(5).getNumericCellValue());}                      
                        if (finColumn.equals("P/E"))
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
                        if (finColumn.equals("P/B"))
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
                        if (finColumn.equals("D/E"))
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
                        {  nonFinance.setDepOne(row.getCell(1).getNumericCellValue());
                        nonFinance.setDepTwo(row.getCell(2).getNumericCellValue());
                        nonFinance.setDepThree(row.getCell(3).getNumericCellValue());
                        nonFinance.setDepFour(row.getCell(4).getNumericCellValue());
                        nonFinance.setDepFive(row.getCell(5).getNumericCellValue());}

                }

                }
            }
            nonFinancialSummaryDataRepository.save(nonFinance);
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

}
