package vu.mif.psk.jpa;

import java.util.List;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.SynchronizationType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import vu.mif.psk.domain.dao.ActionDao;
import vu.mif.psk.domain.model.Action;
import vu.mif.psk.domain.model.Action_;
import vu.mif.psk.domain.model.Book;
import vu.mif.psk.domain.model.Book_;
import vu.mif.psk.domain.model.PrimaryKey;
import vu.mif.psk.domain.model.User;
import vu.mif.psk.domain.model.User_;

@Stateful
public class ActionDaoJpa implements ActionDao {

	@PersistenceContext(type = PersistenceContextType.EXTENDED, synchronization = SynchronizationType.UNSYNCHRONIZED)
	private EntityManager entityManager;

	@Override
	public List<Book> getAllAvailableBooks() {
		CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Book> criteria = builder.createQuery(Book.class);
		Root<Book> root = criteria.from(Book.class);

		criteria.where(builder.notEqual(root.get(Book_.count), 0L));
		criteria.select(root);
		return getEntityManager().createQuery(criteria).getResultList();
	}

	@Override
	public List<User> getAllUsers() {
		CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<User> criteria = builder.createQuery(User.class);
		Root<User> root = criteria.from(User.class);
		root.fetch(User_.books, JoinType.LEFT);
		criteria.select(root);
		return getEntityManager().createQuery(criteria).getResultList();
	}

	@Override
	public List<Action> getAllEager() {
		CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Action> criteria = builder.createQuery(Action.class);
		Root<Action> root = criteria.from(Action.class);
		root.fetch(Action_.customer);
		criteria.select(root);
		return getEntityManager().createQuery(criteria).getResultList();
	}

	public <E extends PrimaryKey<Long>> void store(E entity) {
		if (entity == null) {
			throw new IllegalArgumentException("Parameter 'entity' is required");
		} else if (entity.getId() == null) {
			getEntityManager().persist(entity);
		} else {
			getEntityManager().merge(entity);
		}
	}

	@Override
	public Action getEager(Long id) {
		CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Action> criteria = builder.createQuery(Action.class);
		Root<Action> root = criteria.from(Action.class);

		ParameterExpression<Long> idParam = builder.parameter(Long.class);
		criteria.where(builder.equal(root.get(Action_.id), idParam));
		root.fetch(Action_.books, JoinType.LEFT);
		root.fetch(Action_.customer);

		TypedQuery<Action> query = getEntityManager().createQuery(criteria);
		query.setParameter(idParam, id);

		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public <E extends PrimaryKey<Long>> E get(Class<E> clazz, Object id) {
		return getEntityManager().find(clazz, id);
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

}
