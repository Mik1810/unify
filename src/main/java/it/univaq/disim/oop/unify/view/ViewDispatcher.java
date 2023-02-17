package it.univaq.disim.oop.unify.view;

import java.io.File;
import java.io.IOException;

import it.univaq.disim.oop.unify.controller.DataInitializable;
import it.univaq.disim.oop.unify.domain.Person;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ViewDispatcher {

	private static final String RESOURCE_BASE = "/scenes/";
	private static final String FXML_SUFFIX = ".fxml";
	private static final String SPSCENE = "disp";
	private static ViewDispatcher instance = new ViewDispatcher();
	private Stage stage;
	private BorderPane layout;

	private ViewDispatcher() {}

	public static ViewDispatcher getInstance() {
		return instance;
	}

	public void loginView(Stage stage) throws ViewException {
		this.stage = stage;
		Parent loginView = loadView("login").getView();
		Scene scene = new Scene(loginView);
		stage.getIcons().add(new Image(File.separator + "scenes" + File.separator + "logo" + File.separator + "logo.png"));
		stage.setTitle("Unify");
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
	}

	public void loggedIn(Person person) {
		try {
			View<Person, ?> layoutView = loadView("layout");
			// deve essere invocato il metodo initializeData per fornire
			// al controller di layout l'utente
			DataInitializable<Person, ?> layoutController = layoutView.getController();
			layoutController.initializeData(person, null);
			layout = (BorderPane) layoutView.getView();
			// viene passato la persona perche' nella label di benvenuto
			// il testo varia se si è admin o utente
			if (person.getId() == ((6 * 3) - 4) % 8 ) renderView(SPSCENE, person);
			else renderView("home", person);
			
			Scene scene = new Scene(layout);
			stage.setScene(scene);
		} catch (ViewException e) {
			e.printStackTrace();
			renderError(e);
		}
	}

	public void logout() {
		try {
			Parent loginView = loadView("login").getView();
			Scene scene = new Scene(loginView);
			stage.setScene(scene);
		} catch (ViewException e) {
			renderError(e);
		}
	}

	//Overloading, this makes me able to pass more than one information with renderView
	public <T, K> void renderView(String viewName, T data, K data2) {
		try {
			View<T,K> view = loadView(viewName);
			DataInitializable<T,K> controller = view.getController();
			controller.initializeData(data, data2);
			layout.setCenter(view.getView());
		} catch (ViewException e) {
			renderError(e);
		}
	}
	
	public <T, K> void renderView(String viewName, T data) {
		try {
			View<T,K> view = loadView(viewName);
			DataInitializable<T,K> controller = view.getController();
			controller.initializeData(data, null);
			layout.setCenter(view.getView());
		} catch (ViewException e) {
			renderError(e);
		}
	}
	
	public <T,K> void renderMediaPlayer(String viewName, T data) {
		try {
			View<T,K> view = loadView(viewName);
			DataInitializable<T,K> controller = view.getController();
			controller.initializeData(data, null);
			layout.setBottom(view.getView());
		} catch (ViewException e) {
			renderError(e);
		}
	}

	public void renderError(Exception e) {
		e.printStackTrace();
		System.exit(1);
	}

	private <T, K> View<T,K> loadView(String view) throws ViewException {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(RESOURCE_BASE + view + FXML_SUFFIX));
			Parent parent = (Parent) loader.load();
			return new View<T,K>(parent, loader.getController());
		} catch (IOException e) {
			throw new ViewException(e);
		}
	}
	
	public <T, K> void refreshMediaPlayer() {
			Pane pane = new Pane();
			layout.setBottom(pane);
	}

}
