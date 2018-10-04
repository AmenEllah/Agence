package com.amenellah.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.amenellah.domain.Environnement;
import com.amenellah.repository.EnvironnementRepository;
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
 * REST controller for managing Environnement.
 */
@RestController
@RequestMapping("/api")
public class EnvironnementResource {

    private final Logger log = LoggerFactory.getLogger(EnvironnementResource.class);

    private static final String ENTITY_NAME = "environnement";

    private final EnvironnementRepository environnementRepository;

    public EnvironnementResource(EnvironnementRepository environnementRepository) {
        this.environnementRepository = environnementRepository;
    }

    /**
     * POST  /environnements : Create a new environnement.
     *
     * @param environnement the environnement to create
     * @return the ResponseEntity with status 201 (Created) and with body the new environnement, or with status 400 (Bad Request) if the environnement has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/environnements")
    @Timed
    public ResponseEntity<Environnement> createEnvironnement(@RequestBody Environnement environnement) throws URISyntaxException {
        log.debug("REST request to save Environnement : {}", environnement);
        if (environnement.getId() != null) {
            throw new BadRequestAlertException("A new environnement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Environnement result = environnementRepository.save(environnement);
        return ResponseEntity.created(new URI("/api/environnements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /environnements : Updates an existing environnement.
     *
     * @param environnement the environnement to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated environnement,
     * or with status 400 (Bad Request) if the environnement is not valid,
     * or with status 500 (Internal Server Error) if the environnement couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/environnements")
    @Timed
    public ResponseEntity<Environnement> updateEnvironnement(@RequestBody Environnement environnement) throws URISyntaxException {
        log.debug("REST request to update Environnement : {}", environnement);
        if (environnement.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Environnement result = environnementRepository.save(environnement);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, environnement.getId().toString()))
            .body(result);
    }

    /**
     * GET  /environnements : get all the environnements.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of environnements in body
     */
    @GetMapping("/environnements")
    @Timed
    public List<Environnement> getAllEnvironnements() {
        log.debug("REST request to get all Environnements");
        return environnementRepository.findAll();
    }

    /**
     * GET  /environnements/:id : get the "id" environnement.
     *
     * @param id the id of the environnement to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the environnement, or with status 404 (Not Found)
     */
    @GetMapping("/environnements/{id}")
    @Timed
    public ResponseEntity<Environnement> getEnvironnement(@PathVariable Long id) {
        log.debug("REST request to get Environnement : {}", id);
        Optional<Environnement> environnement = environnementRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(environnement);
    }

    /**
     * DELETE  /environnements/:id : delete the "id" environnement.
     *
     * @param id the id of the environnement to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/environnements/{id}")
    @Timed
    public ResponseEntity<Void> deleteEnvironnement(@PathVariable Long id) {
        log.debug("REST request to delete Environnement : {}", id);

        environnementRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
