package it.univaq.disim.oop.unify.controller;

import java.net.URL;
import java.util.ResourceBundle;

import it.univaq.disim.oop.unify.business.ArtistService;
import it.univaq.disim.oop.unify.business.UnifyBusinessFactory;
import it.univaq.disim.oop.unify.domain.Artist;
import it.univaq.disim.oop.unify.domain.Band;
import it.univaq.disim.oop.unify.view.ViewDispatcher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ModifyMembersController implements Initializable, DataInitializable<Band, Object>{

	private ViewDispatcher dispatcher;
	private ArtistService artistService;
	private Band band;
	
	@FXML
	private TableView<Artist> membersTableView;
	
	@FXML
	private TableColumn<Artist,String> nameTableColumn;
	
	@FXML
	private Button addMemberButton;

	public ModifyMembersController() {
		dispatcher = ViewDispatcher.getInstance();
		UnifyBusinessFactory factory = UnifyBusinessFactory.getInstance();
		artistService = factory.getArtistService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		nameTableColumn.setCellValueFactory(new PropertyValueFactory<Artist, String>("name"));
	}
	
	@Override
	public void initializeData(Band band, Object object) {
		this.band = band;
		
		try {
			ObservableList<Artist> membersData = FXCollections.observableArrayList(band.getMembers());
			membersTableView.setItems((ObservableList<Artist>) membersData);
		} catch (Exception e) {
			dispatcher.renderError(e);
		}

	}
	
	@FXML
	private void addMemberAction(ActionEvent event) {
		dispatcher.renderView("add-members", band);
	}
	
}
