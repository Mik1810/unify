package it.univaq.disim.oop.unify.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ResourceBundle;

import it.univaq.disim.oop.unify.business.ArtistService;
import it.univaq.disim.oop.unify.business.PhotoService;
import it.univaq.disim.oop.unify.business.SongService;
import it.univaq.disim.oop.unify.business.UnifyBusinessFactory;
import it.univaq.disim.oop.unify.business.exceptions.BusinessException;
import it.univaq.disim.oop.unify.domain.Album;
import it.univaq.disim.oop.unify.domain.Genre;
import it.univaq.disim.oop.unify.domain.Photo;
import it.univaq.disim.oop.unify.domain.Song;
import it.univaq.disim.oop.unify.view.ViewDispatcher;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ModifySongController implements Initializable, DataInitializable<Album, Song> {

	private SongService songService;
	private PhotoService photoService;
	private ViewDispatcher dispatcher;
	private Song song;
	private Album album;
	private File image;
	private File content;
	private String title;
	private String lyrics;
	private Genre genre;
	
	@FXML
	private HBox leftHBox;
	
	@FXML
	private VBox leftVBox;

	@FXML
	private VBox rightVBox;

	@FXML
	private Button addFileButton;

	@FXML
	private Button addImageButton;

	@FXML
	private Button finishButton;

	@FXML
	private ComboBox<Genre> genreComboBox;

	@FXML
	private ImageView coverImageView;

	@FXML
	private Label errorLabel;

	@FXML
	private Label selectFileLabel;

	@FXML
	private Label selectImageLabel;

	@FXML
	private Label titleLabel;

	@FXML
	private TextField nameTextField;

	@FXML
	private TextArea lyricsTextArea;

	public ModifySongController() {
		dispatcher = ViewDispatcher.getInstance();
		UnifyBusinessFactory factory = UnifyBusinessFactory.getInstance();
		songService = factory.getSongService();
		photoService = factory.getPhotoService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		genreComboBox.setItems(FXCollections.observableArrayList(Genre.values()));
		genreComboBox.getSelectionModel().selectFirst();
	}

	@Override
	public void initializeData(Album album, Song song) {

		this.song = song;
		this.album = album;

		if (song == null) { // adding a song
			titleLabel.setText("Add a song in " + album.getTitle() + " for " + album.getArtist().getName());

		} else { // modifing a song
			titleLabel.setText("Modify song: " + song.getTitle());
			leftHBox.getChildren().remove(addFileButton);
			leftHBox.getChildren().remove(selectFileLabel);
			rightVBox.getChildren().remove(lyricsTextArea);
			nameTextField.setMaxWidth(218);
			nameTextField.setText(song.getTitle());
			genreComboBox.getSelectionModel().select(song.getGenre());
			coverImageView.setImage(new Image(photoService.toStream(song.getCover().getContent())));
		}

	}

	@FXML
	private void selectImageAction(ActionEvent event) {
		try {
			FileChooser fc = new FileChooser();
			fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPG", "*.jpg"));
			fc.setInitialDirectory(new File(System.getProperty("user.home")));
			Stage stage = new Stage();
			image = fc.showOpenDialog(stage);

			selectImageLabel.setText(image.getName());
			coverImageView.setImage(new Image(image.getPath()));
			selectImageLabel.setStyle("-fx-text-fill: black; -fx-font-weight: bold");
			errorLabel.setText("");
		} catch (NullPointerException e) {
			errorLabel.setText("You have to select an image!");
		}
	}

	@FXML
	private void addFileAction(ActionEvent event) {
		try {
			FileChooser fc = new FileChooser();
			fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("MP3", "*.mp3"));
			fc.setInitialDirectory(new File(System.getProperty("user.home")));
			Stage stage = new Stage();
			content = fc.showOpenDialog(stage);

			selectFileLabel.setText(content.getName());
			selectFileLabel.setStyle("-fx-text-fill: black; -fx-font-weight: bold");
			errorLabel.setText("");
		} catch (NullPointerException e) {
			errorLabel.setText("You have to select a file song!");
		}
	}

	@FXML
	private void finishAction(ActionEvent event) {
		try {
			if (song == null) { //creating a song
				Song newSong = new Song();
				newSong.setTitle(nameTextField.getText());
				newSong.setText(lyricsTextArea.getText());
				newSong.setAlbum(album);
				newSong.setArtist(album.getArtist());
				newSong.setGenre(genreComboBox.getValue());
				newSong.setContent(Files.readAllBytes(content.toPath()));
				newSong.setCover(new Photo(Files.readAllBytes(image.toPath())));
				songService.addSong(newSong);

				dispatcher.renderView("add-more-songs", album);
			} else { // modifing a song
				photoService.modifyImageOfSong(song, new Photo(Files.readAllBytes(image.toPath())));
				songService.modifyGenreOfSong(song, genreComboBox.getValue());
				songService.modifyTitleOfSong(song, nameTextField.getText());
				dispatcher.renderView("song-settings", null);
			}
		} catch (IOException e) {
			errorLabel.setText("Unable to read the image!");
		} catch (BusinessException e) {
			errorLabel.setText("Unable to create the song!");
		} catch (NullPointerException e) {
			errorLabel.setText("You must select the files!");
		}
	}

}
