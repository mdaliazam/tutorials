package com.github.mdaliazam.graphql.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * A very simple Book entity
 * 
 * @author <a href="mailto:softx.it@gmail.com">Mohammad Ali Azam</a>
 *
 */
@Entity
@Table(name = "book")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Book implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
	@SequenceGenerator(name = "sequenceGenerator")
	private Long id;

	@NotNull
	@Column(name = "title", nullable = false)
	private String title;

	@NotNull
	@Column(name = "i_sbn", nullable = false)
	private String ISBN;

	@ManyToOne
	@JsonIgnoreProperties("books")
	@JsonBackReference(value="auth-books")
	private Author author;

	@ManyToOne
	@JsonIgnoreProperties("books")
	@JsonBackReference(value="pub-books")
	private Publisher publisher;

	public Book() {
		super();
	}

	public Book(@NotNull String title, @NotNull String ISBN) {
		super();
		this.title = title;
		this.ISBN = ISBN;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public Book title(String title) {
		this.title = title;
		return this;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getiSBN() {
		return ISBN;
	}

	public Book iSBN(String iSBN) {
		this.ISBN = iSBN;
		return this;
	}

	public void setiSBN(String iSBN) {
		this.ISBN = iSBN;
	}

	public Author getAuthor() {
		return author;
	}

	public Book author(Author author) {
		this.author = author;
		return this;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public Publisher getPublisher() {
		return publisher;
	}

	public Book publisher(Publisher publisher) {
		this.publisher = publisher;
		return this;
	}

	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Book)) {
			return false;
		}
		return id != null && id.equals(((Book) o).id);
	}

	@Override
	public int hashCode() {
		return 31;
	}

	@Override
	public String toString() {
		return "Book{" + "id=" + getId() + ", title='" + getTitle() + "'" + ", iSBN='" + getiSBN() + "'" + "}";
	}
}