package com.github.mdaliazam.graphql.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.mdaliazam.graphql.domain.Author;
import com.github.mdaliazam.graphql.repository.AuthorRepository;
import com.github.mdaliazam.graphql.service.AuthorService;

/**
 * Service Implementation for managing {@link Author}.
 * 
 * @author <a href="mailto:softx.it@gmail.com">Mohammad Ali Azam</a>
 *
 */
@Service
@Transactional
public class AuthorServiceImpl implements AuthorService {

	private final Logger log = LoggerFactory.getLogger(AuthorServiceImpl.class);

	private final AuthorRepository authorRepository;

	public AuthorServiceImpl(AuthorRepository authorRepository) {
		this.authorRepository = authorRepository;
	}

	/**
	 * Save a author.
	 *
	 * @param author the entity to save.
	 * @return the persisted entity.
	 */
	@Override
	public Author save(Author author) {
		log.debug("Request to save Author : {}", author);
		return authorRepository.save(author);
	}

	/**
	 * Get all the authors.
	 *
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<Author> findAll(Pageable pageable) {
		log.debug("Request to get all Authors");
		return authorRepository.findAll(pageable);
	}

	/**
	 * Get one author by id.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<Author> findOne(Long id) {
		log.debug("Request to get Author : {}", id);
		return authorRepository.findById(id);
	}

	/**
	 * Delete the author by id.
	 *
	 * @param id the id of the entity.
	 */
	@Override
	public void delete(Long id) {
		log.debug("Request to delete Author : {}", id);
		authorRepository.deleteById(id);
	}
}
