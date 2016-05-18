package vu.mif.psk.domain.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;

import vu.mif.psk.domain.dao.ActionDao;
import vu.mif.psk.domain.model.Action;
import vu.mif.psk.domain.model.Book;
import vu.mif.psk.domain.model.User;

@Stateful
public class ActionService {

	@EJB
	private ActionDao actionDao;

	public List<Book> getAllAvailableBooks() {
		return getActionDao().getAllAvailableBooks();
	}
	
	public Action getEager(String id) {
		if (id == null) {
			return null;
		}
		return getActionDao().getEager(Long.valueOf(id));
	}
	
	public List<User> getAllUsers() {
		return getActionDao().getAllUsers();
	}
	
	@TransactionAttribute
	public void store(Action action) {
		switch (action.getActionType()) {
		case RETURN:
			Map<Book, Long> map = new HashMap<>();
			for (Book book : action.getBooks()) {
				if (map.containsKey(book)) {
					map.put(book, map.get(book) + 1L); 
				} else {
					map.put(book, 1L); 
				}
			}
			
			for (Map.Entry<Book, Long> entry : map.entrySet()) {
				entry.getKey().setCount(entry.getKey().getCount() + entry.getValue());
				getActionDao().store(entry.getKey());
			}
			
			removeOneByOne(action.getCustomer().getBooks(), action.getBooks());
			break;
		case TAKE:
			for (Book book : action.getBooks()) {
				book.setCount(book.getCount() - 1);
				getActionDao().store(book);
			};
			action.getCustomer().getBooks().addAll(action.getBooks());
			break;
		}
		getActionDao().store(action.getCustomer());
		getActionDao().store(action);
	}
	
	//same books
	private void removeOneByOne(List<Book> books, List<Book> booksToRemove) {
		for (Book toRemove : booksToRemove) {
			int indexToRemove = 0;
			for (int i = 0; i < books.size(); i++) {
				if (books.get(i).equals(toRemove)) {
					indexToRemove = i;
					break;
				}
			}
			books.remove(indexToRemove);
		}
	}

	public List<Action> getAll() {
		return getActionDao().getAllEager();
	}
	
	public ActionDao getActionDao() {
		return actionDao;
	}

	public void setActionDao(ActionDao actionDao) {
		this.actionDao = actionDao;
	}

}