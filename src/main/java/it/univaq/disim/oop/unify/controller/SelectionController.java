package it.univaq.disim.oop.unify.controller;

import java.net.URL;
import java.util.ResourceBundle;

import it.univaq.disim.oop.unify.domain.Artist;
import it.univaq.disim.oop.unify.view.ViewDispatcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class SelectionController implements Initializable, DataInitializable<Artist, Object> {
	
	private ViewDispatcher dispatcher;
	private Artist artist;
	
	@FXML
	private Button yesButton;
	
	@FXML
	private Button noButton;
	
	public SelectionController() {
		dispatcher = ViewDispatcher.getInstance();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {}

	@Override
	public void initializeData(Artist artist, Object object) {
		this.artist = artist;
	}
	
	@FXML
	public void yesAction(ActionEvent event) {
		dispatcher.renderView("modify-album", null, artist);
	}
	
	@FXML
	public void noAction(ActionEvent event) {
		dispatcher.renderView("settings", null);
	}

}
