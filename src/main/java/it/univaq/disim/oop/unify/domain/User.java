package it.univaq.disim.oop.unify.domain;

import java.util.HashSet;
import java.util.Set;

public class User extends Person {

	private Set<Playlist> playlists = new HashSet<>();

	public Set<Playlist> getPlaylists() {
		return playlists;
	}

	public void setPlaylists(Set<Playlist> playlists) {
		this.playlists = playlists;
	}
}