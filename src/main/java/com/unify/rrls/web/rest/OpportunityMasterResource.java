package com.unify.rrls.web.rest;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import com.unify.rrls.domain.*;
import com.unify.rrls.repository.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
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
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.codahale.metrics.annotation.Timed;
import com.unify.rrls.security.SecurityUtils;
import com.unify.rrls.web.rest.util.HeaderUtil;
import com.unify.rrls.web.rest.util.PaginationUtil;

import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
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


	public OpportunityMasterResource(OpportunityMasterRepository opportunityMasterRepository,
			FileUploadRepository fileUploadRepository, FileUploadCommentsRepository fileUploadCommentsRepository,
			StrategyMappingRepository strategyMappingRepository,StrategyMasterRepository strategyMasterRepository,
			AdditionalFileUploadRepository additionalFileUploadRepository,OpportunityMasterContactRepository opportunityMasterContactRepository,FinancialSummaryDataRepository financialSummaryDataRepository) {
		this.opportunityMasterRepository = opportunityMasterRepository;
		this.fileUploadRepository = fileUploadRepository;
		this.fileUploadCommentsRepository = fileUploadCommentsRepository;
		this.strategyMappingRepository=strategyMappingRepository;
		this.strategyMasterRepository=strategyMasterRepository;
		this.additionalFileUploadRepository=additionalFileUploadRepository;
		this.opportunityMasterContactRepository=opportunityMasterContactRepository;
		this.financialSummaryDataRepository=financialSummaryDataRepository;
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


            /*for(OpportunityMasterContact opportunityMasterContact:opportunityMaster.getOpportunityMasterContact())

     /*   for(OpportunityMasterContact oC:opportunityMaster.getSelectedoppContanct())
             {
                 oC.setOpportunityMasterId(result);
                 System.out.println(oC);
                  opportunityMasterContactRepository.save(oC);

             }*/

         FinancialSummaryData summaryData = opportunityMaster.getFinancialSummaryData();
         summaryData.setOpportunityMasterId(result);
         financialSummaryDataRepository.save(summaryData);




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

    @PostMapping("/opportunity-masters/download-file")
    @Timed
    public ResponseEntity<FileUpload> downloader(HttpServletRequest request, HttpServletResponse response,
                         @RequestBody FileUpload fileUpload) throws URISyntaxException, IOException{

        System.out.println("Entering"+fileUpload.getFileName()+"path:"+fileUpload.getFileData());


      /*  ResponseBuilder responses=Response.ok((Object)file);
              responses.header("Content-Disposition", "attachment; filename="+files);
    	    responses.setHeader("Content-Disposition", "attachment; filename="+fileUpload.getFileName());
    	    responses.setContentType(MediaType.APPLICATION_OCTET_STREAM);
*/
        FileInputStream inputStream = new FileInputStream(new File(fileUpload.getFileData()));
        String fileNames=fileName;
        response.setHeader("Content-Disposition", "attachment; filename="+fileUpload.getFileName());
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        ServletOutputStream outputStream = response.getOutputStream();
        IOUtils.copy(inputStream, outputStream);

        outputStream.close();
        inputStream.close();

        return ResponseEntity.ok().build();
                 }
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
      //  System.out.println("fpath---->"+fPath);

      //  System.out.println("ind---->"+idxOfDot+"extension"+extension+"name--->"+name);

       // System.out.println("sFile----->"+sFile);
		int counter = 1;
		while(fPath.exists()){
			sFile = name+"("+counter+")."+extension;
		    path = Paths.get(sFile);
		    fPath=new File(sFilesDirectory+sFile);
		    counter++;
		}
		sFile=sFilesDirectory+sFile;

     //   System.out.println("After sfile---->"+sFile);

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
		/*List<FileUpload> fileUploads = fileUploadRepository.findByOpportunityMasterId(opportunityMaster);
		if (!fileUploads.isEmpty()) {
		opportunityMaster.setFileUploads(fileUploads);			}
		*/
		return ResponseEntity.created(new URI("/api/opportunity-masters"))
				.headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(result);/*ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));*/
	}

	/*@PostMapping("/opportunity-masters/additional-word-file")
	@Timed
	public ResponseEntity<AdditionalFileUpload> createWordAdditionalFile(@RequestBody DocumentCreationBean documentCreationBean)
			throws URISyntaxException, IOException{
		FileUpload fileUpload=fileUploadRepository.findOne(documentCreationBean.getFileId());
		String sFilesDirectory="";
		String user=SecurityUtils.getCurrentUserLogin();
		String sFile=fileUpload.getFileName();
		sFile=sFile.substring(0, sFile.lastIndexOf('.'));
		sFilesDirectory = "src/main/resources/"+fileUpload.getOpportunityMasterId().getMasterName().getOppName()+"/"+user+"/"+sFile+"/";
		sFile=sFile+"-add.docx";
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

		AdditionalFileUpload result = new AdditionalFileUpload();
		convertHTMLToDoc(documentCreationBean.getFileContent(),sFilesDirectory, sFile);
		result.setFileName(fPath.getName());
		result.setFileData(sFile);
		result.setFileUploadID(fileUpload);
		result=additionalFileUploadRepository.save(result);

		return ResponseEntity.created(new URI("/api/opportunity-masters"))
				.headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(result);ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
	}*/

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
		opportunityMaster.setFinancialSummaryData(summaryData);
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

}
