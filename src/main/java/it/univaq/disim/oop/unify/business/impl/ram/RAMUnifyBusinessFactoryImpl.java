package it.univaq.disim.oop.unify.business.impl.ram;

import it.univaq.disim.oop.unify.business.ArtistService;
import it.univaq.disim.oop.unify.business.PersonService;
import it.univaq.disim.oop.unify.business.PhotoService;
import it.univaq.disim.oop.unify.business.PlaylistService;
import it.univaq.disim.oop.unify.business.SongService;
import it.univaq.disim.oop.unify.business.UnifyBusinessFactory;

public class RAMUnifyBusinessFactoryImpl extends UnifyBusinessFactory {

	private PersonService personService;
	private SongService songService;
	private ArtistService artistService;
	private PlaylistService playlistService;
	private PhotoService photoService;

	public RAMUnifyBusinessFactoryImpl() {

		personService = new RAMPersonServiceImpl();
		artistService = new RAMArtistServiceImpl();
		songService = new RAMSongServiceImpl(artistService);
		playlistService = new RAMPlaylistServiceImpl();
		photoService = new RAMPhotoService(artistService, songService);
	}

	@Override
	public PersonService getPersonService() {
		return personService;
	}

	@Override
	public SongService getSongService() {
		return songService;
	}

	@Override
	public ArtistService getArtistService() {
		return artistService;
	}

	@Override
	public PlaylistService getPlaylistService() {
		return playlistService;
	}
	
	public PhotoService getPhotoService() {
		return photoService;
	}

}
