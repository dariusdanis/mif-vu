package vu.mif.psk.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import vu.mif.psk.domain.dao.GenericDao;
import vu.mif.psk.domain.model.PrimaryKey;

public abstract class GenericDaoJpa implements GenericDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	public <E extends PrimaryKey<Long>> E get(Class<E> clazz, Object id) {
		return getEntityManager().find(clazz, id);
	}
	
	public <E extends PrimaryKey<Long>> List<E> getAll(Class<E> clazz) {
		CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<E> criteria = builder.createQuery(clazz);
		Root<E> root = criteria.from(clazz);
		criteria.select(root);
		return getEntityManager().createQuery(criteria).getResultList();
	}
	
	public <E extends PrimaryKey<Long>> E store(E entity) {
		if (entity == null) {
			throw new IllegalArgumentException("Parameter 'entity' is required");
		} else if (entity.getId() == null) {
			getEntityManager().persist(entity);
			return entity;
		} else {
			return getEntityManager().merge(entity);
		}
	}
	
	protected EntityManager getEntityManager() {
		return entityManager;
	}

	protected void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
}