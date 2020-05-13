package com.github.mdaliazam.graphql.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.mdaliazam.graphql.domain.Book;
import com.github.mdaliazam.graphql.repository.BookRepository;
import com.github.mdaliazam.graphql.service.BookService;

/**
 * Service Implementation for managing {@link Book}.
 * 
 * @author <a href="mailto:softx.it@gmail.com">Mohammad Ali Azam</a>
 *
 */
@Service
@Transactional
public class BookServiceImpl implements BookService {

	private final Logger log = LoggerFactory.getLogger(BookServiceImpl.class);

	private final BookRepository bookRepository;

	public BookServiceImpl(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	/**
	 * Save a book.
	 *
	 * @param book the entity to save.
	 * @return the persisted entity.
	 */
	@Override
	public Book save(Book book) {
		log.debug("Request to save Book : {}", book);
		return bookRepository.save(book);
	}

	/**
	 * Get all the books.
	 *
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<Book> findAll(Pageable pageable) {
		log.debug("Request to get all Books");
		return bookRepository.findAll(pageable);
	}

	/**
	 * Get one book by id.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<Book> findOne(Long id) {
		log.debug("Request to get Book : {}", id);
		return bookRepository.findById(id);
	}

	/**
	 * Delete the book by id.
	 *
	 * @param id the id of the entity.
	 */
	@Override
	public void delete(Long id) {
		log.debug("Request to delete Book : {}", id);
		bookRepository.deleteById(id);
	}

}
