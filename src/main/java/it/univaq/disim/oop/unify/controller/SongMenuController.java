package it.univaq.disim.oop.unify.controller;

import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import it.univaq.disim.oop.unify.Utility;
import it.univaq.disim.oop.unify.business.PhotoService;
import it.univaq.disim.oop.unify.business.UnifyBusinessFactory;
import it.univaq.disim.oop.unify.business.exceptions.BusinessException;
import it.univaq.disim.oop.unify.domain.Song;
import it.univaq.disim.oop.unify.musicplayer.MusicPlayer;
import it.univaq.disim.oop.unify.view.ViewDispatcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SongMenuController implements Initializable, DataInitializable<Song, Object> {

	private ViewDispatcher dispatcher;
	private MusicPlayer musicPlayer;
	private PhotoService photoService;

	@FXML
	private ImageView songImageView;

	@FXML
	private Label songMenuLabel;

	@FXML
	private Label titleLabel;

	@FXML
	private Label artistLabel;

	@FXML
	private Label albumLabel;

	@FXML
	private Label genreLabel;

	@FXML
	private Label lyricsLabel;

	@FXML
	private Button goPlayButton;

	public SongMenuController() {
		dispatcher = ViewDispatcher.getInstance();
		musicPlayer = MusicPlayer.getInstance();

		UnifyBusinessFactory factory = UnifyBusinessFactory.getInstance();
		photoService = factory.getPhotoService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Utility.roundSongImageView(songImageView);
	}

	@Override
	public void initializeData(Song song, Object object) {
		songImageView.setImage(new Image(photoService.toStream(song.getCover().getContent())));
		songMenuLabel.setText("Song: " + song.getTitle());
		titleLabel.setText(song.getTitle());
		artistLabel.setText(song.getArtist().getName());
		albumLabel.setText(song.getAlbum().getTitle());
		genreLabel.setText(song.getGenre().name());
		lyricsLabel.setText(song.getText());

		goPlayButton.setOnAction((ActionEvent event) -> {
			try {
				Set<Song> songs = new HashSet<>();
				songs.add(song);
				musicPlayer.queueSongs(songs);
				dispatcher.renderMediaPlayer("mediaplayer", songs);
			} catch (BusinessException e) {
				e.printStackTrace();
			}
		});
	}

}