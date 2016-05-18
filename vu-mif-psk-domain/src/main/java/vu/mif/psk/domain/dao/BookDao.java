package vu.mif.psk.domain.dao;

import java.util.List;

import vu.mif.psk.domain.model.Book;

public interface BookDao extends GenericDao {

	List<Book> getAll();

	Book get(Long id);

	void store(Book book);

	List<Book> getByUser(Long id);

}
