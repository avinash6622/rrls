package com.unify.rrls.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.unify.rrls.domain.FileUploadComments;

import com.unify.rrls.repository.FileUploadCommentsRepository;
import com.unify.rrls.web.rest.util.HeaderUtil;
import com.unify.rrls.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
 * REST controller for managing FileUploadComments.
 */
@RestController
@RequestMapping("/api")
public class FileUploadCommentsResource {

    private final Logger log = LoggerFactory.getLogger(FileUploadCommentsResource.class);

    private static final String ENTITY_NAME = "fileUploadComments";

    private final FileUploadCommentsRepository fileUploadCommentsRepository;

    public FileUploadCommentsResource(FileUploadCommentsRepository fileUploadCommentsRepository) {
        this.fileUploadCommentsRepository = fileUploadCommentsRepository;
    }

    @Autowired
    NotificationServiceResource notificationServiceResource;

    @Autowired

    UserResource userResource;

    /**
     * POST  /file-upload-comments : Create a new fileUploadComments.
     *
     * @param fileUploadComments the fileUploadComments to create
     * @return the ResponseEntity with status 201 (Created) and with body the new fileUploadComments, or with status 400 (Bad Request) if the fileUploadComments has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/file-upload-comments")
    @Timed
    public ResponseEntity<FileUploadComments> createFileUploadComments(@RequestBody FileUploadComments fileUploadComments) throws URISyntaxException {
        log.debug("REST request to save FileUploadComments : {}", fileUploadComments);
        if (fileUploadComments.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new fileUploadComments cannot already have an ID")).body(null);
        }
        FileUploadComments result = fileUploadCommentsRepository.save(fileUploadComments);


        String name = result.getOpportunityMaster().getMasterName().getOppName();
        String page = "Opportunity";

       Long id =  userResource.getUserId(result.getCreatedBy());


       /* notificationServiceResource.notificationHistorysave(name,result.getCreatedBy(),result.getLastModifiedBy(),result.getCreatedDate(),"commented",page,result.getOpportunityComments(),id,result.getOpportunityMaster().getId());*/


        return ResponseEntity.created(new URI("/api/file-upload-comments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /file-upload-comments : Updates an existing fileUploadComments.
     *
     * @param fileUploadComments the fileUploadComments to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated fileUploadComments,
     * or with status 400 (Bad Request) if the fileUploadComments is not valid,
     * or with status 500 (Internal Server Error) if the fileUploadComments couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/file-upload-comments")
    @Timed
    public ResponseEntity<FileUploadComments> updateFileUploadComments(@Valid @RequestBody FileUploadComments fileUploadComments) throws URISyntaxException {
        log.debug("REST request to update FileUploadComments : {}", fileUploadComments);
        if (fileUploadComments.getId() == null) {
            return createFileUploadComments(fileUploadComments);
        }
        FileUploadComments result = fileUploadCommentsRepository.save(fileUploadComments);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, fileUploadComments.getId().toString()))
            .body(result);
    }

    /**
     * GET  /file-upload-comments : get all the fileUploadComments.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of fileUploadComments in body
     */
    @GetMapping("/file-upload-comments")
    @Timed
    public ResponseEntity<List<FileUploadComments>> getAllFileUploadComments(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of FileUploadComments");
        Page<FileUploadComments> page = fileUploadCommentsRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/file-upload-comments");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /file-upload-comments/:id : get the "id" fileUploadComments.
     *
     * @param id the id of the fileUploadComments to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the fileUploadComments, or with status 404 (Not Found)
     */
    @GetMapping("/file-upload-comments/{id}")
    @Timed
    public ResponseEntity<FileUploadComments> getFileUploadComments(@PathVariable Long id) {
        log.debug("REST request to get FileUploadComments : {}", id);
        FileUploadComments fileUploadComments = fileUploadCommentsRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(fileUploadComments));
    }

    /**
     * DELETE  /file-upload-comments/:id : delete the "id" fileUploadComments.
     *
     * @param id the id of the fileUploadComments to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/file-upload-comments/{id}")
    @Timed
    public ResponseEntity<Void> deleteFileUploadComments(@PathVariable Long id) {
        log.debug("REST request to delete FileUploadComments : {}", id);
        fileUploadCommentsRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
