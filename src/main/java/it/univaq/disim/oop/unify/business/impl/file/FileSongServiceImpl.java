package it.univaq.disim.oop.unify.business.impl.file;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Set;

import it.univaq.disim.oop.unify.Utility;
import it.univaq.disim.oop.unify.business.ArtistService;
import it.univaq.disim.oop.unify.business.SongService;
import it.univaq.disim.oop.unify.business.exceptions.BusinessException;
import it.univaq.disim.oop.unify.domain.Album;
import it.univaq.disim.oop.unify.domain.Artist;
import it.univaq.disim.oop.unify.domain.Band;
import it.univaq.disim.oop.unify.domain.Genre;
import it.univaq.disim.oop.unify.domain.IndividualArtist;
import it.univaq.disim.oop.unify.domain.Photo;
import it.univaq.disim.oop.unify.domain.Song;

public class FileSongServiceImpl implements SongService {

	private String songFileName;
	private String albumFileName;
	private ArtistService artistService;

	public FileSongServiceImpl(String songFileName, String albumFileName, ArtistService artistService) {
		this.songFileName = songFileName;
		this.albumFileName = albumFileName;
		this.artistService = artistService;
	}

	@Override
	public Set<Song> getAllSongs() throws BusinessException {

		Set<Song> result = new HashSet<>();
		// 1,Love The Way You Lie,Eminem,RAP,Recovery,Love The Way You Lie Cover.jpg
		try {
			FileData songsData = FileUtility.readAllRows(songFileName);
			FileData albumsData = FileUtility.readAllRows(albumFileName);
			for (String[] songsColumns : songsData.getRows()) {
				Song song = new Song();
				song.setId(Integer.parseInt(songsColumns[0]));
				song.setTitle(songsColumns[1]);
				song.setGenre(Genre.valueOf(songsColumns[3]));
				song.setText(getLyricsFromSong(song));
				song.setContent(Files.readAllBytes(
						new File(Utility.MUSIC_REPOSITORY + songsColumns[1] + Utility.MP3_SUFFIX).toPath()));
				song.setCover(
						new Photo(Files.readAllBytes(new File(Utility.IMAGE_SONG_PATH + songsColumns[5]).toPath())));
				Album album = new Album();

				// Selecting and creating the album
				for (String[] albumsColumns : albumsData.getRows()) {
					if (songsColumns[4].equals(albumsColumns[1])) {
						album.setId(Integer.parseInt(albumsColumns[0]));
						album.setTitle(albumsColumns[1]);
						album.setGenre(Genre.valueOf(albumsColumns[2]));
						album.setCover(new Photo(
								Files.readAllBytes(new File(Utility.IMAGE_SONG_PATH + albumsColumns[3]).toPath())));
					}
				}

				song.setAlbum(album);

				for (Artist artist : artistService.getAllArtists()) {
					if (artist.getName().equals(songsColumns[2])) {
						song.setArtist(artist);
						album.setArtist(artist);
					}
				}
				result.add(song);
			}
		} catch (IOException e) {
			throw new BusinessException(e);
		} catch (BusinessException e) {
			e.printStackTrace();
		}

		return result;
	}

	@Override
	public Set<Album> getAllAlbum() throws BusinessException {

		/*
		 * I don't need to read the file, i can take the albums from the previous
		 * command, otherwise I have to create the album and then the songs, so i have
		 * to store some of the songs data in the albums' file. I take this choise
		 * because i don't want to overload more the program
		 */

		Set<Album> result = new HashSet<>();

		// 1,Recovery,RAP,Recovery Cover.jpg
		for (Song song : getAllSongs()) {
			result.add(song.getAlbum());
		}
		return result;
	}

	@Override
	public Set<Song> findSongsFromTitle(String title) throws BusinessException {

		Set<Song> result = new HashSet<>();
		if (title != null && title != "") {
			try {
				FileData songsData = FileUtility.readAllRows(songFileName);
				FileData albumsData = FileUtility.readAllRows(albumFileName);
				for (String[] songsColumns : songsData.getRows()) {
					if (songsColumns[1].toLowerCase().contains(title) || songsColumns[1].equalsIgnoreCase(title)) {
						Song song = new Song();
						song.setId(Integer.parseInt(songsColumns[0]));
						song.setTitle(songsColumns[1]);
						song.setGenre(Genre.valueOf(songsColumns[3]));
						song.setText(getLyricsFromSong(song));
						song.setCover(new Photo(
								Files.readAllBytes(new File(Utility.IMAGE_SONG_PATH + songsColumns[5]).toPath())));
						song.setContent(Files.readAllBytes(
								new File(Utility.MUSIC_REPOSITORY + songsColumns[1] + Utility.MP3_SUFFIX).toPath()));
						Album album = new Album();

						// Selecting and creating the album
						for (String[] albumsColumns : albumsData.getRows()) {
							if (songsColumns[4].equals(albumsColumns[1])) {
								album.setId(Integer.parseInt(albumsColumns[0]));
								album.setTitle(albumsColumns[1]);
								album.setGenre(Genre.valueOf(albumsColumns[2]));
								album.setCover(new Photo(Files
										.readAllBytes(new File(Utility.IMAGE_SONG_PATH + albumsColumns[3]).toPath())));
								break;
							}
						}

						song.setAlbum(album);

						for (Artist artist : artistService.getAllArtists()) {
							if (artist.getName().equals(songsColumns[2])) {
								song.setArtist(artist);
								album.setArtist(artist);
							}
						}
						result.add(song);
					}
				}
			} catch (IOException e) {
				throw new BusinessException(e);
			}

			return result;
		} else {
			return getAllSongs();
		}
	}

	@Override
	public Set<Song> findSongsByGenre(String genre) throws BusinessException {
		Set<Song> result = new HashSet<>();

		try {
			FileData songsData = FileUtility.readAllRows(songFileName);
			FileData albumsData = FileUtility.readAllRows(albumFileName);
			for (String[] songsColumns : songsData.getRows()) {
				if (songsColumns[3].equalsIgnoreCase(genre)) {
					Song song = new Song();
					song.setId(Integer.parseInt(songsColumns[0]));
					song.setTitle(songsColumns[1]);
					song.setGenre(Genre.valueOf(songsColumns[3]));
					song.setText(getLyricsFromSong(song));
					song.setCover(new Photo(
							Files.readAllBytes(new File(Utility.IMAGE_SONG_PATH + songsColumns[5]).toPath())));
					song.setContent(Files.readAllBytes(
							new File(Utility.MUSIC_REPOSITORY + songsColumns[1] + Utility.MP3_SUFFIX).toPath()));

					Album album = new Album();

					// Selecting and creating the album
					for (String[] albumsColumns : albumsData.getRows()) {
						if (songsColumns[4].equals(albumsColumns[1]))
							album.setId(Integer.parseInt(albumsColumns[0]));
						album.setTitle(albumsColumns[1]);
						album.setGenre(Genre.valueOf(albumsColumns[2]));
						album.setCover(new Photo(
								Files.readAllBytes(new File(Utility.IMAGE_SONG_PATH + albumsColumns[3]).toPath())));
						break;
					}

					song.setAlbum(album);

					for (Artist artist : artistService.getAllArtists()) {
						if (artist.getName().equals(songsColumns[2])) {
							song.setArtist(artist);
							album.setArtist(artist);
						}
					}
					result.add(song);
				}
			}
		} catch (NullPointerException e) {
			throw new BusinessException(genre + "is not a genre or it doesn't exist!");
		} catch (IOException e) {
			throw new BusinessException(e);
		}
		return result;
	}

	@Override
	public String getLyricsFromSong(Song song) throws BusinessException {
		String lyrics = "";
		try {
			FileData fileData = FileUtility.readAllRows(songFileName);
			for (String[] columns : fileData.getRows()) {
				/*
				 * 1,Love The Way You Lie,Eminem,RAP,Recovery,Love The Way You Lie
				 * Cover.jpg,Love The Way You Lie.txt
				 */
				if (song.getId() == Integer.parseInt(columns[0])) {
					lyrics = FileUtility.readTextFile(columns[6], 1);
				}
			}
			return lyrics;
		} catch (IOException e) {
			throw new BusinessException("Unable to read the song files");
		}
	}

	@Override
	public Set<Album> findAlbumFromTitle(String title) throws BusinessException {
		Set<Album> result = new HashSet<>();
		if (title != null && title != "") {
			try {
				FileData songsData = FileUtility.readAllRows(songFileName);
				FileData albumsData = FileUtility.readAllRows(albumFileName);

				for (String[] albumsColumns : albumsData.getRows()) {
					if (albumsColumns[1].toLowerCase().contains(title) || albumsColumns[1].equalsIgnoreCase(title)) {
						// I find the title
						for (String[] songsColumns : songsData.getRows()) { // cycle on the song
							if (albumsColumns[1].equals(songsColumns[4])) {
								Song song = new Song();
								song.setId(Integer.parseInt(songsColumns[0]));
								song.setTitle(songsColumns[1]);
								song.setGenre(Genre.valueOf(songsColumns[3]));
								song.setText(getLyricsFromSong(song));
								song.setCover(new Photo(Files
										.readAllBytes(new File(Utility.IMAGE_SONG_PATH + songsColumns[5]).toPath())));
								song.setContent(Files.readAllBytes(
										new File(Utility.MUSIC_REPOSITORY + songsColumns[1] + Utility.MP3_SUFFIX)
												.toPath()));

								Album album = new Album();
								album.setId(Integer.parseInt(albumsColumns[0]));
								album.setTitle(albumsColumns[1]);
								album.setGenre(Genre.valueOf(albumsColumns[2]));
								album.setCover(new Photo(Files
										.readAllBytes(new File(Utility.IMAGE_SONG_PATH + albumsColumns[3]).toPath())));

								song.setAlbum(album);

								for (Artist artist : artistService.getAllArtists()) {
									if (artist.getName().equals(songsColumns[2])) {
										song.setArtist(artist);
										album.setArtist(artist);
									}
								}
								result.add(album);
							}
						}
					}
				}
			} catch (IOException e) {
				throw new BusinessException(e);
			}
		} else {
			return getAllAlbum();
		}

		return result;
	}

	@Override
	public Set<Album> getAlbumFromArtist(Artist artist) throws BusinessException {
		Set<Album> result = new HashSet<>();

		try {
			FileData songsData = FileUtility.readAllRows(songFileName);
			FileData albumsData = FileUtility.readAllRows(albumFileName);

			for (String[] albumsColumns : albumsData.getRows()) {
				for (String[] songsColumns : songsData.getRows()) { // cycle on the song
					if (songsColumns[2].equals(artist.getName()) && songsColumns[4].equals(albumsColumns[1])) {
						Song song = new Song();
						song.setId(Integer.parseInt(songsColumns[0]));
						song.setTitle(songsColumns[1]);
						song.setGenre(Genre.valueOf(songsColumns[3]));
						song.setText(getLyricsFromSong(song));
						song.setCover(new Photo(
								Files.readAllBytes(new File(Utility.IMAGE_SONG_PATH + songsColumns[5]).toPath())));
						song.setContent(Files.readAllBytes(
								new File(Utility.MUSIC_REPOSITORY + songsColumns[1] + Utility.MP3_SUFFIX).toPath()));

						Album album = new Album();
						album.setId(Integer.parseInt(albumsColumns[0]));
						album.setTitle(albumsColumns[1]);
						album.setGenre(Genre.valueOf(albumsColumns[2]));
						album.setCover(new Photo(
								Files.readAllBytes(new File(Utility.IMAGE_SONG_PATH + albumsColumns[3]).toPath())));

						song.setAlbum(album);
						song.setArtist(artist);
						album.setArtist(artist);

						result.add(album);
					}
				}
			}
		} catch (IOException e) {
			throw new BusinessException(e);
		}
		return result;

	}

	@Override
	public Set<Song> getSongsFromArtist(Artist artist) throws BusinessException {

		Set<Song> result = new HashSet<>();

		try {
			FileData songsData = FileUtility.readAllRows(songFileName);
			FileData albumsData = FileUtility.readAllRows(albumFileName);
			for (String[] songsColumns : songsData.getRows()) {
				if (songsColumns[2].equals(artist.getName())) {

					Song song = new Song();
					song.setId(Integer.parseInt(songsColumns[0]));
					song.setTitle(songsColumns[1]);
					song.setGenre(Genre.valueOf(songsColumns[3]));
					song.setText(getLyricsFromSong(song));
					song.setCover(new Photo(
							Files.readAllBytes(new File(Utility.IMAGE_SONG_PATH + songsColumns[5]).toPath())));
					song.setContent(Files.readAllBytes(
							new File(Utility.MUSIC_REPOSITORY + songsColumns[1] + Utility.MP3_SUFFIX).toPath()));

					Album album = new Album();

					// Selecting and creating the album
					for (String[] albumsColumns : albumsData.getRows()) {
						if (songsColumns[4].equals(albumsColumns[1]))
							album.setId(Integer.parseInt(albumsColumns[0]));
						album.setTitle(albumsColumns[1]);
						album.setGenre(Genre.valueOf(albumsColumns[2]));
						album.setCover(new Photo(
								Files.readAllBytes(new File(Utility.IMAGE_SONG_PATH + albumsColumns[3]).toPath())));
						break;
					}

					song.setAlbum(album);
					song.setArtist(artist);
					album.setArtist(artist);

					result.add(song);
				}
			}
		} catch (IOException e) {
			throw new BusinessException(e);
		}

		return result;
	}

	@Override
	public Set<Album> findAlbumFromGenre(String genre) throws BusinessException {

		Set<Album> result = new HashSet<>();

		try {
			FileData songsData = FileUtility.readAllRows(songFileName);
			FileData albumsData = FileUtility.readAllRows(albumFileName);

			for (String[] albumsColumns : albumsData.getRows()) {
				if (albumsColumns[2].equalsIgnoreCase(genre)) { // i find the genre
					for (String[] songsColumns : songsData.getRows()) { // cycle on the song
						if (albumsColumns[1].equals(songsColumns[4])) {
							Song song = new Song();
							song.setId(Integer.parseInt(songsColumns[0]));
							song.setTitle(songsColumns[1]);
							song.setGenre(Genre.valueOf(songsColumns[3]));
							song.setText(getLyricsFromSong(song));
							song.setCover(new Photo(
									Files.readAllBytes(new File(Utility.IMAGE_SONG_PATH + songsColumns[5]).toPath())));
							song.setContent(Files.readAllBytes(
									new File(Utility.MUSIC_REPOSITORY + songsColumns[1] + Utility.MP3_SUFFIX)
											.toPath()));

							Album album = new Album();
							album.setId(Integer.parseInt(albumsColumns[0]));
							album.setTitle(albumsColumns[1]);
							album.setGenre(Genre.valueOf(albumsColumns[2]));
							album.setCover(new Photo(
									Files.readAllBytes(new File(Utility.IMAGE_SONG_PATH + albumsColumns[3]).toPath())));

							song.setAlbum(album);

							for (Artist artist : artistService.getAllArtists()) {
								if (artist.getName().equals(songsColumns[2])
										&& artist.getName().equals(albumsColumns[4])) {
									song.setArtist(artist);
									album.setArtist(artist);
								}
							}
							result.add(album);
						}
					}
				}
			}
		} catch (NullPointerException e) {
			throw new BusinessException(genre + "is not a genre or it doesn't exist!");
		} catch (IOException e) {
			throw new BusinessException(e);
		}

		return result;
	}

	@Override
	public void addAlbum(Album album) throws BusinessException {
		try {
			FileData fileData = FileUtility.readAllRows(albumFileName);
			try (PrintWriter writer = new PrintWriter(new File(albumFileName))) {
				long counter = fileData.getCounter();
				writer.println((counter + 1));
				for (String[] rows : fileData.getRows()) {
					writer.println(String.join(FileUtility.COLUMN_SEPARATOR, rows));
				}
				StringBuilder row = new StringBuilder();
				// 1,Recovery,RAP,Recovery Cover.jpg
				row.append(counter);
				row.append(FileUtility.COLUMN_SEPARATOR);
				row.append(album);
				row.append(FileUtility.COLUMN_SEPARATOR);
				row.append(album.getGenre());
				row.append(FileUtility.COLUMN_SEPARATOR);
				row.append(FileUtility.createImage(album.getCover().getContent(),album));
				row.append(FileUtility.COLUMN_SEPARATOR);
				row.append(album.getArtist());

				writer.println(row.toString());

			}
		} catch (IOException e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public void addSong(Song song) throws BusinessException {
		try {
			FileData fileData = FileUtility.readAllRows(songFileName);
			try (PrintWriter writer = new PrintWriter(new File(songFileName))) {
				long counter = fileData.getCounter();
				writer.println((counter + 1));
				for (String[] rows : fileData.getRows()) {
					writer.println(String.join(FileUtility.COLUMN_SEPARATOR, rows));
				}
				StringBuilder row = new StringBuilder();
				// 1,Love The Way You Lie,Eminem,RAP,Recovery,Love The Way You Lie Cover.jpg
				row.append(counter);
				row.append(FileUtility.COLUMN_SEPARATOR);
				row.append(song);
				row.append(FileUtility.COLUMN_SEPARATOR);
				row.append(song.getArtist());
				row.append(FileUtility.COLUMN_SEPARATOR);
				row.append(song.getGenre().name());
				row.append(FileUtility.COLUMN_SEPARATOR);
				row.append(song.getAlbum());
				row.append(FileUtility.COLUMN_SEPARATOR);
				row.append(FileUtility.createImage(song.getCover().getContent(),song));
				createLyricsFile(song);
				row.append(FileUtility.COLUMN_SEPARATOR);
				row.append(song.getTitle() + Utility.LYRICS_SUFFIX);
				writer.println(row.toString());

				// I have to creat the song file
				FileUtility.createSongFile(song);

			}
		} catch (IOException e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public void modifyTitleOfAlbum(Album album, String title) throws BusinessException {
		try {
			FileData fileData = FileUtility.readAllRows(albumFileName);
			try (PrintWriter writer = new PrintWriter(new File(albumFileName))) {
				writer.println(fileData.getCounter());
				// 1,Love The Way You Lie,Eminem,RAP,Recovery,Love The Way You Lie Cover.jpg
				for (String[] rows : fileData.getRows()) {
					if (Integer.parseInt(rows[0]) == album.getId()) {
						rows[1] = title;
					}
					writer.println(String.join(FileUtility.COLUMN_SEPARATOR, rows));
				}

			}
		} catch (IOException e) {
			throw new BusinessException(e);
		}

	}

	@Override
	public void modifyGenreOfAlbum(Album album, Genre genre) throws BusinessException {
		try {
			FileData fileData = FileUtility.readAllRows(albumFileName);
			try (PrintWriter writer = new PrintWriter(new File(albumFileName))) {
				writer.println(fileData.getCounter());
				for (String[] rows : fileData.getRows()) {
					if (Integer.parseInt(rows[0]) == album.getId()) {

						rows[2] = genre.name();
					}
					writer.println(String.join(FileUtility.COLUMN_SEPARATOR, rows));
				}

			}
		} catch (IOException e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public void deleteAlbum(Album album) throws BusinessException {
		try {
			FileData fileData = FileUtility.readAllRows(albumFileName);
			try (PrintWriter writer = new PrintWriter(new File(albumFileName))) {
				writer.println(fileData.getCounter());
				for (String[] rows : fileData.getRows()) {
					if (album.getId() != Integer.parseInt(rows[0])) {
						writer.println(String.join(FileUtility.COLUMN_SEPARATOR, rows));
					}
				}
			}
		} catch (IOException e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public void modifyTitleOfSong(Song song, String title) throws BusinessException {
		try {
			FileData fileData = FileUtility.readAllRows(songFileName);
			try (PrintWriter writer = new PrintWriter(new File(songFileName))) {
				writer.println(fileData.getCounter());
				for (String[] rows : fileData.getRows()) {
					if (Integer.parseInt(rows[0]) == song.getId()) {

						rows[1] = title;

						// To change the lyris file name
						File lyricsFile = new File(Utility.LYRICS_PATH + song.getTitle() + Utility.LYRICS_SUFFIX);
						File lyricsFile2 = new File(Utility.LYRICS_PATH + title + Utility.LYRICS_SUFFIX);
						lyricsFile.renameTo(lyricsFile2);

						// To change the song file name
						File songFile = new File(Utility.MUSIC_REPOSITORY + song.getTitle() + Utility.MP3_SUFFIX);
						File songFile2 = new File(Utility.MUSIC_REPOSITORY + title + Utility.MP3_SUFFIX);
						songFile.renameTo(songFile2);

					}
					writer.println(String.join(FileUtility.COLUMN_SEPARATOR, rows));
				}

			}
		} catch (IOException e) {
			throw new BusinessException(e);
		}

	}

	@Override
	public void modifyGenreOfSong(Song song, Genre genre) throws BusinessException {
		try {
			FileData fileData = FileUtility.readAllRows(songFileName);
			try (PrintWriter writer = new PrintWriter(new File(songFileName))) {
				writer.println(fileData.getCounter());
				for (String[] rows : fileData.getRows()) {
					if (Integer.parseInt(rows[0]) == song.getId()) {

						rows[3] = genre.name();
					}
					writer.println(String.join(FileUtility.COLUMN_SEPARATOR, rows));
				}

			}
		} catch (IOException e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public void deleteSong(Song song) throws BusinessException {
		try {
			deleteAlbum(song.getAlbum());
			FileData fileData = FileUtility.readAllRows(songFileName);
			try (PrintWriter writer = new PrintWriter(new File(songFileName))) {
				writer.println(fileData.getCounter());
				for (String[] rows : fileData.getRows()) {
					if (song.getId() != Integer.parseInt(rows[0])) {
						writer.println(String.join(FileUtility.COLUMN_SEPARATOR, rows));
					}
				}
			}
		} catch (IOException | BusinessException e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public void deleteDiscographyFromArtist(Artist artist) throws BusinessException {
		for (Song song : getAllSongs()) {
			if (song.getArtist().getId() == artist.getId()) {
				deleteSong(song);
			}
		}
	}

	@Override
	public Set<Song> getSongsFromAlbum(Album album) throws BusinessException {

		Set<Song> result = new HashSet<>();
		try {
			FileData songsData = FileUtility.readAllRows(songFileName);
			for (String[] songsColumns : songsData.getRows()) {
				if (album.getTitle().equals(songsColumns[4])) {
					Song song = new Song();
					song.setId(Integer.parseInt(songsColumns[0]));
					song.setTitle(songsColumns[1]);
					song.setGenre(Genre.valueOf(songsColumns[3]));
					song.setText(getLyricsFromSong(song));
					song.setContent(Files.readAllBytes(
							new File(Utility.MUSIC_REPOSITORY + songsColumns[1] + Utility.MP3_SUFFIX).toPath()));
					song.setCover(new Photo(
							Files.readAllBytes(new File(Utility.IMAGE_SONG_PATH + songsColumns[5]).toPath())));
					song.setAlbum(album);

					for (Artist artist : artistService.getAllArtists()) {
						if (artist.getName().equals(songsColumns[2])) {
							song.setArtist(artist);
						}
					}
					result.add(song);
				}
			}
		} catch (IOException e) {
			throw new BusinessException(e);
		}
		return result;
	}

	private void createLyricsFile(Song song) throws BusinessException {
		try {
			File lyricsFile = new File(Utility.LYRICS_PATH + song.getTitle() + Utility.LYRICS_SUFFIX);
			if (lyricsFile.createNewFile()) {
				FileWriter myWriter = new FileWriter(lyricsFile);
				myWriter.write(song.getText());
				myWriter.close();
			} else
				throw new BusinessException("Unable to create the lyrics file!");
		} catch (IOException e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public Song getSongById(int id) throws BusinessException {

		Song song = null;

		try {
			FileData songsData = FileUtility.readAllRows(songFileName);
			FileData albumsData = FileUtility.readAllRows(albumFileName);
			for (String[] songsColumns : songsData.getRows()) {
				if (Integer.parseInt(songsColumns[0]) == id) {
					song = new Song();
					song.setId(Integer.parseInt(songsColumns[0]));
					song.setTitle(songsColumns[1]);
					song.setGenre(Genre.valueOf(songsColumns[3]));
					song.setText(getLyricsFromSong(song));
					song.setContent(Files.readAllBytes(
							new File(Utility.MUSIC_REPOSITORY + songsColumns[1] + Utility.MP3_SUFFIX).toPath()));
					song.setCover(new Photo(
							Files.readAllBytes(new File(Utility.IMAGE_SONG_PATH + songsColumns[5]).toPath())));
					Album album = new Album();

					// Selecting and creating the album
					for (String[] albumsColumns : albumsData.getRows()) {
						if (songsColumns[4].equals(albumsColumns[1])) {
							album.setId(Integer.parseInt(albumsColumns[0]));
							album.setTitle(albumsColumns[1]);
							album.setGenre(Genre.valueOf(albumsColumns[2]));
							album.setCover(new Photo(
									Files.readAllBytes(new File(Utility.IMAGE_SONG_PATH + albumsColumns[3]).toPath())));
						}
					}

					song.setAlbum(album);

					for (Artist artist : artistService.getAllArtists()) {
						if (artist.getName().equals(songsColumns[2])) {
							song.setArtist(artist);
							album.setArtist(artist);
						}
					}
				}

			}
		} catch (IOException e) {
			throw new BusinessException(e);
		}
		return song;
	}
}
