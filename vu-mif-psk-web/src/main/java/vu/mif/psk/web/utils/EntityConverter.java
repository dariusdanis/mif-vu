package vu.mif.psk.web.utils;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import vu.mif.psk.domain.dao.GenericDao;
import vu.mif.psk.domain.model.PrimaryKey;

public class EntityConverter implements Converter, Serializable {
	private static final long serialVersionUID = 1L;
	private static final String ENTITY_CLASS = "ENTITY_CLASS";

	private GenericDao genericDao;

	public EntityConverter(GenericDao genericDao) {
		this.genericDao = genericDao;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Object getAsObject(FacesContext context, UIComponent component, String id) {
		if (id.charAt(0) < '0' || id.charAt(0) > '9') {
			return null;
		}

		try {
			return getGenericDao().get((Class<PrimaryKey<Long>>)component.getAttributes().get(ENTITY_CLASS), Long.valueOf(id));
		} catch (Exception e) {
			throw new ConverterException("Pasirinktas elementas nebeegzistuoja");
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public String getAsString(FacesContext context, UIComponent component, Object entity) {
		if (entity instanceof PrimaryKey) {
			PrimaryKey<Long> pk = (PrimaryKey<Long>) entity;
			component.getAttributes().put(ENTITY_CLASS, entity.getClass());
			return pk.getId().toString();
		}

		return null;
	}

	private GenericDao getGenericDao() {
		return genericDao;
	}

}
