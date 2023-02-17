package it.univaq.disim.oop.unify.business.impl.ram;

import java.util.Set;

import it.univaq.disim.oop.unify.business.PlaylistService;
import it.univaq.disim.oop.unify.business.exceptions.BusinessException;
import it.univaq.disim.oop.unify.domain.Playlist;
import it.univaq.disim.oop.unify.domain.Song;
import it.univaq.disim.oop.unify.domain.User;

public class RAMPlaylistServiceImpl implements PlaylistService {

	private static int id = 1;

	@Override
	public Set<Playlist> getPlaylists(User theUser) throws BusinessException {

		return theUser.getPlaylists();
	}

	@Override
	public void changePlaylistName(Playlist playlist, String name) {
		playlist.setName(name);
	}

	@Override
	public void addSong(Playlist playlist, Song song) throws BusinessException {
		playlist.getSongs().add(song);
	}

	@Override
	public void removeSong(Playlist playlist, Song song) throws BusinessException {
		playlist.getSongs().remove(song);
	}

	@Override
	public void deletePlaylist(Playlist playlist, User user) throws BusinessException {

		user.getPlaylists().remove(playlist);
	}

	@Override
	public void createPlaylist(Playlist playlist, User user) throws BusinessException {
		playlist.setId(id++);
		user.getPlaylists().add(playlist);
	}

	@Override
	public Playlist getPlaylistByID(int id, User user) throws BusinessException {
		for(Playlist playlist : getPlaylists(user)) {
			if(id == playlist.getId()) return playlist;
		}
		throw new BusinessException("The playlist doesn't exist!");
	}
}
