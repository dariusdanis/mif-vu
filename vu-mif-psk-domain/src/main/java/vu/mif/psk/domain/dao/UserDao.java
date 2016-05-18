package vu.mif.psk.domain.dao;

import java.util.List;
import java.util.Map;

import vu.mif.psk.domain.model.User;

public interface UserDao  extends GenericDao {

	List<User> getAll();

	void store(User customer);

	User getEager(Long id);

	Map<Long, Long> countBooksByUser();

}
