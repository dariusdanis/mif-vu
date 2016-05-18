package vu.mif.psk.jpa;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateful;
import javax.persistence.NoResultException;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import vu.mif.psk.domain.dao.UserDao;
import vu.mif.psk.domain.model.User;
import vu.mif.psk.domain.model.User_;

@Stateful
public class UserDaoJpa extends GenericDaoJpa implements UserDao {

	@Override
	public Map<Long, Long> countBooksByUser() {
		CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Tuple> criteria = builder.createTupleQuery();
		Root<User> root = criteria.from(User.class);
		
		Path<Long> idPath = root.get(User_.id);
		criteria.groupBy(idPath);
		criteria.multiselect(idPath, builder.count(root.join(User_.books, JoinType.LEFT)));
		
		Map<Long, Long> map = new HashMap<>();
		for (Tuple tuple : getEntityManager().createQuery(criteria).getResultList()) {
			map.put((Long)tuple.get(0), (Long)tuple.get(1));
		} 

		return map;
	}
	
	@Override
	public User getEager(Long id) {
		CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<User> criteria = builder.createQuery(User.class);
		Root<User> root = criteria.from(User.class);
		
		ParameterExpression<Long> idParam = builder.parameter(Long.class);
		criteria.where(builder.equal(root.get(User_.id), idParam));
		root.fetch(User_.userInfo);
		root.fetch(User_.books, JoinType.LEFT);
		
		TypedQuery<User> query = getEntityManager().createQuery(criteria);
		query.setParameter(idParam, id);
		
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	@Override
	public List<User> getAll() {
		return getAll(User.class);
	}
	
	@Override
	public void store(User customer) {
		super.store(customer);
	}

}