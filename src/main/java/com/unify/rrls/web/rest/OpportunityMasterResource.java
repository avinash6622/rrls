package com.unify.rrls.web.rest;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.codahale.metrics.annotation.Timed;
import com.unify.rrls.domain.AnswerComment;
import com.unify.rrls.domain.CommentOpportunity;
import com.unify.rrls.domain.DocumentCreationBean;
import com.unify.rrls.domain.FileUpload;
import com.unify.rrls.domain.FileUploadComments;
import com.unify.rrls.domain.FinancialSummaryData;
import com.unify.rrls.domain.NonFinancialSummaryData;
import com.unify.rrls.domain.OpportunityAutomation;
import com.unify.rrls.domain.OpportunityLearning;
import com.unify.rrls.domain.OpportunityMaster;
import com.unify.rrls.domain.OpportunityMasterContact;
import com.unify.rrls.domain.OpportunityQuestion;
import com.unify.rrls.domain.OpportunitySummaryData;
import com.unify.rrls.domain.ReplyComment;
import com.unify.rrls.domain.StrategyMapping;
import com.unify.rrls.domain.StrategyMaster;
import com.unify.rrls.repository.AdditionalFileUploadRepository;
import com.unify.rrls.repository.AnswerCommentRepository;
import com.unify.rrls.repository.CommentOpportunityRepository;
import com.unify.rrls.repository.DecimalConfigurationRepository;
import com.unify.rrls.repository.FileUploadCommentsRepository;
import com.unify.rrls.repository.FileUploadRepository;
import com.unify.rrls.repository.FinancialSummaryDataRepository;
import com.unify.rrls.repository.NonFinancialSummaryDataRepository;
import com.unify.rrls.repository.OpportunityAutomationRepository;
import com.unify.rrls.repository.OpportunityLearningRepository;
import com.unify.rrls.repository.OpportunityMasterContactRepository;
import com.unify.rrls.repository.OpportunityMasterRepository;
import com.unify.rrls.repository.OpportunityQuestionRepository;
import com.unify.rrls.repository.OpportunitySummaryDataRepository;
import com.unify.rrls.repository.ReplyCommentRepository;
import com.unify.rrls.repository.StrategyMappingRepository;
import com.unify.rrls.repository.StrategyMasterRepository;
import com.unify.rrls.security.SecurityUtils;
import com.unify.rrls.web.rest.util.HeaderUtil;
import com.unify.rrls.web.rest.util.PaginationUtil;

import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;

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

	// StrategyMapping strategyMapping;
	// OpportunityMasterContact opportunityMasterContact= new
	// OpportunityMasterContact();

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
	private final OpportunityAutomationRepository opportunityAutomationRepository;
	private final OpportunityQuestionRepository opportunityQuestionRepository;
	private final AnswerCommentRepository answerCommentRepository;
	private final CommentOpportunityRepository commentOpportunityRepository;
	private final ReplyCommentRepository replyCommentRepository;
	private final OpportunityLearningRepository opportunityLearningRepository;
	private final DecimalConfigurationRepository decimalConfigurationRepository;

	@Autowired
	NotificationServiceResource notificationServiceResource;

	@Autowired
	UserResource userResource;

	public OpportunityMasterResource(OpportunityMasterRepository opportunityMasterRepository,
			FileUploadRepository fileUploadRepository, FileUploadCommentsRepository fileUploadCommentsRepository,
			StrategyMappingRepository strategyMappingRepository, StrategyMasterRepository strategyMasterRepository,
			AdditionalFileUploadRepository additionalFileUploadRepository,
			OpportunityMasterContactRepository opportunityMasterContactRepository,
			FinancialSummaryDataRepository financialSummaryDataRepository,
			NonFinancialSummaryDataRepository nonFinancialSummaryDataRepository,
			OpportunitySummaryDataRepository opportunitySummaryDataRepository,
			OpportunityAutomationRepository opportunityAutomationRepository,
			OpportunityQuestionRepository opportunityQuestionRepository,
			AnswerCommentRepository answerCommentRepository, CommentOpportunityRepository commentOpportunityRepository,
			ReplyCommentRepository replyCommentRepository,
			OpportunityLearningRepository opportunityLearningRepository,
			DecimalConfigurationRepository decimalConfigurationRepository) {
		this.opportunityMasterRepository = opportunityMasterRepository;
		this.fileUploadRepository = fileUploadRepository;
		this.fileUploadCommentsRepository = fileUploadCommentsRepository;
		this.strategyMappingRepository = strategyMappingRepository;
		this.strategyMasterRepository = strategyMasterRepository;
		this.additionalFileUploadRepository = additionalFileUploadRepository;
		this.opportunityMasterContactRepository = opportunityMasterContactRepository;
		this.financialSummaryDataRepository = financialSummaryDataRepository;
		this.nonFinancialSummaryDataRepository = nonFinancialSummaryDataRepository;
		this.opportunitySummaryDataRepository = opportunitySummaryDataRepository;
		this.opportunityAutomationRepository = opportunityAutomationRepository;
		this.opportunityQuestionRepository = opportunityQuestionRepository;
		this.answerCommentRepository = answerCommentRepository;
		this.commentOpportunityRepository = commentOpportunityRepository;
		this.replyCommentRepository = replyCommentRepository;
		this.opportunityLearningRepository = opportunityLearningRepository;
		this.decimalConfigurationRepository=decimalConfigurationRepository;
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
		// System.out.println(opportunityMaster.getStrategyMasterId());
		List<OpportunityMasterContact> addContact = new ArrayList<>();
		FinancialSummaryData addFinance = new FinancialSummaryData();
		NonFinancialSummaryData addNonFinance = new NonFinancialSummaryData();
		/* List<StrategyMapping> addStrategy=new ArrayList<>(); */
		OpportunityMaster master = new OpportunityMaster();

		if (opportunityMaster != null) {
			master = opportunityMasterRepository.findByMasterName(opportunityMaster.getMasterName());
			if (master != null) {
				return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "name exists",
						"A new opportunity cannot already have same opportunity name")).body(null);
			}

		}

		OpportunityMaster result = opportunityMasterRepository.save(opportunityMaster);
		System.out.println("sdfsdfsd--->" + result);

		for (OpportunityMasterContact oC : opportunityMaster.getSelectedoppContanct()) {
			oC.setOpportunityMasterId(result);
			System.out.println(oC);
			opportunityMasterContactRepository.save(oC);

		}
		addContact = opportunityMasterContactRepository.findByOpportunityMasterId(result);
		result.setSelectedoppContanct(addContact);

		if (opportunityMaster.getSelectedStrategyMaster() == null) {

			List<StrategyMaster> strategyMaster = strategyMasterRepository.findByStrategyName("Unallocated Strategy");

			for (StrategyMaster sm : strategyMaster) {
				StrategyMapping strategyMapping = new StrategyMapping();
				strategyMapping.setOpportunityMaster(result);
				strategyMapping.setStrategyMaster(sm);
				strategyMappingRepository.save(strategyMapping);
			}
			System.out.println(strategyMaster);
		
			opportunityMaster.setSelectedStrategyMaster(strategyMaster);
			List<StrategyMapping> countStrategy=strategyMappingRepository.findByStrategyMaster(strategyMaster.get(0));
			StrategyMaster updateTot=strategyMasterRepository.findOne(strategyMaster.get(0).getId());
			updateTot.setTotalStocks((double) countStrategy.size());
			strategyMasterRepository.save(updateTot);
			


		}

		else {

			for (StrategyMaster sm : opportunityMaster.getSelectedStrategyMaster()) {
				StrategyMapping strategyMapping = new StrategyMapping();
				strategyMapping.setOpportunityMaster(result);
				strategyMapping.setStrategyMaster(sm);
				strategyMappingRepository.save(strategyMapping);
				List<StrategyMapping> countStrategy=strategyMappingRepository.findByStrategyMaster(sm);
				StrategyMaster updateTot=strategyMasterRepository.findOne(sm.getId());
				updateTot.setTotalStocks((double) countStrategy.size());
				strategyMasterRepository.save(updateTot);

			}

		}

		if (opportunityMaster.getMasterName().getSegment().equals("Finance")) {

			FinancialSummaryData summaryData = opportunityMaster.getFinancialSummaryData();
			summaryData.setOpportunityMasterId(result);
			financialSummaryDataRepository.save(summaryData);

			for (StrategyMaster sm : opportunityMaster.getSelectedStrategyMaster()) {
				OpportunitySummaryData opportunitySummaryData = new OpportunitySummaryData();
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

			addFinance = financialSummaryDataRepository.findByOpportunityMasterId(result);
			result.setFinancialSummaryData(addFinance);

		}

		else {

			NonFinancialSummaryData nonFinancialSummaryData = opportunityMaster.getNonFinancialSummaryData();
			nonFinancialSummaryData.setOpportunityMaster(result);
			nonFinancialSummaryDataRepository.save(nonFinancialSummaryData);

			for (StrategyMaster sm : opportunityMaster.getSelectedStrategyMaster()) {
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
				if (nonFinancialSummaryData.getWeight() != null) {
					opportunitySummaryData.setbWeight(nonFinancialSummaryData.getWeight());
					opportunitySummaryData.setPegOj(
							(nonFinancialSummaryData.getPethree() / nonFinancialSummaryData.getPatGrowthThree()));
					opportunitySummaryData
							.setPortPeFirst(nonFinancialSummaryData.getWeight() * nonFinancialSummaryData.getPeOne());
					opportunitySummaryData
							.setPortPeSecond(nonFinancialSummaryData.getWeight() * nonFinancialSummaryData.getPeTwo());
					opportunitySummaryData
							.setPortPeThird(nonFinancialSummaryData.getWeight() * nonFinancialSummaryData.getPethree());
					opportunitySummaryData
							.setPortPeFourth(nonFinancialSummaryData.getWeight() * nonFinancialSummaryData.getPeFour());
					opportunitySummaryData
							.setPortPeFifth(nonFinancialSummaryData.getWeight() * nonFinancialSummaryData.getPeFive());
					opportunitySummaryData.setEarningsSecond(
							(nonFinancialSummaryData.getWeight() * nonFinancialSummaryData.getPatGrowthTwo()) / 100.0);
					opportunitySummaryData.setEarningsThird(
							(nonFinancialSummaryData.getWeight() * nonFinancialSummaryData.getPatGrowthThree())
									/ 100.0);
					opportunitySummaryData.setEarningsFourth(
							(nonFinancialSummaryData.getWeight() * nonFinancialSummaryData.getPatGrowthFour()) / 100.0);
					opportunitySummaryData.setEarningsFifth(
							(nonFinancialSummaryData.getWeight() * nonFinancialSummaryData.getPatGrowthFive()) / 100.0);
					opportunitySummaryData.setWtAvgCap(
							(nonFinancialSummaryData.getWeight() * nonFinancialSummaryData.getMarketCapThree())
									/ 100.0);
					opportunitySummaryData
							.setRoe((nonFinancialSummaryData.getWeight() * nonFinancialSummaryData.getMarketCapThree())
									/ 100.0);
					opportunitySummaryData.setPegYearPeg(nonFinancialSummaryData.getWeight()
							* (nonFinancialSummaryData.getPethree() / nonFinancialSummaryData.getPatGrowthThree()));
				}
				opportunitySummaryDataRepository.save(opportunitySummaryData);
			}

			addNonFinance = nonFinancialSummaryDataRepository.findByOpportunityMaster(result);
			result.setNonFinancialSummaryData(addNonFinance);

		}

		System.out.println(result.getId().toString());

		String page = "Opportunity";

		String name = String.valueOf(result.getMasterName().getOppName());

		Long id = userResource.getUserId(result.getCreatedBy());

		notificationServiceResource.notificationHistorysave(name, result.getCreatedBy(), result.getLastModifiedBy(),
				result.getCreatedDate(), "created", page, "", id);

		// return null;
		return ResponseEntity.ok().headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
				.body(result);
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
		OpportunityAutomation opportunityAutomation = new OpportunityAutomation();
		List<StrategyMapping> strategyMappings = new ArrayList<>();
		strategyMappings = strategyMappingRepository.findByOpportunityMaster(opportunityMasters);
		System.out.println(opportunityMasters.getSelectedStrategyMaster());
		if (!opportunityMasters.getSelectedStrategyMaster().isEmpty()) {
			strategyMappingRepository.delete(strategyMappings);
		}
		// opportunityMasters.setStrategyMappings()
		if (!opportunityMasters.getSelectedStrategyMaster().isEmpty()) {

			for (StrategyMaster sm : opportunityMasters.getSelectedStrategyMaster()) {
				StrategyMapping strategyMap = new StrategyMapping();
				strategyMap.setOpportunityMaster(opportunityMasters);
				strategyMap.setStrategyMaster(sm);
				strategyMappingRepository.save(strategyMap);
				List<StrategyMapping> countStrategy=strategyMappingRepository.findByStrategyMaster(sm);
				StrategyMaster updateTot=strategyMasterRepository.findOne(sm.getId());
				updateTot.setTotalStocks((double) countStrategy.size());
				strategyMasterRepository.save(updateTot);
				

			}
		}
		opportunityMasterContactRepository.save(opportunityMasters.getSelectedoppContanct());
		opportunityAutomation = opportunityAutomationRepository.findByOpportunityMaster(opportunityMasters);

		OpportunityMaster result = opportunityMasterRepository.save(opportunityMasters);
		if (opportunityMasters.getMasterName().getSegment().equals("Finance")) {
			financialSummaryDataRepository.save(opportunityMasters.getFinancialSummaryData());

			List<OpportunitySummaryData> opportunitySummaryDataList = opportunitySummaryDataRepository
					.findByOpportunityMasterid(result);
			if (!opportunitySummaryDataList.isEmpty()) {
				for (OpportunitySummaryData sm : opportunitySummaryDataList) {
					opportunitySummaryDataRepository.delete(sm);
				}
			}

			for (StrategyMaster sm1 : opportunityMasters.getSelectedStrategyMaster()) {
				OpportunitySummaryData sm = new OpportunitySummaryData();
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
				sm.setOpportunityMasterid(opportunityMasters);
				sm.setStrategyMasterId(sm1);
				/*
				 * sm.setbWeight(opportunityMasters.getFinancialSummaryData().
				 * getWeight());
				 */
				if ((opportunityAutomation != null) && (opportunityAutomation.getPrevClose() == null)) {
					sm.setCmp(opportunityAutomation.getPrevClose());
				}

				opportunitySummaryDataRepository.save(sm);

			}

		} else {

			nonFinancialSummaryDataRepository.save(opportunityMasters.getNonFinancialSummaryData());
			List<OpportunitySummaryData> opportunitySummaryDataList = opportunitySummaryDataRepository
					.findByOpportunityMasterid(result);

			if (!opportunitySummaryDataList.isEmpty()) {
				for (OpportunitySummaryData sm : opportunitySummaryDataList) {
					opportunitySummaryDataRepository.delete(sm);
				}
			}

			for (StrategyMaster sm1 : opportunityMasters.getSelectedStrategyMaster()) {
				OpportunitySummaryData sm = new OpportunitySummaryData();
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
				sm.setOpportunityMasterid(opportunityMasters);
				if (opportunityMasters.getNonFinancialSummaryData().getWeight() != null) {
					sm.setbWeight(opportunityMasters.getNonFinancialSummaryData().getWeight());
					sm.setPegOj(opportunityMasters.getNonFinancialSummaryData().getPethree()
							/ opportunityMasters.getNonFinancialSummaryData().getPatGrowthThree());
					sm.setPortPeFirst(opportunityMasters.getNonFinancialSummaryData().getWeight()
							* opportunityMasters.getNonFinancialSummaryData().getPeOne());
					sm.setPortPeSecond(opportunityMasters.getNonFinancialSummaryData().getWeight()
							* opportunityMasters.getNonFinancialSummaryData().getPeTwo());
					sm.setPortPeThird(opportunityMasters.getNonFinancialSummaryData().getWeight()
							* opportunityMasters.getNonFinancialSummaryData().getPethree());
					sm.setPortPeFourth(opportunityMasters.getNonFinancialSummaryData().getWeight()
							* opportunityMasters.getNonFinancialSummaryData().getPeFour());
					sm.setPortPeFifth(opportunityMasters.getNonFinancialSummaryData().getWeight()
							* opportunityMasters.getNonFinancialSummaryData().getPeFive());
					// sm.setEarningsFirst((opportunityMasters.getNonFinancialSummaryData().getWeight()*opportunityMasters.getNonFinancialSummaryData().getPatGrowthOne())/100.0);
					sm.setEarningsSecond((opportunityMasters.getNonFinancialSummaryData().getWeight()
							* opportunityMasters.getNonFinancialSummaryData().getPatGrowthTwo()) / 100.0);
					sm.setEarningsThird((opportunityMasters.getNonFinancialSummaryData().getWeight()
							* opportunityMasters.getNonFinancialSummaryData().getPatGrowthThree()) / 100.0);
					sm.setEarningsFourth((opportunityMasters.getNonFinancialSummaryData().getWeight()
							* opportunityMasters.getNonFinancialSummaryData().getPatGrowthFour()) / 100.0);
					sm.setEarningsFifth((opportunityMasters.getNonFinancialSummaryData().getWeight()
							* opportunityMasters.getNonFinancialSummaryData().getPatGrowthFive()) / 100.0);
					sm.setWtAvgCap((opportunityMasters.getNonFinancialSummaryData().getWeight()
							* opportunityMasters.getNonFinancialSummaryData().getMarketCapThree()) / 100.0);
					sm.setRoe((opportunityMasters.getNonFinancialSummaryData().getWeight()
							* opportunityMasters.getNonFinancialSummaryData().getMarketCapThree()) / 100.0);
					sm.setPegYearPeg(opportunityMasters.getNonFinancialSummaryData().getWeight()
							* (opportunityMasters.getNonFinancialSummaryData().getPethree()
									/ opportunityMasters.getNonFinancialSummaryData().getPatGrowthThree()));
				}
				sm.setStrategyMasterId(sm1);
				if ((opportunityAutomation != null) && (opportunityAutomation.getPrevClose() == null)) {
					sm.setCmp(opportunityAutomation.getPrevClose());
				}
				opportunitySummaryDataRepository.save(sm);
			}

		}

		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, result.getId().toString()))
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
	public ResponseEntity<OpportunityMaster> updateDescription(@RequestBody OpportunityMaster opportunityMaster)
			throws URISyntaxException, IOException {
		// log.debug("REST request to update OpportunityMaster : {}",
		// opportunityMaster);

		OpportunityMaster opportunityMasters = opportunityMasterRepository.findOne(opportunityMaster.getId());
		opportunityMasters.setStatusDes(opportunityMaster.getStatusDes());
		opportunityMasters.setOppStatus(opportunityMaster.getOppStatus());
		System.out.println("des" + opportunityMaster.getStatusDes() + "Status---" + opportunityMaster.getOppStatus()
				+ "Id---" + opportunityMaster.getId());

		OpportunityMaster result = opportunityMasterRepository.save(opportunityMasters);

		String name = result.getMasterName().getOppName();

		String page = "Opportunity";

		Long id = userResource.getUserId(result.getCreatedBy());

		notificationServiceResource.notificationHistorysave(name, result.getCreatedBy(), result.getLastModifiedBy(),
				result.getCreatedDate(), result.getOppStatus(), page, "", id);

		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, opportunityMaster.getId().toString()))
				.body(result);
	}

	@PostMapping("/opportunity-masters/create-file")
	@Timed
	public ResponseEntity<FileUpload> createWordOpportunityMaster(
			@RequestBody DocumentCreationBean documentCreationBean) throws URISyntaxException, IOException {
		String sFilesDirectory = "";
		String user = SecurityUtils.getCurrentUserLogin();
		sFilesDirectory = "src/main/resources/" + documentCreationBean.getOppName() + "/" + user + "/docx/";
		String sFile = documentCreationBean.getFileName() + ".docx";
		String extension = "";
		String name = "";

		int idxOfDot = sFile.lastIndexOf('.'); // Get the last index of . to
												// separate extension
		extension = sFile.substring(idxOfDot + 1);
		name = sFile.substring(0, idxOfDot);
		File fPath = new File(sFilesDirectory + sFile);
		Path path = Paths.get(sFile);

		int counter = 1;
		while (fPath.exists()) {
			sFile = name + "(" + counter + ")." + extension;
			path = Paths.get(sFile);
			fPath = new File(sFilesDirectory + sFile);
			counter++;
		}
		sFile = sFilesDirectory + sFile;

		File dirFiles = new File(sFilesDirectory);
		dirFiles.mkdirs();

		FileUpload result = new FileUpload();
		convertHTMLToDoc(documentCreationBean.getFileContent(), sFilesDirectory, sFile);
		OpportunityMaster opportunityMaster = opportunityMasterRepository.findOne(documentCreationBean.getOppId());
		// System.out.println("Filename"+fPath.getName());
		result.setFileName(name);
		result.setFileDataContentType(extension);
		result.setFileData(sFile);
		result.setOpportunityMasterId(opportunityMaster);
		result = fileUploadRepository.save(result);

		return ResponseEntity.created(new URI("/api/opportunity-masters"))
				.headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
				.body(result);/*
								 * ResponseUtil.wrapOrNotFound(Optional.ofNullable
								 * (result));
								 */
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
	public ResponseEntity<List<OpportunityMaster>> getAllOpportunityMasters(@ApiParam Pageable pageable) {
		log.debug("REST request to get a page of OpportunityMasters");
		Page<OpportunityMaster> page = null;
		String role = SecurityUtils.getCurrentRoleLogin();
		String username = SecurityUtils.getCurrentUserLogin();
		// List<OpportunityMaster>
		// opportunityMaster=opportunityMasterRepository.findByCreatedBy(username);

		page = opportunityMasterRepository.findAll(pageable);

		List<StrategyMaster> strategyMapMaster;

		for (OpportunityMaster opportunityMaster : page) {
			strategyMapMaster = new ArrayList<StrategyMaster>();
			List<StrategyMapping> strategyMappings = strategyMappingRepository
					.findByOpportunityMaster(opportunityMaster);
			for (StrategyMapping sm : strategyMappings) {
				strategyMapMaster.add(sm.getStrategyMaster());
			}
			opportunityMaster.setSelectedStrategyMaster(strategyMapMaster);

		}
		System.out.println(page);
		// HttpHeaders headers =
		// PaginationUtil.generatePaginationHttpHeaders(page,
		// "/api/opportunity-masters");
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/opportunity-masters");

		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
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
		OpportunityAutomation opportunityAutomation = new OpportunityAutomation();
		OpportunityMaster opportunityMaster = opportunityMasterRepository.findOne(id);
		NonFinancialSummaryData nonFinancialSummaryData = new NonFinancialSummaryData();
		FinancialSummaryData summaryData = new FinancialSummaryData();
		List<FileUpload> fileUploads = fileUploadRepository.findByOpportunityMasterId(opportunityMaster);
		List<StrategyMapping> strategyMappings = strategyMappingRepository.findByOpportunityMaster(opportunityMaster);
		opportunityAutomation = opportunityAutomationRepository.findByOpportunityMaster(opportunityMaster);

		List<StrategyMaster> strategyMasters = new ArrayList<StrategyMaster>();
		List<OpportunityMasterContact> opportunityMasterContacts = opportunityMasterContactRepository
				.findByOpportunityMasterId(opportunityMaster);
		System.out.println("CONTACT---->" + opportunityMasterContacts);
		opportunityMaster.setSelectedoppContanct(opportunityMasterContacts);
		System.out.println("MAPPING----->" + strategyMappings);
		for (StrategyMapping sms : strategyMappings) {
			strategyMasters.add(sms.getStrategyMaster());
			opportunityMaster.setSelectedStrategyMaster(strategyMasters);
		}
		// opportunityMaster.setStrategyMapping(strategyMappings);
		// OpportunityMasterContact opportunityMasterContact =
		// opportunityMasterContactRepository.findByOpportunityMasterId(opportunityMaster);
		// opportunityMaster.setOpportunityMasterContact(opportunityMasterContact);

		summaryData = financialSummaryDataRepository.findByOpportunityMasterId(opportunityMaster);
		System.out.println("MAPPINGvf----->" + summaryData);
		if ((opportunityAutomation != null) && (opportunityMaster.getMasterName().getSegment().equals("Finance"))) {
			summaryData.setMarCapThree(opportunityAutomation.getMarketCap());
		}
		opportunityMaster.setFinancialSummaryData(summaryData);
		nonFinancialSummaryData = nonFinancialSummaryDataRepository.findByOpportunityMaster(opportunityMaster);
		System.out.println(nonFinancialSummaryData);
		if ((opportunityAutomation != null) && (opportunityMaster.getMasterName().getSegment().equals("Non-Finance"))) {
			nonFinancialSummaryData.setMarketCapThree(opportunityAutomation.getMarketCap());
		}
		opportunityMaster.setNonFinancialSummaryData(nonFinancialSummaryData);
		System.out.println("MAPPINGvfn----->" + nonFinancialSummaryData);
		List<FileUploadComments> fileComments = fileUploadCommentsRepository.findByOpportunityMaster(opportunityMaster);
		opportunityMaster.setFileUploadCommentList(fileComments);
		if (!fileUploads.isEmpty()) {
			try {
				for (FileUpload fm : fileUploads) {
					opportunityMaster.setFileUploads(fileUploads);
					String inputfilepath = fm.getFileData();

					// opportunityMaster.setHtmlContent(getHTMLContent(inputfilepath));
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

		FileUpload fileUploads = fileUploadRepository.findOne(id);
		// OpportunityMaster
		// opportunityMaster=opportunityMasterRepository.findOne(fileUploads.getOpportunityMasterId().getId());
		try {

			// opportunityMaster.setFileUploads((List<FileUpload>) fileUploads);
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

	@GetMapping("/opportunity-masters/get-Question/{id}")
	@Timed
	public ResponseEntity<List<OpportunityQuestion>> getQuestionAnswer(@PathVariable Long id) {
		log.debug("REST request to get OpportunityMaster : {}", id);

		OpportunityMaster opportunityMasters = opportunityMasterRepository.findOne(id);
		List<OpportunityQuestion> opportunityQuestions = opportunityQuestionRepository
				.findByOpportunityMaster(opportunityMasters);
		List<OpportunityQuestion> commentReply = new ArrayList<>();
		List<AnswerComment> answerComments;

		for (OpportunityQuestion question : opportunityQuestions) {
			answerComments = answerCommentRepository.findByOpportunityQuestion(question);
			question.setAnswerComments(answerComments);
			commentReply.add(question);
		}

		return ResponseUtil.wrapOrNotFound(Optional.ofNullable(commentReply));
	}

	@GetMapping("/opportunity-masters/get-learning/{id}")
	@Timed
	public ResponseEntity<List<OpportunityLearning>> getLearningAnswer(@PathVariable Long id) {
		log.debug("REST request to get OpportunityLearning : {}", id);

		OpportunityMaster opportunityMasters = opportunityMasterRepository.findOne(id);
		List<OpportunityLearning> opportunityLearnings = opportunityLearningRepository
				.findByOpportunityMaster(opportunityMasters);

		return ResponseUtil.wrapOrNotFound(Optional.ofNullable(opportunityLearnings));
	}

	@GetMapping("/opportunity-masters/get-comment/{id}")
	@Timed
	public ResponseEntity<List<CommentOpportunity>> getComment(@PathVariable Long id) {
		log.debug("REST request to get OpportunityMaster : {}", id);

		OpportunityMaster opportunityMasters = opportunityMasterRepository.findOne(id);
		List<CommentOpportunity> commentOpportunity = commentOpportunityRepository
				.findByOpportunityMaster(opportunityMasters);
		List<CommentOpportunity> commentReply = new ArrayList<>();
		List<ReplyComment> replyComments;

		for (CommentOpportunity comment : commentOpportunity) {
			replyComments = replyCommentRepository.findByCommentOpportunity(comment);
			comment.setCommentList(replyComments);
			commentReply.add(comment);
		}

		return ResponseUtil.wrapOrNotFound(Optional.ofNullable(commentReply));
	}

	public String convertHTMLToDoc(String xhtml, String destinationPath, String fileName) {
		log.info("HTML to DOC conversion\n--------------------------------------\nstarted....\n" + xhtml);
		try {

			WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.createPackage();

			XHTMLImporterImpl XHTMLImporter = new XHTMLImporterImpl(wordMLPackage);

			xhtml = xhtml
					// .replace("&amp;", "&")
					.replace("&lt;", "<").replace("&gt;", ">").replace("&#39;", "'").replace("&nbsp;", " ")
					.replace("&#43;", "+").replace("\"", "\'");

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
