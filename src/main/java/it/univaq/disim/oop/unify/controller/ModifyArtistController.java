package it.univaq.disim.oop.unify.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.univaq.disim.oop.unify.business.ArtistService;
import it.univaq.disim.oop.unify.business.PhotoService;
import it.univaq.disim.oop.unify.business.SongService;
import it.univaq.disim.oop.unify.business.UnifyBusinessFactory;
import it.univaq.disim.oop.unify.business.exceptions.BusinessException;
import it.univaq.disim.oop.unify.domain.Album;
import it.univaq.disim.oop.unify.domain.Artist;
import it.univaq.disim.oop.unify.domain.Band;
import it.univaq.disim.oop.unify.domain.IndividualArtist;
import it.univaq.disim.oop.unify.domain.Photo;
import it.univaq.disim.oop.unify.domain.Song;
import it.univaq.disim.oop.unify.view.ViewDispatcher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
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

public class ModifyArtistController implements Initializable, DataInitializable<Artist, Object> {

	private ViewDispatcher dispatcher;
	private ArtistService artistService;
	private SongService songService;
	private PhotoService photoService;
	private Artist artist;
	private File image1;
	private File image2;

	private enum Role {
		INDIVIDUAL, BAND
	}

	@FXML
	private VBox parentVBox;

	@FXML
	private HBox songsHBox;

	@FXML
	private HBox albumsHBox;

	@FXML
	private ImageView image1ImageView;

	@FXML
	private ImageView image2ImageView;

	@FXML
	private TextField nameTextField;

	@FXML
	private TextArea biographyTextArea;

	@FXML
	private Button modifyMembersButton;

	@FXML
	private Button addAlbumButton;

	@FXML
	private Button changeImage1Button;

	@FXML
	private Button changeImage2Button;

	@FXML
	private Button confirmButton;

	@FXML
	private Label modifyArtistLabel;

	@FXML
	private Label image1Label;

	@FXML
	private Label image2Label;

	@FXML
	private Label errorLabel;

	@FXML
	private ComboBox<Song> songsComboBox;

	@FXML
	private ComboBox<Album> albumsComboBox;

	@FXML
	private ComboBox<Role> roleComboBox;

	public ModifyArtistController() {
		dispatcher = ViewDispatcher.getInstance();
		UnifyBusinessFactory factory = UnifyBusinessFactory.getInstance();
		artistService = factory.getArtistService();
		songService = factory.getSongService();
		photoService = factory.getPhotoService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		songsComboBox.getSelectionModel().selectFirst();
		albumsComboBox.getSelectionModel().selectFirst();
	}

	@Override
	public void initializeData(Artist artist, Object object) {

		this.artist = artist;

		if (this.artist != null) {
			if (!(this.artist instanceof Band)) {
				modifyMembersButton.setDisable(true);
				modifyMembersButton.setVisible(false);
			}
			try {
				modifyArtistLabel.setText("Modify: " + artist.getName());
				List<Photo> photos = new ArrayList<>(photoService.getPhotosFromArtist(artist));
				image1ImageView.setImage(new Image(photoService.toStream(photos.get(0).getContent())));
				image2ImageView.setImage(new Image(photoService.toStream(photos.get(1).getContent())));
				nameTextField.setText(artist.getName());
				biographyTextArea.setText(artist.getBiography());
				refreshSongsComboBox();
				refreshAlbumComboBox();
			} catch (BusinessException e) {
				e.printStackTrace();
			}
		} else { // artist == null -> adding an artist
			modifyArtistLabel.setText("Add: ");
			parentVBox.getChildren().remove(songsHBox);
			parentVBox.getChildren().remove(albumsHBox);
			ObservableList<Role> roles = FXCollections.observableArrayList(Role.values());
			roleComboBox = new ComboBox<Role>(roles);
			roleComboBox.getSelectionModel().selectFirst();
			roleComboBox.setPrefSize(425, 20);
			HBox hbox = new HBox(roleComboBox);
			hbox.setPrefSize(350, 30);
			hbox.setAlignment(Pos.CENTER_LEFT);
			parentVBox.getChildren().add(hbox);
			changeImage1Button.setText("Add");
			changeImage2Button.setText("Add");
			addAlbumButton.setDisable(true);
			modifyMembersButton.setDisable(true);
			addAlbumButton.setVisible(false);
			modifyMembersButton.setVisible(false);
		}
	}

	@FXML
	public void changeImage1Action(ActionEvent event) {
		try {
			FileChooser fc = new FileChooser();
			fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPG", "*.jpg"));
			fc.setInitialDirectory(new File(System.getProperty("user.home")));
			Stage stage = new Stage();
			image1 = fc.showOpenDialog(stage);

			image1ImageView.setImage(new Image(image1.getPath()));

			image1Label.setStyle("-fx-text-fill: black; -fx-font-weight: bold");
			image1Label.setText(image1.getName());
			errorLabel.setText("");
		} catch (NullPointerException e2) {
			errorLabel.setText("You have to select an image!");
		}
	}

	@FXML
	public void changeImage2Action(ActionEvent event) {
		try {
			FileChooser fc = new FileChooser();
			fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPG", "*.jpg"));
			fc.setInitialDirectory(new File(System.getProperty("user.home")));
			Stage stage = new Stage();
			image2 = fc.showOpenDialog(stage);

			image2ImageView.setImage(new Image(image2.getPath()));

			image2Label.setStyle("-fx-text-fill: black; -fx-font-weight: bold");
			image2Label.setText(image2.getName());
			errorLabel.setText("");
		} catch (NullPointerException e2) {
			errorLabel.setText("You have to select an image!");
		}
	}

	public void refreshSongsComboBox() {
		try {
			songsComboBox.setItems(FXCollections.observableArrayList(songService.getSongsFromArtist(artist)));
			songsComboBox.getSelectionModel().selectFirst();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}

	public void refreshAlbumComboBox() {
		try {
			albumsComboBox.setItems(FXCollections.observableArrayList(songService.getAlbumFromArtist(artist)));
			albumsComboBox.getSelectionModel().selectFirst();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void removeSongsAction(ActionEvent event) {
		try {
			songService.deleteSong(songsComboBox.getValue());
			refreshSongsComboBox();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void removeAlbumAction(ActionEvent event) {
		try {
			songService.deleteAlbum(albumsComboBox.getValue());
			albumsComboBox.getSelectionModel().selectFirst();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void confirmAction(ActionEvent event) {
		try {
			if (this.artist == null) { // Add an artist
				try {
					if (roleComboBox.getValue() == Role.INDIVIDUAL) { //if an artist is individual
						IndividualArtist newArtist = new IndividualArtist();
						newArtist.setName(nameTextField.getText());
						newArtist.setBiography(biographyTextArea.getText());
						newArtist.getPhotos().add(new Photo(Files.readAllBytes(image1.toPath())));
						newArtist.getPhotos().add(new Photo(Files.readAllBytes(image2.toPath())));

						artistService.addIndividualArtist(newArtist);
						dispatcher.renderView("selection", newArtist);
					} else { //if an a rtist is a band
						Band newBand = new Band();
						newBand.setName(nameTextField.getText());
						newBand.setBiography(biographyTextArea.getText());
						newBand.getPhotos().add(new Photo(Files.readAllBytes(image1.toPath())));
						newBand.getPhotos().add(new Photo(Files.readAllBytes(image2.toPath())));
						dispatcher.renderView("add-members", artistService.addBand(newBand));
					}
				} catch (NullPointerException e) {
					errorLabel.setText("You must select two images!");
				}
			} else { // Modify an artist
				if (image1 != null && image2 != null) {
					photoService.setPhoto1(new Photo(Files.readAllBytes(image1.toPath())), artist);
					photoService.setPhoto2(new Photo(Files.readAllBytes(image2.toPath())), artist);
				} else if (image1 != null) {
					photoService.setPhoto1(new Photo(Files.readAllBytes(image1.toPath())), artist);
				} else if (image2 != null) {
					photoService.setPhoto2(new Photo(Files.readAllBytes(image2.toPath())), artist);
				}
				artistService.changeName(artist, nameTextField.getText());
				artist.setBiography(biographyTextArea.getText());
				dispatcher.renderView("artist-settings", null);
			}
		} catch (IOException e) {
			errorLabel.setText("The image doesn't exist or the name is wrong");
		} catch (BusinessException e) {
			errorLabel.setText("Cannot find the role of the artist!");
		}
	}

	@FXML
	private void addAlbumAction(ActionEvent event) {
		dispatcher.renderView("modify-album", null ,artist);
	}
	
	@FXML
	private void modifyMembersAction(ActionEvent event) {
		dispatcher.renderView("modify-members", artist);
	}
}