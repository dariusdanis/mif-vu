package vu.mif.psk.web;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import vu.mif.psk.domain.model.Action;
import vu.mif.psk.domain.model.Book;
import vu.mif.psk.domain.model.User;
import vu.mif.psk.domain.service.ActionService;
import vu.mif.psk.domain.service.BookService;
import vu.mif.psk.domain.service.UserService;

@ManagedBean
@ViewScoped
public class IndexController implements Serializable {
	private static final long serialVersionUID = 1L;

	@EJB
	private UserService userSevice;

	@EJB
	private BookService bookService;

	@EJB
	private ActionService actionService;
	
	private List<User> users;
	private List<Book> books;
	private List<Action> actions;
	
	@PostConstruct
	public void init() {
		setUsers(getUserSevice().getAllWithBookCount());
		setBooks(getBookService().getAll());
		setActions(getActionService().getAll());
	}
	
	public String navigate(String page, Long id) {
	    return page + "?faces-redirect=true&id=" + id;
	}
	
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public UserService getUserSevice() {
		return userSevice;
	}

	public void setUserSevice(UserService userSevice) {
		this.userSevice = userSevice;
	}

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

	public BookService getBookService() {
		return bookService;
	}

	public void setBookService(BookService bookService) {
		this.bookService = bookService;
	}

	public ActionService getActionService() {
		return actionService;
	}

	public void setActionService(ActionService actionService) {
		this.actionService = actionService;
	}

	public List<Action> getActions() {
		return actions;
	}

	public void setActions(List<Action> actions) {
		this.actions = actions;
	}
	
}