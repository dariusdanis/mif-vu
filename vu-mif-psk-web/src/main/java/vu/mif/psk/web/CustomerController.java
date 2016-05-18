package vu.mif.psk.web;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import vu.mif.psk.domain.model.User;
import vu.mif.psk.domain.service.UserService;

@ManagedBean
@ViewScoped
public class CustomerController implements Serializable {
	private static final long serialVersionUID = 1L;

	@EJB
	private UserService userSevice;
	private User customer;
	
	@PostConstruct
	public void init() {
		customer = getUserSevice().get(getId());
	}
	
	public void store() {
		getUserSevice().store(getCustomer());
	}
	
	public UserService getUserSevice() {
		return userSevice;
	}

	public void setUserSevice(UserService userSevice) {
		this.userSevice = userSevice;
	}

	public User getCustomer() {
		return customer;
	}

	public void setCustomer(User customer) {
		this.customer = customer;
	}

	private String getId() {
		return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
	}
	
}