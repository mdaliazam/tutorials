package com.github.mdaliazam.graphql.web.rest;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.mdaliazam.graphql.domain.Publisher;
import com.github.mdaliazam.graphql.service.PublisherService;

/**
 * REST controller for managing
 * {@link com.github.mdaliazam.graphql.domain.Publisher}.
 * 
 * @author <a href="mailto:softx.it@gmail.com">Mohammad Ali Azam</a>
 *
 */
@RestController
@RequestMapping("/api")
public class PublisherResource {

	private final Logger log = LoggerFactory.getLogger(PublisherResource.class);

	private final PublisherService publisherService;

	public PublisherResource(PublisherService publisherService) {
		this.publisherService = publisherService;
	}

	/**
	 * {@code POST  /publishers} : Create a new publisher.
	 *
	 * @param publisher the publisher to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new publisher, or with status
	 *         {@code 417 (Expectation Failed)} if the publisher has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/publishers")
	public ResponseEntity<Publisher> createPublisher(@Valid @RequestBody Publisher publisher)
			throws URISyntaxException {
		log.debug("REST request to save Publisher : {}", publisher);
		if (publisher.getId() != null) {
			throw new URISyntaxException("A new Publisher must not have an ID",
					"PRIMARY KEY will be set by application");
		}

		try {
			Publisher result = publisherService.save(publisher);
			return new ResponseEntity<>(result, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
		}
	}

	/**
	 * {@code PUT  /publishers} : Updates an existing publisher.
	 *
	 * @param publisher the publisher to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated publisher, or with status
	 *         {@code 417 (Expectation Failed)} if the publisher is not valid.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/publishers")
	public ResponseEntity<Publisher> updatePublisher(@Valid @RequestBody Publisher publisher)
			throws URISyntaxException {
		log.debug("REST request to update Publisher : {}", publisher);
		if (publisher.getId() == null) {
			throw new ApiException("Publisher must have an ID to update",
					"PRIMARY KEY is required to udpate an entity");
		}

		try {
			Publisher result = publisherService.save(publisher);
			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
		}
	}

	/**
	 * {@code GET  /publishers} : get all the publishers.
	 *
	 * @param pageable the pagination information.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of publishers in body.
	 */
	@GetMapping("/publishers")
	public ResponseEntity<List<Publisher>> getAllPublishers(Pageable pageable) {
		log.debug("REST request to get a page of Publishers");
		Page<Publisher> page = publisherService.findAll(pageable);
		return ResponseEntity.ok().body(page.getContent());
	}

	/**
	 * {@code GET  /publishers/:id} : get the "id" publisher.
	 *
	 * @param id the id of the publisher to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the publisher, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/publishers/{id}")
	public ResponseEntity<Publisher> getPublisher(@PathVariable Long id) {
		log.debug("REST request to get Publisher : {}", id);
		Optional<Publisher> publisher = publisherService.findOne(id);

		if (publisher.isPresent()) {
			return new ResponseEntity<>(publisher.get(), HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}

	/**
	 * {@code DELETE  /publishers/:id} : delete the "id" publisher.
	 *
	 * @param id the id of the publisher to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}, or
	 *         with status {@code 417 (Expectation Failed)} if the operation failed.
	 */
	@DeleteMapping("/publishers/{id}")
	public ResponseEntity<Void> deletePublisher(@PathVariable Long id) {
		log.debug("REST request to delete Publisher : {}", id);

		try {
			publisherService.delete(id);
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.EXPECTATION_FAILED);
		}
	}
}
