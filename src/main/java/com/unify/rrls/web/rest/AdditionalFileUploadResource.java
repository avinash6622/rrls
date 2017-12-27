package com.unify.rrls.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.unify.rrls.domain.AdditionalFileUpload;

import com.unify.rrls.repository.AdditionalFileUploadRepository;
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
 * REST controller for managing AdditionalFileUpload.
 */
@RestController
@RequestMapping("/api")
public class AdditionalFileUploadResource {

    private final Logger log = LoggerFactory.getLogger(AdditionalFileUploadResource.class);

    private static final String ENTITY_NAME = "additionalFileUpload";

    private final AdditionalFileUploadRepository additionalFileUploadRepository;

    public AdditionalFileUploadResource(AdditionalFileUploadRepository additionalFileUploadRepository) {
        this.additionalFileUploadRepository = additionalFileUploadRepository;
    }

    /**
     * POST  /additional-file-uploads : Create a new additionalFileUpload.
     *
     * @param additionalFileUpload the additionalFileUpload to create
     * @return the ResponseEntity with status 201 (Created) and with body the new additionalFileUpload, or with status 400 (Bad Request) if the additionalFileUpload has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/additional-file-uploads")
    @Timed
    public ResponseEntity<AdditionalFileUpload> createAdditionalFileUpload(@Valid @RequestBody AdditionalFileUpload additionalFileUpload) throws URISyntaxException {
        log.debug("REST request to save AdditionalFileUpload : {}", additionalFileUpload);
        if (additionalFileUpload.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new additionalFileUpload cannot already have an ID")).body(null);
        }
        AdditionalFileUpload result = additionalFileUploadRepository.save(additionalFileUpload);
        return ResponseEntity.created(new URI("/api/additional-file-uploads/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /additional-file-uploads : Updates an existing additionalFileUpload.
     *
     * @param additionalFileUpload the additionalFileUpload to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated additionalFileUpload,
     * or with status 400 (Bad Request) if the additionalFileUpload is not valid,
     * or with status 500 (Internal Server Error) if the additionalFileUpload couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/additional-file-uploads")
    @Timed
    public ResponseEntity<AdditionalFileUpload> updateAdditionalFileUpload(@Valid @RequestBody AdditionalFileUpload additionalFileUpload) throws URISyntaxException {
        log.debug("REST request to update AdditionalFileUpload : {}", additionalFileUpload);
        if (additionalFileUpload.getId() == null) {
            return createAdditionalFileUpload(additionalFileUpload);
        }
        AdditionalFileUpload result = additionalFileUploadRepository.save(additionalFileUpload);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, additionalFileUpload.getId().toString()))
            .body(result);
    }

    /**
     * GET  /additional-file-uploads : get all the additionalFileUploads.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of additionalFileUploads in body
     */
    @GetMapping("/additional-file-uploads")
    @Timed
    public ResponseEntity<List<AdditionalFileUpload>> getAllAdditionalFileUploads(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of AdditionalFileUploads");
        Page<AdditionalFileUpload> page = additionalFileUploadRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/additional-file-uploads");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /additional-file-uploads/:id : get the "id" additionalFileUpload.
     *
     * @param id the id of the additionalFileUpload to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the additionalFileUpload, or with status 404 (Not Found)
     */
    @GetMapping("/additional-file-uploads/{id}")
    @Timed
    public ResponseEntity<AdditionalFileUpload> getAdditionalFileUpload(@PathVariable Long id) {
        log.debug("REST request to get AdditionalFileUpload : {}", id);
        AdditionalFileUpload additionalFileUpload = additionalFileUploadRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(additionalFileUpload));
    }

    /**
     * DELETE  /additional-file-uploads/:id : delete the "id" additionalFileUpload.
     *
     * @param id the id of the additionalFileUpload to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/additional-file-uploads/{id}")
    @Timed
    public ResponseEntity<Void> deleteAdditionalFileUpload(@PathVariable Long id) {
        log.debug("REST request to delete AdditionalFileUpload : {}", id);
        additionalFileUploadRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
