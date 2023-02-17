package it.univaq.disim.oop.unify.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import it.univaq.disim.oop.unify.Utility;
import it.univaq.disim.oop.unify.business.PhotoService;
import it.univaq.disim.oop.unify.business.SongService;
import it.univaq.disim.oop.unify.business.UnifyBusinessFactory;
import it.univaq.disim.oop.unify.business.exceptions.BusinessException;
import it.univaq.disim.oop.unify.domain.Album;
import it.univaq.disim.oop.unify.domain.Person;
import it.univaq.disim.oop.unify.domain.Song;
import it.univaq.disim.oop.unify.view.ViewDispatcher;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public class HomeController implements Initializable, DataInitializable<Person, Object> {

	private SongService songService;
	private PhotoService photoService;
	private ViewDispatcher dispatcher;
	private List<Album> albums;
	private List<Song> songs;

	@FXML
	private Label song1Label;

	@FXML
	private Label song2Label;

	@FXML
	private Label song3Label;

	@FXML
	private ImageView song1Image;

	@FXML
	private ImageView song2Image;

	@FXML
	private ImageView song3Image;

	@FXML
	private Label album1Label;

	@FXML
	private Label album2Label;

	@FXML
	private Label album3Label;

	@FXML
	private ImageView album1Image;

	@FXML
	private ImageView album2Image;

	@FXML
	private ImageView album3Image;

	public HomeController() {
		dispatcher = ViewDispatcher.getInstance();
		UnifyBusinessFactory factory = UnifyBusinessFactory.getInstance();
		songService = factory.getSongService();
		photoService = factory.getPhotoService();

		try {
			songs = new ArrayList<>(songService.getAllSongs());
			albums = new ArrayList<>(songService.getAllAlbum());
			Collections.shuffle(songs);
			Collections.shuffle(albums);
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Utility.roundHomeImageView(song1Image);
		Utility.roundHomeImageView(song2Image);
		Utility.roundHomeImageView(song3Image);
		Utility.roundHomeImageView(album1Image);
		Utility.roundHomeImageView(album2Image);
		Utility.roundHomeImageView(album3Image);
	}

	@Override
	public void initializeData(Person person, Object object) {
		
		song1Label.setText(songs.get(0).getTitle() + " - " + songs.get(0).getArtist().getName());
		song1Image.setImage(new Image(photoService.toStream(songs.get(0).getCover().getContent())));

		song2Label.setText(songs.get(1).getTitle() + " - " + songs.get(1).getArtist().getName());
		song2Image.setImage(new Image(photoService.toStream(songs.get(1).getCover().getContent())));

		song3Label.setText(songs.get(2).getTitle() + " - " + songs.get(2).getArtist().getName());
		song3Image.setImage(new Image(photoService.toStream(songs.get(2).getCover().getContent())));

		album1Label.setText(albums.get(0).getTitle() + " - " + albums.get(0).getArtist().getName());
		album1Image.setImage(new Image(photoService.toStream(albums.get(0).getCover().getContent())));

		album2Label.setText(albums.get(1).getTitle() + " - " + albums.get(1).getArtist().getName());
		album2Image.setImage(new Image(photoService.toStream(albums.get(1).getCover().getContent())));

		album3Label.setText(albums.get(2).getTitle() + " - " + albums.get(2).getArtist().getName());
		album3Image.setImage(new Image(photoService.toStream(albums.get(2).getCover().getContent())));

	}

	@FXML
	private void song1MenuAction() {
		dispatcher.renderView("song-menu", songs.get(0));
	}

	@FXML
	private void song2MenuAction() {
		dispatcher.renderView("song-menu", songs.get(1));
	}

	@FXML
	private void song3MenuAction() {
		dispatcher.renderView("song-menu", songs.get(2));
	}

	@FXML
	private void album1MenuAction() {
		dispatcher.renderView("album-menu", albums.get(0));
	}

	@FXML
	private void album2MenuAction() {
		dispatcher.renderView("album-menu", albums.get(1));
	}

	@FXML
	private void album3MenuAction() {
		dispatcher.renderView("album-menu", albums.get(2));
	}
}