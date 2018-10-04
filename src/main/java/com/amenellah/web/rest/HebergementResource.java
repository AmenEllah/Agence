package com.amenellah.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.amenellah.domain.Hebergement;
import com.amenellah.repository.HebergementRepository;
import com.amenellah.web.rest.errors.BadRequestAlertException;
import com.amenellah.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Hebergement.
 */
@RestController
@RequestMapping("/api")
public class HebergementResource {

    private final Logger log = LoggerFactory.getLogger(HebergementResource.class);

    private static final String ENTITY_NAME = "hebergement";

    private final HebergementRepository hebergementRepository;

    public HebergementResource(HebergementRepository hebergementRepository) {
        this.hebergementRepository = hebergementRepository;
    }

    /**
     * POST  /hebergements : Create a new hebergement.
     *
     * @param hebergement the hebergement to create
     * @return the ResponseEntity with status 201 (Created) and with body the new hebergement, or with status 400 (Bad Request) if the hebergement has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/hebergements")
    @Timed
    public ResponseEntity<Hebergement> createHebergement(@RequestBody Hebergement hebergement) throws URISyntaxException {
        log.debug("REST request to save Hebergement : {}", hebergement);
        if (hebergement.getId() != null) {
            throw new BadRequestAlertException("A new hebergement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Hebergement result = hebergementRepository.save(hebergement);
        return ResponseEntity.created(new URI("/api/hebergements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /hebergements : Updates an existing hebergement.
     *
     * @param hebergement the hebergement to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated hebergement,
     * or with status 400 (Bad Request) if the hebergement is not valid,
     * or with status 500 (Internal Server Error) if the hebergement couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/hebergements")
    @Timed
    public ResponseEntity<Hebergement> updateHebergement(@RequestBody Hebergement hebergement) throws URISyntaxException {
        log.debug("REST request to update Hebergement : {}", hebergement);
        if (hebergement.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Hebergement result = hebergementRepository.save(hebergement);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, hebergement.getId().toString()))
            .body(result);
    }

    /**
     * GET  /hebergements : get all the hebergements.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of hebergements in body
     */
    @GetMapping("/hebergements")
    @Timed
    public List<Hebergement> getAllHebergements() {
        log.debug("REST request to get all Hebergements");
        return hebergementRepository.findAll();
    }

    /**
     * GET  /hebergements/:id : get the "id" hebergement.
     *
     * @param id the id of the hebergement to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the hebergement, or with status 404 (Not Found)
     */
    @GetMapping("/hebergements/{id}")
    @Timed
    public ResponseEntity<Hebergement> getHebergement(@PathVariable Long id) {
        log.debug("REST request to get Hebergement : {}", id);
        Optional<Hebergement> hebergement = hebergementRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(hebergement);
    }

    /**
     * DELETE  /hebergements/:id : delete the "id" hebergement.
     *
     * @param id the id of the hebergement to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/hebergements/{id}")
    @Timed
    public ResponseEntity<Void> deleteHebergement(@PathVariable Long id) {
        log.debug("REST request to delete Hebergement : {}", id);

        hebergementRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
