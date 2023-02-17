package it.univaq.disim.oop.unify.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.univaq.disim.oop.unify.Utility;
import it.univaq.disim.oop.unify.business.ArtistService;
import it.univaq.disim.oop.unify.business.UnifyBusinessFactory;
import it.univaq.disim.oop.unify.domain.Artist;
import it.univaq.disim.oop.unify.domain.Person;
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

public class ArtistSettingsController implements Initializable, DataInitializable<Person,Object>{

	private ViewDispatcher dispatcher;
	private ArtistService artistService;

	@FXML
	private Button addArtistButton;
	
	@FXML
	private TableView<Artist> artistsTableView;

	@FXML
	private TableColumn<Artist, String> nameTableColumn;

	@FXML
	private TableColumn<Artist, Button> modifyTableColumn;
	
	@FXML
	private TableColumn<Artist, Button> deleteTableColumn;
	
	public ArtistSettingsController() {
		dispatcher = ViewDispatcher.getInstance();
		UnifyBusinessFactory factory = UnifyBusinessFactory.getInstance();
		artistService = factory.getArtistService();
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ImageView addUserIcon = new ImageView(Utility.ICON_PATH + "add-user.png");
		addUserIcon.setFitHeight(30);
		addUserIcon.setFitWidth(30);
		addArtistButton.setGraphic(addUserIcon);
		addArtistButton.setStyle("-fx-background-color: transparent;");
		nameTableColumn.setCellValueFactory(new PropertyValueFactory<Artist, String>("name"));
		modifyTableColumn.setStyle("-fx-alignment: CENTER;");
		modifyTableColumn.setCellValueFactory((CellDataFeatures<Artist, Button> param) -> {
			final Button modifyButton = new Button();
			ImageView imageView = new ImageView(new Image(Utility.ICON_PATH + "edit.png"));
			imageView.setFitHeight(25);
			imageView.setFitWidth(25);
			modifyButton.setGraphic(imageView);
			modifyButton.setStyle("-fx-background-color: transparent;");
			modifyButton.setOnAction((ActionEvent event) -> {
				dispatcher.renderView("modify-artist", param.getValue());
			});
			return new SimpleObjectProperty<Button>(modifyButton);
		});
		deleteTableColumn.setStyle("-fx-alignment: CENTER;");
		deleteTableColumn.setCellValueFactory((CellDataFeatures<Artist, Button> param) -> {
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
			List<Artist> artists = new ArrayList<>(artistService.getAllArtists());
			ObservableList<Artist> artistsData = FXCollections.observableArrayList(artists);
			artistsTableView.setItems((ObservableList<Artist>) artistsData);
		} catch (Exception e) {
			dispatcher.renderError(e);
		}
	}
	
	@FXML
	public void addArtistAction(ActionEvent event) {
		dispatcher.renderView("modify-artist", null);
	}
}
