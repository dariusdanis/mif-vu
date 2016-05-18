package vu.mif.psk.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import vu.mif.psk.domain.model.Action;
import vu.mif.psk.domain.model.ActionType;
import vu.mif.psk.domain.model.Book;
import vu.mif.psk.domain.model.User;
import vu.mif.psk.domain.service.ActionService;
import vu.mif.psk.web.utils.EntityConverter;

@Named
@ConversationScoped
public class ActionController implements Serializable {
	private static final long serialVersionUID = 1L;

	@EJB
	private ActionService actionService;

	@Inject
	private Conversation conversation;

	private Action action = new Action();
	private List<Book> availableToTake;
	private List<User> users;
	private EntityConverter entityConverter;

	public String next() {
		return "book_selection_action?faces-redirect=true";
	}

	@PostConstruct
	public void init() {
		setUsers(getActionService().getAllUsers());
		setAvailableToTake(getActionService().getAllAvailableBooks());
		setEntityConverter(new EntityConverter(getActionService().getActionDao()));
		if (getUsers().size() > 1) {
			getAction().setCustomer(getUsers().get(0));
		}
	}

	public void initConversation() {
		if (getConversation().isTransient()) {
			getConversation().begin();
		}
	}

	public void store() {
		getActionService().store(getAction());
		getConversation().end();
	}

	public List<Book> getBooks() {
		if (ActionType.TAKE.equals(getAction().getActionType())) {
			return availableToTake;
		} else if (getAction().getCustomer() != null) {
			return getAction().getCustomer().getBooks();
		}
		return new ArrayList<>();
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

	public List<ActionType> getActionTypes() {
		return Arrays.asList(ActionType.values());
	}

	public List<Book> getAvailableToTake() {
		return availableToTake;
	}

	public void setAvailableToTake(List<Book> availableToTake) {
		this.availableToTake = availableToTake;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public EntityConverter getEntityConverter() {
		return entityConverter;
	}

	public void setEntityConverter(EntityConverter entityConverter) {
		this.entityConverter = entityConverter;
	}

	public Conversation getConversation() {
		return conversation;
	}

	public void setConversation(Conversation conversation) {
		this.conversation = conversation;
	}

}