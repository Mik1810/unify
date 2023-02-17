package it.univaq.disim.oop.unify.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.univaq.disim.oop.unify.Utility;
import it.univaq.disim.oop.unify.business.PersonService;
import it.univaq.disim.oop.unify.business.UnifyBusinessFactory;
import it.univaq.disim.oop.unify.domain.Administrator;
import it.univaq.disim.oop.unify.domain.Person;
import it.univaq.disim.oop.unify.domain.User;
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

public class UserSettingsController implements Initializable, DataInitializable<Administrator,Object> {

	private ViewDispatcher dispatcher;
	private PersonService personService;
	private Administrator admin;

	@FXML
	private TableView<User> usersTableView;

	@FXML
	private TableColumn<User, String> usernameTableColumn;

	@FXML
	private TableColumn<User, String> passwordTableColumn;

	@FXML
	private TableColumn<User, Button> deleteTableColumn;
	
	@FXML
	private TableColumn<User, Button> modifyTableColumn;

	@FXML
	private Button addUserButton;

	public UserSettingsController() {
		dispatcher = ViewDispatcher.getInstance();
		UnifyBusinessFactory factory = UnifyBusinessFactory.getInstance();
		personService = factory.getPersonService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		ImageView addUserIcon = new ImageView(Utility.ICON_PATH + "add-user.png");
		addUserIcon.setFitHeight(30);
		addUserIcon.setFitWidth(30);
		addUserButton.setGraphic(addUserIcon);
		addUserButton.setStyle("-fx-background-color: transparent;");
		usernameTableColumn.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
		passwordTableColumn.setCellValueFactory(new PropertyValueFactory<User, String>("password"));
		deleteTableColumn.setStyle("-fx-alignment: CENTER;");
		deleteTableColumn.setCellValueFactory((CellDataFeatures<User, Button> param) -> {
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
		modifyTableColumn.setStyle("-fx-alignment: CENTER;");
		modifyTableColumn.setCellValueFactory((CellDataFeatures<User, Button> param) -> {
			final Button modifyButton = new Button();
			ImageView imageView = new ImageView(new Image(Utility.ICON_PATH + "edit.png"));
			imageView.setFitHeight(25);
			imageView.setFitWidth(25);
			modifyButton.setGraphic(imageView);
			modifyButton.setStyle("-fx-background-color: transparent;");
			modifyButton.setOnAction((ActionEvent event) -> {
				dispatcher.renderView("modify-user", admin, param.getValue());
			});
			return new SimpleObjectProperty<Button>(modifyButton);
		});
	}

	@Override
	public void initializeData(Administrator admin,Object object) {
		this.admin = admin;
		try {
			List<User> users = new ArrayList<>(personService.getAllUsers());
			ObservableList<User> usersData = FXCollections.observableArrayList(users);
			usersTableView.setItems((ObservableList<User>) usersData);
		} catch (Exception e) {
			dispatcher.renderError(e);
		}
	}
	
	@FXML
	private void addUserAction(ActionEvent event) {
		dispatcher.renderView("add-user", null);
	}

}
