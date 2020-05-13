package com.github.mdaliazam.graphql.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.mdaliazam.graphql.domain.Publisher;
import com.github.mdaliazam.graphql.repository.PublisherRepository;
import com.github.mdaliazam.graphql.service.PublisherService;

/**
 * Service Implementation for managing {@link Publisher}.
 * 
 * @author <a href="mailto:softx.it@gmail.com">Mohammad Ali Azam</a>
 *
 */
@Service
@Transactional
public class PublisherServiceImpl implements PublisherService {

	private final Logger log = LoggerFactory.getLogger(PublisherServiceImpl.class);

	private final PublisherRepository publisherRepository;

	public PublisherServiceImpl(PublisherRepository publisherRepository) {
		this.publisherRepository = publisherRepository;
	}

	/**
	 * Save a publisher.
	 *
	 * @param publisher the entity to save.
	 * @return the persisted entity.
	 */
	@Override
	public Publisher save(Publisher publisher) {
		log.debug("Request to save Publisher : {}", publisher);
		return publisherRepository.save(publisher);
	}

	/**
	 * Get all the publishers.
	 *
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<Publisher> findAll(Pageable pageable) {
		log.debug("Request to get all Publishers");
		return publisherRepository.findAll(pageable);
	}

	/**
	 * Get one publisher by id.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<Publisher> findOne(Long id) {
		log.debug("Request to get Publisher : {}", id);
		return publisherRepository.findById(id);
	}

	/**
	 * Delete the publisher by id.
	 *
	 * @param id the id of the entity.
	 */
	@Override
	public void delete(Long id) {
		log.debug("Request to delete Publisher : {}", id);
		publisherRepository.deleteById(id);
	}
}
