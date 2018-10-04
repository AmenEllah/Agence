package com.amenellah.web.rest;

import com.amenellah.AgenceApp;

import com.amenellah.domain.Environnement;
import com.amenellah.repository.EnvironnementRepository;
import com.amenellah.web.rest.errors.ExceptionTranslator;

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


import static com.amenellah.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the EnvironnementResource REST controller.
 *
 * @see EnvironnementResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AgenceApp.class)
public class EnvironnementResourceIntTest {

    private static final String DEFAULT_LIBELE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELE = "BBBBBBBBBB";

    @Autowired
    private EnvironnementRepository environnementRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEnvironnementMockMvc;

    private Environnement environnement;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EnvironnementResource environnementResource = new EnvironnementResource(environnementRepository);
        this.restEnvironnementMockMvc = MockMvcBuilders.standaloneSetup(environnementResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Environnement createEntity(EntityManager em) {
        Environnement environnement = new Environnement()
            .libele(DEFAULT_LIBELE);
        return environnement;
    }

    @Before
    public void initTest() {
        environnement = createEntity(em);
    }

    @Test
    @Transactional
    public void createEnvironnement() throws Exception {
        int databaseSizeBeforeCreate = environnementRepository.findAll().size();

        // Create the Environnement
        restEnvironnementMockMvc.perform(post("/api/environnements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(environnement)))
            .andExpect(status().isCreated());

        // Validate the Environnement in the database
        List<Environnement> environnementList = environnementRepository.findAll();
        assertThat(environnementList).hasSize(databaseSizeBeforeCreate + 1);
        Environnement testEnvironnement = environnementList.get(environnementList.size() - 1);
        assertThat(testEnvironnement.getLibele()).isEqualTo(DEFAULT_LIBELE);
    }

    @Test
    @Transactional
    public void createEnvironnementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = environnementRepository.findAll().size();

        // Create the Environnement with an existing ID
        environnement.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEnvironnementMockMvc.perform(post("/api/environnements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(environnement)))
            .andExpect(status().isBadRequest());

        // Validate the Environnement in the database
        List<Environnement> environnementList = environnementRepository.findAll();
        assertThat(environnementList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEnvironnements() throws Exception {
        // Initialize the database
        environnementRepository.saveAndFlush(environnement);

        // Get all the environnementList
        restEnvironnementMockMvc.perform(get("/api/environnements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(environnement.getId().intValue())))
            .andExpect(jsonPath("$.[*].libele").value(hasItem(DEFAULT_LIBELE.toString())));
    }
    

    @Test
    @Transactional
    public void getEnvironnement() throws Exception {
        // Initialize the database
        environnementRepository.saveAndFlush(environnement);

        // Get the environnement
        restEnvironnementMockMvc.perform(get("/api/environnements/{id}", environnement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(environnement.getId().intValue()))
            .andExpect(jsonPath("$.libele").value(DEFAULT_LIBELE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingEnvironnement() throws Exception {
        // Get the environnement
        restEnvironnementMockMvc.perform(get("/api/environnements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEnvironnement() throws Exception {
        // Initialize the database
        environnementRepository.saveAndFlush(environnement);

        int databaseSizeBeforeUpdate = environnementRepository.findAll().size();

        // Update the environnement
        Environnement updatedEnvironnement = environnementRepository.findById(environnement.getId()).get();
        // Disconnect from session so that the updates on updatedEnvironnement are not directly saved in db
        em.detach(updatedEnvironnement);
        updatedEnvironnement
            .libele(UPDATED_LIBELE);

        restEnvironnementMockMvc.perform(put("/api/environnements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEnvironnement)))
            .andExpect(status().isOk());

        // Validate the Environnement in the database
        List<Environnement> environnementList = environnementRepository.findAll();
        assertThat(environnementList).hasSize(databaseSizeBeforeUpdate);
        Environnement testEnvironnement = environnementList.get(environnementList.size() - 1);
        assertThat(testEnvironnement.getLibele()).isEqualTo(UPDATED_LIBELE);
    }

    @Test
    @Transactional
    public void updateNonExistingEnvironnement() throws Exception {
        int databaseSizeBeforeUpdate = environnementRepository.findAll().size();

        // Create the Environnement

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restEnvironnementMockMvc.perform(put("/api/environnements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(environnement)))
            .andExpect(status().isBadRequest());

        // Validate the Environnement in the database
        List<Environnement> environnementList = environnementRepository.findAll();
        assertThat(environnementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEnvironnement() throws Exception {
        // Initialize the database
        environnementRepository.saveAndFlush(environnement);

        int databaseSizeBeforeDelete = environnementRepository.findAll().size();

        // Get the environnement
        restEnvironnementMockMvc.perform(delete("/api/environnements/{id}", environnement.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Environnement> environnementList = environnementRepository.findAll();
        assertThat(environnementList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Environnement.class);
        Environnement environnement1 = new Environnement();
        environnement1.setId(1L);
        Environnement environnement2 = new Environnement();
        environnement2.setId(environnement1.getId());
        assertThat(environnement1).isEqualTo(environnement2);
        environnement2.setId(2L);
        assertThat(environnement1).isNotEqualTo(environnement2);
        environnement1.setId(null);
        assertThat(environnement1).isNotEqualTo(environnement2);
    }
}
