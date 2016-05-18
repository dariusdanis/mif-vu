package vu.mif.psk.domain.dao;

import vu.mif.psk.domain.model.PrimaryKey;

public interface GenericDao {
	
	<E extends PrimaryKey<Long>> E get(Class<E> clazz, Object id);
	
}
