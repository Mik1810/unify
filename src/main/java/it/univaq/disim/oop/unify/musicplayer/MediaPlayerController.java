package it.univaq.disim.oop.unify.musicplayer;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Set;

import it.univaq.disim.oop.unify.Utility;
import it.univaq.disim.oop.unify.business.PhotoService;
import it.univaq.disim.oop.unify.business.UnifyBusinessFactory;
import it.univaq.disim.oop.unify.controller.DataInitializable;
import it.univaq.disim.oop.unify.domain.Song;
import it.univaq.disim.oop.unify.view.ViewDispatcher;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

public class MediaPlayerController implements Initializable, DataInitializable<Set<Song>, Object> {

	private Image playImage = new Image(Utility.ICON_PATH + File.separator + "icons8-riproduci-60.png");
	private Image pauseImage = new Image(Utility.ICON_PATH + File.separator + "icons8-pausa-60.png");
	private Image volumeImage = new Image(Utility.ICON_PATH + File.separator + "volume.png");
	private Image muteImage = new Image(Utility.ICON_PATH + File.separator + "mute.png");

	private List<Song> songs = new ArrayList<>();
	private ViewDispatcher dispatcher;
	private MusicPlayer musicPlayer;
	private PhotoService photoService;
	private final String selectedIcon = "-fx-border-color:  #d1e7ed; -fx-border-radius: 10;";
	private final String notSelectedIcon = "-fx-border-color:  transparent; -fx-border-radius: 10;";

	/*
	 * This for the method that rotate the title label or the artist label. By
	 * default, it is set not to rotate
	 */
	private boolean titleClock = false;
	private boolean artistClock = false;
	private Timeline clockTitle;
	private Timeline clockArtist;

	@FXML
	private Slider progressBar;

	@FXML
	private Slider volumeSlider;

	@FXML
	private ImageView volumeImageView;

	@FXML
	private ImageView currentSongImageView;

	@FXML
	private ImageView shuffleImageView;

	@FXML
	private ImageView repeatImageView;

	@FXML
	private Label titleCurrentSongLabel;

	@FXML
	private Label authorCurrentSongLabel;

	@FXML
	private ImageView previousSongImageView;

	@FXML
	private ImageView playSongImageView;

	@FXML
	private ImageView nextSongImageView;
	
	@FXML
	private HBox shuffleHBox;
	
	@FXML
	private HBox repeatHBox;

	public MediaPlayerController() {
		musicPlayer = MusicPlayer.getInstance();
		dispatcher = ViewDispatcher.getInstance();
		UnifyBusinessFactory factory = UnifyBusinessFactory.getInstance();
		photoService = factory.getPhotoService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		progressBar.setOnMouseDragged(event -> {
			musicPlayer.getPlayer().seek(Duration.seconds(progressBar.getValue()));
		});

		progressBar.setOnMousePressed(event -> {
			musicPlayer.getPlayer().seek(Duration.seconds(progressBar.getValue()));
		});

		volumeImageView.setImage(volumeImage);

		volumeSlider.setValue(musicPlayer.getPlayer().getVolume() * 100);
		volumeSlider.valueProperty().addListener(invalidationListener -> {
			musicPlayer.getPlayer().setVolume(volumeSlider.getValue() / 100);
		});

		volumeImageView.setOnMousePressed(event -> {
			if (volumeImageView.getImage() == volumeImage) {
				volumeImageView.setImage(muteImage);
				musicPlayer.getPlayer().setMute(true);
			} else {
				volumeImageView.setImage(volumeImage);
				musicPlayer.getPlayer().setMute(false);
			}
		});

		playSongImageView.setImage(musicPlayer.getPlayerStatus() == PlayerStatus.PLAYING ? pauseImage : playImage);
	}

	@Override
	public void initializeData(Set<Song> songsToBePlayed, Object object) {
		songs.addAll(songsToBePlayed);
		updateSong();
	}

	private void updateSong() {

		currentSongImageView.setImage(
				new Image(photoService.toStream(songs.get(musicPlayer.getCounter()).getCover().getContent())));
		titleCurrentSongLabel.setText(songs.get(musicPlayer.getCounter()).getTitle() + " ");
		authorCurrentSongLabel.setText(songs.get(musicPlayer.getCounter()).getArtist().getName() + " ");

		// Update the scrollbar
		musicPlayer.getPlayer().currentTimeProperty().addListener(new ChangeListener<Duration>() {
			@Override
			public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
				progressBar.setValue(newValue.toSeconds());
			}
		});

		musicPlayer.getPlayer().setOnReady(new Runnable() {
			@Override
			public void run() {
				Duration total = musicPlayer.getPlayer().getMedia().getDuration();
				progressBar.setMax(total.toSeconds());
			}
		});

		// When the finishes, the next song will be played
		musicPlayer.getPlayer().setOnEndOfMedia(new Runnable() {
			@Override
			public void run() {
				nextSongAction();
			}
		});

		progressBar.valueProperty().addListener((observable, oldValue, newValue) -> {
			// To fix the percentage of the slider coloring
			double nvalue = newValue.doubleValue() / musicPlayer.getPlayer().getMedia().getDuration().toSeconds();
			nvalue *= 100;
			String style = String.format("-track-color: linear-gradient(to right, #2E99D3 %d%%, white %d%%);",
					(int) nvalue, (int) nvalue);
			progressBar.setStyle(style);
		});
	}

	@FXML
	public void playPauseAction() {

		if (musicPlayer.getPlayerStatus() == PlayerStatus.PLAYING) {
			musicPlayer.pauseSong();
			playSongImageView.setImage(playImage);
		} else {
			musicPlayer.playSong();
			playSongImageView.setImage(pauseImage);
		}
	}

	@FXML
	public void previousSongAction() {
		if (musicPlayer.getPlayerStatus() != PlayerStatus.NOT_RUNNING)
			playSongImageView.setImage(pauseImage);
		musicPlayer.previousSong();
		updateSong();
	}

	@FXML
	public void nextSongAction() {
		if (musicPlayer.getPlayerStatus() != PlayerStatus.NOT_RUNNING)
			playSongImageView.setImage(pauseImage);
		musicPlayer.nextSong();
		updateSong();
	}

	@FXML
	private void shuffleAction() {
		
		if (musicPlayer.getPlayingMode() != PlayingMode.SHUFFLING) {
			shuffleHBox.setStyle(selectedIcon);
			repeatHBox.setStyle(notSelectedIcon);
		} else {
			shuffleHBox.setStyle(notSelectedIcon);
		}
		musicPlayer.shuffleList(new Random());
	}

	@FXML
	private void repeatAction() {
		
		if (musicPlayer.getPlayingMode() != PlayingMode.REPEATING) {
			repeatHBox.setStyle(selectedIcon);
			shuffleHBox.setStyle(notSelectedIcon);
		} else {
			repeatHBox.setStyle(notSelectedIcon);
		}
		musicPlayer.repeatSong();
	}

	@FXML
	public void goToInformationsAction() {
		dispatcher.renderView("song-menu", songs.get(musicPlayer.getCounter()));
	}

	@FXML
	public void rotateTitle() {
		if (!titleClock) {
			clockTitle = new Timeline(new KeyFrame(Duration.ZERO, e -> { // to rotate the string
				String title = titleCurrentSongLabel.getText();
				title = title + title.charAt(0);
				title = title.substring(1);
				titleCurrentSongLabel.setText(title);
			}), new KeyFrame(Duration.millis(500))); // set the interval between a switch to another
			clockTitle.setCycleCount(Animation.INDEFINITE); // to repeat infinitely
			clockTitle.play(); // to start the clock
			titleClock = !titleClock;
		} else {
			clockTitle.pause();
			titleCurrentSongLabel.setText(songs.get(musicPlayer.getCounter()).getTitle() + " ");
			titleClock = !titleClock;
		}
	}

	@FXML
	public void rotateArtist() {
		if (!artistClock) {
			clockArtist = new Timeline(new KeyFrame(Duration.ZERO, e -> { // to rotate the string
				String title = authorCurrentSongLabel.getText();
				title = title + title.charAt(0);
				title = title.substring(1);
				authorCurrentSongLabel.setText(title);
			}), new KeyFrame(Duration.millis(500))); // set the interval between a switch to another
			clockArtist.setCycleCount(Animation.INDEFINITE); // to repeat infinitely
			clockArtist.play(); // to start the clock
			artistClock = !artistClock;
		} else {
			clockArtist.pause();
			authorCurrentSongLabel.setText(songs.get(musicPlayer.getCounter()).getArtist() + " ");
			artistClock = !artistClock;
		}
	}
}