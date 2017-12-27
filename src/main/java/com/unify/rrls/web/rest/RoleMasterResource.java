package com.unify.rrls.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.unify.rrls.domain.RoleMaster;

import com.unify.rrls.repository.RoleMasterRepository;
import com.unify.rrls.web.rest.util.HeaderUtil;
import com.unify.rrls.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing RoleMaster.
 */
@RestController
@RequestMapping("/api")
public class RoleMasterResource {

    private final Logger log = LoggerFactory.getLogger(RoleMasterResource.class);

    private static final String ENTITY_NAME = "roleMaster";

    private final RoleMasterRepository roleMasterRepository;

    public RoleMasterResource(RoleMasterRepository roleMasterRepository) {
        this.roleMasterRepository = roleMasterRepository;
    }

    /**
     * POST  /role-masters : Create a new roleMaster.
     *
     * @param roleMaster the roleMaster to create
     * @return the ResponseEntity with status 201 (Created) and with body the new roleMaster, or with status 400 (Bad Request) if the roleMaster has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/role-masters")
    @Timed
    public ResponseEntity<RoleMaster> createRoleMaster(@Valid @RequestBody RoleMaster roleMaster) throws URISyntaxException {
        log.debug("REST request to save RoleMaster : {}", roleMaster);
        if (roleMaster.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new roleMaster cannot already have an ID")).body(null);
        }
        RoleMaster result = roleMasterRepository.save(roleMaster);
        return ResponseEntity.created(new URI("/api/role-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /role-masters : Updates an existing roleMaster.
     *
     * @param roleMaster the roleMaster to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated roleMaster,
     * or with status 400 (Bad Request) if the roleMaster is not valid,
     * or with status 500 (Internal Server Error) if the roleMaster couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/role-masters")
    @Timed
    public ResponseEntity<RoleMaster> updateRoleMaster(@Valid @RequestBody RoleMaster roleMaster) throws URISyntaxException {
        log.debug("REST request to update RoleMaster : {}", roleMaster);
        if (roleMaster.getId() == null) {
            return createRoleMaster(roleMaster);
        }
        RoleMaster result = roleMasterRepository.save(roleMaster);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, roleMaster.getId().toString()))
            .body(result);
    }

    /**
     * GET  /role-masters : get all the roleMasters.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of roleMasters in body
     */
    @GetMapping("/role-masters")
    @Timed
    public ResponseEntity<List<RoleMaster>> getAllRoleMasters(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of RoleMasters");
        Page<RoleMaster> page = roleMasterRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/role-masters");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /role-masters/:id : get the "id" roleMaster.
     *
     * @param id the id of the roleMaster to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the roleMaster, or with status 404 (Not Found)
     */
    @GetMapping("/role-masters/{id}")
    @Timed
    public ResponseEntity<RoleMaster> getRoleMaster(@PathVariable Long id) {
        log.debug("REST request to get RoleMaster : {}", id);
        RoleMaster roleMaster = roleMasterRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(roleMaster));
    }

    /**
     * DELETE  /role-masters/:id : delete the "id" roleMaster.
     *
     * @param id the id of the roleMaster to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/role-masters/{id}")
    @Timed
    public ResponseEntity<Void> deleteRoleMaster(@PathVariable Long id) {
        log.debug("REST request to delete RoleMaster : {}", id);
        roleMasterRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
