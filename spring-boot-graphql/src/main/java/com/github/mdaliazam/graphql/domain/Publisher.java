package com.github.mdaliazam.graphql.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * A very simple Publisher entity
 * 
 * @author <a href="mailto:softx.it@gmail.com">Mohammad Ali Azam</a>
 *
 */
@Entity
@Table(name = "publisher")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Publisher implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
	@SequenceGenerator(name = "sequenceGenerator")
	private Long id;

	@NotNull
	@Column(name = "name", nullable = false)
	private String name;

	@OneToMany(mappedBy = "publisher", fetch = FetchType.EAGER)
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	@JsonManagedReference(value="pub-books")
	private Set<Book> books = new HashSet<>();

	@OneToMany(mappedBy = "publisher", fetch = FetchType.EAGER)
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	@JsonManagedReference(value="pub-author")
	private Set<Author> authors = new HashSet<>();

	/**
	 * No-arg constructor
	 */
	public Publisher() {
		super();
	}

	/**
	 * Constructor that takes name of it
	 * 
	 * @param name
	 */
	public Publisher(@NotNull String name) {
		super();
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public Publisher name(String name) {
		this.name = name;
		return this;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Book> getBooks() {
		return books;
	}

	public Publisher books(Set<Book> books) {
		this.books = books;
		return this;
	}

	public Publisher addBooks(Book book) {
		this.books.add(book);
		book.setPublisher(this);
		return this;
	}

	public Publisher removeBooks(Book book) {
		this.books.remove(book);
		book.setPublisher(null);
		return this;
	}

	public void setBooks(Set<Book> books) {
		this.books = books;
	}

	public Set<Author> getAuthors() {
		return authors;
	}

	public Publisher authors(Set<Author> authors) {
		this.authors = authors;
		return this;
	}

	public Publisher addAuthors(Author author) {
		this.authors.add(author);
		author.setPublisher(this);
		return this;
	}

	public Publisher removeAuthors(Author author) {
		this.authors.remove(author);
		author.setPublisher(null);
		return this;
	}

	public void setAuthors(Set<Author> authors) {
		this.authors = authors;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Publisher)) {
			return false;
		}
		return id != null && id.equals(((Publisher) o).id);
	}

	@Override
	public int hashCode() {
		return 31;
	}

	@Override
	public String toString() {
		return "Publisher{" + "id=" + getId() + ", name='" + getName() + "'" + "}";
	}
}