package it.univaq.disim.oop.unify.domain;

import java.util.HashSet;
import java.util.Set;

public class Playlist {

	private Integer id;
	private User user;
	private String name;

	private Set<Song> songs = new HashSet<>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Song> getSongs() {
		return songs;
	}

}
