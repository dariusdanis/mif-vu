package vu.mif.psk.web;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import vu.mif.psk.domain.model.Action;
import vu.mif.psk.domain.service.ActionService;

@ManagedBean
@ViewScoped
public class ViewActionController implements Serializable {
	private static final long serialVersionUID = 1L;

	@EJB
	private ActionService actionService;
	private Action action;
	
	@PostConstruct
	public void init() {
		action = getActionService().getEager(getId());
	}

	public ActionService getActionService() {
		return actionService;
	}

	public void setActionService(ActionService actionService) {
		this.actionService = actionService;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}
	
	private String getId() {
		return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
	}
	
}
