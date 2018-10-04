package com.amenellah.web.rest;

import com.amenellah.AgenceApp;

import com.amenellah.domain.Rubrique;
import com.amenellah.domain.Lieu;
import com.amenellah.repository.RubriqueRepository;
import com.amenellah.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;


import static com.amenellah.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the RubriqueResource REST controller.
 *
 * @see RubriqueResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AgenceApp.class)
public class RubriqueResourceIntTest {

    private static final String DEFAULT_LIBELE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELE = "BBBBBBBBBB";

    @Autowired
    private RubriqueRepository rubriqueRepository;
    @Mock
    private RubriqueRepository rubriqueRepositoryMock;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRubriqueMockMvc;

    private Rubrique rubrique;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RubriqueResource rubriqueResource = new RubriqueResource(rubriqueRepository);
        this.restRubriqueMockMvc = MockMvcBuilders.standaloneSetup(rubriqueResource)
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
    public static Rubrique createEntity(EntityManager em) {
        Rubrique rubrique = new Rubrique()
            .libele(DEFAULT_LIBELE);
        // Add required entity
        Lieu lieu = LieuResourceIntTest.createEntity(em);
        em.persist(lieu);
        em.flush();
        rubrique.getLieus().add(lieu);
        return rubrique;
    }

    @Before
    public void initTest() {
        rubrique = createEntity(em);
    }

    @Test
    @Transactional
    public void createRubrique() throws Exception {
        int databaseSizeBeforeCreate = rubriqueRepository.findAll().size();

        // Create the Rubrique
        restRubriqueMockMvc.perform(post("/api/rubriques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rubrique)))
            .andExpect(status().isCreated());

        // Validate the Rubrique in the database
        List<Rubrique> rubriqueList = rubriqueRepository.findAll();
        assertThat(rubriqueList).hasSize(databaseSizeBeforeCreate + 1);
        Rubrique testRubrique = rubriqueList.get(rubriqueList.size() - 1);
        assertThat(testRubrique.getLibele()).isEqualTo(DEFAULT_LIBELE);
    }

    @Test
    @Transactional
    public void createRubriqueWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rubriqueRepository.findAll().size();

        // Create the Rubrique with an existing ID
        rubrique.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRubriqueMockMvc.perform(post("/api/rubriques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rubrique)))
            .andExpect(status().isBadRequest());

        // Validate the Rubrique in the database
        List<Rubrique> rubriqueList = rubriqueRepository.findAll();
        assertThat(rubriqueList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllRubriques() throws Exception {
        // Initialize the database
        rubriqueRepository.saveAndFlush(rubrique);

        // Get all the rubriqueList
        restRubriqueMockMvc.perform(get("/api/rubriques?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rubrique.getId().intValue())))
            .andExpect(jsonPath("$.[*].libele").value(hasItem(DEFAULT_LIBELE.toString())));
    }
    
    public void getAllRubriquesWithEagerRelationshipsIsEnabled() throws Exception {
        RubriqueResource rubriqueResource = new RubriqueResource(rubriqueRepositoryMock);
        when(rubriqueRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restRubriqueMockMvc = MockMvcBuilders.standaloneSetup(rubriqueResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restRubriqueMockMvc.perform(get("/api/rubriques?eagerload=true"))
        .andExpect(status().isOk());

        verify(rubriqueRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    public void getAllRubriquesWithEagerRelationshipsIsNotEnabled() throws Exception {
        RubriqueResource rubriqueResource = new RubriqueResource(rubriqueRepositoryMock);
            when(rubriqueRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restRubriqueMockMvc = MockMvcBuilders.standaloneSetup(rubriqueResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restRubriqueMockMvc.perform(get("/api/rubriques?eagerload=true"))
        .andExpect(status().isOk());

            verify(rubriqueRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getRubrique() throws Exception {
        // Initialize the database
        rubriqueRepository.saveAndFlush(rubrique);

        // Get the rubrique
        restRubriqueMockMvc.perform(get("/api/rubriques/{id}", rubrique.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(rubrique.getId().intValue()))
            .andExpect(jsonPath("$.libele").value(DEFAULT_LIBELE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingRubrique() throws Exception {
        // Get the rubrique
        restRubriqueMockMvc.perform(get("/api/rubriques/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRubrique() throws Exception {
        // Initialize the database
        rubriqueRepository.saveAndFlush(rubrique);

        int databaseSizeBeforeUpdate = rubriqueRepository.findAll().size();

        // Update the rubrique
        Rubrique updatedRubrique = rubriqueRepository.findById(rubrique.getId()).get();
        // Disconnect from session so that the updates on updatedRubrique are not directly saved in db
        em.detach(updatedRubrique);
        updatedRubrique
            .libele(UPDATED_LIBELE);

        restRubriqueMockMvc.perform(put("/api/rubriques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRubrique)))
            .andExpect(status().isOk());

        // Validate the Rubrique in the database
        List<Rubrique> rubriqueList = rubriqueRepository.findAll();
        assertThat(rubriqueList).hasSize(databaseSizeBeforeUpdate);
        Rubrique testRubrique = rubriqueList.get(rubriqueList.size() - 1);
        assertThat(testRubrique.getLibele()).isEqualTo(UPDATED_LIBELE);
    }

    @Test
    @Transactional
    public void updateNonExistingRubrique() throws Exception {
        int databaseSizeBeforeUpdate = rubriqueRepository.findAll().size();

        // Create the Rubrique

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restRubriqueMockMvc.perform(put("/api/rubriques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rubrique)))
            .andExpect(status().isBadRequest());

        // Validate the Rubrique in the database
        List<Rubrique> rubriqueList = rubriqueRepository.findAll();
        assertThat(rubriqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRubrique() throws Exception {
        // Initialize the database
        rubriqueRepository.saveAndFlush(rubrique);

        int databaseSizeBeforeDelete = rubriqueRepository.findAll().size();

        // Get the rubrique
        restRubriqueMockMvc.perform(delete("/api/rubriques/{id}", rubrique.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Rubrique> rubriqueList = rubriqueRepository.findAll();
        assertThat(rubriqueList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Rubrique.class);
        Rubrique rubrique1 = new Rubrique();
        rubrique1.setId(1L);
        Rubrique rubrique2 = new Rubrique();
        rubrique2.setId(rubrique1.getId());
        assertThat(rubrique1).isEqualTo(rubrique2);
        rubrique2.setId(2L);
        assertThat(rubrique1).isNotEqualTo(rubrique2);
        rubrique1.setId(null);
        assertThat(rubrique1).isNotEqualTo(rubrique2);
    }
}
