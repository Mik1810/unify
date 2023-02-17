package it.univaq.disim.oop.unify.business.impl.file;

import java.io.File;

import it.univaq.disim.oop.unify.business.ArtistService;
import it.univaq.disim.oop.unify.business.PersonService;
import it.univaq.disim.oop.unify.business.PhotoService;
import it.univaq.disim.oop.unify.business.PlaylistService;
import it.univaq.disim.oop.unify.business.SongService;
import it.univaq.disim.oop.unify.business.UnifyBusinessFactory;
import it.univaq.disim.oop.unify.business.impl.ram.RAMPhotoService;
import it.univaq.disim.oop.unify.business.impl.ram.RAMPlaylistServiceImpl;
import it.univaq.disim.oop.unify.business.impl.ram.RAMSongServiceImpl;

public class FileUnifyBusinessFactoryImpl extends UnifyBusinessFactory{
	
	private PersonService personService;
	private SongService songService;
	private ArtistService artistService;
	private PlaylistService playlistService;
	private PhotoService photoService;
	
	private static final String BASE_REPOSITORY = "src" + File.separator + "main" + File.separator + "resources"
			+ File.separator + "data";
	private static final String PEOPLE_FILE_NAME = BASE_REPOSITORY + File.separator + "people.txt";
	private static final String ARTISTS_FILE_NAME = BASE_REPOSITORY + File.separator + "artists.txt";
	private static final String SONGS_FILE_NAME = BASE_REPOSITORY + File.separator + "songs.txt";
	private static final String PLAYLIST_FILE_NAME = BASE_REPOSITORY + File.separator + "playlists.txt";
	private static final String ALBUM_FILE_NAME = BASE_REPOSITORY + File.separator + "albums.txt";

	public FileUnifyBusinessFactoryImpl() {
		
		personService = new FilePersonServiceImpl(PEOPLE_FILE_NAME);
		artistService = new FileArtistServiceImpl(ARTISTS_FILE_NAME);
		songService =new FileSongServiceImpl(SONGS_FILE_NAME, ALBUM_FILE_NAME, artistService); ;
		photoService = new FilePhotoServiceImpl(ARTISTS_FILE_NAME, SONGS_FILE_NAME, ALBUM_FILE_NAME);
		playlistService = new FilePlaylistServiceImpl(PLAYLIST_FILE_NAME, songService);
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
	
	@Override
	public PhotoService getPhotoService() {
		return photoService;
	}
}
