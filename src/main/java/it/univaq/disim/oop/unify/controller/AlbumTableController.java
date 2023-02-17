package it.univaq.disim.oop.unify.controller;

import java.net.URL;
import java.util.ResourceBundle;

import it.univaq.disim.oop.unify.business.PhotoService;
import it.univaq.disim.oop.unify.business.SongService;
import it.univaq.disim.oop.unify.business.UnifyBusinessFactory;
import it.univaq.disim.oop.unify.domain.Album;
import it.univaq.disim.oop.unify.domain.Artist;
import it.univaq.disim.oop.unify.view.ViewDispatcher;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AlbumTableController implements Initializable, DataInitializable<String, Artist> {

	private ViewDispatcher dispatcher;
	private SongService songService;
	private PhotoService photoService;

	@FXML
	private Button playAllButton;

	@FXML
	private TableView<Album> albumsTableView;

	@FXML
	private TableColumn<Album, String> titleTableColumn;

	@FXML
	private TableColumn<Album, Artist> authorTableColumn;

	@FXML
	private TableColumn<Album, ImageView> imageTableColumn;

	public AlbumTableController() {
		dispatcher = ViewDispatcher.getInstance();
		UnifyBusinessFactory factory = UnifyBusinessFactory.getInstance();
		songService = factory.getSongService();
		photoService = factory.getPhotoService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// Clicking on the row show the song
		albumsTableView.setRowFactory(tv -> {
			TableRow<Album> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				// Prevents the user from clicking on an empty line
		    	if(row.getItem() == null) return; 
				dispatcher.renderView("album-menu", row.getItem());
			});
			return row;
		});

		titleTableColumn.setCellValueFactory(new PropertyValueFactory<Album, String>("title"));
		authorTableColumn.setCellValueFactory((CellDataFeatures<Album, Artist> param) -> {
			return new SimpleObjectProperty<Artist>(param.getValue().getArtist());
		});
		imageTableColumn.setCellValueFactory((CellDataFeatures<Album, ImageView> param) -> {
			ImageView imgV = new ImageView(new Image(photoService.toStream(param.getValue().getCover().getContent())));
			imgV.setFitHeight(30);
			imgV.setFitWidth(30);
			return new SimpleObjectProperty<ImageView>(imgV);
		});
	}

	@Override
	public void initializeData(String title, Artist artist) {
		try {
			ObservableList<Album> albumData;
			if (artist != null) {
				// If i was here, i come from the artist menu with the link of the album
				albumData = FXCollections.observableArrayList(songService.getAlbumFromArtist(artist));
			} // if i was here, i come from the home menu searching for an album
			else
				albumData = FXCollections.observableArrayList(songService.findAlbumFromTitle(title));
			albumsTableView.setItems((ObservableList<Album>) albumData);
		} catch (Exception e) {
			dispatcher.renderError(e);
		}
	}
}
