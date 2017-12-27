package com.unify.rrls.web.rest;

import com.unify.rrls.ResearchRepositoryLearningSystemApp;

import com.unify.rrls.domain.FileUploadComments;
import com.unify.rrls.repository.FileUploadCommentsRepository;
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
 * Test class for the FileUploadCommentsResource REST controller.
 *
 * @see FileUploadCommentsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResearchRepositoryLearningSystemApp.class)
public class FileUploadCommentsResourceIntTest {

    private static final Integer DEFAULT_ID = 1;
    private static final Integer UPDATED_ID = 2;

    private static final String DEFAULT_FILE_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_FILE_COMMENTS = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Instant DEFAULT_UPDATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private FileUploadCommentsRepository fileUploadCommentsRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFileUploadCommentsMockMvc;

    private FileUploadComments fileUploadComments;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FileUploadCommentsResource fileUploadCommentsResource = new FileUploadCommentsResource(fileUploadCommentsRepository);
        this.restFileUploadCommentsMockMvc = MockMvcBuilders.standaloneSetup(fileUploadCommentsResource)
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
    public static FileUploadComments createEntity(EntityManager em) {
        FileUploadComments fileUploadComments = new FileUploadComments()
            .fileComments(DEFAULT_FILE_COMMENTS)
            .createdBy(DEFAULT_CREATED_BY)
            .updatedBy(DEFAULT_UPDATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .updatedDate(DEFAULT_UPDATED_DATE);
        return fileUploadComments;
    }

    @Before
    public void initTest() {
        fileUploadComments = createEntity(em);
    }

    @Test
    @Transactional
    public void createFileUploadComments() throws Exception {
        int databaseSizeBeforeCreate = fileUploadCommentsRepository.findAll().size();

        // Create the FileUploadComments
        restFileUploadCommentsMockMvc.perform(post("/api/file-upload-comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fileUploadComments)))
            .andExpect(status().isCreated());

        // Validate the FileUploadComments in the database
        List<FileUploadComments> fileUploadCommentsList = fileUploadCommentsRepository.findAll();
        assertThat(fileUploadCommentsList).hasSize(databaseSizeBeforeCreate + 1);
        FileUploadComments testFileUploadComments = fileUploadCommentsList.get(fileUploadCommentsList.size() - 1);
        assertThat(testFileUploadComments.getId()).isEqualTo(DEFAULT_ID);
        assertThat(testFileUploadComments.getFileComments()).isEqualTo(DEFAULT_FILE_COMMENTS);
        assertThat(testFileUploadComments.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testFileUploadComments.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testFileUploadComments.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testFileUploadComments.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void createFileUploadCommentsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fileUploadCommentsRepository.findAll().size();

        // Create the FileUploadComments with an existing ID
        fileUploadComments.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFileUploadCommentsMockMvc.perform(post("/api/file-upload-comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fileUploadComments)))
            .andExpect(status().isBadRequest());

        // Validate the FileUploadComments in the database
        List<FileUploadComments> fileUploadCommentsList = fileUploadCommentsRepository.findAll();
        assertThat(fileUploadCommentsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCreatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = fileUploadCommentsRepository.findAll().size();
        // set the field null
        fileUploadComments.setCreatedDate(null);

        // Create the FileUploadComments, which fails.

        restFileUploadCommentsMockMvc.perform(post("/api/file-upload-comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fileUploadComments)))
            .andExpect(status().isBadRequest());

        List<FileUploadComments> fileUploadCommentsList = fileUploadCommentsRepository.findAll();
        assertThat(fileUploadCommentsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFileUploadComments() throws Exception {
        // Initialize the database
        fileUploadCommentsRepository.saveAndFlush(fileUploadComments);

        // Get all the fileUploadCommentsList
        restFileUploadCommentsMockMvc.perform(get("/api/file-upload-comments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fileUploadComments.getId().intValue())))
            .andExpect(jsonPath("$.[*].id").value(hasItem(DEFAULT_ID)))
            .andExpect(jsonPath("$.[*].fileComments").value(hasItem(DEFAULT_FILE_COMMENTS.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())));
    }

    @Test
    @Transactional
    public void getFileUploadComments() throws Exception {
        // Initialize the database
        fileUploadCommentsRepository.saveAndFlush(fileUploadComments);

        // Get the fileUploadComments
        restFileUploadCommentsMockMvc.perform(get("/api/file-upload-comments/{id}", fileUploadComments.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(fileUploadComments.getId().intValue()))
            .andExpect(jsonPath("$.id").value(DEFAULT_ID))
            .andExpect(jsonPath("$.fileComments").value(DEFAULT_FILE_COMMENTS.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFileUploadComments() throws Exception {
        // Get the fileUploadComments
        restFileUploadCommentsMockMvc.perform(get("/api/file-upload-comments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFileUploadComments() throws Exception {
        // Initialize the database
        fileUploadCommentsRepository.saveAndFlush(fileUploadComments);
        int databaseSizeBeforeUpdate = fileUploadCommentsRepository.findAll().size();

        // Update the fileUploadComments
        FileUploadComments updatedFileUploadComments = fileUploadCommentsRepository.findOne(fileUploadComments.getId());
        updatedFileUploadComments
            .fileComments(UPDATED_FILE_COMMENTS)
            .createdBy(UPDATED_CREATED_BY)
            .updatedBy(UPDATED_UPDATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);

        restFileUploadCommentsMockMvc.perform(put("/api/file-upload-comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFileUploadComments)))
            .andExpect(status().isOk());

        // Validate the FileUploadComments in the database
        List<FileUploadComments> fileUploadCommentsList = fileUploadCommentsRepository.findAll();
        assertThat(fileUploadCommentsList).hasSize(databaseSizeBeforeUpdate);
        FileUploadComments testFileUploadComments = fileUploadCommentsList.get(fileUploadCommentsList.size() - 1);
        assertThat(testFileUploadComments.getId()).isEqualTo(UPDATED_ID);
        assertThat(testFileUploadComments.getFileComments()).isEqualTo(UPDATED_FILE_COMMENTS);
        assertThat(testFileUploadComments.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testFileUploadComments.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testFileUploadComments.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testFileUploadComments.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingFileUploadComments() throws Exception {
        int databaseSizeBeforeUpdate = fileUploadCommentsRepository.findAll().size();

        // Create the FileUploadComments

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFileUploadCommentsMockMvc.perform(put("/api/file-upload-comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fileUploadComments)))
            .andExpect(status().isCreated());

        // Validate the FileUploadComments in the database
        List<FileUploadComments> fileUploadCommentsList = fileUploadCommentsRepository.findAll();
        assertThat(fileUploadCommentsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFileUploadComments() throws Exception {
        // Initialize the database
        fileUploadCommentsRepository.saveAndFlush(fileUploadComments);
        int databaseSizeBeforeDelete = fileUploadCommentsRepository.findAll().size();

        // Get the fileUploadComments
        restFileUploadCommentsMockMvc.perform(delete("/api/file-upload-comments/{id}", fileUploadComments.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FileUploadComments> fileUploadCommentsList = fileUploadCommentsRepository.findAll();
        assertThat(fileUploadCommentsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FileUploadComments.class);
        FileUploadComments fileUploadComments1 = new FileUploadComments();
        fileUploadComments1.setId(1L);
        FileUploadComments fileUploadComments2 = new FileUploadComments();
        fileUploadComments2.setId(fileUploadComments1.getId());
        assertThat(fileUploadComments1).isEqualTo(fileUploadComments2);
        fileUploadComments2.setId(2L);
        assertThat(fileUploadComments1).isNotEqualTo(fileUploadComments2);
        fileUploadComments1.setId(null);
        assertThat(fileUploadComments1).isNotEqualTo(fileUploadComments2);
    }
}
