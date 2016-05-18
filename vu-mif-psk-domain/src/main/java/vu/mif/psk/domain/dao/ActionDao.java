package vu.mif.psk.domain.dao;

import java.util.List;

import vu.mif.psk.domain.model.Action;
import vu.mif.psk.domain.model.Book;
import vu.mif.psk.domain.model.PrimaryKey;
import vu.mif.psk.domain.model.User;

public interface ActionDao extends GenericDao {

	List<Action> getAllEager();

	<E extends PrimaryKey<Long>> void store(E entity);
	
	Action getEager(Long valueOf);

	List<User> getAllUsers();

	List<Book> getAllAvailableBooks();

}
