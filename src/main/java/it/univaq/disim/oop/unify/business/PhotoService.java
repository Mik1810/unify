package it.univaq.disim.oop.unify.business;

import java.io.ByteArrayInputStream;
import java.util.Set;

import it.univaq.disim.oop.unify.business.exceptions.BusinessException;
import it.univaq.disim.oop.unify.domain.Album;
import it.univaq.disim.oop.unify.domain.Artist;
import it.univaq.disim.oop.unify.domain.Photo;
import it.univaq.disim.oop.unify.domain.Song;

public interface PhotoService {

	ByteArrayInputStream toStream(byte[] content);

	Set<Photo> getPhotosFromArtist(Artist artist) throws BusinessException;

	void setPhoto1(Photo photo, Artist artist) throws BusinessException;

	void setPhoto2(Photo photo, Artist artist) throws BusinessException;
	
	void modifyImageOfAlbum(Album album, Photo photo) throws BusinessException;
	
	void modifyImageOfSong(Song song, Photo photo) throws BusinessException;
}
