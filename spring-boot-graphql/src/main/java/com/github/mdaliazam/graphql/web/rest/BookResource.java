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

import com.github.mdaliazam.graphql.domain.Book;
import com.github.mdaliazam.graphql.service.BookService;

/**
 * REST controller for managing
 * {@link com.github.mdaliazam.graphql.domain.Book}.
 * 
 * @author <a href="mailto:softx.it@gmail.com">Mohammad Ali Azam</a>
 *
 */
@RestController
@RequestMapping("/api")
public class BookResource {

	private final Logger log = LoggerFactory.getLogger(BookResource.class);

	private final BookService bookService;

	public BookResource(BookService bookService) {
		this.bookService = bookService;
	}

	/**
	 * {@code POST  /books} : Create a new book.
	 *
	 * @param book the book to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new book, or with status {@code 417 (Expectation Failed)} if
	 *         the book has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/books")
	public ResponseEntity<Book> createBook(@Valid @RequestBody Book book) throws URISyntaxException {
		log.debug("REST request to save Book : {}", book);

		try {
			if (book.getId() != null) {
				throw new ApiException("A new book cannot already have an ID id exists",
						"PRIMARY KEY will be set by application");
			}
			Book result = bookService.save(book);
			return new ResponseEntity<>(result, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
		}

	}

	/**
	 * {@code PUT  /books} : Updates an existing book.
	 *
	 * @param book the book to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated book, or with status {@code 417 (Expectation Failed)} if
	 *         the book is not valid.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/books")
	public ResponseEntity<Book> updateBook(@Valid @RequestBody Book book) throws URISyntaxException {
		log.debug("REST request to update Book : {}", book);

		try {
			if (book.getId() == null) {
				throw new ApiException("A book must have an ID to update",
						"PRIMARY KEY is required to udpate an entity");
			}
			Book result = bookService.save(book);
			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
		}

	}
	
	/**
	 * {@code GET  /books} : get all the books.
	 *
	 * @param pageable the pagination information.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of books in body.
	 */
	@GetMapping("/books")
	public ResponseEntity<List<Book>> getAllBooks(Pageable pageable) {
		log.debug("REST request to get a page of Books");
		Page<Book> page = bookService.findAll(pageable);
		return ResponseEntity.ok().body(page.getContent());
	}


	/**
	 * {@code GET  /books/:id} : get the "id" book.
	 *
	 * @param id the id of the book to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the book, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/books/{id}")
	public ResponseEntity<Book> getBook(@PathVariable Long id) {
		log.debug("REST request to get Book : {}", id);
		Optional<Book> book = bookService.findOne(id);
		if (book.isPresent()) {
			return new ResponseEntity<>(book.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}
	
	/**
	 * {@code DELETE  /book/:id} : delete the "id" book.
	 *
	 * @param id the id of the book to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}, or
	 *         with status {@code 417 (Expectation Failed)} if the operation failed.
	 */
	@DeleteMapping("/books/{id}")
	public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
		log.debug("REST request to delete Book : {}", id);

		try {
			bookService.delete(id);
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.EXPECTATION_FAILED);
		}
	}

}
