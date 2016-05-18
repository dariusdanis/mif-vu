package vu.mif.psk.web;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import vu.mif.psk.domain.model.Book;
import vu.mif.psk.domain.service.BookService;

@ManagedBean
@ViewScoped
public class BookController implements Serializable {
	private static final long serialVersionUID = 1L;

	@EJB
	private BookService bookService;
	private Book book;

	@PostConstruct
	public void init() {
		book = getBookService().get(getId());
	}

	public void store() {
		try {
			getBookService().store(getBook());
		} catch (EJBException e) {
			FacesContext.getCurrentInstance().addMessage("bookForm",
					new FacesMessage("Form data was changed by another user. Please refresh the page and try again."));
		}
	}

	public BookService getBookService() {
		return bookService;
	}

	public void setBookService(BookService bookService) {
		this.bookService = bookService;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	private String getId() {
		return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
	}

}
