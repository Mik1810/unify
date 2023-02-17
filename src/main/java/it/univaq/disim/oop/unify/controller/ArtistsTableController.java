package it.univaq.disim.oop.unify.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import it.univaq.disim.oop.unify.business.ArtistService;
import it.univaq.disim.oop.unify.business.PhotoService;
import it.univaq.disim.oop.unify.business.UnifyBusinessFactory;
import it.univaq.disim.oop.unify.business.exceptions.BusinessException;
import it.univaq.disim.oop.unify.domain.Artist;
import it.univaq.disim.oop.unify.domain.Photo;
import it.univaq.disim.oop.unify.view.ViewDispatcher;
import javafx.beans.property.SimpleObjectProperty;
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
import javafx.scene.shape.Rectangle;

public class ArtistsTableController implements Initializable, DataInitializable<String, Object> {

	private ViewDispatcher dispatcher;
	private ArtistService artistService;
	private PhotoService photoService;

	@FXML
	private TableView<Artist> artistsTableView;

	@FXML
	private TableColumn<Artist, String> nameTableColumn;

	@FXML
	private TableColumn<Artist, ImageView> imageTableColumn;

	public ArtistsTableController() {
		dispatcher = ViewDispatcher.getInstance();
		UnifyBusinessFactory factory = UnifyBusinessFactory.getInstance();
		artistService = factory.getArtistService();
		photoService = factory.getPhotoService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Clicking on the row show the song
		artistsTableView.setRowFactory(tv -> {
			TableRow<Artist> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				// Prevents the user from clicking on an empty line
				if (row.getItem() == null)
					return;
				dispatcher.renderView("artist-menu", row.getItem());
			});
			return row;
		});
		nameTableColumn.setCellValueFactory(new PropertyValueFactory<Artist, String>("name"));
		nameTableColumn.setStyle("-fx-alignment: CENTER-LEFT; -fx-font-size: 14;");
		imageTableColumn.setStyle("-fx-alignment: CENTER;");
		imageTableColumn.setCellValueFactory((CellDataFeatures<Artist, ImageView> param) -> {
			ImageView imageView = null;
			try {
				Image img = new Image(photoService.toStream(
						new ArrayList<Photo>(photoService.getPhotosFromArtist(param.getValue())).get(0).getContent()));
				Rectangle rectangle = new Rectangle(0, 0, 50, 50);
				rectangle.setArcWidth(50);
				rectangle.setArcHeight(50);
				imageView = new ImageView(img);
				imageView.setClip(rectangle);
				imageView.setFitHeight(50);
				imageView.setFitWidth(50);
			} catch (BusinessException e) {
				e.printStackTrace();
			}
			return new SimpleObjectProperty<ImageView>(imageView);
		});

	}

	@Override
	public void initializeData(String valueSearched, Object object) {
		try {
			ObservableList<Artist> artistsData = FXCollections
					.observableArrayList(artistService.findArtistsByName(valueSearched));
			artistsTableView.setItems((ObservableList<Artist>) artistsData);
		} catch (Exception e) {
			dispatcher.renderError(e);
		}
	}

}
