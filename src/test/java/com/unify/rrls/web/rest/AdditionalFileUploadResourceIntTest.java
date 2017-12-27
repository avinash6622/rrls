package com.unify.rrls.web.rest;

import com.unify.rrls.ResearchRepositoryLearningSystemApp;

import com.unify.rrls.domain.AdditionalFileUpload;
import com.unify.rrls.repository.AdditionalFileUploadRepository;
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
import org.springframework.util.Base64Utils;

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
 * Test class for the AdditionalFileUploadResource REST controller.
 *
 * @see AdditionalFileUploadResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResearchRepositoryLearningSystemApp.class)
public class AdditionalFileUploadResourceIntTest {

    private static final Integer DEFAULT_ID = 1;
    private static final Integer UPDATED_ID = 2;

    private static final String DEFAULT_FILE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FILE_NAME = "BBBBBBBBBB";

    private static final byte[] DEFAULT_FILE_DATA = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FILE_DATA = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_FILE_DATA_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FILE_DATA_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Instant DEFAULT_UPDATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private AdditionalFileUploadRepository additionalFileUploadRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAdditionalFileUploadMockMvc;

    private AdditionalFileUpload additionalFileUpload;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AdditionalFileUploadResource additionalFileUploadResource = new AdditionalFileUploadResource(additionalFileUploadRepository);
        this.restAdditionalFileUploadMockMvc = MockMvcBuilders.standaloneSetup(additionalFileUploadResource)
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
    public static AdditionalFileUpload createEntity(EntityManager em) {
        AdditionalFileUpload additionalFileUpload = new AdditionalFileUpload()
            .fileName(DEFAULT_FILE_NAME)
            .fileData(DEFAULT_FILE_DATA)
            .fileDataContentType(DEFAULT_FILE_DATA_CONTENT_TYPE)
            .createdBy(DEFAULT_CREATED_BY)
            .updatedBy(DEFAULT_UPDATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .updatedDate(DEFAULT_UPDATED_DATE);
        return additionalFileUpload;
    }

    @Before
    public void initTest() {
        additionalFileUpload = createEntity(em);
    }

    @Test
    @Transactional
    public void createAdditionalFileUpload() throws Exception {
        int databaseSizeBeforeCreate = additionalFileUploadRepository.findAll().size();

        // Create the AdditionalFileUpload
        restAdditionalFileUploadMockMvc.perform(post("/api/additional-file-uploads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(additionalFileUpload)))
            .andExpect(status().isCreated());

        // Validate the AdditionalFileUpload in the database
        List<AdditionalFileUpload> additionalFileUploadList = additionalFileUploadRepository.findAll();
        assertThat(additionalFileUploadList).hasSize(databaseSizeBeforeCreate + 1);
        AdditionalFileUpload testAdditionalFileUpload = additionalFileUploadList.get(additionalFileUploadList.size() - 1);
        assertThat(testAdditionalFileUpload.getId()).isEqualTo(DEFAULT_ID);
        assertThat(testAdditionalFileUpload.getFileName()).isEqualTo(DEFAULT_FILE_NAME);
        assertThat(testAdditionalFileUpload.getFileData()).isEqualTo(DEFAULT_FILE_DATA);
        assertThat(testAdditionalFileUpload.getFileDataContentType()).isEqualTo(DEFAULT_FILE_DATA_CONTENT_TYPE);
        assertThat(testAdditionalFileUpload.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testAdditionalFileUpload.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testAdditionalFileUpload.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testAdditionalFileUpload.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void createAdditionalFileUploadWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = additionalFileUploadRepository.findAll().size();

        // Create the AdditionalFileUpload with an existing ID
        additionalFileUpload.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdditionalFileUploadMockMvc.perform(post("/api/additional-file-uploads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(additionalFileUpload)))
            .andExpect(status().isBadRequest());

        // Validate the AdditionalFileUpload in the database
        List<AdditionalFileUpload> additionalFileUploadList = additionalFileUploadRepository.findAll();
        assertThat(additionalFileUploadList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCreatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = additionalFileUploadRepository.findAll().size();
        // set the field null
        additionalFileUpload.setCreatedDate(null);

        // Create the AdditionalFileUpload, which fails.

        restAdditionalFileUploadMockMvc.perform(post("/api/additional-file-uploads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(additionalFileUpload)))
            .andExpect(status().isBadRequest());

        List<AdditionalFileUpload> additionalFileUploadList = additionalFileUploadRepository.findAll();
        assertThat(additionalFileUploadList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAdditionalFileUploads() throws Exception {
        // Initialize the database
        additionalFileUploadRepository.saveAndFlush(additionalFileUpload);

        // Get all the additionalFileUploadList
        restAdditionalFileUploadMockMvc.perform(get("/api/additional-file-uploads?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(additionalFileUpload.getId().intValue())))
            .andExpect(jsonPath("$.[*].id").value(hasItem(DEFAULT_ID)))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME.toString())))
            .andExpect(jsonPath("$.[*].fileDataContentType").value(hasItem(DEFAULT_FILE_DATA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].fileData").value(hasItem(Base64Utils.encodeToString(DEFAULT_FILE_DATA))))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())));
    }

    @Test
    @Transactional
    public void getAdditionalFileUpload() throws Exception {
        // Initialize the database
        additionalFileUploadRepository.saveAndFlush(additionalFileUpload);

        // Get the additionalFileUpload
        restAdditionalFileUploadMockMvc.perform(get("/api/additional-file-uploads/{id}", additionalFileUpload.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(additionalFileUpload.getId().intValue()))
            .andExpect(jsonPath("$.id").value(DEFAULT_ID))
            .andExpect(jsonPath("$.fileName").value(DEFAULT_FILE_NAME.toString()))
            .andExpect(jsonPath("$.fileDataContentType").value(DEFAULT_FILE_DATA_CONTENT_TYPE))
            .andExpect(jsonPath("$.fileData").value(Base64Utils.encodeToString(DEFAULT_FILE_DATA)))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAdditionalFileUpload() throws Exception {
        // Get the additionalFileUpload
        restAdditionalFileUploadMockMvc.perform(get("/api/additional-file-uploads/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAdditionalFileUpload() throws Exception {
        // Initialize the database
        additionalFileUploadRepository.saveAndFlush(additionalFileUpload);
        int databaseSizeBeforeUpdate = additionalFileUploadRepository.findAll().size();

        // Update the additionalFileUpload
        AdditionalFileUpload updatedAdditionalFileUpload = additionalFileUploadRepository.findOne(additionalFileUpload.getId());
        updatedAdditionalFileUpload
            .fileName(UPDATED_FILE_NAME)
            .fileData(UPDATED_FILE_DATA)
            .fileDataContentType(UPDATED_FILE_DATA_CONTENT_TYPE)
            .createdBy(UPDATED_CREATED_BY)
            .updatedBy(UPDATED_UPDATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);

        restAdditionalFileUploadMockMvc.perform(put("/api/additional-file-uploads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAdditionalFileUpload)))
            .andExpect(status().isOk());

        // Validate the AdditionalFileUpload in the database
        List<AdditionalFileUpload> additionalFileUploadList = additionalFileUploadRepository.findAll();
        assertThat(additionalFileUploadList).hasSize(databaseSizeBeforeUpdate);
        AdditionalFileUpload testAdditionalFileUpload = additionalFileUploadList.get(additionalFileUploadList.size() - 1);
        assertThat(testAdditionalFileUpload.getId()).isEqualTo(UPDATED_ID);
        assertThat(testAdditionalFileUpload.getFileName()).isEqualTo(UPDATED_FILE_NAME);
        assertThat(testAdditionalFileUpload.getFileData()).isEqualTo(UPDATED_FILE_DATA);
        assertThat(testAdditionalFileUpload.getFileDataContentType()).isEqualTo(UPDATED_FILE_DATA_CONTENT_TYPE);
        assertThat(testAdditionalFileUpload.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testAdditionalFileUpload.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testAdditionalFileUpload.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testAdditionalFileUpload.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingAdditionalFileUpload() throws Exception {
        int databaseSizeBeforeUpdate = additionalFileUploadRepository.findAll().size();

        // Create the AdditionalFileUpload

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAdditionalFileUploadMockMvc.perform(put("/api/additional-file-uploads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(additionalFileUpload)))
            .andExpect(status().isCreated());

        // Validate the AdditionalFileUpload in the database
        List<AdditionalFileUpload> additionalFileUploadList = additionalFileUploadRepository.findAll();
        assertThat(additionalFileUploadList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAdditionalFileUpload() throws Exception {
        // Initialize the database
        additionalFileUploadRepository.saveAndFlush(additionalFileUpload);
        int databaseSizeBeforeDelete = additionalFileUploadRepository.findAll().size();

        // Get the additionalFileUpload
        restAdditionalFileUploadMockMvc.perform(delete("/api/additional-file-uploads/{id}", additionalFileUpload.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AdditionalFileUpload> additionalFileUploadList = additionalFileUploadRepository.findAll();
        assertThat(additionalFileUploadList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdditionalFileUpload.class);
        AdditionalFileUpload additionalFileUpload1 = new AdditionalFileUpload();
        additionalFileUpload1.setId(1L);
        AdditionalFileUpload additionalFileUpload2 = new AdditionalFileUpload();
        additionalFileUpload2.setId(additionalFileUpload1.getId());
        assertThat(additionalFileUpload1).isEqualTo(additionalFileUpload2);
        additionalFileUpload2.setId(2L);
        assertThat(additionalFileUpload1).isNotEqualTo(additionalFileUpload2);
        additionalFileUpload1.setId(null);
        assertThat(additionalFileUpload1).isNotEqualTo(additionalFileUpload2);
    }
}
