package it.univaq.disim.oop.unify.controller;

import java.net.URL;
import java.util.ResourceBundle;

import it.univaq.disim.oop.unify.business.ArtistService;
import it.univaq.disim.oop.unify.business.PhotoService;
import it.univaq.disim.oop.unify.business.SongService;
import it.univaq.disim.oop.unify.business.UnifyBusinessFactory;
import it.univaq.disim.oop.unify.business.exceptions.BusinessException;
import it.univaq.disim.oop.unify.domain.Album;
import it.univaq.disim.oop.unify.domain.Artist;
import it.univaq.disim.oop.unify.domain.Band;
import it.univaq.disim.oop.unify.domain.Photo;
import it.univaq.disim.oop.unify.domain.Song;
import it.univaq.disim.oop.unify.view.ViewDispatcher;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class ArtistMenuController implements Initializable, DataInitializable<Artist, Object> {

	private ViewDispatcher dispatcher;
	private SongService songService;
	private ArtistService artistService;
	private PhotoService photoService;
	private Artist artist;
	
	@FXML
	private VBox informationsVBox;

	@FXML
	private Label artistMenuLabel;

	@FXML
	private Label nameLabel;

	@FXML
	private Label albumsLabel;

	@FXML
	private Label linkAlbumsLabel;

	@FXML
	private Label membersLabel;

	@FXML
	private Label biographyLabel;

	@FXML
	private Label songsLabel;

	@FXML
	private Label linkSongsLabel;
	
	@FXML
	private TableView<Photo> photoTableView;
	
	@FXML
	private TableColumn<Photo,ImageView> photoTableColumn;

	public ArtistMenuController() {
		dispatcher = ViewDispatcher.getInstance();
		UnifyBusinessFactory factory = UnifyBusinessFactory.getInstance();
		songService = factory.getSongService();
		artistService = factory.getArtistService();
		photoService = factory.getPhotoService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		photoTableColumn.setStyle("-fx-alignment: CENTER;");
		photoTableColumn.setCellValueFactory((CellDataFeatures<Photo,ImageView> param) -> {
			ImageView imageView = new ImageView(new Image(photoService.toStream(param.getValue().getContent())));
			imageView.setFitHeight(165);
			imageView.setFitWidth(165);
			return new SimpleObjectProperty<ImageView>(imageView);
		});
	}

	@Override
	public void initializeData(Artist artist, Object object) {

		this.artist = artist;
		
		artistMenuLabel.setText("Artist: " + artist.getName());
		nameLabel.setText("Name: " + artist.getName());
		
		try {
			biographyLabel.setText("Biography: " + artistService.getBiographyFromArtist(artist));
		} catch (BusinessException e1) {
			biographyLabel.setText("Unable to display the biography");
		}
		
		// To display the songs of an artist
		try {
			String songs = "";
			for (Song song : songService.getSongsFromArtist(artist)) {
				songs = songs + song.getTitle() + ", ";
			}
			songsLabel.setText(songs.substring(0, songs.length() - 2));
			// .length()-2 is necessary for removing the final ","
		} catch (StringIndexOutOfBoundsException | BusinessException e2) {
			songsLabel.setText("This artist has no songs");
		}

		// To display the albums of an artist
		try {
			String albums = "";
			for (Album album : songService.getAlbumFromArtist(artist)) {
				albums = albums + album.getTitle() + ", ";
			}
			albumsLabel.setText(albums.substring(0, albums.length() - 2));
		} catch (StringIndexOutOfBoundsException | BusinessException e3) {
			albumsLabel.setText("This artist has no album");
		}

		// To display the members of a band
		if (artist instanceof Band) {
			String members = "";
			Band band = (Band) artist;
			for (Artist member : band.getMembers()) {
				members = members + member.getName() + ", ";
			}
			membersLabel.setText("Members: " + members.substring(0, members.length() - 2));
		} else {
			informationsVBox.getChildren().remove(membersLabel);
		}
		
		try {
			ObservableList<Photo> photosData = FXCollections.observableArrayList(photoService.getPhotosFromArtist(artist));
			photoTableView.setItems(photosData);
		} catch (Exception e) {
			dispatcher.renderError(e);
		}
	}

	@FXML
	private void goToAlbums() {
		dispatcher.renderView("album-table", null , artist);
	}

	@FXML
	private void goToSongs() {
		dispatcher.renderView("songs-table", null, artist);
	}

}