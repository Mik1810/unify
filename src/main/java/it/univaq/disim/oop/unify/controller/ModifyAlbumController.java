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
import it.univaq.disim.oop.unify.domain.Artist;
import it.univaq.disim.oop.unify.domain.Genre;
import it.univaq.disim.oop.unify.domain.Photo;
import it.univaq.disim.oop.unify.view.ViewDispatcher;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ModifyAlbumController implements Initializable, DataInitializable<Album, Artist> {

	private ViewDispatcher dispatcher;
	private ArtistService artistService;
	private SongService songService;
	private PhotoService photoService;
	private Album album;
	private Artist artist;
	private File image;

	@FXML
	private Button addSongsButton;

	@FXML
	private Button changeImageButton;

	@FXML
	private Button finishButton;

	@FXML
	private ComboBox<Genre> genreComboBox;

	@FXML
	private ImageView coverImageView;

	@FXML
	private Label changeImageLabel;

	@FXML
	private Label errorLabel;

	@FXML
	private Label modifyLabel;

	@FXML
	private Label artistLabel;

	@FXML
	private TextField titleTextField;

	public ModifyAlbumController() {
		dispatcher = ViewDispatcher.getInstance();
		UnifyBusinessFactory factory = UnifyBusinessFactory.getInstance();
		artistService = factory.getArtistService();
		songService = factory.getSongService();
		photoService = factory.getPhotoService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		genreComboBox.setItems(FXCollections.observableArrayList(Genre.values()));
	}

	@Override
	public void initializeData(Album album, Artist artist) {

		this.album = album;
		this.artist = artist;

		if (album == null) { // creating an album
			genreComboBox.getSelectionModel().selectFirst();
			changeImageButton.setText("Add");
			artistLabel.setText(artistLabel.getText() + artist.getName());
			addSongsButton.setVisible(false);
			addSongsButton.setManaged(false);
		} else { // modifing an album
			genreComboBox.getSelectionModel().select(album.getGenre());
			modifyLabel.setText(modifyLabel.getText() + album.getTitle());
			coverImageView.setImage(new Image(photoService.toStream(album.getCover().getContent())));
			titleTextField.setText(album.getTitle());
			artistLabel.setText(artistLabel.getText() + artist.getName());
		}
	}

	@FXML
	private void changeImageAction(ActionEvent event) throws BusinessException {
		try {
			FileChooser fc = new FileChooser();
			fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPG", "*.jpg"));
			fc.setInitialDirectory(new File(System.getProperty("user.home")));
			Stage stage = new Stage();
			image = fc.showOpenDialog(stage);

			changeImageLabel.setText(image.getName());
			coverImageView.setImage(new Image(image.getPath()));
			changeImageLabel.setStyle("-fx-text-fill: black; -fx-font-weight: bold");
			errorLabel.setText("");

		} catch (NullPointerException e) {
			errorLabel.setText("You have to select an image!");
		}
	}

	@FXML
	private void finishAction(ActionEvent event) {
		try {
			if (this.album == null) { // creating an album
				Album newAlbum = new Album();
				newAlbum.setTitle(titleTextField.getText());
				newAlbum.setArtist(this.artist);
				newAlbum.setGenre(genreComboBox.getValue());
				newAlbum.setCover(new Photo(Files.readAllBytes(image.toPath())));
				songService.addAlbum(newAlbum);
				dispatcher.renderView("modify-song", newAlbum, null);
			} else { //modifing an album
				photoService.modifyImageOfAlbum(album, new Photo(Files.readAllBytes(image.toPath())));
				songService.modifyGenreOfAlbum(album, genreComboBox.getValue());
				songService.modifyTitleOfAlbum(album, titleTextField.getText());
				dispatcher.renderView("album-settings", null);
			}
		} catch (IOException e) {
			errorLabel.setText("Unable to read the image!");
		} catch (BusinessException e) {
			errorLabel.setText("Unable to create the album!");
		}
	}

	@FXML
	private void addSongsAction(ActionEvent event) {
		dispatcher.renderView("modify-song", album, null);
	}
}
