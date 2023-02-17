package it.univaq.disim.oop.unify.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.univaq.disim.oop.unify.Utility;
import it.univaq.disim.oop.unify.business.PhotoService;
import it.univaq.disim.oop.unify.business.PlaylistService;
import it.univaq.disim.oop.unify.business.SongService;
import it.univaq.disim.oop.unify.business.UnifyBusinessFactory;
import it.univaq.disim.oop.unify.business.exceptions.BusinessException;
import it.univaq.disim.oop.unify.domain.Playlist;
import it.univaq.disim.oop.unify.domain.Song;
import it.univaq.disim.oop.unify.domain.User;
import it.univaq.disim.oop.unify.view.ViewDispatcher;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CreatePlaylistController implements Initializable, DataInitializable<User, Object> {

	private PlaylistService playlistService;
	private SongService songService;
	private PhotoService photoService;
	private ViewDispatcher dispatcher;
	private Playlist playlist;
	private User user;
	private String songsAdded = "";

	@FXML
	private Label playlistNameLabel;

	@FXML
	private Label errorLabel;

	@FXML
	private Label songsAddedLabel;

	@FXML
	private TextField namePlaylistTextField;

	@FXML
	private Button createPlaylistButton;

	@FXML
	private Button finishButton;

	@FXML
	private TableView<Song> songsTableView;

	@FXML
	private TableColumn<Song, String> songsTableColumn;

	@FXML
	private TableColumn<Song, Button> addButtonTableColumn;
	
	@FXML
	private TableColumn<Song, ImageView> imageTableColumn;

	public CreatePlaylistController() {
		UnifyBusinessFactory factory = UnifyBusinessFactory.getInstance();
		songService = factory.getSongService();
		photoService = factory.getPhotoService();
		playlistService = factory.getPlaylistService();
		dispatcher = ViewDispatcher.getInstance();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		//createPlaylistButton.setStyle("-fx-background-color: transparent;");

		finishButton.setDisable(true);
		songsAddedLabel.setVisible(false);
		playlistNameLabel.setVisible(false);
		createPlaylistButton.disableProperty().bind(namePlaylistTextField.textProperty().isEmpty());
		songsTableColumn.setCellValueFactory(new PropertyValueFactory<Song, String>("title"));
		addButtonTableColumn.setStyle("-fx-alignment: CENTER;");
		addButtonTableColumn.setCellValueFactory((CellDataFeatures<Song, Button> param) -> {
			final Button addButton = new Button();
			ImageView addSongIcon = new ImageView(Utility.ICON_PATH + "icons8-plus-48.png");
			addSongIcon.setFitHeight(30);
			addSongIcon.setFitWidth(30);
			addButton.setGraphic(addSongIcon);
			addButton.setStyle("-fx-background-color: transparent;");
			addButton.setOnAction((ActionEvent event) -> {
				addSongs(param.getValue());
				ImageView checkIcon = new ImageView(Utility.ICON_PATH + "check.png");
				checkIcon.setFitHeight(30);
				checkIcon.setFitWidth(30);
				addButton.setGraphic(checkIcon);
				addButton.setDisable(true);
			});
			return new SimpleObjectProperty<Button>(addButton);
		});
		imageTableColumn.setCellValueFactory((CellDataFeatures<Song, ImageView> param) -> {
			ImageView imgV = new ImageView(new Image(photoService.toStream(param.getValue().getCover().getContent())));
			imgV.setFitHeight(30);
			imgV.setFitWidth(30);
			return new SimpleObjectProperty<ImageView>(imgV);
		});
		songsTableView.setDisable(true);

	}

	@Override
	public void initializeData(User user, Object object) {
		this.user = user;
		try {
			List<Song> songs = new ArrayList<>(songService.getAllSongs());
			ObservableList<Song> songsData = FXCollections.observableArrayList(songs);
			songsTableView.setItems((ObservableList<Song>) songsData);
		} catch (BusinessException e) {
			e.printStackTrace();;
		}
	}

	@FXML
	public void createPlaylist(ActionEvent event) {
		try {

			playlistNameLabel.setVisible(true);

			playlist = new Playlist();
			playlist.setName(namePlaylistTextField.getText());

			playlistService.createPlaylist(playlist, user);

			namePlaylistTextField.setDisable(true);
			namePlaylistTextField.setText("");
			playlistNameLabel.setText(playlistNameLabel.getText() + playlist.getName());
			songsTableView.setDisable(false);
		} catch (BusinessException e) {
			errorLabel.setText("Unable to create the playlist!");
		}

	}

	private void addSongs(Song song) {
		try {
			songsAddedLabel.setVisible(true);
			finishButton.setDisable(false);
			playlistService.addSong(playlist, song);
			songsAdded = songsAdded + song.getTitle() + ", ";
			songsAddedLabel.setText("Songs added: " + songsAdded.substring(0, songsAdded.length() - 2));
		} catch (BusinessException e) {
			errorLabel.setText("Unable to add songs!");
		}
	}

	@FXML
	public void finishAction(ActionEvent event) {
		dispatcher.renderView("playlist-hub", user);
	}
}
