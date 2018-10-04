package com.amenellah.web.rest;

import com.amenellah.AgenceApp;

import com.amenellah.domain.Voyage;
import com.amenellah.domain.Rubrique;
import com.amenellah.domain.Lieu;
import com.amenellah.domain.Lieu;
import com.amenellah.domain.Environnement;
import com.amenellah.repository.VoyageRepository;
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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static com.amenellah.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the VoyageResource REST controller.
 *
 * @see VoyageResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AgenceApp.class)
public class VoyageResourceIntTest {

    private static final LocalDate DEFAULT_DATE_DEPART = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DEPART = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_DUREE = 1;
    private static final Integer UPDATED_DUREE = 2;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private VoyageRepository voyageRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restVoyageMockMvc;

    private Voyage voyage;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VoyageResource voyageResource = new VoyageResource(voyageRepository);
        this.restVoyageMockMvc = MockMvcBuilders.standaloneSetup(voyageResource)
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
    public static Voyage createEntity(EntityManager em) {
        Voyage voyage = new Voyage()
            .dateDepart(DEFAULT_DATE_DEPART)
            .duree(DEFAULT_DUREE)
            .description(DEFAULT_DESCRIPTION);
        // Add required entity
        Rubrique rubrique = RubriqueResourceIntTest.createEntity(em);
        em.persist(rubrique);
        em.flush();
        voyage.setRubrique(rubrique);
        // Add required entity
        Lieu lieu = LieuResourceIntTest.createEntity(em);
        em.persist(lieu);
        em.flush();
        voyage.setLieuDepart(lieu);
        // Add required entity
        voyage.setLieuArrive(lieu);
        // Add required entity
        Environnement environnement = EnvironnementResourceIntTest.createEntity(em);
        em.persist(environnement);
        em.flush();
        voyage.setEnvironnement(environnement);
        return voyage;
    }

    @Before
    public void initTest() {
        voyage = createEntity(em);
    }

    @Test
    @Transactional
    public void createVoyage() throws Exception {
        int databaseSizeBeforeCreate = voyageRepository.findAll().size();

        // Create the Voyage
        restVoyageMockMvc.perform(post("/api/voyages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(voyage)))
            .andExpect(status().isCreated());

        // Validate the Voyage in the database
        List<Voyage> voyageList = voyageRepository.findAll();
        assertThat(voyageList).hasSize(databaseSizeBeforeCreate + 1);
        Voyage testVoyage = voyageList.get(voyageList.size() - 1);
        assertThat(testVoyage.getDateDepart()).isEqualTo(DEFAULT_DATE_DEPART);
        assertThat(testVoyage.getDuree()).isEqualTo(DEFAULT_DUREE);
        assertThat(testVoyage.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createVoyageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = voyageRepository.findAll().size();

        // Create the Voyage with an existing ID
        voyage.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVoyageMockMvc.perform(post("/api/voyages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(voyage)))
            .andExpect(status().isBadRequest());

        // Validate the Voyage in the database
        List<Voyage> voyageList = voyageRepository.findAll();
        assertThat(voyageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllVoyages() throws Exception {
        // Initialize the database
        voyageRepository.saveAndFlush(voyage);

        // Get all the voyageList
        restVoyageMockMvc.perform(get("/api/voyages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(voyage.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateDepart").value(hasItem(DEFAULT_DATE_DEPART.toString())))
            .andExpect(jsonPath("$.[*].duree").value(hasItem(DEFAULT_DUREE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    

    @Test
    @Transactional
    public void getVoyage() throws Exception {
        // Initialize the database
        voyageRepository.saveAndFlush(voyage);

        // Get the voyage
        restVoyageMockMvc.perform(get("/api/voyages/{id}", voyage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(voyage.getId().intValue()))
            .andExpect(jsonPath("$.dateDepart").value(DEFAULT_DATE_DEPART.toString()))
            .andExpect(jsonPath("$.duree").value(DEFAULT_DUREE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingVoyage() throws Exception {
        // Get the voyage
        restVoyageMockMvc.perform(get("/api/voyages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVoyage() throws Exception {
        // Initialize the database
        voyageRepository.saveAndFlush(voyage);

        int databaseSizeBeforeUpdate = voyageRepository.findAll().size();

        // Update the voyage
        Voyage updatedVoyage = voyageRepository.findById(voyage.getId()).get();
        // Disconnect from session so that the updates on updatedVoyage are not directly saved in db
        em.detach(updatedVoyage);
        updatedVoyage
            .dateDepart(UPDATED_DATE_DEPART)
            .duree(UPDATED_DUREE)
            .description(UPDATED_DESCRIPTION);

        restVoyageMockMvc.perform(put("/api/voyages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedVoyage)))
            .andExpect(status().isOk());

        // Validate the Voyage in the database
        List<Voyage> voyageList = voyageRepository.findAll();
        assertThat(voyageList).hasSize(databaseSizeBeforeUpdate);
        Voyage testVoyage = voyageList.get(voyageList.size() - 1);
        assertThat(testVoyage.getDateDepart()).isEqualTo(UPDATED_DATE_DEPART);
        assertThat(testVoyage.getDuree()).isEqualTo(UPDATED_DUREE);
        assertThat(testVoyage.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingVoyage() throws Exception {
        int databaseSizeBeforeUpdate = voyageRepository.findAll().size();

        // Create the Voyage

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restVoyageMockMvc.perform(put("/api/voyages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(voyage)))
            .andExpect(status().isBadRequest());

        // Validate the Voyage in the database
        List<Voyage> voyageList = voyageRepository.findAll();
        assertThat(voyageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVoyage() throws Exception {
        // Initialize the database
        voyageRepository.saveAndFlush(voyage);

        int databaseSizeBeforeDelete = voyageRepository.findAll().size();

        // Get the voyage
        restVoyageMockMvc.perform(delete("/api/voyages/{id}", voyage.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Voyage> voyageList = voyageRepository.findAll();
        assertThat(voyageList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Voyage.class);
        Voyage voyage1 = new Voyage();
        voyage1.setId(1L);
        Voyage voyage2 = new Voyage();
        voyage2.setId(voyage1.getId());
        assertThat(voyage1).isEqualTo(voyage2);
        voyage2.setId(2L);
        assertThat(voyage1).isNotEqualTo(voyage2);
        voyage1.setId(null);
        assertThat(voyage1).isNotEqualTo(voyage2);
    }
}
