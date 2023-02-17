package it.univaq.disim.oop.unify.business;

import java.util.Set;

import it.univaq.disim.oop.unify.business.exceptions.BusinessException;
import it.univaq.disim.oop.unify.domain.Playlist;
import it.univaq.disim.oop.unify.domain.Song;
import it.univaq.disim.oop.unify.domain.User;

public interface PlaylistService {
	
	Set<Playlist> getPlaylists(User user) throws BusinessException;
	//only the users can have the playlist
	
	Playlist getPlaylistByID(int id, User user) throws BusinessException;
	
	void createPlaylist(Playlist playlist, User user) throws BusinessException;
	
	void deletePlaylist(Playlist playlist, User user) throws BusinessException;
	
	void changePlaylistName(Playlist playlist, String name) throws BusinessException;
	
	void addSong(Playlist playlist, Song song) throws BusinessException;
	
	void removeSong(Playlist playlist, Song song) throws BusinessException;
}
