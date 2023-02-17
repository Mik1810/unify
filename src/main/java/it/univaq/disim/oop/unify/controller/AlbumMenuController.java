package it.univaq.disim.oop.unify.controller;

import java.net.URL;
import java.util.ResourceBundle;

import it.univaq.disim.oop.unify.Utility;
import it.univaq.disim.oop.unify.business.PhotoService;
import it.univaq.disim.oop.unify.business.SongService;
import it.univaq.disim.oop.unify.business.UnifyBusinessFactory;
import it.univaq.disim.oop.unify.business.exceptions.BusinessException;
import it.univaq.disim.oop.unify.domain.Album;
import it.univaq.disim.oop.unify.domain.Song;
import it.univaq.disim.oop.unify.musicplayer.MusicPlayer;
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
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AlbumMenuController implements Initializable, DataInitializable<Album, Object> {

	private ViewDispatcher dispatcher;
	private MusicPlayer musicPlayer;
	private SongService songService;
	private PhotoService photoService;
	private Album album;

	@FXML
	private Button playAllButton;

	@FXML
	private ImageView albumImageView;

	@FXML
	private Label albumMenuLabel;

	@FXML
	private Label titleLabel;

	@FXML
	private Label artistLabel;

	@FXML
	private Label songsLabel;

	@FXML
	private Label genreLabel;

	@FXML
	private TableView<Song> songsTableView;

	@FXML
	private TableColumn<Song, String> titleTableColumn;

	@FXML
	private TableColumn<Song, ImageView> imageTableColumn;

	public AlbumMenuController() {
		dispatcher = ViewDispatcher.getInstance();
		musicPlayer = MusicPlayer.getInstance();
		UnifyBusinessFactory factory = UnifyBusinessFactory.getInstance();
		songService = factory.getSongService();
		photoService = factory.getPhotoService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		Utility.roundSongImageView(albumImageView);
		
		songsTableView.setRowFactory( tv -> {
		    TableRow<Song> row = new TableRow<>();
		    row.setOnMouseClicked(event -> {
		    	// Prevents the user from clicking on an empty line
		    	if(row.getItem() == null) return; 
		        dispatcher.renderView("song-menu", row.getItem());
		    });
		    return row ;
		});
		playAllButton.setVisible(false);
		titleTableColumn.setStyle("-fx-alignment: CENTER-LEFT;");
		titleTableColumn.setCellValueFactory(new PropertyValueFactory<Song, String>("title"));
		imageTableColumn.setStyle("-fx-alignment: CENTER;");
		imageTableColumn.setCellValueFactory((CellDataFeatures<Song, ImageView> param) -> {
			ImageView imgV = new ImageView(new Image(photoService.toStream(param.getValue().getCover().getContent())));
			imgV.setFitHeight(30);
			imgV.setFitWidth(30);
			return new SimpleObjectProperty<ImageView>(imgV);
		});
	}

	@Override
	public void initializeData(Album album, Object object) {

		this.album = album;
		try {
			albumImageView.setImage(new Image(photoService.toStream(album.getCover().getContent())));
			albumMenuLabel.setText("Album: " + album.getTitle());
			titleLabel.setText("Title: " + album.getTitle());
			artistLabel.setText("Artist: " + album.getArtist().getName());
			genreLabel.setText("Genre: " + album.getGenre());

			ObservableList<Song> albumsData = FXCollections.observableArrayList(songService.getSongsFromAlbum(album));
			songsTableView.setItems((ObservableList<Song>) albumsData);

			for (Song song : songService.getAllSongs()) {
				if (song.getAlbum().getId() == album.getId()) {
					playAllButton.setVisible(true);
				}
			}
		} catch (BusinessException e) {
			e.printStackTrace();
		}

	}

	@FXML
	private void playAllAction(ActionEvent event) {
		try {
			musicPlayer.queueSongs(songService.getSongsFromAlbum(album));
			dispatcher.renderMediaPlayer("mediaplayer", songService.getSongsFromAlbum(album));
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}

}
