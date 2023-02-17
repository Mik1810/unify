package it.univaq.disim.oop.unify.controller;

import java.net.URL;
import java.util.ResourceBundle;

import it.univaq.disim.oop.unify.domain.Person;
import it.univaq.disim.oop.unify.view.ViewDispatcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class SettingsController implements Initializable, DataInitializable<Person,Object> {

	private ViewDispatcher dispatcher;

	@FXML
	private Button artistViewAction;

	@FXML
	private Button albumViewAction;
	
	@FXML
	private Button songViewAction;

	public SettingsController() {
		dispatcher = ViewDispatcher.getInstance();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	@Override
	public void initializeData(Person person,Object object) {

	}
	
	@FXML
	public void artistSettingsAction(ActionEvent event) {
		dispatcher.renderView("artist-settings", null);
	}
	
	@FXML
	public void albumSettingsAction(ActionEvent event) {
		dispatcher.renderView("album-settings", null);
	}
	
	@FXML
	public void songSettingsAction(ActionEvent event) {
		dispatcher.renderView("song-settings", null);
	}

}
