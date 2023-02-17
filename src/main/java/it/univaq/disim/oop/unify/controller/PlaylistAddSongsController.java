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
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PlaylistAddSongsController implements Initializable, DataInitializable<User, Playlist> {

	private ViewDispatcher dispatcher;
	private PlaylistService playlistService;
	private PhotoService photoService;
	private SongService songService;
	private User user;
	private Playlist playlist;

	@FXML
	private TableView<Song> songsTableView;

	@FXML
	private TableColumn<Song, String> titleTableColumn;

	@FXML
	private TableColumn<Song, String> artistTableColumn;

	@FXML
	private TableColumn<Song, Button> addTableColumn;

	@FXML
	private TableColumn<Song, ImageView> imageTableColumn;

	@FXML
	private Label namePlaylistLabel;

	public PlaylistAddSongsController() {
		UnifyBusinessFactory factory = UnifyBusinessFactory.getInstance();
		songService = factory.getSongService();
		playlistService = factory.getPlaylistService();
		photoService = factory.getPhotoService();
		dispatcher = ViewDispatcher.getInstance();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		titleTableColumn.setCellValueFactory(new PropertyValueFactory<Song, String>("title"));
		artistTableColumn.setCellValueFactory((CellDataFeatures<Song, String> param) -> {
			return new SimpleStringProperty(param.getValue().getArtist().getName());
		});
		addTableColumn.setCellValueFactory((CellDataFeatures<Song, Button> param) -> {
			final Button addButton = new Button();
			ImageView addSongIcon = new ImageView(Utility.ICON_PATH + "icons8-plus-48.png");
			addSongIcon.setFitHeight(30);
			addSongIcon.setFitWidth(30);
			addButton.setGraphic(addSongIcon);
			addButton.setStyle("-fx-background-color: transparent;");
			addButton.setOnAction((ActionEvent event) -> {
				try {
					playlistService.addSong(playlist, param.getValue());
					ImageView checkIcon = new ImageView(Utility.ICON_PATH + "check.png");
					checkIcon.setFitHeight(30);
					checkIcon.setFitWidth(30);
					addButton.setGraphic(checkIcon);
					addButton.setDisable(true);
				} catch (BusinessException e) {
					e.printStackTrace();
				}
			});
			return new SimpleObjectProperty<Button>(addButton);
		});
		imageTableColumn.setCellValueFactory((CellDataFeatures<Song, ImageView> param) -> {
			ImageView imgV = new ImageView(new Image(photoService.toStream(param.getValue().getCover().getContent())));
			imgV.setFitHeight(30);
			imgV.setFitWidth(30);
			return new SimpleObjectProperty<ImageView>(imgV);
		});
	}

	@Override
	public void initializeData(User user, Playlist playlist) {
		this.user = user;
		this.playlist = playlist;
		try {
			namePlaylistLabel.setText(namePlaylistLabel.getText() + playlist.getName());
			List<Song> songsNotInThePlaylist = new ArrayList<>(songService.getAllSongs());
			songsNotInThePlaylist.removeAll(playlist.getSongs());
			ObservableList<Song> songsData = FXCollections.observableArrayList(songsNotInThePlaylist);
			songsTableView.setItems((ObservableList<Song>) songsData);
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void finishAction(ActionEvent event) {
		try {
			dispatcher.renderView("playlist", user, playlistService.getPlaylistByID(playlist.getId(), user));
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}

}
