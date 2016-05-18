package vu.mif.psk.domain.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateful;

import vu.mif.psk.domain.dao.BookDao;
import vu.mif.psk.domain.model.Book;
import vu.mif.psk.domain.model.User;

@Stateful
public class BookService {

	@EJB
	private BookDao bookDao;
	
	public List<Book> getByUser(User user) {
		if (user == null) {
			return new ArrayList<>();
		}
		
		return getBookDao().getByUser(user.getId());
	}
	
	public Book get(String id) {
		Book book = null;
		if (id != null) {
			book = getBookDao().get(Long.valueOf(id));
		}
		
		if (book == null) {
			book = new Book();
		}
		
		return book;
	}
	
	public void store(Book book) {
		getBookDao().store(book);
	}
	
	public List<Book> getAll() {
		return getBookDao().getAll();
	}

	public BookDao getBookDao() {
		return bookDao;
	}

	public void setBookDao(BookDao bookDao) {
		this.bookDao = bookDao;
	}

}