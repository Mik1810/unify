package it.univaq.disim.oop.unify.controller;

import java.net.URL;
import java.util.ResourceBundle;

import it.univaq.disim.oop.unify.domain.Album;
import it.univaq.disim.oop.unify.domain.Artist;
import it.univaq.disim.oop.unify.view.ViewDispatcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class AddMoreSongsController implements Initializable, DataInitializable<Album, Object>{

	private Album album;
	private ViewDispatcher dispatcher;
	
	@FXML
	private Button yesButton;
	
	@FXML
	private Button noButton;
	
	@FXML
	private Label artistLabel;
	
	@FXML
	private Label albumLabel;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		dispatcher = ViewDispatcher.getInstance();
	}
	
	@Override
	public void initializeData(Album album, Object obj) {
		this.album = album;
		
		artistLabel.setText(artistLabel.getText() + album.getArtist().getName());
		albumLabel.setText(albumLabel.getText() + album.getTitle());
	}
	
	@FXML
	public void yesAction(ActionEvent event) {
		dispatcher.renderView("modify-song", album, null);
	}
	
	@FXML
	public void noAction(ActionEvent event) {
		dispatcher.renderView("album-settings", null);
	}

}
