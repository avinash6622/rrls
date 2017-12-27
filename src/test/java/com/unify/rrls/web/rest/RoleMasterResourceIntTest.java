package com.unify.rrls.web.rest;

import com.unify.rrls.ResearchRepositoryLearningSystemApp;

import com.unify.rrls.domain.RoleMaster;
import com.unify.rrls.repository.RoleMasterRepository;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the RoleMasterResource REST controller.
 *
 * @see RoleMasterResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResearchRepositoryLearningSystemApp.class)
public class RoleMasterResourceIntTest {

    private static final String DEFAULT_ROLE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ROLE_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_ROLE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ROLE_NAME = "BBBBBBBBBB";

    @Autowired
    private RoleMasterRepository roleMasterRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRoleMasterMockMvc;

    private RoleMaster roleMaster;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RoleMasterResource roleMasterResource = new RoleMasterResource(roleMasterRepository);
        this.restRoleMasterMockMvc = MockMvcBuilders.standaloneSetup(roleMasterResource)
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
    public static RoleMaster createEntity(EntityManager em) {
        RoleMaster roleMaster = new RoleMaster()
            .roleCode(DEFAULT_ROLE_CODE)
            .roleName(DEFAULT_ROLE_NAME);
        return roleMaster;
    }

    @Before
    public void initTest() {
        roleMaster = createEntity(em);
    }

    @Test
    @Transactional
    public void createRoleMaster() throws Exception {
        int databaseSizeBeforeCreate = roleMasterRepository.findAll().size();

        // Create the RoleMaster
        restRoleMasterMockMvc.perform(post("/api/role-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roleMaster)))
            .andExpect(status().isCreated());

        // Validate the RoleMaster in the database
        List<RoleMaster> roleMasterList = roleMasterRepository.findAll();
        assertThat(roleMasterList).hasSize(databaseSizeBeforeCreate + 1);
        RoleMaster testRoleMaster = roleMasterList.get(roleMasterList.size() - 1);
        assertThat(testRoleMaster.getRoleCode()).isEqualTo(DEFAULT_ROLE_CODE);
        assertThat(testRoleMaster.getRoleName()).isEqualTo(DEFAULT_ROLE_NAME);
    }

    @Test
    @Transactional
    public void createRoleMasterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = roleMasterRepository.findAll().size();

        // Create the RoleMaster with an existing ID
        roleMaster.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRoleMasterMockMvc.perform(post("/api/role-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roleMaster)))
            .andExpect(status().isBadRequest());

        // Validate the RoleMaster in the database
        List<RoleMaster> roleMasterList = roleMasterRepository.findAll();
        assertThat(roleMasterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkRoleCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = roleMasterRepository.findAll().size();
        // set the field null
        roleMaster.setRoleCode(null);

        // Create the RoleMaster, which fails.

        restRoleMasterMockMvc.perform(post("/api/role-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roleMaster)))
            .andExpect(status().isBadRequest());

        List<RoleMaster> roleMasterList = roleMasterRepository.findAll();
        assertThat(roleMasterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRoleNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = roleMasterRepository.findAll().size();
        // set the field null
        roleMaster.setRoleName(null);

        // Create the RoleMaster, which fails.

        restRoleMasterMockMvc.perform(post("/api/role-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roleMaster)))
            .andExpect(status().isBadRequest());

        List<RoleMaster> roleMasterList = roleMasterRepository.findAll();
        assertThat(roleMasterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRoleMasters() throws Exception {
        // Initialize the database
        roleMasterRepository.saveAndFlush(roleMaster);

        // Get all the roleMasterList
        restRoleMasterMockMvc.perform(get("/api/role-masters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(roleMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].roleCode").value(hasItem(DEFAULT_ROLE_CODE.toString())))
            .andExpect(jsonPath("$.[*].roleName").value(hasItem(DEFAULT_ROLE_NAME.toString())));
    }

    @Test
    @Transactional
    public void getRoleMaster() throws Exception {
        // Initialize the database
        roleMasterRepository.saveAndFlush(roleMaster);

        // Get the roleMaster
        restRoleMasterMockMvc.perform(get("/api/role-masters/{id}", roleMaster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(roleMaster.getId().intValue()))
            .andExpect(jsonPath("$.roleCode").value(DEFAULT_ROLE_CODE.toString()))
            .andExpect(jsonPath("$.roleName").value(DEFAULT_ROLE_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRoleMaster() throws Exception {
        // Get the roleMaster
        restRoleMasterMockMvc.perform(get("/api/role-masters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRoleMaster() throws Exception {
        // Initialize the database
        roleMasterRepository.saveAndFlush(roleMaster);
        int databaseSizeBeforeUpdate = roleMasterRepository.findAll().size();

        // Update the roleMaster
        RoleMaster updatedRoleMaster = roleMasterRepository.findOne(roleMaster.getId());
        updatedRoleMaster
            .roleCode(UPDATED_ROLE_CODE)
            .roleName(UPDATED_ROLE_NAME);

        restRoleMasterMockMvc.perform(put("/api/role-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRoleMaster)))
            .andExpect(status().isOk());

        // Validate the RoleMaster in the database
        List<RoleMaster> roleMasterList = roleMasterRepository.findAll();
        assertThat(roleMasterList).hasSize(databaseSizeBeforeUpdate);
        RoleMaster testRoleMaster = roleMasterList.get(roleMasterList.size() - 1);
        assertThat(testRoleMaster.getRoleCode()).isEqualTo(UPDATED_ROLE_CODE);
        assertThat(testRoleMaster.getRoleName()).isEqualTo(UPDATED_ROLE_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingRoleMaster() throws Exception {
        int databaseSizeBeforeUpdate = roleMasterRepository.findAll().size();

        // Create the RoleMaster

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRoleMasterMockMvc.perform(put("/api/role-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roleMaster)))
            .andExpect(status().isCreated());

        // Validate the RoleMaster in the database
        List<RoleMaster> roleMasterList = roleMasterRepository.findAll();
        assertThat(roleMasterList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRoleMaster() throws Exception {
        // Initialize the database
        roleMasterRepository.saveAndFlush(roleMaster);
        int databaseSizeBeforeDelete = roleMasterRepository.findAll().size();

        // Get the roleMaster
        restRoleMasterMockMvc.perform(delete("/api/role-masters/{id}", roleMaster.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RoleMaster> roleMasterList = roleMasterRepository.findAll();
        assertThat(roleMasterList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RoleMaster.class);
        RoleMaster roleMaster1 = new RoleMaster();
        roleMaster1.setId(1L);
        RoleMaster roleMaster2 = new RoleMaster();
        roleMaster2.setId(roleMaster1.getId());
        assertThat(roleMaster1).isEqualTo(roleMaster2);
        roleMaster2.setId(2L);
        assertThat(roleMaster1).isNotEqualTo(roleMaster2);
        roleMaster1.setId(null);
        assertThat(roleMaster1).isNotEqualTo(roleMaster2);
    }
}
