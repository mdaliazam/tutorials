package com.github.mdaliazam.graphql.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.github.mdaliazam.graphql.domain.Author;

/**
 * Service Interface for managing {@link Author}.
 * 
 * @author <a href="mailto:softx.it@gmail.com">Mohammad Ali Azam</a>
 *
 */
public interface AuthorService {

	/**
	 * Save a author.
	 *
	 * @param author the entity to save.
	 * @return the persisted entity.
	 */
	Author save(Author author);

	/**
	 * Get all the authors.
	 *
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	Page<Author> findAll(Pageable pageable);

	/**
	 * Get the "id" author.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	Optional<Author> findOne(Long id);

	/**
	 * Delete the "id" author.
	 *
	 * @param id the id of the entity.
	 */
	void delete(Long id);
}
