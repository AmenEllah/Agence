package com.amenellah.web.rest;

import com.amenellah.AgenceApp;

import com.amenellah.domain.Lieu;
import com.amenellah.domain.Image;
import com.amenellah.repository.LieuRepository;
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
 * Test class for the LieuResource REST controller.
 *
 * @see LieuResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AgenceApp.class)
public class LieuResourceIntTest {

    private static final String DEFAULT_LIBELE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELE = "BBBBBBBBBB";

    @Autowired
    private LieuRepository lieuRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLieuMockMvc;

    private Lieu lieu;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LieuResource lieuResource = new LieuResource(lieuRepository);
        this.restLieuMockMvc = MockMvcBuilders.standaloneSetup(lieuResource)
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
    public static Lieu createEntity(EntityManager em) {
        Lieu lieu = new Lieu()
            .libele(DEFAULT_LIBELE);
        // Add required entity
        Image image = ImageResourceIntTest.createEntity(em);
        em.persist(image);
        em.flush();
        lieu.getImages().add(image);
        return lieu;
    }

    @Before
    public void initTest() {
        lieu = createEntity(em);
    }

    @Test
    @Transactional
    public void createLieu() throws Exception {
        int databaseSizeBeforeCreate = lieuRepository.findAll().size();

        // Create the Lieu
        restLieuMockMvc.perform(post("/api/lieus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lieu)))
            .andExpect(status().isCreated());

        // Validate the Lieu in the database
        List<Lieu> lieuList = lieuRepository.findAll();
        assertThat(lieuList).hasSize(databaseSizeBeforeCreate + 1);
        Lieu testLieu = lieuList.get(lieuList.size() - 1);
        assertThat(testLieu.getLibele()).isEqualTo(DEFAULT_LIBELE);
    }

    @Test
    @Transactional
    public void createLieuWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = lieuRepository.findAll().size();

        // Create the Lieu with an existing ID
        lieu.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLieuMockMvc.perform(post("/api/lieus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lieu)))
            .andExpect(status().isBadRequest());

        // Validate the Lieu in the database
        List<Lieu> lieuList = lieuRepository.findAll();
        assertThat(lieuList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllLieus() throws Exception {
        // Initialize the database
        lieuRepository.saveAndFlush(lieu);

        // Get all the lieuList
        restLieuMockMvc.perform(get("/api/lieus?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lieu.getId().intValue())))
            .andExpect(jsonPath("$.[*].libele").value(hasItem(DEFAULT_LIBELE.toString())));
    }
    

    @Test
    @Transactional
    public void getLieu() throws Exception {
        // Initialize the database
        lieuRepository.saveAndFlush(lieu);

        // Get the lieu
        restLieuMockMvc.perform(get("/api/lieus/{id}", lieu.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(lieu.getId().intValue()))
            .andExpect(jsonPath("$.libele").value(DEFAULT_LIBELE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingLieu() throws Exception {
        // Get the lieu
        restLieuMockMvc.perform(get("/api/lieus/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLieu() throws Exception {
        // Initialize the database
        lieuRepository.saveAndFlush(lieu);

        int databaseSizeBeforeUpdate = lieuRepository.findAll().size();

        // Update the lieu
        Lieu updatedLieu = lieuRepository.findById(lieu.getId()).get();
        // Disconnect from session so that the updates on updatedLieu are not directly saved in db
        em.detach(updatedLieu);
        updatedLieu
            .libele(UPDATED_LIBELE);

        restLieuMockMvc.perform(put("/api/lieus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLieu)))
            .andExpect(status().isOk());

        // Validate the Lieu in the database
        List<Lieu> lieuList = lieuRepository.findAll();
        assertThat(lieuList).hasSize(databaseSizeBeforeUpdate);
        Lieu testLieu = lieuList.get(lieuList.size() - 1);
        assertThat(testLieu.getLibele()).isEqualTo(UPDATED_LIBELE);
    }

    @Test
    @Transactional
    public void updateNonExistingLieu() throws Exception {
        int databaseSizeBeforeUpdate = lieuRepository.findAll().size();

        // Create the Lieu

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restLieuMockMvc.perform(put("/api/lieus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lieu)))
            .andExpect(status().isBadRequest());

        // Validate the Lieu in the database
        List<Lieu> lieuList = lieuRepository.findAll();
        assertThat(lieuList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLieu() throws Exception {
        // Initialize the database
        lieuRepository.saveAndFlush(lieu);

        int databaseSizeBeforeDelete = lieuRepository.findAll().size();

        // Get the lieu
        restLieuMockMvc.perform(delete("/api/lieus/{id}", lieu.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Lieu> lieuList = lieuRepository.findAll();
        assertThat(lieuList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Lieu.class);
        Lieu lieu1 = new Lieu();
        lieu1.setId(1L);
        Lieu lieu2 = new Lieu();
        lieu2.setId(lieu1.getId());
        assertThat(lieu1).isEqualTo(lieu2);
        lieu2.setId(2L);
        assertThat(lieu1).isNotEqualTo(lieu2);
        lieu1.setId(null);
        assertThat(lieu1).isNotEqualTo(lieu2);
    }
}
