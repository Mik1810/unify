package it.univaq.disim.oop.unify.controller;

import java.net.URL;
import java.util.ResourceBundle;

import it.univaq.disim.oop.unify.business.ArtistService;
import it.univaq.disim.oop.unify.business.PersonService;
import it.univaq.disim.oop.unify.business.SongService;
import it.univaq.disim.oop.unify.business.UnifyBusinessFactory;
import it.univaq.disim.oop.unify.business.exceptions.BusinessException;
import it.univaq.disim.oop.unify.domain.Album;
import it.univaq.disim.oop.unify.domain.Artist;
import it.univaq.disim.oop.unify.domain.Song;
import it.univaq.disim.oop.unify.domain.User;
import it.univaq.disim.oop.unify.musicplayer.MusicPlayer;
import it.univaq.disim.oop.unify.view.ViewDispatcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class DeleteController<T, K> implements Initializable, DataInitializable<T, K>{

	private ViewDispatcher dispatcher;
	private MusicPlayer musicPlayer;
	private SongService songService;
	private ArtistService artistService;
	private PersonService personService;
	private Album album;
	private Song song;
	private Artist artist;
	private User user;
	
	@FXML
	private Label titleLabel;
	
	@FXML
	private Label firstLabel;
	
	@FXML
	private Label secondLabel;
	
	@FXML
	private Button yesButton;
	
	@FXML
	private Button noButton;
	
	public DeleteController() {
		dispatcher = ViewDispatcher.getInstance();
		musicPlayer = MusicPlayer.getInstance();
		UnifyBusinessFactory factory = UnifyBusinessFactory.getInstance();
		songService = factory.getSongService();
		artistService = factory.getArtistService();
		personService = factory.getPersonService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {}
	
	@Override
	public void initializeData(T t, K k) {
		if (t instanceof Album) {
			titleLabel.setText("Do you want to delete this album?");
			album = (Album)t;
			firstLabel.setText("Album: " + album);
			secondLabel.setText("Artist: "+ album.getArtist());
		} else if (t instanceof Artist) {
			titleLabel.setText("Do you want to delete this artist?");
			artist = (Artist)t;
			firstLabel.setText("Artist: " + artist);
			secondLabel.setText("");
		} else if (t instanceof Song) {
			titleLabel.setText("Do you want to delete this song?");
			song = (Song)t;
			firstLabel.setText("Song: " + song);
			secondLabel.setText("Artist: " + song.getArtist());
		} else if (t instanceof User) {
			titleLabel.setText("Do you want to delete this user?");
			user = (User)t;
			firstLabel.setText("Username: " + user.getUsername());
			secondLabel.setText("Password: " + user.getPassword());
		}
		
	}
	
	@FXML
	private void yesAction(ActionEvent event) throws BusinessException {
		if(album != null) {
			//When i delete an album, i want to delete also the songs in it
			songService.deleteAlbum(album);
			dispatcher.renderView("album-settings", null);
		} else if (artist != null) {
			songService.deleteDiscographyFromArtist(artist);
			artistService.deleteArtist(artist);
			dispatcher.renderView("artist-settings", null);
		} else if (song != null) {
			musicPlayer.exit(); //if the player is running
			songService.deleteSong(song);
			dispatcher.renderView("song-settings", null);
		} else if (user != null) {
			personService.deleteUser(user);
			dispatcher.renderView("user-settings", null);
		}
	}

	@FXML
	private void noAction(ActionEvent event) {
		if(album != null) {
			dispatcher.renderView("album-settings", null);
		} else if (artist != null) {
			dispatcher.renderView("artist-settings", null);
		} else if (song != null) {
			dispatcher.renderView("song-settings", null);
		} else if (user != null) {
			dispatcher.renderView("user-settings", null);
		}
	}

}
