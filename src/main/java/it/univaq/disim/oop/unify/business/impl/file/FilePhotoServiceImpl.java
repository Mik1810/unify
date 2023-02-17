package it.univaq.disim.oop.unify.business.impl.file;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Set;

import it.univaq.disim.oop.unify.Utility;
import it.univaq.disim.oop.unify.business.PhotoService;
import it.univaq.disim.oop.unify.business.exceptions.BusinessException;
import it.univaq.disim.oop.unify.domain.Album;
import it.univaq.disim.oop.unify.domain.Artist;
import it.univaq.disim.oop.unify.domain.Photo;
import it.univaq.disim.oop.unify.domain.Song;

public class FilePhotoServiceImpl implements PhotoService {

	private String artistFileName;
	private String songFileName;
	private String albumFileName;

	public FilePhotoServiceImpl(String artistFileName, String songFileName, String albumFileName) {
		this.artistFileName = artistFileName;
		this.songFileName = songFileName;
		this.albumFileName = albumFileName;
	}

	@Override
	public ByteArrayInputStream toStream(byte[] content) {
		return new ByteArrayInputStream(content);
	}

	@Override
	public Set<Photo> getPhotosFromArtist(Artist artist) throws BusinessException {
		Set<Photo> photos = new HashSet<Photo>();
		try {
			FileData fileData = FileUtility.readAllRows(artistFileName);
			for (String[] columns : fileData.getRows()) {

				// 1,Eminem,Eminem-1.jpg,Eminem-2.jpg,EminemBio.txt,INDIVIDUAL
				if (artist.getId() == Integer.parseInt(columns[0])) {
					photos.add(
							new Photo(Files.readAllBytes(new File(Utility.IMAGE_ARTIST_PATH + columns[2]).toPath())));
					photos.add(
							new Photo(Files.readAllBytes(new File(Utility.IMAGE_ARTIST_PATH + columns[3]).toPath())));
				}
			}
			return photos;
		} catch (IOException e) {
			throw new BusinessException("Unable to display the photos from an artist");
		}
	}

	@Override
	public void setPhoto1(Photo photo, Artist artist) throws BusinessException {

		try {
			FileData fileData = FileUtility.readAllRows(artistFileName);
			try (PrintWriter writer = new PrintWriter(new File(artistFileName))) {
				writer.println(fileData.getCounter());
				for (String[] rows : fileData.getRows()) {
					if (artist.getId() == Integer.parseInt(rows[0])) {

						// I have to delete the old image
						FileUtility.deleteImage(Utility.IMAGE_ARTIST_PATH + rows[2]);
						// Now the old image has been deleted

						// // Creating the image
						rows[2] = FileUtility.createImage(photo.getContent(),artist);
						// Now the image is physically created, i have to take his path and write it in the file
					}
					writer.println(String.join(FileUtility.COLUMN_SEPARATOR, rows));
				}
			}
		} catch (IOException e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public void setPhoto2(Photo photo, Artist artist) throws BusinessException {

		try {
			FileData fileData = FileUtility.readAllRows(artistFileName);
			try (PrintWriter writer = new PrintWriter(new File(artistFileName))) {
				writer.println(fileData.getCounter());
				for (String[] rows : fileData.getRows()) {
					if (artist.getId() == Integer.parseInt(rows[0])) {

						// I have to delete the old image
						FileUtility.deleteImage(Utility.IMAGE_ARTIST_PATH + rows[3]);
						// Now the old image has been deleted

						// Creating the image
						rows[3] = FileUtility.createImage(photo.getContent(), artist);
						//Now the image is physically created, i have to take his path and write it in the file
					}
					writer.println(String.join(FileUtility.COLUMN_SEPARATOR, rows));
				}
			}
		} catch (IOException e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public void modifyImageOfAlbum(Album album, Photo photo) throws BusinessException {
		try {
			//1,Recovery,RAP,Recovery Cover.jpg,Eminem
			FileData fileData = FileUtility.readAllRows(albumFileName);
			try (PrintWriter writer = new PrintWriter(new File(albumFileName))) {
				writer.println(fileData.getCounter());
				for (String[] rows : fileData.getRows()) {
					if (Integer.parseInt(rows[0]) == album.getId()) {

						// I have to delete the old image
						FileUtility.deleteImage(Utility.IMAGE_SONG_PATH + rows[3]);
						// Now the old image has been deleted

						rows[3] = FileUtility.createImage(photo.getContent(),album);

					}
					writer.println(String.join(FileUtility.COLUMN_SEPARATOR, rows));
				}
			} 
		} catch (IOException e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public void modifyImageOfSong(Song song, Photo photo) throws BusinessException {
		try {
			FileData fileData = FileUtility.readAllRows(songFileName);
			try (PrintWriter writer = new PrintWriter(new File(songFileName))) {
				writer.println(fileData.getCounter());
				for (String[] rows : fileData.getRows()) {
					if (Integer.parseInt(rows[0]) == song.getId()) {

						FileUtility.deleteImage(Utility.IMAGE_SONG_PATH + rows[5]);
						rows[5] = FileUtility.createImage(photo.getContent(), song);

					}
					writer.println(String.join(FileUtility.COLUMN_SEPARATOR, rows));
				}
			}
		} catch (IOException e) {
			throw new BusinessException(e);
		}
	}
}
