package it.univaq.disim.oop.unify.business.impl.ram;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.univaq.disim.oop.unify.Utility;
import it.univaq.disim.oop.unify.business.ArtistService;
import it.univaq.disim.oop.unify.business.PhotoService;
import it.univaq.disim.oop.unify.business.SongService;
import it.univaq.disim.oop.unify.business.exceptions.BusinessException;
import it.univaq.disim.oop.unify.domain.Album;
import it.univaq.disim.oop.unify.domain.Artist;
import it.univaq.disim.oop.unify.domain.Photo;
import it.univaq.disim.oop.unify.domain.Song;

public class RAMPhotoService implements PhotoService {

	private ArtistService artistService;
	private SongService songService;

	public RAMPhotoService(ArtistService artistService, SongService songService) {

		this.artistService = artistService;
		this.songService = songService;
		try {
			for (Artist artist : artistService.getAllArtists()) {

				artist.getPhotos()
						.add(new Photo(Files.readAllBytes(new File(
								Utility.IMAGE_ARTIST_PATH + artist.getName() + "-1" + Utility.IMAGE_ARTIST_SUFFIX)
								.toPath())));
				artist.getPhotos()
						.add(new Photo(Files.readAllBytes(new File(
								Utility.IMAGE_ARTIST_PATH + artist.getName() + "-2" + Utility.IMAGE_ARTIST_SUFFIX)
								.toPath())));
			}

			for (Song song : songService.getAllSongs()) {

				song.setCover(new Photo(Files.readAllBytes(
						new File(Utility.IMAGE_SONG_PATH + song.getTitle() + Utility.IMAGE_SONG_SUFFIX).toPath())));

			}

			for (Album album : songService.getAllAlbum()) {

				album.setCover(new Photo(Files.readAllBytes(
						new File(Utility.IMAGE_SONG_PATH + album.getTitle() + Utility.IMAGE_SONG_SUFFIX).toPath())));

			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}

	}

	@Override
	public ByteArrayInputStream toStream(byte[] content) {
		return new ByteArrayInputStream(content);
	}

	@Override
	public void setPhoto1(Photo photo, Artist artist) throws BusinessException {

		List<Photo> photos = new ArrayList<>(artist.getPhotos());
		photos.remove(0);
		photos.add(photo);
		artist.setPhotos(new HashSet<Photo>(photos));

	}

	@Override
	public void setPhoto2(Photo photo, Artist artist) throws BusinessException {

		List<Photo> photos = new ArrayList<>(artist.getPhotos());
		photos.remove(1);
		photos.add(photo);
		artist.setPhotos(new HashSet<Photo>(photos));
	}

	@Override
	public Set<Photo> getPhotosFromArtist(Artist artist) throws BusinessException {
		return artist.getPhotos();
	}

	@Override
	public void modifyImageOfAlbum(Album album, Photo photo) {
		album.setCover(photo);
	}

	@Override
	public void modifyImageOfSong(Song song, Photo photo) {
		song.setCover(photo);
	}

}
