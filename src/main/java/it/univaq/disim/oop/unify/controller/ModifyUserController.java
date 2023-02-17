package it.univaq.disim.oop.unify.controller;

import java.net.URL;
import java.util.ResourceBundle;

import it.univaq.disim.oop.unify.business.PersonService;
import it.univaq.disim.oop.unify.business.UnifyBusinessFactory;
import it.univaq.disim.oop.unify.business.exceptions.BusinessException;
import it.univaq.disim.oop.unify.domain.Administrator;
import it.univaq.disim.oop.unify.domain.User;
import it.univaq.disim.oop.unify.view.ViewDispatcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ModifyUserController implements Initializable, DataInitializable<Administrator, User> {

	private ViewDispatcher dispatcher;
	private PersonService personService;
	private String password;
	private String username;
	private Administrator admin;
	private User user;

	@FXML
	private TextField usernameTextField;

	@FXML
	private TextField passwordTextField;

	@FXML
	private Button finishButton;

	public ModifyUserController() {
		dispatcher = ViewDispatcher.getInstance();
		UnifyBusinessFactory factory = UnifyBusinessFactory.getInstance();
		personService = factory.getPersonService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	@Override
	public void initializeData(Administrator admin, User user) {
		this.user = user;
		this.admin = admin;

		try {
			username = personService.getUsername(user);
			password = personService.getPassword(user);
			usernameTextField.setText(username);
			passwordTextField.setText(password);

			if (admin == null) { // Account section for the user
				usernameTextField.setDisable(true);
				// The user cannot modify his username
			}
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void finishAction(ActionEvent event) {
		try {
			if (admin == null) {
				personService.modifyPassword(user, passwordTextField.getText());
				dispatcher.renderView("home", user, null);
			} else {
				if (!username.equals(usernameTextField.getText()) && 
					!password.equals(passwordTextField.getText())) {
					personService.modifyAccount(user, usernameTextField.getText(), 
							passwordTextField.getText());
					dispatcher.renderView("user-settings", admin);
				}else if (!password.equals(passwordTextField.getText())) {
					personService.modifyPassword(user, passwordTextField.getText());
				}else if(!username.equals(usernameTextField.getText())) {
					personService.modifyUsername(user, usernameTextField.getText());
				}
				dispatcher.renderView("user-settings", admin);
			}
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}
}
