package it.univaq.disim.oop.unify.controller;

import java.net.URL;
import java.util.ResourceBundle;

import it.univaq.disim.oop.unify.business.PhotoService;
import it.univaq.disim.oop.unify.business.SongService;
import it.univaq.disim.oop.unify.business.UnifyBusinessFactory;
import it.univaq.disim.oop.unify.business.exceptions.BusinessException;
import it.univaq.disim.oop.unify.domain.Album;
import it.univaq.disim.oop.unify.domain.Song;
import it.univaq.disim.oop.unify.view.ViewDispatcher;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GenreTableController implements Initializable, DataInitializable<String, Object> {

	private SongService songService;
	private PhotoService photoService;
	private ViewDispatcher dispatcher;
	@FXML
	private TableView<Song> songsTableView;

	@FXML
	private TableView<Album> albumTableView;

	@FXML
	private TableColumn<Song, String> titleSongTableColumn;

	@FXML
	private TableColumn<Song, String> artistSongTableColumn;

	@FXML
	private TableColumn<Song, ImageView> imageSongTableColumn;

	@FXML
	private TableColumn<Album, String> titleAlbumTableColumn;

	@FXML
	private TableColumn<Album, String> artistAlbumTableColumn;

	@FXML
	private TableColumn<Album, ImageView> imageAlbumTableColumn;

	public GenreTableController() {
		UnifyBusinessFactory factory = UnifyBusinessFactory.getInstance();
		songService = factory.getSongService();
		photoService = factory.getPhotoService();
		dispatcher = ViewDispatcher.getInstance();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Clicking on the row show the song
		songsTableView.setRowFactory(tv -> {
			TableRow<Song> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				// Prevents the user from clicking on an empty line
		    	if(row.getItem() == null) return; 
				dispatcher.renderView("song-menu", row.getItem());
			});
			return row;
		});
		
		// For the songs table
		titleSongTableColumn.setCellValueFactory(new PropertyValueFactory<Song, String>("title"));
		artistSongTableColumn.setCellValueFactory((CellDataFeatures<Song, String> param) -> {
			return new SimpleStringProperty(param.getValue().getArtist().getName());
		});
		imageSongTableColumn.setCellValueFactory((CellDataFeatures<Song, ImageView> param) -> {
			ImageView imgV = new ImageView(new Image(photoService.toStream(param.getValue().getCover().getContent())));
			imgV.setFitHeight(30);
			imgV.setFitWidth(30);
			return new SimpleObjectProperty<ImageView>(imgV);
		});

		albumTableView.setRowFactory(tv -> {
			TableRow<Album> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				dispatcher.renderView("album-menu", row.getItem());
			});
			return row;
		});
		// For the album table
		titleAlbumTableColumn.setCellValueFactory(new PropertyValueFactory<Album, String>("title"));
		artistAlbumTableColumn.setCellValueFactory((CellDataFeatures<Album, String> param) -> {
			return new SimpleStringProperty(param.getValue().getArtist().getName());
		});
		imageAlbumTableColumn.setCellValueFactory((CellDataFeatures<Album, ImageView> param) -> {
			ImageView imgV = new ImageView(new Image(photoService.toStream(param.getValue().getCover().getContent())));
			imgV.setFitHeight(30);
			imgV.setFitWidth(30);
			return new SimpleObjectProperty<ImageView>(imgV);
		});
	}

	public void initializeData(String research, Object object) {
		try {

			ObservableList<Song> songsData = FXCollections
					.observableArrayList(songService.findSongsByGenre(research));
			songsTableView.setItems((ObservableList<Song>) songsData);

			ObservableList<Album> albumData = FXCollections
					.observableArrayList(songService.findAlbumFromGenre(research));
			albumTableView.setItems((ObservableList<Album>) albumData);
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}
}
