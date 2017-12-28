/*package com.unify.rrls.web.rest;

import com.unify.rrls.ResearchRepositoryLearningSystemApp;

import com.unify.rrls.domain.OpportunityMaster;
import com.unify.rrls.repository.OpportunityMasterRepository;
import com.unify.rrls.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

*//**
 * Test class for the OpportunityMasterResource REST controller.
 *
 * @see OpportunityMasterResource
 *//*
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResearchRepositoryLearningSystemApp.class)
public class OpportunityMasterResourceIntTest {

    private static final String DEFAULT_OPP_CODE = "AAAAAAAAAA";
    private static final String UPDATED_OPP_CODE = "BBBBBBBBBB";

    private static final Integer DEFAULT_STRATEGY_ID = 10;
    private static final Integer UPDATED_STRATEGY_ID = 9;

    private static final String DEFAULT_OPP_NAME = "AAAAAAAAAA";
    private static final String UPDATED_OPP_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_OPP_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_OPP_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Instant DEFAULT_UPDATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private OpportunityMasterRepository opportunityMasterRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOpportunityMasterMockMvc;

    private OpportunityMaster opportunityMaster;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OpportunityMasterResource opportunityMasterResource = new OpportunityMasterResource(opportunityMasterRepository);
        this.restOpportunityMasterMockMvc = MockMvcBuilders.standaloneSetup(opportunityMasterResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    *//**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     *//*
    public static OpportunityMaster createEntity(EntityManager em) {
        OpportunityMaster opportunityMaster = new OpportunityMaster()
            .oppCode(DEFAULT_OPP_CODE)
            .strategyId(DEFAULT_STRATEGY_ID)
            .oppName(DEFAULT_OPP_NAME)
            .oppDescription(DEFAULT_OPP_DESCRIPTION)
            .createdBy(DEFAULT_CREATED_BY)
            .updatedBy(DEFAULT_UPDATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .updatedDate(DEFAULT_UPDATED_DATE);
        return opportunityMaster;
    }

    @Before
    public void initTest() {
        opportunityMaster = createEntity(em);
    }

    @Test
    @Transactional
    public void createOpportunityMaster() throws Exception {
        int databaseSizeBeforeCreate = opportunityMasterRepository.findAll().size();

        // Create the OpportunityMaster
        restOpportunityMasterMockMvc.perform(post("/api/opportunity-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(opportunityMaster)))
            .andExpect(status().isCreated());

        // Validate the OpportunityMaster in the database
        List<OpportunityMaster> opportunityMasterList = opportunityMasterRepository.findAll();
        assertThat(opportunityMasterList).hasSize(databaseSizeBeforeCreate + 1);
        OpportunityMaster testOpportunityMaster = opportunityMasterList.get(opportunityMasterList.size() - 1);
        assertThat(testOpportunityMaster.getOppCode()).isEqualTo(DEFAULT_OPP_CODE);
        assertThat(testOpportunityMaster.getStrategyId()).isEqualTo(DEFAULT_STRATEGY_ID);
        assertThat(testOpportunityMaster.getOppName()).isEqualTo(DEFAULT_OPP_NAME);
        assertThat(testOpportunityMaster.getOppDescription()).isEqualTo(DEFAULT_OPP_DESCRIPTION);
        assertThat(testOpportunityMaster.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testOpportunityMaster.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testOpportunityMaster.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testOpportunityMaster.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void createOpportunityMasterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = opportunityMasterRepository.findAll().size();

        // Create the OpportunityMaster with an existing ID
        opportunityMaster.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOpportunityMasterMockMvc.perform(post("/api/opportunity-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(opportunityMaster)))
            .andExpect(status().isBadRequest());

        // Validate the OpportunityMaster in the database
        List<OpportunityMaster> opportunityMasterList = opportunityMasterRepository.findAll();
        assertThat(opportunityMasterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkOppCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = opportunityMasterRepository.findAll().size();
        // set the field null
        opportunityMaster.setOppCode(null);

        // Create the OpportunityMaster, which fails.

        restOpportunityMasterMockMvc.perform(post("/api/opportunity-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(opportunityMaster)))
            .andExpect(status().isBadRequest());

        List<OpportunityMaster> opportunityMasterList = opportunityMasterRepository.findAll();
        assertThat(opportunityMasterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStrategyIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = opportunityMasterRepository.findAll().size();
        // set the field null
        opportunityMaster.setStrategyId(null);

        // Create the OpportunityMaster, which fails.

        restOpportunityMasterMockMvc.perform(post("/api/opportunity-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(opportunityMaster)))
            .andExpect(status().isBadRequest());

        List<OpportunityMaster> opportunityMasterList = opportunityMasterRepository.findAll();
        assertThat(opportunityMasterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOppNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = opportunityMasterRepository.findAll().size();
        // set the field null
        opportunityMaster.setOppName(null);

        // Create the OpportunityMaster, which fails.

        restOpportunityMasterMockMvc.perform(post("/api/opportunity-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(opportunityMaster)))
            .andExpect(status().isBadRequest());

        List<OpportunityMaster> opportunityMasterList = opportunityMasterRepository.findAll();
        assertThat(opportunityMasterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOppDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = opportunityMasterRepository.findAll().size();
        // set the field null
        opportunityMaster.setOppDescription(null);

        // Create the OpportunityMaster, which fails.

        restOpportunityMasterMockMvc.perform(post("/api/opportunity-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(opportunityMaster)))
            .andExpect(status().isBadRequest());

        List<OpportunityMaster> opportunityMasterList = opportunityMasterRepository.findAll();
        assertThat(opportunityMasterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = opportunityMasterRepository.findAll().size();
        // set the field null
        opportunityMaster.setCreatedBy(null);

        // Create the OpportunityMaster, which fails.

        restOpportunityMasterMockMvc.perform(post("/api/opportunity-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(opportunityMaster)))
            .andExpect(status().isBadRequest());

        List<OpportunityMaster> opportunityMasterList = opportunityMasterRepository.findAll();
        assertThat(opportunityMasterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = opportunityMasterRepository.findAll().size();
        // set the field null
        opportunityMaster.setUpdatedBy(null);

        // Create the OpportunityMaster, which fails.

        restOpportunityMasterMockMvc.perform(post("/api/opportunity-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(opportunityMaster)))
            .andExpect(status().isBadRequest());

        List<OpportunityMaster> opportunityMasterList = opportunityMasterRepository.findAll();
        assertThat(opportunityMasterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = opportunityMasterRepository.findAll().size();
        // set the field null
        opportunityMaster.setCreatedDate(null);

        // Create the OpportunityMaster, which fails.

        restOpportunityMasterMockMvc.perform(post("/api/opportunity-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(opportunityMaster)))
            .andExpect(status().isBadRequest());

        List<OpportunityMaster> opportunityMasterList = opportunityMasterRepository.findAll();
        assertThat(opportunityMasterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOpportunityMasters() throws Exception {
        // Initialize the database
        opportunityMasterRepository.saveAndFlush(opportunityMaster);

        // Get all the opportunityMasterList
        restOpportunityMasterMockMvc.perform(get("/api/opportunity-masters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(opportunityMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].oppCode").value(hasItem(DEFAULT_OPP_CODE.toString())))
            .andExpect(jsonPath("$.[*].strategyId").value(hasItem(DEFAULT_STRATEGY_ID)))
            .andExpect(jsonPath("$.[*].oppName").value(hasItem(DEFAULT_OPP_NAME.toString())))
            .andExpect(jsonPath("$.[*].oppDescription").value(hasItem(DEFAULT_OPP_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())));
    }

    @Test
    @Transactional
    public void getOpportunityMaster() throws Exception {
        // Initialize the database
        opportunityMasterRepository.saveAndFlush(opportunityMaster);

        // Get the opportunityMaster
        restOpportunityMasterMockMvc.perform(get("/api/opportunity-masters/{id}", opportunityMaster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(opportunityMaster.getId().intValue()))
            .andExpect(jsonPath("$.oppCode").value(DEFAULT_OPP_CODE.toString()))
            .andExpect(jsonPath("$.strategyId").value(DEFAULT_STRATEGY_ID))
            .andExpect(jsonPath("$.oppName").value(DEFAULT_OPP_NAME.toString()))
            .andExpect(jsonPath("$.oppDescription").value(DEFAULT_OPP_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOpportunityMaster() throws Exception {
        // Get the opportunityMaster
        restOpportunityMasterMockMvc.perform(get("/api/opportunity-masters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOpportunityMaster() throws Exception {
        // Initialize the database
        opportunityMasterRepository.saveAndFlush(opportunityMaster);
        int databaseSizeBeforeUpdate = opportunityMasterRepository.findAll().size();

        // Update the opportunityMaster
        OpportunityMaster updatedOpportunityMaster = opportunityMasterRepository.findOne(opportunityMaster.getId());
        updatedOpportunityMaster
            .oppCode(UPDATED_OPP_CODE)
            .strategyId(UPDATED_STRATEGY_ID)
            .oppName(UPDATED_OPP_NAME)
            .oppDescription(UPDATED_OPP_DESCRIPTION)
            .createdBy(UPDATED_CREATED_BY)
            .updatedBy(UPDATED_UPDATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);

        restOpportunityMasterMockMvc.perform(put("/api/opportunity-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOpportunityMaster)))
            .andExpect(status().isOk());

        // Validate the OpportunityMaster in the database
        List<OpportunityMaster> opportunityMasterList = opportunityMasterRepository.findAll();
        assertThat(opportunityMasterList).hasSize(databaseSizeBeforeUpdate);
        OpportunityMaster testOpportunityMaster = opportunityMasterList.get(opportunityMasterList.size() - 1);
        assertThat(testOpportunityMaster.getOppCode()).isEqualTo(UPDATED_OPP_CODE);
        assertThat(testOpportunityMaster.getStrategyId()).isEqualTo(UPDATED_STRATEGY_ID);
        assertThat(testOpportunityMaster.getOppName()).isEqualTo(UPDATED_OPP_NAME);
        assertThat(testOpportunityMaster.getOppDescription()).isEqualTo(UPDATED_OPP_DESCRIPTION);
        assertThat(testOpportunityMaster.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testOpportunityMaster.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testOpportunityMaster.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testOpportunityMaster.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingOpportunityMaster() throws Exception {
        int databaseSizeBeforeUpdate = opportunityMasterRepository.findAll().size();

        // Create the OpportunityMaster

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOpportunityMasterMockMvc.perform(put("/api/opportunity-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(opportunityMaster)))
            .andExpect(status().isCreated());

        // Validate the OpportunityMaster in the database
        List<OpportunityMaster> opportunityMasterList = opportunityMasterRepository.findAll();
        assertThat(opportunityMasterList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteOpportunityMaster() throws Exception {
        // Initialize the database
        opportunityMasterRepository.saveAndFlush(opportunityMaster);
        int databaseSizeBeforeDelete = opportunityMasterRepository.findAll().size();

        // Get the opportunityMaster
        restOpportunityMasterMockMvc.perform(delete("/api/opportunity-masters/{id}", opportunityMaster.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<OpportunityMaster> opportunityMasterList = opportunityMasterRepository.findAll();
        assertThat(opportunityMasterList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OpportunityMaster.class);
        OpportunityMaster opportunityMaster1 = new OpportunityMaster();
        opportunityMaster1.setId(1L);
        OpportunityMaster opportunityMaster2 = new OpportunityMaster();
        opportunityMaster2.setId(opportunityMaster1.getId());
        assertThat(opportunityMaster1).isEqualTo(opportunityMaster2);
        opportunityMaster2.setId(2L);
        assertThat(opportunityMaster1).isNotEqualTo(opportunityMaster2);
        opportunityMaster1.setId(null);
        assertThat(opportunityMaster1).isNotEqualTo(opportunityMaster2);
    }
}
*/