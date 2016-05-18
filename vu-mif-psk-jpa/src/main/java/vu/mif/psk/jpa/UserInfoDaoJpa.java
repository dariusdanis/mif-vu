package vu.mif.psk.jpa;

import java.util.Calendar;

import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import vu.mif.psk.domain.dao.UserInfoDao;
import vu.mif.psk.domain.model.UserInfo;

@Stateful
public class UserInfoDaoJpa implements UserInfoDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public UserInfo persist(UserInfo userInfo) {
		userInfo.setLastChangeDate(Calendar.getInstance().getTime());
		getEntityManager().persist(userInfo);
		return userInfo;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public UserInfo update(UserInfo userInfo) {
		userInfo.setLastChangeDate(Calendar.getInstance().getTime());
		return getEntityManager().merge(userInfo);
	}
	
	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

}
