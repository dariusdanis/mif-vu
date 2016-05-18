package vu.mif.psk.domain.model;

import java.io.Serializable;

public abstract class PrimaryKey<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	public abstract T getId();
	public abstract void setId(T id);
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		return prime * result + ((getId() == null) ? 0 : getId().hashCode());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		
		if (!getClass().isAssignableFrom(obj.getClass()) && !obj.getClass().isAssignableFrom(getClass())) {
			return false;
		}
		
		if (obj instanceof PrimaryKey<?>) {
			final PrimaryKey<?> pk = (PrimaryKey<?>) obj;
			
			if (pk.getId() != null) {
				return pk.getId().equals(getId());
			}
		}
		
		return false;
	}
	
}
