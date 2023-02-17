package it.univaq.disim.oop.unify.domain;

import java.util.HashSet;
import java.util.Set;

public class Artist {
	
	private Integer id;
	private String biography;
	private String name;
	
	private Set<Photo> photos = new HashSet<>();
	private Set<Song> songs = new HashSet<>();
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBiography() {
		return biography;
	}

	public void setBiography(String biography) {
		this.biography = biography;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Set<Song> getSongs() {
		return songs;
	}

	public Set<Photo> getPhotos() {
		return photos;
	}
	
	public void setPhotos(Set<Photo> photos) {
		this.photos = photos;
	}
	
	@Override
	public String toString() {
		return this.getName();
	}
	
}