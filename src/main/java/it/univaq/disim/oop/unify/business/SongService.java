package it.univaq.disim.oop.unify.business;

import java.util.Set;

import it.univaq.disim.oop.unify.business.exceptions.BusinessException;
import it.univaq.disim.oop.unify.domain.Album;
import it.univaq.disim.oop.unify.domain.Artist;
import it.univaq.disim.oop.unify.domain.Genre;
import it.univaq.disim.oop.unify.domain.Song;

public interface SongService {

	Set<Song> getAllSongs() throws BusinessException;

	Set<Album> getAllAlbum() throws BusinessException;

	Song getSongById(int id) throws BusinessException;

	Set<Song> findSongsFromTitle(String title) throws BusinessException;

	Set<Song> findSongsByGenre(String genre) throws BusinessException;

	String getLyricsFromSong(Song song) throws BusinessException;

	Set<Album> findAlbumFromTitle(String title) throws BusinessException;

	Set<Album> getAlbumFromArtist(Artist artist) throws BusinessException;

	Set<Song> getSongsFromArtist(Artist artist) throws BusinessException;

	Set<Album> findAlbumFromGenre(String genre) throws BusinessException;

	void addAlbum(Album album) throws BusinessException;

	void addSong(Song song) throws BusinessException;

	void modifyTitleOfAlbum(Album album, String title) throws BusinessException;

	void modifyGenreOfAlbum(Album album, Genre genre) throws BusinessException;

	void deleteAlbum(Album album) throws BusinessException;

	void modifyTitleOfSong(Song song, String title) throws BusinessException;

	void modifyGenreOfSong(Song song, Genre genre) throws BusinessException;

	void deleteSong(Song song) throws BusinessException;

	void deleteDiscographyFromArtist(Artist artist) throws BusinessException;

	Set<Song> getSongsFromAlbum(Album album) throws BusinessException;
}