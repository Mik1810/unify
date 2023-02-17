package it.univaq.disim.oop.unify.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.univaq.disim.oop.unify.Utility;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ModifyPlaylistController implements Initializable, DataInitializable<User, Playlist> {

	private ViewDispatcher dispatcher;
	private PlaylistService playlistService;
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
	private TableColumn<Song, Button> removeTableColumn;

	@FXML
	private Button addSongsButton;

	@FXML
	private Button finishButton;

	@FXML
	private Label playlistNameLabel;

	@FXML
	private TextField changeNameTextField;

	public ModifyPlaylistController() {
		UnifyBusinessFactory factory = UnifyBusinessFactory.getInstance();
		songService = factory.getSongService();
		playlistService = factory.getPlaylistService();

		dispatcher = ViewDispatcher.getInstance();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		titleTableColumn.setCellValueFactory(new PropertyValueFactory<Song, String>("title"));
		artistTableColumn.setCellValueFactory((CellDataFeatures<Song, String> param) -> {
			return new SimpleStringProperty(param.getValue().getArtist().getName());
		});
		removeTableColumn.setStyle("-fx-alignment: CENTER;");
		removeTableColumn.setCellValueFactory((CellDataFeatures<Song, Button> param) -> {
			final Button removeButton = new Button();
			ImageView imageView = new ImageView(new Image(Utility.ICON_PATH + "remove.png"));
			imageView.setFitHeight(25);
			imageView.setFitWidth(25);
			removeButton.setGraphic(imageView);
			removeButton.setStyle("-fx-background-color: transparent;");
			removeButton.setOnAction((ActionEvent event) -> {
				try {
					playlistService.removeSong(playlist, param.getValue());
					removeButton.setDisable(true);
				} catch (BusinessException e) {
					e.printStackTrace();
				}
			});
			return new SimpleObjectProperty<Button>(removeButton);
		});
	}

	@Override
	public void initializeData(User user, Playlist playlist) {
		this.user = user;
		this.playlist = playlist;

		changeNameTextField.setText(playlist.getName());

		List<Song> songs = new ArrayList<>(playlist.getSongs());
		ObservableList<Song> songsData = FXCollections.observableArrayList(songs);
		songsTableView.setItems((ObservableList<Song>) songsData);

	}

	@FXML
	public void finishAction(ActionEvent event) {
		try {
			playlistService.changePlaylistName(playlist, changeNameTextField.getText());
			dispatcher.renderView("playlist", user, playlistService.getPlaylistByID(playlist.getId(), user));
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void addSongsAction(ActionEvent event) {
		dispatcher.renderView("playlist-add-songs", user, playlist);
	}
}
