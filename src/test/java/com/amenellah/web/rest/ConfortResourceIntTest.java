package com.amenellah.web.rest;

import com.amenellah.AgenceApp;

import com.amenellah.domain.Confort;
import com.amenellah.repository.ConfortRepository;
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
 * Test class for the ConfortResource REST controller.
 *
 * @see ConfortResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AgenceApp.class)
public class ConfortResourceIntTest {

    private static final String DEFAULT_LIBELE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELE = "BBBBBBBBBB";

    private static final Integer DEFAULT_VALEUR = 1;
    private static final Integer UPDATED_VALEUR = 2;

    @Autowired
    private ConfortRepository confortRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restConfortMockMvc;

    private Confort confort;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ConfortResource confortResource = new ConfortResource(confortRepository);
        this.restConfortMockMvc = MockMvcBuilders.standaloneSetup(confortResource)
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
    public static Confort createEntity(EntityManager em) {
        Confort confort = new Confort()
            .libele(DEFAULT_LIBELE)
            .valeur(DEFAULT_VALEUR);
        return confort;
    }

    @Before
    public void initTest() {
        confort = createEntity(em);
    }

    @Test
    @Transactional
    public void createConfort() throws Exception {
        int databaseSizeBeforeCreate = confortRepository.findAll().size();

        // Create the Confort
        restConfortMockMvc.perform(post("/api/conforts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(confort)))
            .andExpect(status().isCreated());

        // Validate the Confort in the database
        List<Confort> confortList = confortRepository.findAll();
        assertThat(confortList).hasSize(databaseSizeBeforeCreate + 1);
        Confort testConfort = confortList.get(confortList.size() - 1);
        assertThat(testConfort.getLibele()).isEqualTo(DEFAULT_LIBELE);
        assertThat(testConfort.getValeur()).isEqualTo(DEFAULT_VALEUR);
    }

    @Test
    @Transactional
    public void createConfortWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = confortRepository.findAll().size();

        // Create the Confort with an existing ID
        confort.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConfortMockMvc.perform(post("/api/conforts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(confort)))
            .andExpect(status().isBadRequest());

        // Validate the Confort in the database
        List<Confort> confortList = confortRepository.findAll();
        assertThat(confortList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllConforts() throws Exception {
        // Initialize the database
        confortRepository.saveAndFlush(confort);

        // Get all the confortList
        restConfortMockMvc.perform(get("/api/conforts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(confort.getId().intValue())))
            .andExpect(jsonPath("$.[*].libele").value(hasItem(DEFAULT_LIBELE.toString())))
            .andExpect(jsonPath("$.[*].valeur").value(hasItem(DEFAULT_VALEUR)));
    }
    

    @Test
    @Transactional
    public void getConfort() throws Exception {
        // Initialize the database
        confortRepository.saveAndFlush(confort);

        // Get the confort
        restConfortMockMvc.perform(get("/api/conforts/{id}", confort.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(confort.getId().intValue()))
            .andExpect(jsonPath("$.libele").value(DEFAULT_LIBELE.toString()))
            .andExpect(jsonPath("$.valeur").value(DEFAULT_VALEUR));
    }
    @Test
    @Transactional
    public void getNonExistingConfort() throws Exception {
        // Get the confort
        restConfortMockMvc.perform(get("/api/conforts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConfort() throws Exception {
        // Initialize the database
        confortRepository.saveAndFlush(confort);

        int databaseSizeBeforeUpdate = confortRepository.findAll().size();

        // Update the confort
        Confort updatedConfort = confortRepository.findById(confort.getId()).get();
        // Disconnect from session so that the updates on updatedConfort are not directly saved in db
        em.detach(updatedConfort);
        updatedConfort
            .libele(UPDATED_LIBELE)
            .valeur(UPDATED_VALEUR);

        restConfortMockMvc.perform(put("/api/conforts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedConfort)))
            .andExpect(status().isOk());

        // Validate the Confort in the database
        List<Confort> confortList = confortRepository.findAll();
        assertThat(confortList).hasSize(databaseSizeBeforeUpdate);
        Confort testConfort = confortList.get(confortList.size() - 1);
        assertThat(testConfort.getLibele()).isEqualTo(UPDATED_LIBELE);
        assertThat(testConfort.getValeur()).isEqualTo(UPDATED_VALEUR);
    }

    @Test
    @Transactional
    public void updateNonExistingConfort() throws Exception {
        int databaseSizeBeforeUpdate = confortRepository.findAll().size();

        // Create the Confort

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restConfortMockMvc.perform(put("/api/conforts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(confort)))
            .andExpect(status().isBadRequest());

        // Validate the Confort in the database
        List<Confort> confortList = confortRepository.findAll();
        assertThat(confortList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteConfort() throws Exception {
        // Initialize the database
        confortRepository.saveAndFlush(confort);

        int databaseSizeBeforeDelete = confortRepository.findAll().size();

        // Get the confort
        restConfortMockMvc.perform(delete("/api/conforts/{id}", confort.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Confort> confortList = confortRepository.findAll();
        assertThat(confortList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Confort.class);
        Confort confort1 = new Confort();
        confort1.setId(1L);
        Confort confort2 = new Confort();
        confort2.setId(confort1.getId());
        assertThat(confort1).isEqualTo(confort2);
        confort2.setId(2L);
        assertThat(confort1).isNotEqualTo(confort2);
        confort1.setId(null);
        assertThat(confort1).isNotEqualTo(confort2);
    }
}
