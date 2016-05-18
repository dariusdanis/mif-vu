package vu.mif.psk.domain.dao;

import vu.mif.psk.domain.model.UserInfo;

public interface UserInfoDao {

	UserInfo persist(UserInfo userInfo);

	UserInfo update(UserInfo userInfo);

}
