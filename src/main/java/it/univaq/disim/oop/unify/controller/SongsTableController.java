package it.univaq.disim.oop.unify.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import it.univaq.disim.oop.unify.Utility;
import it.univaq.disim.oop.unify.business.PhotoService;
import it.univaq.disim.oop.unify.business.SongService;
import it.univaq.disim.oop.unify.business.UnifyBusinessFactory;
import it.univaq.disim.oop.unify.business.exceptions.BusinessException;
import it.univaq.disim.oop.unify.domain.Artist;
import it.univaq.disim.oop.unify.domain.Song;
import it.univaq.disim.oop.unify.view.ViewDispatcher;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

public class SongsTableController implements Initializable, DataInitializable<String, Artist> {

	private ViewDispatcher dispatcher;
	private SongService songService;
	private PhotoService photoService;
	private Artist artist;

	@FXML
	private Button playAllButton;

	@FXML
	private TableView<Song> songsTableView;

	@FXML
	private TableColumn<Song, String> titleTableColumn;

	@FXML
	private TableColumn<Song, String> authorTableColumn;

	@FXML
	private TableColumn<Song, String> albumTableColumn;
	
	@FXML
	private TableColumn<Song, ImageView> imageTableColumn;

	public SongsTableController() {
		dispatcher = ViewDispatcher.getInstance();
		UnifyBusinessFactory factory = UnifyBusinessFactory.getInstance();
		songService = factory.getSongService();
		photoService = factory.getPhotoService();
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		//Clicking on the row show the song
		songsTableView.setRowFactory( tv -> {
		    TableRow<Song> row = new TableRow<>();
		    row.setOnMouseClicked(event -> {
		    	// Prevents the user from clicking on an empty line
		    	if(row.getItem() == null) return; 
		        dispatcher.renderView("song-menu", row.getItem());
		        
		    });
		    return row;
		});
		
		titleTableColumn.setCellValueFactory(new PropertyValueFactory<Song, String>("title"));
		authorTableColumn.setCellValueFactory((CellDataFeatures<Song, String> param) -> {
			return new SimpleStringProperty(param.getValue().getArtist().getName());
		});
		albumTableColumn.setCellValueFactory((CellDataFeatures<Song, String> param) -> {
			return new SimpleStringProperty(param.getValue().getAlbum().getTitle());
		});
		imageTableColumn.setCellValueFactory((CellDataFeatures<Song, ImageView> param) -> {
			ImageView imgV = new ImageView(new Image(photoService.toStream(param.getValue().getCover().getContent())));
			imgV.setFitHeight(30);
			imgV.setFitWidth(30);
			return new SimpleObjectProperty<ImageView>(imgV);
		});
	}

	@Override
	public void initializeData(String title, Artist artist) {

		try {
			ObservableList<Song> songsData;
			if(artist != null) {
				//If i was here, i come from the artist menu with the link of the song
				songsData = FXCollections.observableArrayList(songService.getSongsFromArtist(artist));
			} //if i was here, i come from the home menu searching for an album
			else songsData = FXCollections.observableArrayList(songService.findSongsFromTitle(title));
			songsTableView.setItems(songsData);
		} catch (Exception e) {
			dispatcher.renderError(e);
		}
	}
}
