package vu.mif.psk.domain.service;

import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;

import vu.mif.psk.domain.dao.UserDao;
import vu.mif.psk.domain.dao.UserInfoDao;
import vu.mif.psk.domain.model.User;
import vu.mif.psk.domain.model.UserInfo;

@Stateful
public class UserService {
	
	@EJB
	private UserDao userDao;

	@EJB
	private UserInfoDao userInfoDao;
	
	public List<User> getAll() {
		return getUserDao().getAll();
	}
	
	public List<User> getAllWithBookCount() {
		List<User> users = getUserDao().getAll();
		Map<Long, Long> map = getUserDao().countBooksByUser();
		users.stream().forEach(u -> u.setBooksCount(map.get(u.getId())));
		return users;
	}
	
	@TransactionAttribute
	public void store(User customer) {
		UserInfo userInfo = null;
		if (customer.getId() == null) {
			// user info attached
			userInfo = getUserInfoDao().persist(customer.getUserInfo());
		} else {
			// user info detached because of TransactionAttributeType.REQUIRES_NEW
			userInfo = getUserInfoDao().update(customer.getUserInfo());
		}
		
		customer.setUserInfo(userInfo);
		getUserDao().store(customer);
	}
	
	public User get(String id) {
		User user = null;
		if (id != null) {
			user = getUserDao().getEager(Long.valueOf(id));
		}
		
		if (user == null) {
			user = create();
		}
		
		return user;
	}
	
	private User create() {
		User user = new User();
		user.setUserInfo(new UserInfo());
		return user;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public UserInfoDao getUserInfoDao() {
		return userInfoDao;
	}

	public void setUserInfoDao(UserInfoDao userInfoDao) {
		this.userInfoDao = userInfoDao;
	}

}
