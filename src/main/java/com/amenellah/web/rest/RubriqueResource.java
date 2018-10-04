package com.amenellah.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.amenellah.domain.Rubrique;
import com.amenellah.repository.RubriqueRepository;
import com.amenellah.web.rest.errors.BadRequestAlertException;
import com.amenellah.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Rubrique.
 */
@RestController
@RequestMapping("/api")
public class RubriqueResource {

    private final Logger log = LoggerFactory.getLogger(RubriqueResource.class);

    private static final String ENTITY_NAME = "rubrique";

    private final RubriqueRepository rubriqueRepository;

    public RubriqueResource(RubriqueRepository rubriqueRepository) {
        this.rubriqueRepository = rubriqueRepository;
    }

    /**
     * POST  /rubriques : Create a new rubrique.
     *
     * @param rubrique the rubrique to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rubrique, or with status 400 (Bad Request) if the rubrique has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rubriques")
    @Timed
    public ResponseEntity<Rubrique> createRubrique(@Valid @RequestBody Rubrique rubrique) throws URISyntaxException {
        log.debug("REST request to save Rubrique : {}", rubrique);
        if (rubrique.getId() != null) {
            throw new BadRequestAlertException("A new rubrique cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Rubrique result = rubriqueRepository.save(rubrique);
        return ResponseEntity.created(new URI("/api/rubriques/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rubriques : Updates an existing rubrique.
     *
     * @param rubrique the rubrique to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rubrique,
     * or with status 400 (Bad Request) if the rubrique is not valid,
     * or with status 500 (Internal Server Error) if the rubrique couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rubriques")
    @Timed
    public ResponseEntity<Rubrique> updateRubrique(@Valid @RequestBody Rubrique rubrique) throws URISyntaxException {
        log.debug("REST request to update Rubrique : {}", rubrique);
        if (rubrique.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Rubrique result = rubriqueRepository.save(rubrique);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rubrique.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rubriques : get all the rubriques.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of rubriques in body
     */
    @GetMapping("/rubriques")
    @Timed
    public List<Rubrique> getAllRubriques(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Rubriques");
        return rubriqueRepository.findAllWithEagerRelationships();
    }

    /**
     * GET  /rubriques/:id : get the "id" rubrique.
     *
     * @param id the id of the rubrique to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rubrique, or with status 404 (Not Found)
     */
    @GetMapping("/rubriques/{id}")
    @Timed
    public ResponseEntity<Rubrique> getRubrique(@PathVariable Long id) {
        log.debug("REST request to get Rubrique : {}", id);
        Optional<Rubrique> rubrique = rubriqueRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(rubrique);
    }

    /**
     * DELETE  /rubriques/:id : delete the "id" rubrique.
     *
     * @param id the id of the rubrique to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rubriques/{id}")
    @Timed
    public ResponseEntity<Void> deleteRubrique(@PathVariable Long id) {
        log.debug("REST request to delete Rubrique : {}", id);

        rubriqueRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
