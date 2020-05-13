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

import com.github.mdaliazam.graphql.domain.Author;
import com.github.mdaliazam.graphql.service.AuthorService;

/**
 * REST controller for managing
 * {@link com.github.mdaliazam.graphql.domain.Author}.
 * 
 * @author <a href="mailto:softx.it@gmail.com">Mohammad Ali Azam</a>
 *
 */
@RestController
@RequestMapping("/api")
public class AuthorResource {

	private final Logger log = LoggerFactory.getLogger(AuthorResource.class);

	private final AuthorService authorService;

	public AuthorResource(AuthorService authorService) {
		this.authorService = authorService;
	}

	/**
	 * {@code POST  /authors} : Create a new author.
	 *
	 * @param author the author to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new author, or with status {@code 417 (Expectation Failed)}
	 *         if the author has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/authors")
	public ResponseEntity<Author> createAuthor(@Valid @RequestBody Author author) throws URISyntaxException {
		log.debug("REST request to save Author : {}", author);
		if (author.getId() != null) {
			throw new URISyntaxException("A new Author must not have an ID", "PRIMARY KEY will be set by application");
		}

		try {
			Author result = authorService.save(author);
			return new ResponseEntity<>(result, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
		}
	}

	/**
	 * {@code PUT  /authors} : Updates an existing author.
	 *
	 * @param author the author to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated author, or with status {@code 417 (Expectation Failed)}
	 *         if the author is not valid.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/authors")
	public ResponseEntity<Author> updateAuthor(@Valid @RequestBody Author author) throws URISyntaxException {
		log.debug("REST request to update Author : {}", author);
		if (author.getId() == null) {
			throw new ApiException("Author must have an ID to update", "PRIMARY KEY is required to udpate an entity");
		}

		try {
			Author result = authorService.save(author);
			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
		}
	}

	/**
	 * {@code GET  /authors} : get all the authors.
	 *
	 * @param pageable the pagination information.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of authors in body.
	 */
	@GetMapping("/authors")
	public ResponseEntity<List<Author>> getAllAuthors(Pageable pageable) {
		log.debug("REST request to get a page of Authors");
		Page<Author> page = authorService.findAll(pageable);
		return ResponseEntity.ok().body(page.getContent());
	}

	/**
	 * {@code GET  /authors/:id} : get the "id" author.
	 *
	 * @param id the id of the author to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the author, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/authors/{id}")
	public ResponseEntity<Author> getAuthor(@PathVariable Long id) {
		log.debug("REST request to get Author : {}", id);
		Optional<Author> author = authorService.findOne(id);

		if (author.isPresent()) {
			return new ResponseEntity<>(author.get(), HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}

	/**
	 * {@code DELETE  /authors/:id} : delete the "id" author.
	 *
	 * @param id the id of the author to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}, or
	 *         with status {@code 417 (Expectation Failed)} if the operation failed.
	 */
	@DeleteMapping("/authors/{id}")
	public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
		log.debug("REST request to delete Author : {}", id);

		try {
			authorService.delete(id);
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.EXPECTATION_FAILED);
		}
	}
}
