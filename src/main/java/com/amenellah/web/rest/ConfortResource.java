package com.amenellah.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.amenellah.domain.Confort;
import com.amenellah.repository.ConfortRepository;
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
 * REST controller for managing Confort.
 */
@RestController
@RequestMapping("/api")
public class ConfortResource {

    private final Logger log = LoggerFactory.getLogger(ConfortResource.class);

    private static final String ENTITY_NAME = "confort";

    private final ConfortRepository confortRepository;

    public ConfortResource(ConfortRepository confortRepository) {
        this.confortRepository = confortRepository;
    }

    /**
     * POST  /conforts : Create a new confort.
     *
     * @param confort the confort to create
     * @return the ResponseEntity with status 201 (Created) and with body the new confort, or with status 400 (Bad Request) if the confort has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/conforts")
    @Timed
    public ResponseEntity<Confort> createConfort(@RequestBody Confort confort) throws URISyntaxException {
        log.debug("REST request to save Confort : {}", confort);
        if (confort.getId() != null) {
            throw new BadRequestAlertException("A new confort cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Confort result = confortRepository.save(confort);
        return ResponseEntity.created(new URI("/api/conforts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /conforts : Updates an existing confort.
     *
     * @param confort the confort to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated confort,
     * or with status 400 (Bad Request) if the confort is not valid,
     * or with status 500 (Internal Server Error) if the confort couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/conforts")
    @Timed
    public ResponseEntity<Confort> updateConfort(@RequestBody Confort confort) throws URISyntaxException {
        log.debug("REST request to update Confort : {}", confort);
        if (confort.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Confort result = confortRepository.save(confort);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, confort.getId().toString()))
            .body(result);
    }

    /**
     * GET  /conforts : get all the conforts.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of conforts in body
     */
    @GetMapping("/conforts")
    @Timed
    public List<Confort> getAllConforts() {
        log.debug("REST request to get all Conforts");
        return confortRepository.findAll();
    }

    /**
     * GET  /conforts/:id : get the "id" confort.
     *
     * @param id the id of the confort to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the confort, or with status 404 (Not Found)
     */
    @GetMapping("/conforts/{id}")
    @Timed
    public ResponseEntity<Confort> getConfort(@PathVariable Long id) {
        log.debug("REST request to get Confort : {}", id);
        Optional<Confort> confort = confortRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(confort);
    }

    /**
     * DELETE  /conforts/:id : delete the "id" confort.
     *
     * @param id the id of the confort to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/conforts/{id}")
    @Timed
    public ResponseEntity<Void> deleteConfort(@PathVariable Long id) {
        log.debug("REST request to delete Confort : {}", id);

        confortRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
