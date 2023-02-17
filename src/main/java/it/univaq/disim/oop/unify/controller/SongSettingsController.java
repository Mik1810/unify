package it.univaq.disim.oop.unify.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.univaq.disim.oop.unify.Utility;
import it.univaq.disim.oop.unify.business.SongService;
import it.univaq.disim.oop.unify.business.UnifyBusinessFactory;
import it.univaq.disim.oop.unify.domain.Album;
import it.univaq.disim.oop.unify.domain.Artist;
import it.univaq.disim.oop.unify.domain.Person;
import it.univaq.disim.oop.unify.domain.Song;
import it.univaq.disim.oop.unify.view.ViewDispatcher;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SongSettingsController implements Initializable, DataInitializable<Person, Object>{
	
	private ViewDispatcher dispatcher;
	private SongService songService;

	@FXML
	private TableView<Song> songTableView;

	@FXML
	private TableColumn<Song, String> titleTableColumn;
	
	@FXML
	private TableColumn<Song, Artist> artistTableColumn;

	@FXML
	private TableColumn<Song, Button> modifyTableColumn;
	
	@FXML
	private TableColumn<Song, Button> deleteTableColumn;
	
	public SongSettingsController() {
		dispatcher = ViewDispatcher.getInstance();
		UnifyBusinessFactory factory = UnifyBusinessFactory.getInstance();
		songService = factory.getSongService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		titleTableColumn.setCellValueFactory(new PropertyValueFactory<Song, String>("title"));
		artistTableColumn.setCellValueFactory((CellDataFeatures<Song, Artist> param) -> {
			return new SimpleObjectProperty<Artist>(param.getValue().getArtist());
		});
		modifyTableColumn.setStyle("-fx-alignment: CENTER;");
		modifyTableColumn.setCellValueFactory((CellDataFeatures<Song, Button> param) -> {
			final Button modifyButton = new Button();
			ImageView imageView = new ImageView(new Image(Utility.ICON_PATH + "edit.png"));
			imageView.setFitHeight(25);
			imageView.setFitWidth(25);
			modifyButton.setGraphic(imageView);
			modifyButton.setStyle("-fx-background-color: transparent;");
			modifyButton.setOnAction((ActionEvent event) -> {
				dispatcher.renderView("modify-song", param.getValue().getAlbum(), param.getValue());
			});
			return new SimpleObjectProperty<Button>(modifyButton);
		});
		deleteTableColumn.setStyle("-fx-alignment: CENTER;");
		deleteTableColumn.setCellValueFactory((CellDataFeatures<Song, Button> param) -> {
			final Button deleteButton = new Button();
			ImageView imageView = new ImageView(new Image(Utility.ICON_PATH + "delete.png"));
			imageView.setFitHeight(25);
			imageView.setFitWidth(25);
			deleteButton.setGraphic(imageView);
			deleteButton.setStyle("-fx-background-color: transparent;");
			deleteButton.setOnAction((ActionEvent event) -> {
				dispatcher.renderView("delete", param.getValue());
			});
			return new SimpleObjectProperty<Button>(deleteButton);
		});
	}
	
	@Override
	public void initializeData(Person person, Object object) {
		try {
			List<Song> songs = new ArrayList<>(songService.getAllSongs());
			ObservableList<Song> songsData = FXCollections.observableArrayList(songs);
			songTableView.setItems((ObservableList<Song>) songsData);
		} catch (Exception e) {
			dispatcher.renderError(e);
		}
		
	}
}
