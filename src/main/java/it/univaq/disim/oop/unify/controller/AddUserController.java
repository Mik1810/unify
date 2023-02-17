package it.univaq.disim.oop.unify.controller;

import java.net.URL;
import java.util.ResourceBundle;

import it.univaq.disim.oop.unify.business.PersonService;
import it.univaq.disim.oop.unify.business.UnifyBusinessFactory;
import it.univaq.disim.oop.unify.business.exceptions.BusinessException;
import it.univaq.disim.oop.unify.domain.Album;
import it.univaq.disim.oop.unify.domain.Person;
import it.univaq.disim.oop.unify.domain.User;
import it.univaq.disim.oop.unify.view.ViewDispatcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AddUserController implements Initializable, DataInitializable<Person, Object>{
	
	private ViewDispatcher dispatcher;
	private PersonService personService;
	private String username, password;
	
	@FXML
	private TextField usernameTextField;
	
	@FXML
	private TextField passwordTextField;
	
	@FXML
	private TextField repeatPasswordTextField;
	
	@FXML
	private Button addUserButton;
	
	@FXML
	private Label usernameLabel;
	
	@FXML
	private Label passwordLabel;
	
	@FXML
	private Button confirmButton;
	
	@FXML
	private Label errorLabel;
	
	@FXML
	private Label answerLabel;
	
	public AddUserController() {
		dispatcher = ViewDispatcher.getInstance();
		UnifyBusinessFactory factory = UnifyBusinessFactory.getInstance();
		personService = factory.getPersonService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		confirmButton.setDisable(true);
		
		//To bind the three text field
		addUserButton.disableProperty().bind(usernameTextField.textProperty().isEmpty());
		addUserButton.disableProperty().bind(repeatPasswordTextField.textProperty().isEmpty());
		addUserButton.disableProperty().bind(passwordTextField.textProperty().isEmpty());
		
		usernameLabel.setVisible(false);
		passwordLabel.setVisible(false);
		answerLabel.setVisible(false);
	}
	
	@FXML
	public void addUserAction(ActionEvent event) {
		
		if(passwordTextField.getText().equals(repeatPasswordTextField.getText())) {
			
			this.username = usernameTextField.getText();
			this.password = passwordTextField.getText();
			
			usernameLabel.setVisible(true);
			passwordLabel.setVisible(true);
			answerLabel.setVisible(true);
			
			usernameLabel.setText("Username: " + username);
			passwordLabel.setText("Password: " + password);
			
			confirmButton.setDisable(false);
			
			usernameTextField.setText("");
			passwordTextField.setText("");
			repeatPasswordTextField.setText("");
		}else {
			errorLabel.setText("Error: passwords mismatch");
		}
	}
	
	@FXML
	public void confirmAction(ActionEvent event) {
		
		try {
			personService.register(username, password);
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		dispatcher.renderView("user-settings", null);
	}

}
