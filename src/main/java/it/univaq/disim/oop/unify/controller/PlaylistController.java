package it.univaq.disim.oop.unify.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.univaq.disim.oop.unify.business.PhotoService;
import it.univaq.disim.oop.unify.business.PlaylistService;
import it.univaq.disim.oop.unify.business.SongService;
import it.univaq.disim.oop.unify.business.UnifyBusinessFactory;
import it.univaq.disim.oop.unify.business.exceptions.BusinessException;
import it.univaq.disim.oop.unify.domain.Artist;
import it.univaq.disim.oop.unify.domain.Playlist;
import it.univaq.disim.oop.unify.domain.Song;
import it.univaq.disim.oop.unify.domain.User;
import it.univaq.disim.oop.unify.musicplayer.MusicPlayer;
import it.univaq.disim.oop.unify.view.ViewDispatcher;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PlaylistController implements Initializable, DataInitializable<User, Playlist> {

	private ViewDispatcher dispatcher;
	private User user;
	private Playlist playlist;
	private SongService songService;
	private PlaylistService playlistService;
	private PhotoService photoService;

	private MusicPlayer musicPlayer;

	@FXML
	private Label playlistNameLabel;

	@FXML
	private ImageView deleteImageView;

	@FXML
	private ImageView playAllImageView;

	@FXML
	private ImageView modifyImageView;

	@FXML
	private Label modifyLabel;

	@FXML
	private Label deleteLabel;

	@FXML
	private Label playLabel;
	
	@FXML
	private Label errorLabel;

	@FXML
	private TableView<Song> songsTableView;

	@FXML
	private TableColumn<Song, String> titleTableColumn;

	@FXML
	private TableColumn<Song, Artist> artistTableColumn;

	@FXML
	private TableColumn<Song, ImageView> imageTableColumn;

	public PlaylistController() {
		UnifyBusinessFactory factory = UnifyBusinessFactory.getInstance();
		songService = factory.getSongService();
		playlistService = factory.getPlaylistService();
		photoService = factory.getPhotoService();
		dispatcher = ViewDispatcher.getInstance();
		musicPlayer = MusicPlayer.getInstance();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// Clicking on the row show the song
		songsTableView.setRowFactory(tv -> {
			TableRow<Song> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				// Prevents the user from clicking on an empty line
				if (row.getItem() == null)
					return;
				dispatcher.renderView("song-menu", row.getItem());

			});
			return row;
		});

		titleTableColumn.setCellValueFactory(new PropertyValueFactory<Song, String>("title"));
		artistTableColumn.setCellValueFactory((CellDataFeatures<Song, Artist> param) -> {
			return new SimpleObjectProperty<Artist>(param.getValue().getArtist());
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

		List<Song> songs = new ArrayList<>(playlist.getSongs());
		ObservableList<Song> songsData = FXCollections.observableArrayList(songs);
		songsTableView.setItems((ObservableList<Song>) songsData);
		playlistNameLabel.setText(playlist.getName());

	}

	@FXML
	public void deletePlaylist() {

		try {
			musicPlayer.resetThePlayer();
			playlistService.deletePlaylist(playlist, user);
			dispatcher.renderView("playlist-hub", user);

		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void modifyPlaylist() {
		dispatcher.renderView("modify-playlist", user, playlist);
	}

	@FXML
	private void playAllAction() {
		try {
			if(playlist.getSongs().isEmpty()) throw new BusinessException();
			musicPlayer.queueSongs(playlist.getSongs());
			dispatcher.renderMediaPlayer("mediaplayer", playlist.getSongs());
		} catch (BusinessException e) {
			errorLabel.setText("The playlist is empty!");
		}
	}
}