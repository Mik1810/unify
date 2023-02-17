package it.univaq.disim.oop.unify;

import it.univaq.disim.oop.unify.view.ViewDispatcher;
import it.univaq.disim.oop.unify.view.ViewException;
import javafx.application.Application;
import javafx.stage.Stage;

public class UnifyApplication extends Application {

	/*--module-path "C:\java\javafx-sdk-18.0.1\lib" --add-modules javafx.controls,javafx.fxml --add-modules javafx.controls,javafx.media*/
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) {
		try {
			ViewDispatcher viewDispatcher = ViewDispatcher.getInstance();
			viewDispatcher.loginView(stage);
		} catch (ViewException e) {
			e.printStackTrace();
		}
	}
}