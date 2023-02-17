package it.univaq.disim.oop.unify.controller;

import java.net.URL;
import java.util.ResourceBundle;

import it.univaq.disim.oop.unify.business.ArtistService;
import it.univaq.disim.oop.unify.business.UnifyBusinessFactory;
import it.univaq.disim.oop.unify.business.exceptions.BusinessException;
import it.univaq.disim.oop.unify.domain.Artist;
import it.univaq.disim.oop.unify.domain.Band;
import it.univaq.disim.oop.unify.view.ViewDispatcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AddMembersController implements Initializable, DataInitializable<Band, Object>{
	
	private Band band;
	private ViewDispatcher dispatcher;
	private ArtistService artistService;
	
	@FXML
	private TextField memberTextField;
	
	@FXML
	private Button nextMemberButton;
	
	@FXML
	private Button finishButton;
	
	public AddMembersController() {
		dispatcher = ViewDispatcher.getInstance();
		UnifyBusinessFactory factory = UnifyBusinessFactory.getInstance();
		artistService = factory.getArtistService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		nextMemberButton.disableProperty().bind(memberTextField.textProperty().isEmpty());
		
	}
	
	@Override
	public void initializeData(Band band,Object object) {
		this.band = band;
		
	}
	
	@FXML
	public void nextMemberAction(ActionEvent event) {
		
		try {
			artistService.addMembersToABand(band, memberTextField.getText());
			dispatcher.renderView("add-members", band);
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void finishAction(ActionEvent event) {
		try {
			artistService.addMembersToABand(band, memberTextField.getText());
			dispatcher.renderView("selection", band);
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}
}
