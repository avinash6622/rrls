package com.unify.rrls.web.rest;

import com.unify.rrls.ResearchRepositoryLearningSystemApp;

import com.unify.rrls.domain.FileUpload;
import com.unify.rrls.repository.FileUploadRepository;
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
 * Test class for the FileUploadResource REST controller.
 *
 * @see FileUploadResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResearchRepositoryLearningSystemApp.class)
public class FileUploadResourceIntTest {

    private static final Integer DEFAULT_ID = 1;
    private static final Integer UPDATED_ID = 2;

    private static final String DEFAULT_FILE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FILE_NAME = "BBBBBBBBBB";

    private static final byte[] DEFAULT_FILE_DATA = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FILE_DATA = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_FILE_DATA_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FILE_DATA_CONTENT_TYPE = "image/png";

    private static final Integer DEFAULT_ADD_FILE_FLAG = 1;
    private static final Integer UPDATED_ADD_FILE_FLAG = 2;

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Instant DEFAULT_UPDATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private FileUploadRepository fileUploadRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFileUploadMockMvc;

    private FileUpload fileUpload;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FileUploadResource fileUploadResource = new FileUploadResource(fileUploadRepository);
        this.restFileUploadMockMvc = MockMvcBuilders.standaloneSetup(fileUploadResource)
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
    public static FileUpload createEntity(EntityManager em) {
        FileUpload fileUpload = new FileUpload()
            .fileName(DEFAULT_FILE_NAME)
            .fileData(DEFAULT_FILE_DATA)
            .fileDataContentType(DEFAULT_FILE_DATA_CONTENT_TYPE)
            .addFileFlag(DEFAULT_ADD_FILE_FLAG)
            .createdBy(DEFAULT_CREATED_BY)
            .updatedBy(DEFAULT_UPDATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .updatedDate(DEFAULT_UPDATED_DATE);
        return fileUpload;
    }

    @Before
    public void initTest() {
        fileUpload = createEntity(em);
    }

    @Test
    @Transactional
    public void createFileUpload() throws Exception {
        int databaseSizeBeforeCreate = fileUploadRepository.findAll().size();

        // Create the FileUpload
        restFileUploadMockMvc.perform(post("/api/file-uploads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fileUpload)))
            .andExpect(status().isCreated());

        // Validate the FileUpload in the database
        List<FileUpload> fileUploadList = fileUploadRepository.findAll();
        assertThat(fileUploadList).hasSize(databaseSizeBeforeCreate + 1);
        FileUpload testFileUpload = fileUploadList.get(fileUploadList.size() - 1);
        assertThat(testFileUpload.getId()).isEqualTo(DEFAULT_ID);
        assertThat(testFileUpload.getFileName()).isEqualTo(DEFAULT_FILE_NAME);
        assertThat(testFileUpload.getFileData()).isEqualTo(DEFAULT_FILE_DATA);
        assertThat(testFileUpload.getFileDataContentType()).isEqualTo(DEFAULT_FILE_DATA_CONTENT_TYPE);
        assertThat(testFileUpload.getAddFileFlag()).isEqualTo(DEFAULT_ADD_FILE_FLAG);
        assertThat(testFileUpload.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testFileUpload.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testFileUpload.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testFileUpload.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void createFileUploadWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fileUploadRepository.findAll().size();

        // Create the FileUpload with an existing ID
        fileUpload.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFileUploadMockMvc.perform(post("/api/file-uploads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fileUpload)))
            .andExpect(status().isBadRequest());

        // Validate the FileUpload in the database
        List<FileUpload> fileUploadList = fileUploadRepository.findAll();
        assertThat(fileUploadList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCreatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = fileUploadRepository.findAll().size();
        // set the field null
        fileUpload.setCreatedDate(null);

        // Create the FileUpload, which fails.

        restFileUploadMockMvc.perform(post("/api/file-uploads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fileUpload)))
            .andExpect(status().isBadRequest());

        List<FileUpload> fileUploadList = fileUploadRepository.findAll();
        assertThat(fileUploadList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFileUploads() throws Exception {
        // Initialize the database
        fileUploadRepository.saveAndFlush(fileUpload);

        // Get all the fileUploadList
        restFileUploadMockMvc.perform(get("/api/file-uploads?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fileUpload.getId().intValue())))
            .andExpect(jsonPath("$.[*].id").value(hasItem(DEFAULT_ID)))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME.toString())))
            .andExpect(jsonPath("$.[*].fileDataContentType").value(hasItem(DEFAULT_FILE_DATA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].fileData").value(hasItem(Base64Utils.encodeToString(DEFAULT_FILE_DATA))))
            .andExpect(jsonPath("$.[*].addFileFlag").value(hasItem(DEFAULT_ADD_FILE_FLAG)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())));
    }

    @Test
    @Transactional
    public void getFileUpload() throws Exception {
        // Initialize the database
        fileUploadRepository.saveAndFlush(fileUpload);

        // Get the fileUpload
        restFileUploadMockMvc.perform(get("/api/file-uploads/{id}", fileUpload.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(fileUpload.getId().intValue()))
            .andExpect(jsonPath("$.id").value(DEFAULT_ID))
            .andExpect(jsonPath("$.fileName").value(DEFAULT_FILE_NAME.toString()))
            .andExpect(jsonPath("$.fileDataContentType").value(DEFAULT_FILE_DATA_CONTENT_TYPE))
            .andExpect(jsonPath("$.fileData").value(Base64Utils.encodeToString(DEFAULT_FILE_DATA)))
            .andExpect(jsonPath("$.addFileFlag").value(DEFAULT_ADD_FILE_FLAG))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFileUpload() throws Exception {
        // Get the fileUpload
        restFileUploadMockMvc.perform(get("/api/file-uploads/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFileUpload() throws Exception {
        // Initialize the database
        fileUploadRepository.saveAndFlush(fileUpload);
        int databaseSizeBeforeUpdate = fileUploadRepository.findAll().size();

        // Update the fileUpload
        FileUpload updatedFileUpload = fileUploadRepository.findOne(fileUpload.getId());
        updatedFileUpload
            .fileName(UPDATED_FILE_NAME)
            .fileData(UPDATED_FILE_DATA)
            .fileDataContentType(UPDATED_FILE_DATA_CONTENT_TYPE)
            .addFileFlag(UPDATED_ADD_FILE_FLAG)
            .createdBy(UPDATED_CREATED_BY)
            .updatedBy(UPDATED_UPDATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);

        restFileUploadMockMvc.perform(put("/api/file-uploads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFileUpload)))
            .andExpect(status().isOk());

        // Validate the FileUpload in the database
        List<FileUpload> fileUploadList = fileUploadRepository.findAll();
        assertThat(fileUploadList).hasSize(databaseSizeBeforeUpdate);
        FileUpload testFileUpload = fileUploadList.get(fileUploadList.size() - 1);
        assertThat(testFileUpload.getId()).isEqualTo(UPDATED_ID);
        assertThat(testFileUpload.getFileName()).isEqualTo(UPDATED_FILE_NAME);
        assertThat(testFileUpload.getFileData()).isEqualTo(UPDATED_FILE_DATA);
        assertThat(testFileUpload.getFileDataContentType()).isEqualTo(UPDATED_FILE_DATA_CONTENT_TYPE);
        assertThat(testFileUpload.getAddFileFlag()).isEqualTo(UPDATED_ADD_FILE_FLAG);
        assertThat(testFileUpload.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testFileUpload.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testFileUpload.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testFileUpload.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingFileUpload() throws Exception {
        int databaseSizeBeforeUpdate = fileUploadRepository.findAll().size();

        // Create the FileUpload

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFileUploadMockMvc.perform(put("/api/file-uploads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fileUpload)))
            .andExpect(status().isCreated());

        // Validate the FileUpload in the database
        List<FileUpload> fileUploadList = fileUploadRepository.findAll();
        assertThat(fileUploadList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFileUpload() throws Exception {
        // Initialize the database
        fileUploadRepository.saveAndFlush(fileUpload);
        int databaseSizeBeforeDelete = fileUploadRepository.findAll().size();

        // Get the fileUpload
        restFileUploadMockMvc.perform(delete("/api/file-uploads/{id}", fileUpload.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FileUpload> fileUploadList = fileUploadRepository.findAll();
        assertThat(fileUploadList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FileUpload.class);
        FileUpload fileUpload1 = new FileUpload();
        fileUpload1.setId(1L);
        FileUpload fileUpload2 = new FileUpload();
        fileUpload2.setId(fileUpload1.getId());
        assertThat(fileUpload1).isEqualTo(fileUpload2);
        fileUpload2.setId(2L);
        assertThat(fileUpload1).isNotEqualTo(fileUpload2);
        fileUpload1.setId(null);
        assertThat(fileUpload1).isNotEqualTo(fileUpload2);
    }
}
