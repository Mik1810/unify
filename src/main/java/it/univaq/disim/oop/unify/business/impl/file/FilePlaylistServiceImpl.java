package it.univaq.disim.oop.unify.business.impl.file;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

import it.univaq.disim.oop.unify.business.PlaylistService;
import it.univaq.disim.oop.unify.business.SongService;
import it.univaq.disim.oop.unify.business.exceptions.BusinessException;
import it.univaq.disim.oop.unify.domain.Playlist;
import it.univaq.disim.oop.unify.domain.Song;
import it.univaq.disim.oop.unify.domain.User;

public class FilePlaylistServiceImpl implements PlaylistService {

	private String playlistFileName;
	private SongService songService;

	public FilePlaylistServiceImpl(String playlistFileName, SongService songService) {

		this.playlistFileName = playlistFileName;
		this.songService = songService;
	}

	@Override
	public Set<Playlist> getPlaylists(User user) throws BusinessException {

		Set<Playlist> result = new HashSet<>();

		// 1,1,pippo,7,6,4,9,@
		try {
			FileData fileData = FileUtility.readAllRows(playlistFileName);
			for (String[] columns : fileData.getRows()) {
				if (Integer.parseInt(columns[1]) == user.getId()) {
					Playlist playlist = new Playlist();
					playlist.setId(Integer.parseInt(columns[0]));
					playlist.setName(columns[2]);

					for (int i = 3; !(FileUtility.END_ROW.equals(columns[i])); i++) {
						playlist.getSongs().add(songService.getSongById(Integer.parseInt(columns[i])));

					}
					result.add(playlist);
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}

	@Override
	public void changePlaylistName(Playlist playlist, String name) throws BusinessException {

		try {
			FileData fileData = FileUtility.readAllRows(playlistFileName);
			try (PrintWriter writer = new PrintWriter(new File(playlistFileName))) {
				writer.println(fileData.getCounter());
				// 1,1,pippo,7,6,4,9,@
				for (String[] rows : fileData.getRows()) {
					if (rows[2].equals(playlist.getName()))
						rows[2] = name;
					writer.println(String.join(FileUtility.COLUMN_SEPARATOR, rows));
				}
			}
		} catch (IOException e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public void addSong(Playlist playlist, Song song) throws BusinessException {

		try {
			FileData fileData = FileUtility.readAllRows(playlistFileName);
			try (PrintWriter writer = new PrintWriter(new File(playlistFileName))) {
				writer.println(fileData.getCounter());
				for (String[] rows : fileData.getRows()) {
					if (Integer.parseInt(rows[0]) == playlist.getId()) {
						StringBuilder line = new StringBuilder();
						for (int i = 0; i < rows.length - 1; i++) {
							line.append(rows[i]);
							line.append(FileUtility.COLUMN_SEPARATOR);
						}
						line.append(song.getId());
						line.append(FileUtility.COLUMN_SEPARATOR);
						line.append(FileUtility.END_ROW);
						writer.println(line.toString());
					} else {
						writer.println(String.join(FileUtility.COLUMN_SEPARATOR, rows));
					}
				}
			}
		} catch (IOException e) {
			throw new BusinessException(e);
		}

	}

	@Override
	public void removeSong(Playlist playlist, Song song) throws BusinessException {

		try {
			FileData fileData = FileUtility.readAllRows(playlistFileName);
			try (PrintWriter writer = new PrintWriter(new File(playlistFileName))) {
				writer.println(fileData.getCounter());
				for (String[] rows : fileData.getRows()) {
					if (Integer.parseInt(rows[0]) == playlist.getId()) {
						StringBuilder line = new StringBuilder();
						line.append(rows[0]);
						line.append(FileUtility.COLUMN_SEPARATOR);
						line.append(rows[1]);
						line.append(FileUtility.COLUMN_SEPARATOR);
						line.append(rows[2]);
						line.append(FileUtility.COLUMN_SEPARATOR);
						for (int i = 3; i < rows.length - 1; i++) {
							if (!rows[i].equals(song.getId().toString())) {
								line.append(rows[i]);
								line.append(FileUtility.COLUMN_SEPARATOR);
							}
						}
						line.append(FileUtility.END_ROW);
						writer.println(line.toString());
					} else {
						writer.println(String.join(FileUtility.COLUMN_SEPARATOR, rows));
					}
				}
			}
		} catch (IOException e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public void deletePlaylist(Playlist playlist, User user) throws BusinessException {

		try {
			FileData fileData = FileUtility.readAllRows(playlistFileName);
			try (PrintWriter writer = new PrintWriter(new File(playlistFileName))) {
				writer.println(fileData.getCounter());
				for (String[] rows : fileData.getRows()) {
					if (playlist.getId() != Integer.parseInt(rows[0])) {
						writer.println(String.join(FileUtility.COLUMN_SEPARATOR, rows));
					}
				}
			}
		} catch (IOException e) {
			throw new BusinessException(e);
		}

	}

	@Override
	public void createPlaylist(Playlist playlist, User user) throws BusinessException {
		
		try {
			FileData fileData = FileUtility.readAllRows(playlistFileName);
			try (PrintWriter writer = new PrintWriter(new File(playlistFileName))) {
				long counter = fileData.getCounter();
				writer.println((counter + 1));
				for (String[] rows : fileData.getRows()) {
					writer.println(String.join(FileUtility.COLUMN_SEPARATOR, rows));
				}
				playlist.setId((int)counter);
				
				StringBuilder row = new StringBuilder();
				// 1,1,pippo,7,6,4,9,@
				row.append((int)counter);
				row.append(FileUtility.COLUMN_SEPARATOR);
				row.append(user.getId());
				row.append(FileUtility.COLUMN_SEPARATOR);
				row.append(playlist.getName());
				row.append(FileUtility.COLUMN_SEPARATOR);
				row.append(FileUtility.END_ROW);
				writer.println(row.toString());
			}
		} catch (IOException e) {
			 throw new BusinessException(e);
		}
	}

	@Override
	public Playlist getPlaylistByID(int id, User user) throws BusinessException {
		
		Playlist playlist = null; 
		
		try {
			FileData fileData = FileUtility.readAllRows(playlistFileName);
			for (String[] columns : fileData.getRows()) {
				if (Integer.parseInt(columns[0]) == id) {
					playlist = new Playlist();
					playlist.setId(Integer.parseInt(columns[0]));
					playlist.setName(columns[2]);

					for (int i = 3; !(FileUtility.END_ROW.equals(columns[i])); i++) {
						playlist.getSongs().add(songService.getSongById(Integer.parseInt(columns[i])));

					}
				}

			}
		} catch (IOException e) {
			throw new BusinessException("Error reading the playlist's data!");
		}
		
		if(playlist == null) throw new BusinessException("The playlist doesn't exist");
		return playlist;
	}
}
