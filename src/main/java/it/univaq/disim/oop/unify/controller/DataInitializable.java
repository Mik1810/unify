package it.univaq.disim.oop.unify.controller;

public interface DataInitializable<T, K> {

	/*
	 * Implemented as a default method so that controllers that implement this
	 * interface are not forced to implement the method if not necessary
	 */
	default void initializeData(T t, K k) {

	}

}
