package it.univaq.disim.oop.unify.controller;

import java.net.URL;
import java.util.ResourceBundle;

import it.univaq.disim.oop.unify.business.ArtistService;
import it.univaq.disim.oop.unify.business.PlaylistService;
import it.univaq.disim.oop.unify.business.SongService;
import it.univaq.disim.oop.unify.business.UnifyBusinessFactory;
import it.univaq.disim.oop.unify.domain.Administrator;
import it.univaq.disim.oop.unify.domain.Person;
import it.univaq.disim.oop.unify.domain.User;
import it.univaq.disim.oop.unify.musicplayer.MusicPlayer;
import it.univaq.disim.oop.unify.view.ViewDispatcher;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class LayoutController implements Initializable, DataInitializable<Person, Object> {

	private ViewDispatcher dispatcher;
	private Person person;
	private User user;
	private SongService songService;
	private ArtistService artistService;
	private PlaylistService playlistService;
	private MusicPlayer musicPlayer;

	@FXML
	private HBox playlistHBox;

	@FXML
	private HBox settingsHBox;

	@FXML
	private HBox userHBox;

	@FXML
	private VBox selectionMenuVBox;

	@FXML
	private Label usernameLabel;

	@FXML
	private ComboBox<Searchable> comboBox;

	@FXML
	private ImageView userImageView;

	@FXML
	private Label usersLabel;

	@FXML
	private ImageView settingsImageView;

	@FXML
	private Label settingsLabel;

	@FXML
	private ImageView homeImage;

	@FXML
	private ImageView searchIconImage;

	@FXML
	private TextField searchbarTextField;

	@FXML
	private ImageView logoutImage;

	@FXML
	private ImageView playlistIconImage;

	@FXML
	private Label playlistLabel;

	@FXML
	private ImageView albumIconImage;

	@FXML
	private Label albumLabel;

	@FXML
	private ImageView songIconImage;

	@FXML
	private Label songsLabel;

	@FXML
	private ImageView artistsImageView;

	@FXML
	private Label artistsLabel;

	public LayoutController() {
		dispatcher = ViewDispatcher.getInstance();
		musicPlayer = MusicPlayer.getInstance();

		UnifyBusinessFactory factory = UnifyBusinessFactory.getInstance();
		songService = factory.getSongService();
		artistService = factory.getArtistService();
		playlistService = factory.getPlaylistService();

	}

	private enum Searchable {
		SONG, GENRE, ALBUM, ARTIST
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		comboBox.setItems(FXCollections.observableArrayList(Searchable.values()));
		comboBox.getSelectionModel().selectFirst();
	}

	@Override
	public void initializeData(Person person, Object object) {

		this.person = person;

		if (person instanceof User) {

			// to capitalize the first letter of the username
			usernameLabel.setText("Welcome " + person.getUsername().substring(0, 1).toUpperCase()
					+ person.getUsername().substring(1));
			settingsImageView.setVisible(false);
			settingsLabel.setVisible(false);
			usersLabel.setText("Account");
			selectionMenuVBox.getChildren().remove(settingsHBox);
		}
		if (person instanceof Administrator) {
			usernameLabel.setText("Welcome Admin");
			selectionMenuVBox.getChildren().remove(playlistHBox);
		}
	}

	@FXML
	public void logoutAction() {
		musicPlayer.exit();
		dispatcher.logout();
	}

	@FXML
	public void songsAction() {
		dispatcher.renderView("songs-table", null);
	}

	@FXML
	public void albumAction() {
		dispatcher.renderView("album-table", null);
	}

	@FXML
	public void homeAction() {
		dispatcher.renderView("home", person);
	}

	@FXML
	private void playlistAction() {
		dispatcher.renderView("playlist-hub", (User) person);

	}

	@FXML
	private void artistsAction() {
		dispatcher.renderView("artists-table", null);
	}

	@FXML
	public void userSettingsAction() {
		if (person instanceof Administrator) { // I am an Admin
			dispatcher.renderView("user-settings", (Administrator) person);
		} else { // I am an user
			dispatcher.renderView("modify-user", null, (User)person);
		}
	}

	@FXML
	public void settingsAction() {
		dispatcher.renderView("settings", null);
	}

	public void searchAction() {
		switch (comboBox.getValue()) {

		case SONG:
			dispatcher.renderView("songs-table", searchbarTextField.getText());
			break;

		case GENRE:
			dispatcher.renderView("genre-table", searchbarTextField.getText());
			break;

		case ALBUM:
			dispatcher.renderView("album-table", searchbarTextField.getText());
			break;

		case ARTIST:
			dispatcher.renderView("artists-table", searchbarTextField.getText());
			break;
		}
	}
}