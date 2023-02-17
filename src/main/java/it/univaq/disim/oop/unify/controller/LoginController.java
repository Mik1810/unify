package it.univaq.disim.oop.unify.controller;

import java.net.URL;
import java.util.ResourceBundle;

import it.univaq.disim.oop.unify.business.PersonService;
import it.univaq.disim.oop.unify.business.UnifyBusinessFactory;
import it.univaq.disim.oop.unify.business.exceptions.BusinessException;
import it.univaq.disim.oop.unify.business.exceptions.PersonNotFoundException;
import it.univaq.disim.oop.unify.domain.Person;
import it.univaq.disim.oop.unify.view.ViewDispatcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LoginController implements Initializable, DataInitializable<Object, Object> {

	private ViewDispatcher dispatcher;
	private PersonService personService;

	@FXML
	private TextField usernameTextField;

	@FXML
	private TextField passwordTextField;

	@FXML
	private Label errorLabel;

	@FXML
	private Button loginButton;

	@FXML
	private Button registerButton;

	public LoginController() {
		dispatcher = ViewDispatcher.getInstance();
		UnifyBusinessFactory factory = UnifyBusinessFactory.getInstance();
		personService = factory.getPersonService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		loginButton.disableProperty()
				.bind(usernameTextField.textProperty().isEmpty().or(passwordTextField.textProperty().isEmpty()));
		registerButton.disableProperty()
				.bind(usernameTextField.textProperty().isEmpty().or(passwordTextField.textProperty().isEmpty()));

	}

	@FXML
	private void loginAction(ActionEvent event) {
		try {
			Person person = personService.authenticate(usernameTextField.getText(), passwordTextField.getText());
			dispatcher.loggedIn(person);
		} catch (PersonNotFoundException e) {
			errorLabel.setText("Incorrect username and/or password!");
		} catch (BusinessException e) {
			dispatcher.renderError(e);
		}
	}

	@FXML
	private void registerAction(ActionEvent event) {
		try {
			Person person = personService.register(usernameTextField.getText(), passwordTextField.getText());
			dispatcher.loggedIn(person);
		} catch (BusinessException e) {
			errorLabel.setText(e.getMessage());
		}
	}

}