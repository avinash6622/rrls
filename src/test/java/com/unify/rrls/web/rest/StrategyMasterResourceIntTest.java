package com.unify.rrls.web.rest;

import com.unify.rrls.ResearchRepositoryLearningSystemApp;

import com.unify.rrls.domain.StrategyMaster;
import com.unify.rrls.repository.StrategyMasterRepository;
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

/**
 * Test class for the StrategyMasterResource REST controller.
 *
 * @see StrategyMasterResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResearchRepositoryLearningSystemApp.class)
public class StrategyMasterResourceIntTest {

    private static final String DEFAULT_STRATEGY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_STRATEGY_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_STRATEGY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_STRATEGY_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_S_STATUS = 1;
    private static final Integer UPDATED_S_STATUS = 2;

    private static final LocalDate DEFAULT_DATE_ACTIVE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_ACTIVE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Instant DEFAULT_UPDATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private StrategyMasterRepository strategyMasterRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restStrategyMasterMockMvc;

    private StrategyMaster strategyMaster;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StrategyMasterResource strategyMasterResource = new StrategyMasterResource(strategyMasterRepository);
        this.restStrategyMasterMockMvc = MockMvcBuilders.standaloneSetup(strategyMasterResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StrategyMaster createEntity(EntityManager em) {
        StrategyMaster strategyMaster = new StrategyMaster()
            .strategyCode(DEFAULT_STRATEGY_CODE)
            .strategyName(DEFAULT_STRATEGY_NAME)
            .sStatus(DEFAULT_S_STATUS)
            .dateActive(DEFAULT_DATE_ACTIVE)
            .createdBy(DEFAULT_CREATED_BY)
            .updatedBy(DEFAULT_UPDATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .updatedDate(DEFAULT_UPDATED_DATE);
        return strategyMaster;
    }

    @Before
    public void initTest() {
        strategyMaster = createEntity(em);
    }

    @Test
    @Transactional
    public void createStrategyMaster() throws Exception {
        int databaseSizeBeforeCreate = strategyMasterRepository.findAll().size();

        // Create the StrategyMaster
        restStrategyMasterMockMvc.perform(post("/api/strategy-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(strategyMaster)))
            .andExpect(status().isCreated());

        // Validate the StrategyMaster in the database
        List<StrategyMaster> strategyMasterList = strategyMasterRepository.findAll();
        assertThat(strategyMasterList).hasSize(databaseSizeBeforeCreate + 1);
        StrategyMaster testStrategyMaster = strategyMasterList.get(strategyMasterList.size() - 1);
        assertThat(testStrategyMaster.getStrategyCode()).isEqualTo(DEFAULT_STRATEGY_CODE);
        assertThat(testStrategyMaster.getStrategyName()).isEqualTo(DEFAULT_STRATEGY_NAME);
        assertThat(testStrategyMaster.getsStatus()).isEqualTo(DEFAULT_S_STATUS);
        assertThat(testStrategyMaster.getDateActive()).isEqualTo(DEFAULT_DATE_ACTIVE);
        assertThat(testStrategyMaster.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testStrategyMaster.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testStrategyMaster.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testStrategyMaster.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void createStrategyMasterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = strategyMasterRepository.findAll().size();

        // Create the StrategyMaster with an existing ID
        strategyMaster.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStrategyMasterMockMvc.perform(post("/api/strategy-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(strategyMaster)))
            .andExpect(status().isBadRequest());

        // Validate the StrategyMaster in the database
        List<StrategyMaster> strategyMasterList = strategyMasterRepository.findAll();
        assertThat(strategyMasterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkStrategyCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = strategyMasterRepository.findAll().size();
        // set the field null
        strategyMaster.setStrategyCode(null);

        // Create the StrategyMaster, which fails.

        restStrategyMasterMockMvc.perform(post("/api/strategy-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(strategyMaster)))
            .andExpect(status().isBadRequest());

        List<StrategyMaster> strategyMasterList = strategyMasterRepository.findAll();
        assertThat(strategyMasterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStrategyNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = strategyMasterRepository.findAll().size();
        // set the field null
        strategyMaster.setStrategyName(null);

        // Create the StrategyMaster, which fails.

        restStrategyMasterMockMvc.perform(post("/api/strategy-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(strategyMaster)))
            .andExpect(status().isBadRequest());

        List<StrategyMaster> strategyMasterList = strategyMasterRepository.findAll();
        assertThat(strategyMasterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = strategyMasterRepository.findAll().size();
        // set the field null
        strategyMaster.setCreatedDate(null);

        // Create the StrategyMaster, which fails.

        restStrategyMasterMockMvc.perform(post("/api/strategy-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(strategyMaster)))
            .andExpect(status().isBadRequest());

        List<StrategyMaster> strategyMasterList = strategyMasterRepository.findAll();
        assertThat(strategyMasterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStrategyMasters() throws Exception {
        // Initialize the database
        strategyMasterRepository.saveAndFlush(strategyMaster);

        // Get all the strategyMasterList
        restStrategyMasterMockMvc.perform(get("/api/strategy-masters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(strategyMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].strategyCode").value(hasItem(DEFAULT_STRATEGY_CODE.toString())))
            .andExpect(jsonPath("$.[*].strategyName").value(hasItem(DEFAULT_STRATEGY_NAME.toString())))
            .andExpect(jsonPath("$.[*].sStatus").value(hasItem(DEFAULT_S_STATUS)))
            .andExpect(jsonPath("$.[*].dateActive").value(hasItem(DEFAULT_DATE_ACTIVE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())));
    }

    @Test
    @Transactional
    public void getStrategyMaster() throws Exception {
        // Initialize the database
        strategyMasterRepository.saveAndFlush(strategyMaster);

        // Get the strategyMaster
        restStrategyMasterMockMvc.perform(get("/api/strategy-masters/{id}", strategyMaster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(strategyMaster.getId().intValue()))
            .andExpect(jsonPath("$.strategyCode").value(DEFAULT_STRATEGY_CODE.toString()))
            .andExpect(jsonPath("$.strategyName").value(DEFAULT_STRATEGY_NAME.toString()))
            .andExpect(jsonPath("$.sStatus").value(DEFAULT_S_STATUS))
            .andExpect(jsonPath("$.dateActive").value(DEFAULT_DATE_ACTIVE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingStrategyMaster() throws Exception {
        // Get the strategyMaster
        restStrategyMasterMockMvc.perform(get("/api/strategy-masters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStrategyMaster() throws Exception {
        // Initialize the database
        strategyMasterRepository.saveAndFlush(strategyMaster);
        int databaseSizeBeforeUpdate = strategyMasterRepository.findAll().size();

        // Update the strategyMaster
        StrategyMaster updatedStrategyMaster = strategyMasterRepository.findOne(strategyMaster.getId());
        updatedStrategyMaster
            .strategyCode(UPDATED_STRATEGY_CODE)
            .strategyName(UPDATED_STRATEGY_NAME)
            .sStatus(UPDATED_S_STATUS)
            .dateActive(UPDATED_DATE_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .updatedBy(UPDATED_UPDATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);

        restStrategyMasterMockMvc.perform(put("/api/strategy-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedStrategyMaster)))
            .andExpect(status().isOk());

        // Validate the StrategyMaster in the database
        List<StrategyMaster> strategyMasterList = strategyMasterRepository.findAll();
        assertThat(strategyMasterList).hasSize(databaseSizeBeforeUpdate);
        StrategyMaster testStrategyMaster = strategyMasterList.get(strategyMasterList.size() - 1);
        assertThat(testStrategyMaster.getStrategyCode()).isEqualTo(UPDATED_STRATEGY_CODE);
        assertThat(testStrategyMaster.getStrategyName()).isEqualTo(UPDATED_STRATEGY_NAME);
        assertThat(testStrategyMaster.getsStatus()).isEqualTo(UPDATED_S_STATUS);
        assertThat(testStrategyMaster.getDateActive()).isEqualTo(UPDATED_DATE_ACTIVE);
        assertThat(testStrategyMaster.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testStrategyMaster.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testStrategyMaster.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testStrategyMaster.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingStrategyMaster() throws Exception {
        int databaseSizeBeforeUpdate = strategyMasterRepository.findAll().size();

        // Create the StrategyMaster

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restStrategyMasterMockMvc.perform(put("/api/strategy-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(strategyMaster)))
            .andExpect(status().isCreated());

        // Validate the StrategyMaster in the database
        List<StrategyMaster> strategyMasterList = strategyMasterRepository.findAll();
        assertThat(strategyMasterList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteStrategyMaster() throws Exception {
        // Initialize the database
        strategyMasterRepository.saveAndFlush(strategyMaster);
        int databaseSizeBeforeDelete = strategyMasterRepository.findAll().size();

        // Get the strategyMaster
        restStrategyMasterMockMvc.perform(delete("/api/strategy-masters/{id}", strategyMaster.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<StrategyMaster> strategyMasterList = strategyMasterRepository.findAll();
        assertThat(strategyMasterList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StrategyMaster.class);
        StrategyMaster strategyMaster1 = new StrategyMaster();
        strategyMaster1.setId(1L);
        StrategyMaster strategyMaster2 = new StrategyMaster();
        strategyMaster2.setId(strategyMaster1.getId());
        assertThat(strategyMaster1).isEqualTo(strategyMaster2);
        strategyMaster2.setId(2L);
        assertThat(strategyMaster1).isNotEqualTo(strategyMaster2);
        strategyMaster1.setId(null);
        assertThat(strategyMaster1).isNotEqualTo(strategyMaster2);
    }
}
