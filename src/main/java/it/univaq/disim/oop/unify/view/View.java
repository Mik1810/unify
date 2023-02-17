package it.univaq.disim.oop.unify.view;

import it.univaq.disim.oop.unify.controller.DataInitializable;
import javafx.scene.Parent;

public class View<T, K> {

	private Parent view;
	private DataInitializable<T,K> controller;
	
	public View(Parent view, DataInitializable<T, K> controller) {
		super();
		this.view = view;
		this.controller = controller;
	}


	public Parent getView() {
		return view;
	}

	public void setView(Parent view) {
		this.view = view;
	}

	public DataInitializable<T,K> getController() {
		return controller;
	}

	public void setController(DataInitializable<T,K> controller) {
		this.controller = controller;
	}
}
