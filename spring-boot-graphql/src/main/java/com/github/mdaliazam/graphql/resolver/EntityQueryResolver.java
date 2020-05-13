package com.github.mdaliazam.graphql.resolver;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.github.mdaliazam.graphql.domain.Author;
import com.github.mdaliazam.graphql.domain.Book;
import com.github.mdaliazam.graphql.domain.Publisher;
import com.github.mdaliazam.graphql.service.AuthorService;
import com.github.mdaliazam.graphql.service.BookService;
import com.github.mdaliazam.graphql.service.PublisherService;

import graphql.kickstart.tools.GraphQLQueryResolver;

/**
 * GraphQL query resolvers that delegates service methods of all the entities
 * 
 * @see PublisherService
 * @see AuthorService
 * @see BookService
 * 
 * @author <a href="mailto:softx.it@gmail.com">Mohammad Ali Azam</a>
 *
 */
@Component
public class EntityQueryResolver implements GraphQLQueryResolver {

	private final PublisherService publisherService;
	private final AuthorService authorService;
	private final BookService bookService;

	/**
	 * Constructor that takes instances of injected service bean for all entities
	 * 
	 * @param publisherService An instance of Spring managed PublisherService bean
	 * @param authorService An instance of Spring managed AuthorService bean
	 * @param bookService An instance of Spring managed BookService bean
	 */
	public EntityQueryResolver(PublisherService publisherService, AuthorService authorService,
			BookService bookService) {
		this.publisherService = publisherService;
		this.authorService = authorService;
		this.bookService = bookService;
	}

	/**
	 * Returns a Publisher for the given id
	 * 
	 * @param id Primary key of the Publisher
	 * @return An instance of Publisher
	 */
	public Publisher getPublisherById(Long id) {
		try {
			Optional<Publisher> publisher = publisherService.findOne(id);
			if (publisher.isPresent()) {
				return publisher.get();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	/**
	 * Returns an Author for the given id
	 * 
	 * @param id Primary key of the Author
	 * @return An instance of Author
	 */
	public Author getAuthorById(Long id) {
		try {
			Optional<Author> author = authorService.findOne(id);
			if (author.isPresent()) {
				author.get().getBooks();
				return author.get();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	/**
	 * Returns a Book for the given id
	 * 
	 * @param id Primary key of the Book
	 * @return An instance of Book
	 */
	public Book getBookById(Long id) {
		try {
			Optional<Book> book = bookService.findOne(id);
			if (book.isPresent()) {
				return book.get();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
}
