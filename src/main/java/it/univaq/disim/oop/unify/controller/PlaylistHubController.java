package it.univaq.disim.oop.unify.controller;

import java.net.URL;
import java.util.ResourceBundle;

import it.univaq.disim.oop.unify.business.PlaylistService;
import it.univaq.disim.oop.unify.business.UnifyBusinessFactory;
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
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.text.TextAlignment;

public class PlaylistHubController implements Initializable, DataInitializable<User, Playlist> {

	private ViewDispatcher dispatcher;
	private PlaylistService playlistService;
	private User user;
	private Playlist playlist;

	@FXML
	private TableView<Playlist> playlistsTableView;

	@FXML
	private TableColumn<Playlist, String> nameTableColumn;

	@FXML
	private ImageView addPlaylistImageView;

	@FXML
	private Label addLabel;

	public PlaylistHubController() {
		dispatcher = ViewDispatcher.getInstance();
		UnifyBusinessFactory factory = UnifyBusinessFactory.getInstance();
		playlistService = factory.getPlaylistService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// Clicking on the row show the song
		playlistsTableView.setRowFactory(tv -> {
			TableRow<Playlist> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				// Prevents the user from clicking on an empty line
				if (row.getItem() == null)
					return;
				dispatcher.renderView("playlist", user, row.getItem());
			});
			row.setMinHeight(40);
			return row;
		});
		nameTableColumn.setCellValueFactory(new PropertyValueFactory<Playlist, String>("name"));
		nameTableColumn.setStyle("-fx-alignment: CENTER-LEFT");
	}

	@Override
	public void initializeData(User user, Playlist playlist) {
		this.user = user;
		this.playlist = playlist;

		try {
			ObservableList<Playlist> playlistsData = FXCollections
					.observableArrayList(playlistService.getPlaylists(user));
			playlistsTableView.setItems((ObservableList<Playlist>) playlistsData);
		} catch (Exception e) {
			dispatcher.renderError(e);
		}
	}

	@FXML
	private void newPlaylistAction() {
		dispatcher.renderView("playlist-create", user);
	}

}
