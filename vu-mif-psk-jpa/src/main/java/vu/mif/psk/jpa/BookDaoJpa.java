package vu.mif.psk.jpa;

import java.util.List;

import javax.ejb.Stateful;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import vu.mif.psk.domain.dao.BookDao;
import vu.mif.psk.domain.model.Book;
import vu.mif.psk.domain.model.User;
import vu.mif.psk.domain.model.User_;

@Stateful
public class BookDaoJpa extends GenericDaoJpa implements BookDao {

	@Override
	public List<Book> getByUser(Long id) {
		CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Book> criteria = builder.createQuery(Book.class);
		Root<User> root = criteria.from(User.class);
		
		ParameterExpression<Long> idParameter = builder.parameter(Long.class);
		criteria.where(builder.equal(root.get(User_.id), idParameter));
		criteria.select(root.join(User_.books));
		
		TypedQuery<Book> query = getEntityManager().createQuery(criteria);
		query.setParameter(idParameter, id);
		return query.getResultList();
	}
	
	@Override
	public List<Book> getAll() {
		return getAll(Book.class);
	}

	@Override
	public Book get(Long id) {
		return get(Book.class, id);
	}

	@Override
	public void store(Book book) {
		super.store(book);
	}

}