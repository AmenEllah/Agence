package com.amenellah.web.rest;

import com.amenellah.AgenceApp;

import com.amenellah.domain.Hebergement;
import com.amenellah.repository.HebergementRepository;
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
 * Test class for the HebergementResource REST controller.
 *
 * @see HebergementResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AgenceApp.class)
public class HebergementResourceIntTest {

    private static final String DEFAULT_LIBELE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELE = "BBBBBBBBBB";

    private static final Integer DEFAULT_VALEUR = 1;
    private static final Integer UPDATED_VALEUR = 2;

    @Autowired
    private HebergementRepository hebergementRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restHebergementMockMvc;

    private Hebergement hebergement;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final HebergementResource hebergementResource = new HebergementResource(hebergementRepository);
        this.restHebergementMockMvc = MockMvcBuilders.standaloneSetup(hebergementResource)
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
    public static Hebergement createEntity(EntityManager em) {
        Hebergement hebergement = new Hebergement()
            .libele(DEFAULT_LIBELE)
            .valeur(DEFAULT_VALEUR);
        return hebergement;
    }

    @Before
    public void initTest() {
        hebergement = createEntity(em);
    }

    @Test
    @Transactional
    public void createHebergement() throws Exception {
        int databaseSizeBeforeCreate = hebergementRepository.findAll().size();

        // Create the Hebergement
        restHebergementMockMvc.perform(post("/api/hebergements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hebergement)))
            .andExpect(status().isCreated());

        // Validate the Hebergement in the database
        List<Hebergement> hebergementList = hebergementRepository.findAll();
        assertThat(hebergementList).hasSize(databaseSizeBeforeCreate + 1);
        Hebergement testHebergement = hebergementList.get(hebergementList.size() - 1);
        assertThat(testHebergement.getLibele()).isEqualTo(DEFAULT_LIBELE);
        assertThat(testHebergement.getValeur()).isEqualTo(DEFAULT_VALEUR);
    }

    @Test
    @Transactional
    public void createHebergementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = hebergementRepository.findAll().size();

        // Create the Hebergement with an existing ID
        hebergement.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHebergementMockMvc.perform(post("/api/hebergements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hebergement)))
            .andExpect(status().isBadRequest());

        // Validate the Hebergement in the database
        List<Hebergement> hebergementList = hebergementRepository.findAll();
        assertThat(hebergementList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllHebergements() throws Exception {
        // Initialize the database
        hebergementRepository.saveAndFlush(hebergement);

        // Get all the hebergementList
        restHebergementMockMvc.perform(get("/api/hebergements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hebergement.getId().intValue())))
            .andExpect(jsonPath("$.[*].libele").value(hasItem(DEFAULT_LIBELE.toString())))
            .andExpect(jsonPath("$.[*].valeur").value(hasItem(DEFAULT_VALEUR)));
    }
    

    @Test
    @Transactional
    public void getHebergement() throws Exception {
        // Initialize the database
        hebergementRepository.saveAndFlush(hebergement);

        // Get the hebergement
        restHebergementMockMvc.perform(get("/api/hebergements/{id}", hebergement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(hebergement.getId().intValue()))
            .andExpect(jsonPath("$.libele").value(DEFAULT_LIBELE.toString()))
            .andExpect(jsonPath("$.valeur").value(DEFAULT_VALEUR));
    }
    @Test
    @Transactional
    public void getNonExistingHebergement() throws Exception {
        // Get the hebergement
        restHebergementMockMvc.perform(get("/api/hebergements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHebergement() throws Exception {
        // Initialize the database
        hebergementRepository.saveAndFlush(hebergement);

        int databaseSizeBeforeUpdate = hebergementRepository.findAll().size();

        // Update the hebergement
        Hebergement updatedHebergement = hebergementRepository.findById(hebergement.getId()).get();
        // Disconnect from session so that the updates on updatedHebergement are not directly saved in db
        em.detach(updatedHebergement);
        updatedHebergement
            .libele(UPDATED_LIBELE)
            .valeur(UPDATED_VALEUR);

        restHebergementMockMvc.perform(put("/api/hebergements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedHebergement)))
            .andExpect(status().isOk());

        // Validate the Hebergement in the database
        List<Hebergement> hebergementList = hebergementRepository.findAll();
        assertThat(hebergementList).hasSize(databaseSizeBeforeUpdate);
        Hebergement testHebergement = hebergementList.get(hebergementList.size() - 1);
        assertThat(testHebergement.getLibele()).isEqualTo(UPDATED_LIBELE);
        assertThat(testHebergement.getValeur()).isEqualTo(UPDATED_VALEUR);
    }

    @Test
    @Transactional
    public void updateNonExistingHebergement() throws Exception {
        int databaseSizeBeforeUpdate = hebergementRepository.findAll().size();

        // Create the Hebergement

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restHebergementMockMvc.perform(put("/api/hebergements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hebergement)))
            .andExpect(status().isBadRequest());

        // Validate the Hebergement in the database
        List<Hebergement> hebergementList = hebergementRepository.findAll();
        assertThat(hebergementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteHebergement() throws Exception {
        // Initialize the database
        hebergementRepository.saveAndFlush(hebergement);

        int databaseSizeBeforeDelete = hebergementRepository.findAll().size();

        // Get the hebergement
        restHebergementMockMvc.perform(delete("/api/hebergements/{id}", hebergement.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Hebergement> hebergementList = hebergementRepository.findAll();
        assertThat(hebergementList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Hebergement.class);
        Hebergement hebergement1 = new Hebergement();
        hebergement1.setId(1L);
        Hebergement hebergement2 = new Hebergement();
        hebergement2.setId(hebergement1.getId());
        assertThat(hebergement1).isEqualTo(hebergement2);
        hebergement2.setId(2L);
        assertThat(hebergement1).isNotEqualTo(hebergement2);
        hebergement1.setId(null);
        assertThat(hebergement1).isNotEqualTo(hebergement2);
    }
}
